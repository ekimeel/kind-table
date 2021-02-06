package kind.table.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.cols.*;

/**
 * The type Histogram.
 */
public class Histogram extends AbstractFunc<Table> {

    /**
     * The constant COL_BIN_NUM.
     */
    public static final String COL_BIN_NUM = "bin_num";
    /**
     * The constant COL_BIN_START.
     */
    public static final String COL_BIN_START = "bin_start";
    /**
     * The constant COL_BIN_END.
     */
    public static final String COL_BIN_END = "bin_end";
    /**
     * The constant COL_BIN_SIZE.
     */
    public static final String COL_BIN_SIZE = "bin_size";

    /**
     * Of histogram.
     *
     * @param col  the col
     * @param bins the bins
     * @return the histogram
     */
    public static Histogram of(String col, int bins) { return new Histogram(ColRef.of(col), bins); }

    /**
     * Of histogram.
     *
     * @param col  the col
     * @param bins the bins
     * @return the histogram
     */
    public static Histogram of(int col, int bins) { return new Histogram(ColRef.of(col), bins); }

    /**/
    private final ColRef colRef;
    private final int bins;

    private Histogram(ColRef colRef, int bins) {
        this.colRef = colRef;
        this.bins = bins;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfColRefNotFound(table, this.colRef);
        if (bins < 2 || bins > 99) {
            throw new IllegalArgumentException("invalid bin size");
        }
    }

    @Override
    protected Table evalTemplate(Table table) {
        final Table result = new TableBuilder()
                .withDblCol(COL_BIN_NUM)
                .withDblCol(COL_BIN_START)
                .withDblCol(COL_BIN_END)
                .withIntCol(COL_BIN_SIZE)
                .build();

        final Col col = table.getColByRef(this.colRef);
        final Double exactMin = table.eval(Min.of(col.getIndex())).doubleValue();
        final Double exactMax = table.eval(Max.of(col.getIndex())).doubleValue();

        final Double min = exactMin - (exactMin * 0.01);
        final Double max = exactMax + (exactMax * 0.01);

        final Double width = (max - min) / bins;

        Double next = min;
        for (int i = 1; i <= bins; i++) {
            result.addRow(i, next, next + width, null);
            next = next + width;
        }

        result.getRows().stream().forEach( row -> {
            final Double start = row.get(1);
            final Double end = row.get(2);
            final Long count = table.getRows().stream().filter( x -> {
                final Object obj = x.get(col.getIndex());
                if (obj == null) { return false; }

                final Double v = ((Number)obj).doubleValue();
                return (v > start && v <= end)? true : (start == max || end == max);
            } ).count();
            row.set(3, count.intValue());
        });

        return result;

    }

    private Integer findBin(Double datum, int bins, double min, double max)
    {
        final double binWidth = (max - min) / bins;
        if ( datum >= max ) {
            return bins - 1;
        } else if ( datum <= min ) {
            return 0;
        }

        return (int) Math.floor((datum - min) / binWidth);
    }



}
