package ru.nsu.pisarev;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class SubstringFinderTest {

    @TempDir static File tempDir;

    @Test
    public void exampleTest() throws IOException {
        File f = new File(tempDir, "input.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, StandardCharsets.UTF_8))) {
            bw.write("абракадабра");
        }

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(f), StandardCharsets.UTF_8)) {

            List<Long> res = SubstringFinder.find(reader, "бра");
            List<Long> expected = Arrays.asList(1L, 8L);
            assertEquals(expected, res);
        }
    }

    @Test
    public void substringBufferBoundaryTest() throws IOException {
        File f = new File(tempDir, "boundary.txt");
        String chunk = "xxxx";
        String substring = "abcdXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            sb.append(chunk);
        }
        sb.append("tailA");
        sb.append(substring);
        sb.append("tailB");
        Files.writeString(f.toPath(), sb.toString(), StandardCharsets.UTF_8);

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(f), StandardCharsets.UTF_8)) {
            List<Long> res = SubstringFinder.find(reader, substring);

            long expectedIdx = (chunk.length() * 1000 + "tailA".length());
            assertEquals(1, res.size(), "Should find the single inserted substring");
            assertEquals(expectedIdx, res.get(0).longValue());
        }
    }

    @Test
    public void largeGeneratedFileTest() throws IOException {
        File f = new File(tempDir, "large.txt");
        String substring = "key";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, StandardCharsets.UTF_8))) {

            for (int i = 0; i < 100_000; i++) {
                if (i % 10000 == 1234) {
                    bw.write("prefix-" + substring + "-suffix\n");
                } else {
                    bw.write("random-line-" + i + "\n");
                }
            }
        }
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(f), StandardCharsets.UTF_8)) {

            List<Long> res = SubstringFinder.find(reader, substring);

            assertFalse(res.isEmpty());
            byte[] fileBytes = Files.readAllBytes(f.toPath());
            String fileContent = new String(fileBytes, StandardCharsets.UTF_8);
            for (Long idx : res) {
                long start = idx;
                assertTrue(start >= 0 && start + substring.length() <= fileContent.length());
                assertEquals(substring, fileContent.substring((int) start, (int) start + substring.length()));
            }
        }
    }

    @Test
    public void emptySubstringShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.find(null, ""));
    }

    @Test
    void unicodeChars() throws IOException {
        String s = "\uD83C\uDF27\uD83C\uDF27\uD83D\uDE0A";
        System.out.println(s);
        File f = new File(tempDir, "unicode.txt");
        Files.writeString(f.toPath(), s, StandardCharsets.UTF_8);

        String pattern = "\uD83C\uDF27";
        System.out.println(pattern);
        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(f), StandardCharsets.UTF_8)) {
            List<Long> positions = SubstringFinder.findUtf8(reader, pattern);
            assertEquals(List.of(0L,1L), positions);
        }
    }

    @ParameterizedTest
    @ValueSource( strings = {
            "aa", "ab", "aab", "ababa"})
    void largeRepeatTest(String pattern) throws IOException {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 1_000_000; i++) {
            if (Math.random() > 0.5) {
                sb.append("a");
            } else {
                sb.append("b");
            }
        }
        String largeFileString = sb.toString();
        File largeFile = new File(tempDir, pattern + "boundary.txt");
        Files.writeString(largeFile.toPath(), sb.toString(), StandardCharsets.UTF_8);

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(largeFile), StandardCharsets.UTF_8)) {
            List<Long> positions = SubstringFinder.find(reader, pattern);
            Matcher matcher = Pattern.compile(pattern).matcher(largeFileString);
            List<Long> matcherPositions = new ArrayList<>();

            int startPos = -1;
            while (matcher.find(startPos+1)) {
                startPos = matcher.start();
                matcherPositions.add((long) startPos);
            }

            assertEquals(matcherPositions.size(), positions.size());
            assertEquals(new HashSet<>(matcherPositions), new HashSet<>(positions));
        }
    }

}

