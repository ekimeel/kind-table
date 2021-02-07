package kind.table.funcs;

import com.google.common.math.PairedStatsAccumulator;
import kind.table.Row;
import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;
import java.util.Spliterator;

/**
 * The type Corr coef (Pearsons Correlation Coefficient).
 */
public final class CorrCoef extends AbstractFunc<Double> {

    /**
     * Of corr coef.
     *
     * @param colA the col a
     * @param colB the col b
     * @return the corr coef
     */
    public static CorrCoef of(String colA, String colB) { return new CorrCoef(ColRef.of(colA), ColRef.of(colB)); }

    /**
     * Of corr coef.
     *
     * @param colA the col a
     * @param colB the col b
     * @return the corr coef
     */
    public static CorrCoef of(int colA, int colB) { return new CorrCoef(ColRef.of(colA), ColRef.of(colB)); }
    /**/
    private final ColRef colRefA;
    private final ColRef colRefB;

    private CorrCoef(ColRef colRefA, ColRef colRefB) {
        this.colRefA = colRefA;
        this.colRefB = colRefB;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRefA);
        errorIfNull(this.colRefB);
        errorIfColRefNotFound(table, this.colRefA);
        errorIfColRefNotFound(table, this.colRefB);
    }

    @Override
    public Double evalTemplate(Table table) {

        final Col colA = table.getColByRef(this.colRefA);
        final Col colB = table.getColByRef(this.colRefB);

        final Spliterator<Row> spliterator = table.rowSpliterator( (r) -> (r.get(colA.getIndex()) != null
                && r.get(colB.getIndex()) != null));

        final PairedStatsAccumulator accumulator = new PairedStatsAccumulator();
        spliterator.forEachRemaining( (r) -> accumulator.add(
                ((Number)r.get(colA.getIndex())).doubleValue(),
                ((Number)r.get(colB.getIndex())).doubleValue()
        ));

        return accumulator.pearsonsCorrelationCoefficient();
    }


}

