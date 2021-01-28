package kind.table.funcs;

import kind.table.cols.Col;

public final class UnsupportedColException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Col with type [%s] is not supported.";

    public UnsupportedColException(Col col) {
        super(String.format(DEFAULT_MESSAGE, (col == null)? "null" : col.getName()));
    }

    public UnsupportedColException(String message) {
        super(message);
    }

    public UnsupportedColException(String message, Throwable cause) {
        super(message, cause);
    }
}
