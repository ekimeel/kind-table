package kind.table.analyzers;

/**
 * The enum Analyzer params.
 */
public enum AnalyzerParams {
    /**
     * The X cols.
     */
    x_cols("x_cols", "exclude cols");

    /**
     * The Key.
     */
    final String key;
    /**
     * The Description.
     */
    final String description;

    AnalyzerParams(String key, String description) {
        this.key = key;
        this.description = description;
    }

    /**
     * Gets key.
     *
     * @return the key
     */
    public String getKey() {
        return key;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }
}
