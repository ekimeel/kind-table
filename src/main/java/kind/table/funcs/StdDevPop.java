package kind.table.funcs;

import com.google.common.math.Stats;
import com.google.common.math.StatsAccumulator;
import kind.table.Row;
import kind.table.cols.ColRef;
import kind.table.cols.Col;
import kind.table.cols.DblCol;
import kind.table.Table;
import kind.table.cols.NumCol;

import java.util.List;
import java.util.Spliterator;

/**
 * The population standard deviation
 */
public final class StandardDeviation implements Func<Double> {

    /**
     * Of standard deviation.
     *
     * @param col the col
     * @return the standard deviation
     */
    public static StandardDeviation of(String col) { return new StandardDeviation(ColRef.of(col)); }

    /**
     * Of standard deviation.
     *
     * @param col the col
     * @return the standard deviation
     */
    public static StandardDeviation of(int col) { return new StandardDeviation(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private StandardDeviation(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public Double eval(Table table) {

        final Col col = table.getColByRef(this.colRef);
        final int index = col.getIndex();
        final Spliterator<Row> spliterator = table.rowSpliterator( (r) -> r.get(index) != null );

        final StatsAccumulator accumulator = new StatsAccumulator();
        spliterator.forEachRemaining( (r) -> accumulator.add( ((Number)r.get(index)).doubleValue() ));

        return accumulator.populationStandardDeviation();

    }


}
