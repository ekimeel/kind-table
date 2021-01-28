package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

import java.util.List;

public final class Sum<T extends Number> implements Func<T> {

    public static <E extends Number> Sum<E> from(String col) { return new Sum<>(ColRef.of(col)); }
    public static <E extends Number> Sum<E> from(int col) { return new Sum<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    public Sum(ColRef colRef) {
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
        final List<T> values = table.getVals(this.colRef);

        if (col instanceof DblCol){
            return (T)sumDouble(values);
        } else if (col instanceof IntCol){
            return (T)sumInteger(values);
        } else if (col instanceof LngCol){
            return (T)sumLong(values);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %s.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
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
