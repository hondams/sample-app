package com.github.hondams.fw.value.code;

import com.github.hondams.fw.util.ReloadableMultiLocaleResourceBundle;
import jakarta.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Getter;
import lombok.Setter;
import org.jspecify.annotations.NonNull;

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
    private String[] baseNames = {"codeset"};
    @Getter
    @Setter
    private Locale defaultLocale;

    private CodeSetResourceAccessor resourceAccessor;

    private final AtomicReference<List<String>> codeSetNames = new AtomicReference<>();
    private final Map<String, CodeSet> codeSetMap = new ConcurrentHashMap<>();

    @PostConstruct
    public void init() {
        this.resourceAccessor =
                new CodeSetResourceAccessor(new ReloadableMultiLocaleResourceBundle(this.directory,
                        this.baseNames, this.defaultLocale));
    }

    public @NonNull List<String> getCodeSetNames() {
        List<String> got = this.codeSetNames.get();
        if (got != null) {
            return got;
        }
        List<String> created = this.resourceAccessor.getCodeSetNames(); // createCodeSetNames()
                                                                        // は不変リストを返すこと
        if (this.codeSetNames.compareAndSet(null, created)) {
            return created;
        } else {
            return this.codeSetNames.get();
        }
    }

    public @NonNull CodeSet getCodeSet(String codeSetName) {
        return this.codeSetMap.computeIfAbsent(codeSetName, this::createCodeSet);
    }

    protected CodeSet createCodeSet(String codeSetName) {
        CodeSetSourceType sourceType = this.resourceAccessor.getSourceType(codeSetName);
        if (sourceType == CodeSetSourceType.RESOURCE) {
            return new ResourceCodeSet(this.resourceAccessor, codeSetName);
        }
        throw new UnsupportedOperationException("CodeSetSourceType not supported: " + sourceType);
    }

    public void clearCache() {
        this.resourceAccessor.clearCache();
        this.codeSetMap.clear();
        this.codeSetNames.set(null);
    }
}
