package kind.table.funcs;

import com.google.common.math.Stats;
import kind.table.cols.ColRef;
import kind.table.cols.Column;
import kind.table.cols.DblColumn;
import kind.table.Table;
import kind.table.cols.NumberColumn;

import java.util.List;

/**
 * The population standard deviation
 *
 */
public final class StandardDeviation implements Func<Double> {

    public static StandardDeviation of(String col) { return new StandardDeviation(ColRef.from(col)); }
    public static StandardDeviation of(int col) { return new StandardDeviation(ColRef.from(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    private StandardDeviation(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return (column instanceof NumberColumn);
    }

    @Override
    public Double eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }

        final Column column = table.getColByRef(this.colRef);

        if (column instanceof DblColumn) {
            return Stats.of(table.getVals(colRef)).populationStandardDeviation();
        } else {
            final List<Double> values = table.valuesToDoubles(column.getIndex());
            return Stats.of(values).populationStandardDeviation();
        }
    }


}
