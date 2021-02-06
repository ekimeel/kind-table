package kind.table.funcs;

import kind.table.cols.Col;
import kind.table.cols.ColRef;
import kind.table.Table;


/**
 * The type Last.
 *
 * @param <T> the type parameter
 */
public final class Last<T> extends AbstractFunc<T> {

    /**
     * Of last.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the last
     */
    public static <E> Last<E> of(int col) { return new Last(ColRef.of(col)); }

    /**
     * Of last.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the last
     */
    public static <E> Last<E> of(String col) { return new Last(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private Last(ColRef colRef) {
        this.colRef =colRef;
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

        final Col col = table.getColByRef(this.colRef);
        return table.get(table.getRowCount()-1, col.getIndex());
    }

}
