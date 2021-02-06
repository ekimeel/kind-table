package kind.table.funcs;

import kind.table.cols.Col;

/**
 * The type Unsupported col exception.
 */
public final class UnsupportedColException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Col with type [%s] is not supported.";

    /**
     * Instantiates a new Unsupported col exception.
     *
     * @param col the col
     */
    public UnsupportedColException(Col col) {
        super(String.format(DEFAULT_MESSAGE, (col == null)? "null" : col.getName()));
    }

    /**
     * Instantiates a new Unsupported col exception.
     *
     * @param message the message
     */
    public UnsupportedColException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Unsupported col exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public UnsupportedColException(String message, Throwable cause) {
        super(message, cause);
    }
}
