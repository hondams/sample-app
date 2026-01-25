package com.github.hondams.fw.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class FileSystemResourceBundleControl extends ResourceBundle.Control {

    private final Path directory;

    private final Charset charset;

    /**
     * デフォルトで UTF-8 を使用するコンストラクタ（後方互換のため）。
     */
    public FileSystemResourceBundleControl(Path directory) {
        this(directory, StandardCharsets.UTF_8);
    }

    /**
     * 指定した Charset を使ってプロパティを読み込むコンストラクタ。
     */
    public FileSystemResourceBundleControl(Path directory, Charset charset) {
        this.directory = Objects.requireNonNull(directory, "directory must not be null");
        this.charset = Objects.requireNonNull(charset, "charset must not be null");
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format,
            ClassLoader loader, boolean reload) throws IOException {
        // properties 形式のみサポート
        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, "properties");
        Path p = this.directory.resolve(resourceName);
        if (Files.exists(p)) {
            try (InputStream in = Files.newInputStream(p);
                    InputStreamReader reader = new InputStreamReader(in, this.charset)) {
                return new PropertyResourceBundle(reader);
            }
        }
        return null;
    }

    @Override
    public long getTimeToLive(String baseName, Locale locale) {
        // ResourceBundle 側で長期キャッシュされると、別のディレクトリでロードしたバンドルが再利用されて
        // しまうため、ここではキャッシュしない設定にする（必要ならより高度な reload 管理を追加する）。
        return TTL_DONT_CACHE;
    }
}
