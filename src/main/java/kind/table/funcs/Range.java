package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Range<T extends Number> implements Func<T> {

    private final Integer col;
    private Table table;

    public Range(int col) {
        this.col = col;
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

        final Column column = table.getCol(this.col);

        if (column instanceof DoubleColumn){
            return (T) rangeDouble();
        } else if (column instanceof IntegerColumn){
            return (T) rangeInteger();
        } else if (column instanceof LongColumn){
            return (T) rangeLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support column type %.",
            this.getClass().getSimpleName(),
            column.getClass().getSimpleName()));
        }

    }

    private Double rangeDouble() {
        final List<Double> values = table.getValues(col).stream().mapToDouble(x -> ((Number)x).intValue())
                .boxed()
                .collect(Collectors.toList());

        final Double min = Collections.min(values);
        final Double max = Collections.max(values);
        return max - min;
    }

    private Integer rangeInteger() {
        final List<Integer> values = table.getValues(col).stream().mapToInt(x -> ((Number)x).intValue())
                .boxed()
                .collect(Collectors.toList());

        final Integer min = Collections.min(values);
        final Integer max = Collections.max(values);
        return max - min;
    }

    private Long rangeLong() {
        final List<Long> values = table.getValues(col).stream().mapToLong(x -> ((Number)x).longValue())
                .boxed()
                .collect(Collectors.toList());

        final Long min = Collections.min(values);
        final Long max = Collections.max(values);
        return max - min;
    }
}
