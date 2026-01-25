package com.github.hondams.fw.value.code;

import com.github.hondams.fw.util.FileSystemResourceBundleControl;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;
import lombok.Getter;
import lombok.Setter;

public class CodeSetManager {

    // CodeSet.<CodeSetName>.Type=resource/database
    // CodeSet.<CodeSetName>.Format=[0-2:<OtherCodeSetName>]
    // CodeSet.<CodeSetName>.Value.<SortNumber>=<CodeValue>
    // CodeSet.<CodeSetName>.Label.<CodeValue>=<CodeLabel>

    // Database Table Columns:
    // <PK>CodeSetName
    // <PK>CodeValue
    // <PK>Locale
    // CodeLabel

    @Getter
    @Setter
    private Path directory;
    @Getter
    @Setter
    private String baseName = "codeset";
    @Getter
    @Setter
    private Locale defaultLocale;

    private final Map<Locale, ResourceBundle> bundles = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        if (this.defaultLocale != null) {
            this.defaultLocale = Locale.getDefault();
        }
    }

    public String getLabel(String key) {
        return getLabel(key, this.defaultLocale);
    }

    public String getLabel(String key, Locale locale) {
        ResourceBundle resourceBundle = getBundle(locale);
        if (!resourceBundle.containsKey(key)) {
            return null;
        }
        return resourceBundle.getString(key);
    }

    private ResourceBundle getBundle(Locale locale) {
        return this.bundles.computeIfAbsent(locale, this::createBundle);
    }

    private ResourceBundle createBundle(Locale locale) {
        if (this.directory != null) {
            return ResourceBundle.getBundle(this.baseName, locale,
                    new FileSystemResourceBundleControl(this.directory));
        } else {
            return ResourceBundle.getBundle(this.baseName, locale);
        }
    }

    public void clearCache() {
        this.bundles.clear();
    }
}
