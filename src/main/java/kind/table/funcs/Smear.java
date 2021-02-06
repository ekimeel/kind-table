package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;
import kind.table.cols.NumCol;

import java.util.List;

/**
 * The type Smear.
 */
/*
 * Last observation carried forward (Smear) is a method of imputing missing data. If a is non-null and
 * becomes null, then the last non-null value replaces the null values
 */
public final class Smear extends AbstractFunc<Table> {

    /**
     * Of smear.
     *
     * @param col the col
     * @return the smear
     */
    public static Smear of(String col) { return new Smear(ColRef.of(col)); }

    /**
     * Of smear.
     *
     * @param col the col
     * @return the smear
     */
    public static Smear of(int col) { return new Smear(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private Smear(ColRef colRef) {
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
    public Table evalTemplate(Table table) {
        final Table copy = table.copy();
        final Col col = copy.getColByRef(this.colRef);

        Object priorInstance = null;
        final List values = copy.getVals(this.colRef);

        for (int i = 0; i < values.size(); i++) {
            final Object value = values.get(i);

            if ((value == null) && priorInstance != null) {
                copy.set(i, col.getIndex(), priorInstance);
            } else if (value != null) {
                priorInstance = value;
            }
        }

        return copy;
    }

}