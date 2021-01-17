package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Range<T extends Number> implements Func<T> {

    public static <E extends Number> Range<E> of(String col) { return new Range<>(ColRef.of(col)); }
    public static <E extends Number> Range<E> of(int col) { return new Range<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    private Range(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return (column instanceof NumberColumn);
    }

    @Override
    public T eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }

        final Column column = table.getColByRef(this.colRef);

        if (column instanceof DblColumn){
            return (T) rangeDouble();
        } else if (column instanceof IntColumn){
            return (T) rangeInteger();
        } else if (column instanceof LngColumn){
            return (T) rangeLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support column type %.",
            this.getClass().getSimpleName(),
            column.getClass().getSimpleName()));
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
