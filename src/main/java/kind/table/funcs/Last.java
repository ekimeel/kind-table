package kind.table.funcs;

import kind.table.cols.ColRef;
import kind.table.cols.Column;
import kind.table.Table;


public final class Last<T> implements Func<T> {

    public static <E> Last<E> of(int col) { return new Last(ColRef.of(col)); }
    public static <E> Last<E> of(String col) { return new Last(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private Last(ColRef colRef) {
        this.colRef =colRef;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return true;
    }

    @Override
    public T eval(Table table) {
        if (table == null) {
            return null;
        }

        final Column column = table.getColByRef(this.colRef);
        return table.get(table.getRowCount()-1, column.getIndex());
    }

}
