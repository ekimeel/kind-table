package kind.table.funcs;

import com.google.common.math.Stats;
import kind.table.*;
import kind.table.cols.Column;
import kind.table.cols.DblColumn;
import kind.table.cols.NumberColumn;

import java.util.List;

public final class Mean implements Func<java.lang.Double> {

    private final Integer col;
    private Table table;

    public Mean(int col) {
        this.col = col;
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

        final Column column = table.getColByIndex(this.col);

        if (column instanceof DblColumn) {
            return Stats.of(table.getVals(col)).mean();
        } else {
            final List<Double> values = table.valuesToDoubles(col);
            return Stats.of(values).mean();
        }
    }


}
