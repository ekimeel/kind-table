package kind.table;

public final class TableSettingsBuilder {

    private int maxAllowableRows;
    private int maxAllowableColumns;
    private int maxAllowableThreads;
    private int allowParallelProcessingAfterRow;
    private int evalTimeout;
    private String timeZone;

    public TableSettingsBuilder() {
    }

    public TableSettingsBuilder withMaxAllowableRows(int maxAllowableRows) {
        this.maxAllowableRows = maxAllowableRows;
        return this;
    }

    public TableSettingsBuilder withMaxAllowableColumns(int maxAllowableColumns) {
        this.maxAllowableColumns = maxAllowableColumns;
        return this;
    }

    public TableSettingsBuilder withMaxAllowableThreads(int maxAllowableThreads) {
        this.maxAllowableThreads = maxAllowableThreads;
        return this;
    }

    public TableSettingsBuilder withAllowParallelProcessingAfterRow(int allowParallelProcessingAfterRow) {
        this.allowParallelProcessingAfterRow = allowParallelProcessingAfterRow;
        return this;
    }

    public TableSettingsBuilder withEvalTimeout(int evalTimeout) {
        this.evalTimeout = evalTimeout;
        return this;
    }

    public TableSettingsBuilder withTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    public TableSettings build() {
        return new TableSettings(this.maxAllowableRows, this.maxAllowableColumns, this.maxAllowableThreads,
                this.evalTimeout, this.allowParallelProcessingAfterRow, this.timeZone);
    }


}
