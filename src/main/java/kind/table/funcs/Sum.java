package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

import java.util.List;

public final class Sum<T extends Number>  implements Func<T> {

    private final Integer col;
    private Table table;

    public static <E extends Number> Sum<E> of(int col) { return new Sum<>(col); }
    public Sum(int col) {
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

        final Column column = table.getColByIndex(this.col);
        final List<T> values = table.getVals(this.col);

        if (column instanceof DblColumn){
            return (T)sumDouble(values);
        } else if (column instanceof IntColumn){
            return (T)sumInteger(values);
        } else if (column instanceof LngColumn){
            return (T)sumLong(values);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support column type %s.",
                    this.getClass().getSimpleName(),
                    column.getClass().getSimpleName()));
        }

    }


    private Double sumDouble(List<T> values) {
        return values.
                stream().
                mapToDouble( x -> x.doubleValue()).sum();
    }

    private Integer sumInteger(List<T> values) {
        return values.
                stream().
                mapToInt( x -> x.intValue()).sum();
    }

    private Long sumLong(List<T> values) {
        return values.
                stream().
                mapToLong( x -> x.longValue()).sum();
    }
}
