package kind.table.analyzers;

import kind.table.Table;

/**
 * The type Analyzer response.
 */
public final class AnalyzerResponse {

    private Table table;
    private long time;
    private Object err;


    /**
     * Is ok boolean.
     *
     * @see this.getErr
     * @return the boolean
     *
     */
    public boolean isOk() { return err == null; }

    /**
     * Gets table.
     *
     * @return the table
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets table.
     *
     * @param table the table
     */
    void setTable(Table table) {
        this.table = table;
    }

    /**
     * Gets response time.
     *
     * @return the response time
     */
    public long getResponseTime() {
        return 0;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    void setTime(long time) {
        this.time = time;
    }

    /**
     * Gets err.
     *
     * @return the err
     */
    public Object getErr() {
        return this.err;
    }

    /**
     * Sets err.
     *
     * @param err the err
     */
    void setErr(Object err) {
        this.err = err;
    }
}
