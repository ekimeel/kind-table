package kind.table.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.cols.*;

public class Histogram extends AbstractFunc<Table> {

    public static final String COL_BIN_NUM = "bin_num";
    public static final String COL_BIN_START = "bin_start";
    public static final String COL_BIN_END = "bin_end";
    public static final String COL_BIN_SIZE = "bin_size";

    public static Histogram from(String col, int bins) { return new Histogram(ColRef.of(col), bins); }
    public static Histogram from(int col, int bins) { return new Histogram(ColRef.of(col), bins); }

    /**/
    private final ColRef colRef;
    private final int bins;


    public Histogram(ColRef colRef, int bins) {
        this.colRef = colRef;
        this.bins = bins;
    }

    @Override
    public boolean acceptCol(Col col) {
        return true;
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
        final Double exactMin = table.eval(Min.from(col.getIndex())).doubleValue();
        final Double exactMax = table.eval(Max.from(col.getIndex())).doubleValue();

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
                final Double v = ((Number)x.get(col.getIndex())).doubleValue();
                return (v > start && v <= end)? true : (start == max || end == max);
            } ).count();
            row.set(3, count.intValue());
        });



        return result;
        /*

        // Make sure that all bins are initialized
        final Map<Integer, Integer> histogram = range(0, bins).boxed()
                .collect(toMap(identity(), x -> 0));




        final Stream<Double> data = table.getRows().stream()
                .mapToDouble( x -> ((Number)x.get(col.getIndex())).doubleValue() )
                .boxed();

        histogram.putAll(data
                .collect(groupingBy(x -> findBin(x, bins, min, max),
                        mapping(x -> 1, summingInt(x -> x)))));

        histogram.forEach( (k, v) -> {
            result.addRow(k, v);
        });

        return result;
*/

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
