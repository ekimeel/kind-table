package kind.table;

/**
 * The type Table settings builder.
 */
public final class TableSettingsBuilder {

    private int defaultRowCapacity;
    private int maxAllowableRows;
    private int maxAllowableColumns;
    private int maxAllowableThreads;
    private int allowParallelProcessingAfterRow;
    private int evalTimeout;
    private String timeZone;

    /**
     * Instantiates a new Table settings builder.
     */
    public TableSettingsBuilder() {
    }

    /**
     * With max allowable rows table settings builder.
     *
     * @param maxAllowableRows the max allowable rows
     * @return the table settings builder
     */
    public TableSettingsBuilder withMaxAllowableRows(int maxAllowableRows) {
        this.maxAllowableRows = maxAllowableRows;
        return this;
    }

    /**
     * With max allowable columns table settings builder.
     *
     * @param maxAllowableColumns the max allowable columns
     * @return the table settings builder
     */
    public TableSettingsBuilder withMaxAllowableColumns(int maxAllowableColumns) {
        this.maxAllowableColumns = maxAllowableColumns;
        return this;
    }

    /**
     * With max allowable threads table settings builder.
     *
     * @param maxAllowableThreads the max allowable threads
     * @return the table settings builder
     */
    public TableSettingsBuilder withMaxAllowableThreads(int maxAllowableThreads) {
        this.maxAllowableThreads = maxAllowableThreads;
        return this;
    }

    /**
     * With allow parallel processing after row table settings builder.
     *
     * @param allowParallelProcessingAfterRow the allow parallel processing after row
     * @return the table settings builder
     */
    public TableSettingsBuilder withAllowParallelProcessingAfterRow(int allowParallelProcessingAfterRow) {
        this.allowParallelProcessingAfterRow = allowParallelProcessingAfterRow;
        return this;
    }

    /**
     * With eval timeout table settings builder.
     *
     * @param evalTimeout the eval timeout
     * @return the table settings builder
     */
    public TableSettingsBuilder withEvalTimeout(int evalTimeout) {
        this.evalTimeout = evalTimeout;
        return this;
    }

    /**
     * With time zone table settings builder.
     *
     * @param timeZone the time zone
     * @return the table settings builder
     */
    public TableSettingsBuilder withTimeZone(String timeZone) {
        this.timeZone = timeZone;
        return this;
    }

    /**
     * With row capacity table settings builder.
     *
     * @param minCapacity the min capacity
     * @return the table settings builder
     */
    public TableSettingsBuilder withRowCapacity(int minCapacity) {
        this.defaultRowCapacity = defaultRowCapacity;
        return this;
    }

    /**
     * Build table settings.
     *
     * @return the table settings
     */
    public TableSettings build() {
        return new TableSettings(this.defaultRowCapacity,
        this.maxAllowableRows, this.maxAllowableColumns, this.maxAllowableThreads,
                this.evalTimeout, this.allowParallelProcessingAfterRow, this.timeZone);
    }


}
