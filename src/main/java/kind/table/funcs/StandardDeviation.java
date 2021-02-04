package kind.table.funcs;

import com.google.common.math.Stats;
import kind.table.cols.ColRef;
import kind.table.cols.Col;
import kind.table.cols.DblCol;
import kind.table.Table;
import kind.table.cols.NumCol;

import java.util.List;

/**
 * The population standard deviation
 *
 */
public final class StandardDeviation implements Func<Double> {

    public static StandardDeviation of(String col) { return new StandardDeviation(ColRef.of(col)); }
    public static StandardDeviation of(int col) { return new StandardDeviation(ColRef.of(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    private StandardDeviation(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public Double eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }

        final Col col = table.getColByRef(this.colRef);

        if (col instanceof DblCol) {
            return Stats.of(table.getVals(colRef)).populationStandardDeviation();
        } else {
            final List<Double> values = table.valuesToDoubles(col.getIndex());
            return Stats.of(values).populationStandardDeviation();
        }
    }


}
