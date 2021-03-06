package kind.table;

/**
 * Provides settings for a {@link kind.table.Table}
 */
public final class TableSettings implements Copyable<TableSettings> {

    public static final int DEFAULT_MAX_ALLOWABLE_COLUMNS = 1024;
    public static final int DEFAULT_MAX_ALLOWABLE_ROWS = Integer.MAX_VALUE;
    public static final int DEFAULT_MAX_ALLOWABLE_THREADS = 4;
    public static final int DEFAULT_EVAL_TIMEOUT = 300;
    public static final int DEFAULT_ROW_CAPACITY = 20;
    public static final int DEFAULT_ALLOW_PARALLEL_PROCESSING_AFTER_ROW = 99999;

    public static final String DEFAULT_TIMEZONE = "UTC";
    public static final TableSettings DEFAULT_SETTINGS;

    static{
        DEFAULT_SETTINGS = new TableSettings(
                DEFAULT_ROW_CAPACITY,
                DEFAULT_MAX_ALLOWABLE_ROWS,
                DEFAULT_MAX_ALLOWABLE_COLUMNS,
                DEFAULT_MAX_ALLOWABLE_THREADS,
                DEFAULT_EVAL_TIMEOUT,
                DEFAULT_ALLOW_PARALLEL_PROCESSING_AFTER_ROW,
                DEFAULT_TIMEZONE);
    }


    private final int defaultRowCapacity;
    private final int maxAllowableRows;
    private final int maxAllowableColumns;
    private final int maxAllowableThreads;
    private final int allowParallelProcessingAfterRow;
    private final int evalTimeout;
    private final String timeZone;


    TableSettings(int defaultRowCapacity, int maxAllowableRows, int maxAllowableColumns, int maxAllowableThreads, int evalTimeout,
                         int allowThreadsAfterRowCount, String timeZone) {
        this.defaultRowCapacity = defaultRowCapacity;
        this.maxAllowableRows = maxAllowableRows;
        this.maxAllowableColumns = maxAllowableColumns;
        this.maxAllowableThreads = maxAllowableThreads;
        this.evalTimeout = evalTimeout;
        this.allowParallelProcessingAfterRow = allowThreadsAfterRowCount;
        this.timeZone = timeZone;
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

    /**
     * Returns the default row capacity for the table. If you're expecting to have a large table estimate the size for
     * better performance.
     *
     * @return
     */
    public int getDefaultRowCapacity() {
        return defaultRowCapacity;
    }

    /**
     * Returns the time zone
     *
     * @return
     */
    public String getTimeZone() {
        return timeZone;
    }

    @Override
    public TableSettings copy() {
        final TableSettings copy = new TableSettings(
                this.getDefaultRowCapacity(),
                this.getMaxAllowableRows(),
                getMaxAllowableColumns(),
                getMaxAllowableThreads(),
                getEvalTimeout(),
                getAllowParallelProcessingAfterRow(),
                getTimeZone());

        return copy;
    }
}
