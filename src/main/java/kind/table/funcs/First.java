package kind.table.funcs;

import kind.table.*;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

/**
 * Returns the first value in the provided column.
 *
 * @param <T>
 */
public final class First<T> implements Func<T> {

    public static <E> First<E> from(int col) { return new First<>(ColRef.of(col)); }
    public static <E> First<E> from(String col) { return new First<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private First(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return true;
    }

    @Override
    public T eval(Table table) {
        if (table == null) {
            return null;
        }
        final Col col = table.getColByRef(colRef);
        return table.getFirstRow().get(col.getIndex());
    }


}
