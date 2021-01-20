package kind.table.funcs;

import com.google.common.math.Stats;
import kind.table.*;
import kind.table.cols.ColRef;
import kind.table.cols.Column;
import kind.table.cols.DblColumn;
import kind.table.cols.NumberColumn;

import java.util.List;

public final class Mean implements Func<Double> {

    public static Mean of(String col) { return new Mean(ColRef.from(col)); }
    public static Mean of(int col) { return new Mean(ColRef.from(col)); }
    /**/
    private final ColRef colRef;

    private Mean(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return (column instanceof NumberColumn);
    }

    @Override
    public Double eval(Table table) {
        if (table == null) {
            return null;
        }

        final Column column = table.getColByRef(this.colRef);

        if (column instanceof DblColumn) {
            return Stats.of(table.getVals(colRef)).mean();
        } else {
            final List<Double> values = table.valuesToDoubles(column.getIndex());
            return Stats.of(values).mean();
        }
    }


}
