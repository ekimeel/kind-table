package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Range<T extends Number> implements Func<T> {

    public static <E extends Number> Range<E> from(String col) { return new Range<>(ColRef.of(col)); }
    public static <E extends Number> Range<E> from(int col) { return new Range<>(ColRef.of(col)); }
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

        if (col instanceof DblCol){
            return (T) rangeDouble();
        } else if (col instanceof IntCol){
            return (T) rangeInteger();
        } else if (col instanceof LngCol){
            return (T) rangeLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %.",
            this.getClass().getSimpleName(),
            col.getClass().getSimpleName()));
        }

    }

    private Double rangeDouble() {
        final List<Double> values = table.getVals(colRef)
                .stream().mapToDouble(x -> ((Number)x).intValue())
                .boxed()
                .collect(Collectors.toList());

        final Double min = Collections.min(values);
        final Double max = Collections.max(values);
        return max - min;
    }

    private Integer rangeInteger() {
        final List<Integer> values = table.getVals(colRef)
                .stream().mapToInt(x -> ((Number)x).intValue())
                .boxed()
                .collect(Collectors.toList());

        final Integer min = Collections.min(values);
        final Integer max = Collections.max(values);
        return max - min;
    }

    private Long rangeLong() {
        final List<Long> values = table.getVals(colRef)
                .stream().mapToLong(x -> ((Number)x).longValue())
                .boxed()
                .collect(Collectors.toList());

        final Long min = Collections.min(values);
        final Long max = Collections.max(values);
        return max - min;
    }
}
