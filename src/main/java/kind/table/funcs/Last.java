package kind.table.funcs;

import kind.table.cols.Column;
import kind.table.Table;


public final class Last<T extends Number> implements Func<T> {

    private final Integer col;
    private Table table;

    public Last(int col) {
        this.col = col;
    }

    @Override
    public T eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }

        final Column column = table.getColumn(this.col);
        return table.get(table.getRowCount()-1,col);
    }

}
