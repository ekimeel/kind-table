package kind.table.funcs;

import com.google.common.math.Stats;
import kind.table.*;
import kind.table.cols.*;
import kind.table.cols.Col;
import kind.table.cols.NumCol;

import java.util.List;

public final class Mean implements Func<Double> {

    public static Mean from(String col) { return new Mean(ColRef.of(col)); }
    public static Mean from(int col) { return new Mean(ColRef.of(col)); }
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
        if (table == null) {
            return null;
        }

        final Col col = table.getColByRef(this.colRef);

        if (col instanceof DblCol) {
            return Stats.of(table.getVals(colRef)).mean();
        } else {
            final List<Double> values = table.valuesToDoubles(col.getIndex());
            return Stats.of(values).mean();
        }
    }


}
