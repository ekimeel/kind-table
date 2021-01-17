package kind.table.funcs;

import kind.table.*;
import kind.table.cols.ColRef;
import kind.table.cols.Column;

/**
 * Returns the first value in the provided column.
 *
 * @param <T>
 */
public final class First<T> implements Func<T> {

    public static <E> First<E> of(int col) { return new First<>(ColRef.of(col)); }
    public static <E> First<E> of(String col) { return new First<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private First(ColRef colRef) {
        this.colRef = colRef;
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
        final Column col = table.getColByRef(colRef);
        return table.getFirstRow().get(col.getIndex());
    }


}
