package kind.table.funcs;

import com.google.common.math.Stats;
import kind.table.cols.Column;
import kind.table.cols.DoubleColumn;
import kind.table.Table;

import java.util.List;

/**
 * The population standard deviation
 *
 */
public final class StandardDeviation implements Func<Double> {

    private final Integer col;
    private Table table;

    public StandardDeviation(int col) {
        this.col = col;
    }

    @Override
    public Double eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }

        final Column column = table.getColumn(this.col);

        if (column instanceof DoubleColumn) {
            return Stats.of(table.getValues(col)).populationStandardDeviation();
        } else {
            final List<Double> values = table.valuesToDoubles(col);
            return Stats.of(values).populationStandardDeviation();
        }
    }


}
