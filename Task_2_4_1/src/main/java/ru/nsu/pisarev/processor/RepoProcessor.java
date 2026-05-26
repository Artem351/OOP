package ru.nsu.pisarev.processor;


import ru.nsu.pisarev.model.Settings;
import ru.nsu.pisarev.model.Student;
import ru.nsu.pisarev.model.Task;
import ru.nsu.pisarev.model.TaskResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record RepoProcessor(Settings settings, Path workspace) {

    public TaskResult processRepo(Student student, Task taskDef) throws Exception {
        Path dir = workspace.resolve(student.nick() + "_" + taskDef.id());
        if (Files.exists(dir)) {
            deleteDir(dir);
            Thread.sleep(200);
        }
        Files.createDirectories(dir);
        Thread.sleep(200);
        boolean cloned = runProcess(workspace, "git", "clone", "--branch", "main", "--single-branch", "--depth", "1",
                student.repoUrl(), dir.toString());
        if (!cloned) {
            System.err.println("Cloning from master...");
            cloned = runProcess(workspace, "git", "clone", "--branch", "master", "--single-branch", "--depth", "1",
                    student.repoUrl(), dir.toString());
        }
        if (!cloned) {
            System.err.println("1111111111111111111111111");
            return new TaskResult(taskDef, false, false, false, 0, 0, 0, 0, 0, "FAIL_CLONE");
        }


        boolean compiled = runMavenGoal(dir, "compile");
        if (!compiled) {
            System.out.println("222222222222222");
            return new TaskResult(taskDef, false, false, false, 0, 0, 0, 0, 0, "FAIL_COMPILE");
        }

        boolean styleOk = runGradleTask(dir, "checkstyleMain");
        boolean docsOk = runGradleTask(dir, "javadoc");
        if ((!styleOk || !docsOk) && "strict".equals(settings.strategy())) {
            return new TaskResult(taskDef, true, styleOk, docsOk, 0, 0, 0, 0, 0, "FAIL_STYLE_DOCS");
        }


        System.out.println("Testing...");
        boolean testsOk = runGradleTask(dir, "test");

        int[] testStats = parseGradleTestReports(dir);
        int total = testStats[0], passed = testStats[1], failed = testStats[2], skipped = testStats[3];
        if (failed > 0 && "strict".equals(settings.strategy())) {
            return new TaskResult(taskDef, true, styleOk, docsOk, total, passed, failed, skipped, 0, "FAIL_TESTS");
        }

        double basePoints = taskDef.maxPoints() * ((double) passed / Math.max(1, total));
        if (!styleOk) {
            basePoints *= 0.8;
        }
        if (!docsOk) {
            basePoints *= 0.9;
        }

        LocalDate lastCommit = getLastCommitDate(dir);
        String deadlineStatus;
        if (lastCommit.isAfter(taskDef.hardDeadline())) {
            basePoints *= 0.5;
            deadlineStatus = "HARD_LATE";
        } else if (lastCommit.isAfter(taskDef.softDeadline())) {
            basePoints *= 0.7;
            deadlineStatus = "SOFT_LATE";
        } else {
            deadlineStatus = "ON_TIME";
        }

        return new TaskResult(taskDef, true, styleOk, docsOk, total, passed, failed, skipped, Math.round(basePoints), deadlineStatus);
    }


    private boolean runProcess(Path dir, String... cmd) {
        try {
            ProcessBuilder pb = new ProcessBuilder(cmd)
                    .directory(dir.toFile())
                    .redirectErrorStream(true);
            Process p = pb.start();
            StringBuilder output = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    output.append(line).append(System.lineSeparator());
                }
            }

            boolean success = p.waitFor(settings.timeoutSec(), TimeUnit.SECONDS) && p.exitValue() == 0;
            if (!success) {
                System.err.println("Error: " + String.join(" ", cmd));
                System.err.println("Output: " + output.toString().trim());
            }
            return success;
        } catch (Exception e) {
            System.err.println("Exception in: " + String.join(" ", cmd));
            e.printStackTrace();
            return false;
        }
    }
    private int extractMatch(String content, Pattern pattern) {
        Matcher m = pattern.matcher(content);
        return m.find() ? Integer.parseInt(m.group(1)) : 0;
    }
    private int[] parseSurefireReports(Path dir) throws Exception {
        Path reportsDir = dir.resolve("target/surefire-reports");
        if (!Files.exists(reportsDir)) {
            return new int[]{0, 0, 0, 0};
        }
        int t = 0, f = 0, s = 0;
        try (var stream = Files.walk(reportsDir).filter(p -> p.toString().endsWith(".txt"))) {
            for (Path report : stream.toList()) {
                List<String> lines = Files.readAllLines(report);
                if (lines.isEmpty()) {
                    continue;
                }
                for (String part : lines.get(0).split(",")) {
                    part = part.trim().toLowerCase();
                    if (part.contains("tests run")) {
                        t += extractNumber(part);
                    }
                    if (part.contains("failures") || part.contains("errors")) {
                        f += extractNumber(part);
                    }
                    if (part.contains("skipped")) {
                        s += extractNumber(part);
                    }
                }
            }
        }
        return new int[]{t, Math.max(0, t - f - s), f, s};
    }


    private boolean runGradleTask(Path dir, String task) {
        String[] cmd = task.equals("test")
                ? new String[]{"gradle", "test", "--quiet"}
                : new String[]{"gradle", task, "--quiet", "-x", "test"};
        return runProcess(dir, cmd);
    }
    private int[] parseGradleTestReports(Path dir) throws Exception {
        Path resultsDir = dir.resolve("build/test-results/test");
        if (!Files.exists(resultsDir)) return new int[]{0, 0, 0, 0};

        int total = 0, failed = 0, skipped = 0;
        Pattern testsPattern = Pattern.compile("tests=\"(\\d+)\"");
        Pattern failuresPattern = Pattern.compile("failures=\"(\\d+)\"");
        Pattern errorsPattern = Pattern.compile("errors=\"(\\d+)\"");
        Pattern skippedPattern = Pattern.compile("skipped=\"(\\d+)\"");

        try (var stream = Files.walk(resultsDir).filter(p -> p.toString().endsWith(".xml"))) {
            for (Path xml : stream.toList()) {
                String content = Files.readString(xml);
                total += extractMatch(content, testsPattern);
                failed += extractMatch(content, failuresPattern);
                failed += extractMatch(content, errorsPattern);
                skipped += extractMatch(content, skippedPattern);
            }
        }
        int passed = Math.max(0, total - failed - skipped);
        return new int[]{total, passed, failed, skipped};
    }
    private int extractNumber(String s) {
        return Integer.parseInt(s.replaceAll("[^0-9]", ""));
    }

    private LocalDate getLastCommitDate(Path dir) throws Exception {
        ProcessBuilder pb = new ProcessBuilder("git", "log", "-1", "--format=%ct", "HEAD").directory(dir.toFile());
        Process p = pb.start();
        String ts = new BufferedReader(new InputStreamReader(p.getInputStream())).readLine();
        return LocalDate.ofEpochDay(Long.parseLong(ts) / 86400);
    }


    private boolean runMavenGoal(Path dir, String goal) {
        return runProcess(dir, "mvn", goal, "-q", "-DskipTests=true");
    }
    public void deleteDir(Path path) throws IOException {
        if (!Files.exists(path)) return;
        try (var stream = Files.walk(path)) {
            stream.sorted(Comparator.reverseOrder()).forEach(p -> {
                try {
                    Files.delete(p);
                } catch (IOException e) {
                    System.err.println("Can't delete: " + p);
                }
            });
        }
    }
}