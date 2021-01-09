package kind.table.funcs;

import kind.table.*;
import kind.table.cols.Column;
import kind.table.cols.DoubleColumn;
import kind.table.cols.IntegerColumn;
import kind.table.cols.LongColumn;

import java.util.OptionalInt;

public final class Max<T extends Number> implements Func<T> {

    private final Integer col;
    private Table table;

    public Max(int col) {
        this.col = col;
    }

    @Override
    public T eval(Table table) {
        this.table = table;
        if (table == null) {
            return null;
        }

        final Column column = table.getColumn(this.col);

        if (column instanceof DoubleColumn){
            return (T) maxDouble();
        } else if (column instanceof IntegerColumn){
            return (T) maxInteger();
        } else if (column instanceof LongColumn){
            return (T) maxLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support column type %.",
                    this.getClass().getSimpleName(),
                    column.getClass().getSimpleName()));
        }

    }

    private Double maxDouble() {
        return table.getValues(col).stream().mapToDouble( x -> ((Number)x).doubleValue()).max().getAsDouble();
    }

    private Integer maxInteger() {
        OptionalInt v = table.getValues(col)
                .stream()
                .mapToInt( x -> ((Number)x).intValue())
                .max();


        return v.getAsInt();
    }

    private Long maxLong() {
        return table.getValues(col).stream().mapToLong( x -> ((Number)x).longValue()).max().getAsLong();
    }
}
