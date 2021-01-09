package kind.table;

public final class TableSettings implements Copyable<TableSettings> {

    public static final int DEFAULT_MAX_ALLOWABLE_COLUMNS = 1024;
    public static final int DEFAULT_MAX_ALLOWABLE_ROWS = Integer.MAX_VALUE;
    public static final int DEFAULT_MAX_ALLOWABLE_THREADS = 4;
    public static final int DEFAULT_EVAL_TIMEOUT = 300;
    public static final int DEFAULT_ALLOW_PARALLEL_PROCESSING_AFTER_ROW = 99999;
    public static final TableSettings DEFAULT_SETTINGS;

    static{
        DEFAULT_SETTINGS = new TableSettings(
                DEFAULT_MAX_ALLOWABLE_ROWS,
                DEFAULT_MAX_ALLOWABLE_COLUMNS,
                DEFAULT_MAX_ALLOWABLE_THREADS,
                DEFAULT_EVAL_TIMEOUT,
                DEFAULT_ALLOW_PARALLEL_PROCESSING_AFTER_ROW);
    }


    private final int maxAllowableRows;
    private final int maxAllowableColumns;
    private final int maxAllowableThreads;
    private final int allowParallelProcessingAfterRow;
    private final int evalTimeout;

    public TableSettings(int maxAllowableRows, int maxAllowableColumns, int maxAllowableThreads, int evalTimeout,
                         int allowThreadsAfterRowCount) {
        this.maxAllowableRows = maxAllowableRows;
        this.maxAllowableColumns = maxAllowableColumns;
        this.maxAllowableThreads = maxAllowableThreads;
        this.evalTimeout = evalTimeout;
        this.allowParallelProcessingAfterRow = allowThreadsAfterRowCount;
    }

    /**
     * Returns the max allowable columns the table is allowed to have
     * @return Returns the max allowable columns the table can have
     */
    public int getMaxAllowableColumns() {
        return maxAllowableColumns;
    }

    /**
     * Returns the max allowable rows the table is allowed to have
     * @return Returns the max allowable rows the table can have
     */
    public int getMaxAllowableRows() {
        return maxAllowableRows;
    }

    /**
     * Returns the max allowable threads the table is allowed to use
     * @return Returns the max allowable threads the table can use
     */
    public int getMaxAllowableThreads() {
        return maxAllowableThreads;
    }

    /**
     * Returns the eval timeout in seconds
     * @return Returns the eval timeout in seconds
     */
    public int getEvalTimeout() {
        return evalTimeout;
    }

    /**
     * Returns the number of rows a table is required to have before parallel processing can be considered
     *
     * @return Returns a row number (count) before parallel processing can be considered
     */
    public int getAllowParallelProcessingAfterRow() {
        return allowParallelProcessingAfterRow;
    }

    @Override
    public TableSettings copy() {
        final TableSettings copy = new TableSettings(this.getMaxAllowableRows(),
                getMaxAllowableColumns(),
                getMaxAllowableThreads(),
                getEvalTimeout(),
                getAllowParallelProcessingAfterRow());

        return copy;
    }
}
