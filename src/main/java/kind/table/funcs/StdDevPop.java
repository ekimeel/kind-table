package kind.table.funcs;

import com.google.common.math.PairedStats;
import com.google.common.math.StatsAccumulator;
import kind.table.Row;
import kind.table.cols.ColRef;
import kind.table.cols.Col;
import kind.table.Table;
import kind.table.cols.NumCol;

import java.util.Spliterator;

/**
 * The population standard deviation
 */
public final class StdDevPop extends AbstractFunc<Double> {

    /**
     * Of standard deviation.
     *
     * @param col the col
     * @return the standard deviation
     */
    public static StdDevPop of(String col) { return new StdDevPop(ColRef.of(col)); }

    /**
     * Of standard deviation.
     *
     * @param col the col
     * @return the standard deviation
     */
    public static StdDevPop of(int col) { return new StdDevPop(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private StdDevPop(ColRef colRef) {
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
    public Double evalTemplate(Table table) {

        final Col col = table.getColByRef(this.colRef);
        final int index = col.getIndex();
        final Spliterator<Row> spliterator = table.rowSpliterator( (r) -> r.get(index) != null );

        final StatsAccumulator accumulator = new StatsAccumulator();
        spliterator.forEachRemaining( (r) -> accumulator.add( ((Number)r.get(index)).doubleValue() ));

        return accumulator.populationStandardDeviation();
    }


}
