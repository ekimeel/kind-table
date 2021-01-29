package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;
import kind.table.cols.NumCol;

import java.util.List;

/*
 * Last observation carried forward (Smear) is a method of imputing missing data. If a is non-null and
 * becomes null, then the last non-null value replaces the null values
 */
public final class Smear implements Func<Table> {

    public static Smear from(String col) { return new Smear(ColRef.of(col)); }
    public static Smear from(int col) { return new Smear(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private Smear(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public Table eval(Table table) {
        final Table copy = table.copy();
        final Col col = copy.getColByRef(this.colRef);

        if (!this.acceptCol(col)) {
            throw new UnsupportedColException(col);
        }

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