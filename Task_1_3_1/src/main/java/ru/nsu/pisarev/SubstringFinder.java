package ru.nsu.pisarev;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;



public final class SubstringFinder {
    static final int BUF_CHARS = 8192;

    public static List<Long> find(String filename, String substring) throws IOException {
        if (substring == null || substring.isEmpty()) {
            throw new IllegalArgumentException("substring must be non-null and non-empty");
        }
        char[] substringCharArray = substring.toCharArray();
        int strLength = substringCharArray.length;
        int[] lps = buildLps(substringCharArray);

        List<Long> result = new ArrayList<>();
        char[] buf = new char[BUF_CHARS];

        try (InputStreamReader reader = new InputStreamReader(
                new FileInputStream(filename), StandardCharsets.UTF_8)) {

            long totalProcessedBeforeChunk = 0L;
            int matched = 0;
            int read;
            while ((read = reader.read(buf, 0, buf.length)) != -1) {
                for (int i = 0; i < read; i++) {
                    char c = buf[i];
                    while (matched > 0 && substringCharArray[matched] != c) {
                        matched = lps[matched - 1];
                    }
                    if (substringCharArray[matched] == c) {
                        matched++;
                    }
                    if (matched == strLength) {
                        long startIndex = totalProcessedBeforeChunk + i - strLength + 1;
                        result.add(startIndex);
                        matched = lps[matched - 1];
                    }
                }
                totalProcessedBeforeChunk += read;
            }
        }

        return result;
    }


    private static int[] buildLps(char[] substring) {
        int strLength = substring.length;
        int[] lps = new int[strLength];
        int len = 0;
        for (int i = 1; i < strLength; i++) {
            while (len > 0 && substring[len] != substring[i]) {
                len = lps[len - 1];
            }
            if (substring[len] == substring[i]) {
                len++;
            }
            lps[i] = len;
        }
        return lps;
    }

    private SubstringFinder() {
        throw new UnsupportedOperationException();
    }
}


