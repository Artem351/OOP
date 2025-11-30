package ru.nsu.pisarev;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;



public class SubstringTest {

    @Test
    public void exampleTest(@TempDir File tempDir) throws IOException {
        File f = new File(tempDir, "input.txt");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, StandardCharsets.UTF_8))) {
            bw.write("абракадабра");
        }

        List<Long> res = SubstringFinder.find(f.getAbsolutePath(), "бра");
        List<Long> expected = Arrays.asList(1L, 8L);
        assertEquals(expected, res);
    }

    @Test
    public void substringBufferBoundaryTest(@TempDir File tempDir) throws IOException {
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

        List<Long> res = SubstringFinder.find(f.getAbsolutePath(), substring);
        long expectedIdx = (long) (chunk.length() * 1000 + "tailA".length());
        assertEquals(1, res.size(), "Should find the single inserted substring");
        assertEquals(expectedIdx, res.get(0).longValue());
    }

    @Test
    public void largeGeneratedFileTest(@TempDir File tempDir) throws IOException {
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

        List<Long> res = SubstringFinder.find(f.getAbsolutePath(), substring);
        assertFalse(res.isEmpty());
        byte[] fileBytes = Files.readAllBytes(f.toPath());
        String fileContent = new String(fileBytes, StandardCharsets.UTF_8);
        for (Long idx : res) {
            long start = idx;
            assertTrue(start >= 0 && start + substring.length() <= fileContent.length());
            assertEquals(substring, fileContent.substring((int) start, (int) start + substring.length()));
        }
    }

    @Test
    public void emptySubstringShouldThrow() {
        assertThrows(IllegalArgumentException.class, () -> SubstringFinder.find("whatever", ""));
    }
}

