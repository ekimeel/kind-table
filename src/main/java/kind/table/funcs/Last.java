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
    public boolean acceptColumn(Column column) {
        return true;
    }

    @Override
    public T eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }

        final Column column = table.getCol(this.col);
        return table.get(table.getRowCount()-1,col);
    }

}
