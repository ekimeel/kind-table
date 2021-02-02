package kind.table.analyzers;

public enum AnalyzerParams {
    x_cols("x_cols", "exclude cols");

    final String key;
    final String description;

    AnalyzerParams(String key, String description) {
        this.key = key;
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }
}
