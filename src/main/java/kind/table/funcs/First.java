package kind.table.funcs;

import kind.table.*;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

/**
 * Returns the first value in the provided column.
 *
 * @param <T> the type parameter
 */
public final class First<T> extends AbstractFunc<T> {

    /**
     * Of first.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the first
     */
    public static <E> First<E> of(int col) { return new First<>(ColRef.of(col)); }

    /**
     * Of first.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the first
     */
    public static <E> First<E> of(String col) { return new First<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private First(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfColRefNotFound(table, this.colRef);
    }
    @Override
    public T evalTemplate(Table table) {
        if (table == null) {
            return null;
        }
        final Col col = table.getColByRef(colRef);
        return table.getFirstRow().get(col.getIndex());
    }


}
