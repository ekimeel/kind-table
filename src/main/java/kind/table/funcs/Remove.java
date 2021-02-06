package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.*;

/**
 * The type Remove.
 */
public final class Remove extends AbstractFunc<Table> {

    /**
     * From remove.
     *
     * @param col the col
     * @return the remove
     */
    public static Remove from(String col) { return new Remove(ColRef.of(col)); }

    /**
     * From remove.
     *
     * @param col the col
     * @return the remove
     */
    public static Remove from(int col) { return new Remove(ColRef.of(col)); }

    /**/
    private final ColRef colRef;

    /**
     * Instantiates a new Remove.
     *
     * @param colRef the col ref
     */
    public Remove(ColRef colRef) {
        this.colRef = colRef;
    }


    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfColRefNotFound(table, this.colRef);
    }

    @Override
    protected Table evalTemplate(Table table) {
        final Table copy = table.copy();
        final Col col = copy.getColByRef(this.colRef);

        copy.removeCol(col);

        return copy;
    }


}
