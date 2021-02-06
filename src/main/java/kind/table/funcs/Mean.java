package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;
import kind.table.cols.Col;
import kind.table.cols.NumCol;

/**
 * The type Mean.
 */
public final class Mean extends AbstractFunc<Double> {

    /**
     * Of mean.
     *
     * @param col the col
     * @return the mean
     */
    public static Mean of(String col) { return new Mean(ColRef.of(col)); }

    /**
     * Of mean.
     *
     * @param col the col
     * @return the mean
     */
    public static Mean of(int col) { return new Mean(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private Mean(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfNotNumCol(this.colRef, table);
        errorIfColRefNotFound(table, this.colRef);
    }

    @Override
    public Double evalTemplate(Table table) {
        final Col col = table.getColByRef(this.colRef);
        final Number sum = table.eval(Sum.of(col.getIndex()));
        return sum.doubleValue() / table.getRowCount();
    }


}
