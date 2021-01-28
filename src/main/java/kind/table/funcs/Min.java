package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

public final class Min<T extends Number> implements Func<T> {

    public static <E extends Number> Min<E> from(String col) { return new Min(ColRef.of(col)); }
    public static <E extends Number> Min<E> from(int col) { return new Min(ColRef.of(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    private Min(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public T eval(Table table) {
        if (table == null) {
            return null;
        }
        this.table = table;
        final Col col = table.getColByRef(this.colRef);

        if (col instanceof DblCol){
            return (T) minDouble();
        } else if (col instanceof IntCol){
            return (T) minInteger();
        } else if (col instanceof LngCol){
            return (T)minLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }

    private Double minDouble() {
        return table.getVals(colRef)
                .stream()
                .mapToDouble(x -> ((Number)x).doubleValue())
                .min().getAsDouble();
    }

    private Integer minInteger() {
        return table.getVals(colRef)
                .stream()
                .mapToInt(x -> ((Number)x).intValue())
                .min().getAsInt();
    }

    private Long minLong() {
        return table.getVals(colRef)
                .stream()
                .mapToLong(x -> ((Number)x).longValue())
                .min().getAsLong();
    }
}
