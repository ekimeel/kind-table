package kind.table.funcs;

import kind.table.*;
import kind.table.cols.Column;
import kind.table.cols.DoubleColumn;
import kind.table.cols.IntegerColumn;
import kind.table.cols.LongColumn;

public final class Sum<T extends Number> implements Func<T> {

    private final Integer col;
    private Table table;

    public Sum(int col) {
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
            return (T) sumDouble();
        } else if (column instanceof IntegerColumn){
            return (T) sumInteger();
        } else if (column instanceof LongColumn){
            return (T)sumLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support column type %.",
                    this.getClass().getSimpleName(),
                    column.getClass().getSimpleName()));
        }

    }

    private Double sumDouble() {
        return table.getValues(col).stream().mapToDouble( x -> ((Number)x).doubleValue()).sum();
    }

    private Integer sumInteger() {
        return table.getValues(col).stream().mapToInt( x -> ((Number)x).intValue()).sum();
    }

    private Long sumLong() {
        return table.getValues(col).stream().mapToLong( x -> ((Number)x).longValue()).sum();
    }
}
