package ru.nsu.pisarev;


import ru.nsu.pisarev.model.TaskResult;
import ru.nsu.pisarev.config.ConfigParser;
import ru.nsu.pisarev.model.Group;
import ru.nsu.pisarev.model.Task;
import ru.nsu.pisarev.model.StudentResult;
import ru.nsu.pisarev.processor.RepoProcessor;
import ru.nsu.pisarev.report.HtmlReporter;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path workspace=null;
        RepoProcessor processor=null;
        try {
            if (args.length < 1) {
                System.err.println("Config path");
                return;
            }

            Path configPath = Path.of(args[0]);
            if (!Files.exists(configPath)) {
                throw new IllegalArgumentException("Config doesn't exist: " + configPath);
            }

            ConfigParser parser = new ConfigParser();
            parser.parse(configPath);

            workspace = Path.of("checker_workspace");
            Files.createDirectories(workspace);
            processor = new RepoProcessor(parser.getSettings(), workspace);
            List<StudentResult> allResults = new ArrayList<>();

            for (var check : parser.getChecks()) {
                Optional<Group> groupOpt = parser.getGroups().stream()
                        .filter(g -> g.name().equals(check.groupName())).findFirst();
                if (groupOpt.isEmpty()) {
                    continue;
                }

                for (var student : groupOpt.get().students()) {
                    if (!check.studentNicks().contains(student.nick())) {
                        continue;
                    }

                    List<TaskResult> taskResults = new ArrayList<>();
                    for (var taskId : check.taskIds()) {
                        Optional<Task> taskOpt = parser.getTasks().stream()
                                .filter(t -> t.id().equals(taskId)).findFirst();
                        if (taskOpt.isPresent()) {
                            System.out.println("Checking: " + student.nick() + " -> " + taskId);
                            taskResults.add(processor.processRepo(student, taskOpt.get()));
                        }
                    }
                    allResults.add(new StudentResult(student, taskResults));
                }
            }

            HtmlReporter.printReport(allResults, parser.getCheckpoints(), parser.getSettings());
            System.out.println("Completed.");

        } catch (Exception e) {
            System.err.println("Critical exception in:");
            if (workspace != null && Files.exists(workspace)) {
                try (var stream = Files.walk(workspace)) {
                    stream.sorted(Comparator.reverseOrder())
                            .forEach(p -> {
                                try {
                                    Files.delete(p);
                                } catch (IOException ex) {
                                    System.err.println("Can't delete: " + p);
                                }
                            });
                }
            }
        }
    }
}