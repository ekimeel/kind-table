package kind.table.analyzers;

import kind.table.Table;

/**
 * The type Analyzer response.
 */
public final class Response {

    private String id;
    private Table table;
    private Object err;

    /**
     * Is ok boolean.
     *
     * @return the boolean
     * @see Response#getErr() Response#getErr()
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

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    void setId(String id) {
        this.id = id;
    }

}
