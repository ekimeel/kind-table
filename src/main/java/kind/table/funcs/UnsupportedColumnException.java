package kind.table.funcs;

import kind.table.cols.Column;

public final class UnsupportedColumnException extends RuntimeException{

    private static final String DEFAULT_MESSAGE = "Column with type [%s] is not supported.";

    public UnsupportedColumnException(Column column) {
        super(String.format(DEFAULT_MESSAGE, (column == null)? "null" : column.getName()));
    }

    public UnsupportedColumnException(String message) {
        super(message);
    }

    public UnsupportedColumnException(String message, Throwable cause) {
        super(message, cause);
    }
}
