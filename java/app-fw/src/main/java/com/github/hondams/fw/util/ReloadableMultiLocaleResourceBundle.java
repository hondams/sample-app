package com.github.hondams.fw.util;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

/**
 * 複数のベース名（baseNames）に対応し、ロケールごとに {@link ResourceBundle} をキャッシュして 参照できるユーティリティクラス。
 *
 * <p>
 * 主な特徴:
 * <ul>
 * <li>複数の baseName を順に検索して値を取得する（先に見つかったものを返す）。
 * <li>ロケール指定がない場合はコンストラクタで与えたデフォルトロケール（またはシステムデフォルト）を使用。
 * <li>ファイルシステムからリソースを読み取る必要がある場合は、コンストラクタに {@code directory} を渡すことで
 * {@link FileSystemResourceBundleControl} を使用する（コントロールは内部で再利用される）。
 * <li>内部で {@code Map<Locale, List<ResourceBundle>>} をキャッシュするため、複数ロケールを扱うときは キャッシュ成長に注意が必要（必要なら TTL
 * や LRU を導入すること）。
 * </ul>
 *
 * <p>
 * スレッドセーフ: 内部でスレッドセーフな {@link ConcurrentHashMap} を使用しているため、複数スレッドからの読み取りは安全です。
 */
public class ReloadableMultiLocaleResourceBundle {

    private final List<String> baseNames;

    private final Locale defaultLocale;

    private final Map<Locale, List<ResourceBundle>> bundles = new ConcurrentHashMap<>();

    private final FileSystemResourceBundleControl control;

    /**
     * コンストラクタ。
     *
     * @param directory ファイルシステムからリソースバンドルを読み込む場合の基準ディレクトリ。null の場合は クラスパス経由で
     *        {@link ResourceBundle#getBundle} を使って読み込む。
     * @param baseNames 検索する baseName の配列（null は不可）。配列は内部で不変のリストとして保持される。
     * @param defaultLocale デフォルトのロケール。null の場合は {@link Locale#getDefault()} を使用する。
     * @throws NullPointerException baseNames が null の場合
     */
    public ReloadableMultiLocaleResourceBundle(@Nullable Path directory,
            @NonNull String[] baseNames, @Nullable Locale defaultLocale) {
        this.baseNames =
                Arrays.asList(Objects.requireNonNull(baseNames, "baseNames must not be null"));
        this.defaultLocale = (defaultLocale == null ? Locale.getDefault() : defaultLocale);
        this.control = (directory == null ? null : new FileSystemResourceBundleControl(directory));
    }

    /**
     * デフォルトロケール（コンストラクタで指定したもの、またはシステムデフォルト）の 全ての baseName に含まれるキーの集合を返します。
     *
     * <p>
     * 注意: 複数ロケールやすべてのロケールをマージしたキー集合が必要な場合は、このメソッドは 期待通りの結果を返さない可能性があります（本実装はデフォルトロケールのみを参照します）。
     *
     * @return キーのソートされた集合（空の場合は空集合を返す）。
     */
    public Set<String> keySet() {
        Set<String> keySet = new TreeSet<>();
        List<ResourceBundle> resourceBundles = getBundles(this.defaultLocale);
        for (ResourceBundle resourceBundle : resourceBundles) {
            keySet.addAll(resourceBundle.keySet());
        }
        return keySet;
    }

    /**
     * 指定したキーに対応する文字列を返します。ロケールを指定しない場合はデフォルトロケールを使います。
     *
     * <p>
     * 検索の順序: 内部に保持されている {@code baseNames} の先頭から順にそれぞれの {@link ResourceBundle}
     * を検索し、最初に見つかった値を返します。キーが見つからない場合は {@code null} を返します。
     *
     * @param key 検索するキー（null 不可）
     * @return マッピングされた文字列、見つからない場合は {@code null}
     */
    public @Nullable String getString(@NonNull String key) {
        return getString(key, null);
    }

    /**
     * 指定したロケールでキーを検索して文字列を返します。ロケールが null の場合はデフォルトロケールを使います。
     *
     * <p>
     * このメソッドは以下のポリシーに従います:
     * <ul>
     * <li>各 baseName について {@link ResourceBundle#getBundle} を呼び、見つかった順に検索する。
     * <li>各 {@link ResourceBundle} はロケール特化→親（ロケールなし）へとフォールバックする標準挙動を持つ。
     * <li>どのバンドルにもキーが存在しない場合は {@code null} を返す。
     * </ul>
     *
     * @param key 検索するキー（null 不可）
     * @param locale 使用するロケール。null の場合はデフォルトロケールを使用する。
     * @return マッピングされた文字列、見つからない場合は {@code null}
     */
    public @Nullable String getString(@NonNull String key, @Nullable Locale locale) {
        Objects.requireNonNull(key, "key must not be null");
        List<ResourceBundle> resourceBundles =
                getBundles(locale == null ? this.defaultLocale : locale);
        for (ResourceBundle resourceBundle : resourceBundles) {
            if (resourceBundle.containsKey(key)) {
                return resourceBundle.getString(key);
            }
        }
        return null;
    }

    private List<ResourceBundle> getBundles(Locale locale) {
        return this.bundles.computeIfAbsent(locale, this::createBundles);
    }

    private List<ResourceBundle> createBundles(Locale locale) {
        List<ResourceBundle> resourceBundles = new ArrayList<>();
        for (String baseName : this.baseNames) {
            resourceBundles.add(createBundle(baseName, locale));
        }
        return resourceBundles;
    }

    private ResourceBundle createBundle(String baseName, Locale locale) {
        if (this.control != null) {
            try {
                return ResourceBundle.getBundle(baseName, locale, this.control);
            } catch (MissingResourceException e) {
                // フォールバック: デフォルトロケールのバンドルを使う
                return ResourceBundle.getBundle(baseName, this.defaultLocale, this.control);
            }
        } else {
            try {
                return ResourceBundle.getBundle(baseName, locale);
            } catch (MissingResourceException e) {
                // フォールバック: デフォルトロケールのバンドルを使う
                return ResourceBundle.getBundle(baseName, this.defaultLocale);
            }
        }
    }

    /**
     * 内部キャッシュされた {@code ResourceBundle} をクリアします。
     *
     * <p>
     * 処理内容:
     * <ul>
     * <li>ロケールごとに保持している内部キャッシュ（{@code bundles}）を消去する。
     * <li>さらに {@link ResourceBundle#clearCache()} を呼び、もしカスタムの
     * {@link FileSystemResourceBundleControl} を使用している場合はそのコントロールの クラスローダー単位でキャッシュをクリアする。
     * </ul>
     */
    public void clearCache() {
        this.bundles.clear();
        if (this.control != null) {
            // カスタム Control を使っている場合は、そのクラスローダー単位でキャッシュをクリア
            ResourceBundle.clearCache(this.control.getClass().getClassLoader());
        } else {
            ResourceBundle.clearCache();
        }

    }
}
