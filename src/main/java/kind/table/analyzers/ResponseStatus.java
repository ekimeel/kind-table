package kind.table.analyzers;

/**
 * The enum Response status.
 */
public enum ResponseStatus {
    /**
     * Empty response status.
     */
    EMPTY(0),
    /**
     * Ok response status.
     */
    OK(1),
    /**
     * Warn response status.
     */
    WARN(2),
    /**
     * Error response status.
     */
    ERROR(3),
    /**
     * Fatal response status.
     */
    FATAL(4),;
    private final int id;

    ResponseStatus(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }
}
