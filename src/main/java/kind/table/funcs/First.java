package kind.table.funcs;

import kind.table.*;
import kind.table.cols.Column;

/**
 * Returns the first value in the provided column.
 *
 * @param <T>
 */
public final class First<T extends Number> implements Func<T> {

    private final Integer col;
    private Table table;

    public First(int col) {
        this.col = col;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return true;
    }

    @Override
    public T eval(Table table) {

        this.table = table;
        if (table == null) {
            return null;
        }

        return table.getFirstRow().get(col);
    }


}
