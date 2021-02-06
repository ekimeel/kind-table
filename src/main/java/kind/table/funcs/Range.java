package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

import java.util.Collections;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Collectors;

/**
 * The type Range.
 *
 * @param <T> the type parameter
 */
public class Range<T extends Number> implements Func<T> {

    /**
     * Of range.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the range
     */
    public static <E extends Number> Range<E> of(String col) { return new Range<>(ColRef.of(col)); }

    /**
     * Of range.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the range
     */
    public static <E extends Number> Range<E> of(int col) { return new Range<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    private Range(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public T eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }
        final Col col = table.getColByRef(this.colRef);
        final int index = col.getIndex();

        final T min = table.eval(Min.of(index));
        final T max = table.eval(Max.of(index));

        if (col instanceof DblCol){
            final Double low = min.doubleValue();
            final Double high = max.doubleValue();
            final Double range = high - low;
            return (T)range;
        } else if (col instanceof IntCol){
            final Integer low = min.intValue();
            final Integer high = max.intValue();
            final Integer range = high - low;
            return (T)range;
        } else if (col instanceof LngCol){
            final Long low = min.longValue();
            final Long high = max.longValue();
            final Long range = high - low;
            return (T)range;
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %.",
            this.getClass().getSimpleName(),
            col.getClass().getSimpleName()));
        }

    }

    private Double rangeDouble(Spliterator<Row> rowSpliterator, int index) {
        final List<Double> values = table.getVals(colRef)
                .stream()
                .filter( x -> (x != null))
                .mapToDouble(x -> ((Number)x).intValue())
                .boxed()
                .collect(Collectors.toList());

        final Double min = Collections.min(values);
        final Double max = Collections.max(values);
        return max - min;
    }

    private Integer rangeInteger(Spliterator<Row> rowSpliterator, int index) {
        final List<Integer> values = table.getVals(colRef)
                .stream()
                .filter( x -> (x != null))
                .mapToInt(x -> ((Number)x).intValue())
                .boxed()
                .collect(Collectors.toList());

        final Integer min = Collections.min(values);
        final Integer max = Collections.max(values);
        return max - min;
    }

    private Long rangeLong(Spliterator<Row> rowSpliterator, int index) {
        final List<Long> values = table.getVals(colRef)
                .stream()
                .filter( x -> (x != null))
                .mapToLong(x -> ((Number)x).longValue())
                .boxed()
                .collect(Collectors.toList());

        final Long min = Collections.min(values);
        final Long max = Collections.max(values);
        return max - min;
    }
}
