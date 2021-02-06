package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;
import kind.table.cols.Col;
import kind.table.cols.NumCol;

/**
 * The type Mean.
 */
public final class Mean implements Func<Double> {

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
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public Double eval(Table table) {
        final Col col = table.getColByRef(this.colRef);
        final Number sum = table.eval(Sum.of(col.getIndex()));
        return sum.doubleValue() / table.getRowCount();
    }


}
