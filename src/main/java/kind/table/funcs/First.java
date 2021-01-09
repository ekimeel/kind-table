package kind.table.funcs;

import kind.table.*;
import kind.table.cols.Column;

public final class First<T extends Number> implements Func<T> {

    private final Integer col;
    private Table table;

    public First(int col) {
        this.col = col;
    }

    @Override
    public T eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }

        final Column column = table.getColumn(this.col);
        return table.getFirstRow().get(col);
    }

}
