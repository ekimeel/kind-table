package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

public final class Min<T extends Number> implements Func<T> {

    private final Integer col;
    private Table table;

    public Min(int col) {
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

        if (column instanceof DblColumn){
            return (T) minDouble();
        } else if (column instanceof IntColumn){
            return (T) minInteger();
        } else if (column instanceof LngColumn){
            return (T)minLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support column type %.",
                    this.getClass().getSimpleName(),
                    column.getClass().getSimpleName()));
        }

    }

    private Double minDouble() {
        return table.getVals(col).stream().mapToDouble(x -> ((Number)x).doubleValue()).min().getAsDouble();
    }

    private Integer minInteger() {
        return table.getVals(col).stream().mapToInt(x -> ((Number)x).intValue()).min().getAsInt();
    }

    private Long minLong() {
        return table.getVals(col).stream().mapToLong(x -> ((Number)x).longValue()).min().getAsLong();
    }
}
