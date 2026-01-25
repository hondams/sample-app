package com.github.hondams.fw.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Locale;
import org.junit.jupiter.api.Test;

public class ReloadableMultiLocaleResourceBundleTest {

    @Test
    public void loadFromFileSystemUtf8() throws Exception {
        Path dir = Files.createTempDirectory("rbtest_utf8");
        try {
            Path p = dir.resolve("messages.properties");
            Files.writeString(p, "greeting=こんにちは", StandardCharsets.UTF_8);

            ReloadableMultiLocaleResourceBundle r = new ReloadableMultiLocaleResourceBundle(dir,
                    new String[] {"messages"}, Locale.JAPAN);

            System.out.println("keySet=" + r.keySet());

            assertEquals("こんにちは", r.getString("greeting"));
        } finally {
            deleteRecursively(dir);
        }
    }

    @Test
    public void clearCacheReflectsFileChange() throws Exception {
        Path dir = Files.createTempDirectory("rbtest_clear");
        try {
            Path p = dir.resolve("messages.properties");
            Files.writeString(p, "k=old", StandardCharsets.UTF_8);

            ReloadableMultiLocaleResourceBundle r = new ReloadableMultiLocaleResourceBundle(dir,
                    new String[] {"messages"}, Locale.ENGLISH);

            assertEquals("old", r.getString("k"));

            // overwrite file
            Files.writeString(p, "k=new", StandardCharsets.UTF_8);

            // clear cache and re-read
            r.clearCache();

            assertEquals("new", r.getString("k"));
        } finally {
            deleteRecursively(dir);
        }
    }

    private static void deleteRecursively(Path p) throws IOException {
        if (Files.notExists(p)) {
            return;
        }
        Files.walk(p).sorted(Comparator.reverseOrder()).forEach(fp -> {
            try {
                Files.deleteIfExists(fp);
            } catch (IOException e) {
                // ignore
            }
        });
    }
}
