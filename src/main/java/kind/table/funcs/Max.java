package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;
import java.util.OptionalInt;

public final class Max<T extends Number> implements Func<T> {

    public static <E extends Number> Max<E> from(String col) { return new Max<>(ColRef.of(col)); }
    public static <E extends Number> Max<E> from(int col) { return new Max<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    public Max(ColRef colRef) {
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
            return (T) maxDouble();
        } else if (col instanceof IntCol){
            return (T) maxInteger();
        } else if (col instanceof LngCol){
            return (T) maxLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }

    private Double maxDouble() {
        return table.getVals(colRef)
                .stream()
                .mapToDouble(x -> ((Number)x).doubleValue())
                .max().getAsDouble();
    }

    private Integer maxInteger() {
        OptionalInt v = table.getVals(colRef)
                .stream()
                .mapToInt( x -> ((Number)x).intValue())
                .max();


        return v.getAsInt();
    }

    private Long maxLong() {
        return table.getVals(colRef)
                .stream()
                .mapToLong(x -> ((Number)x).longValue())
                .max().getAsLong();
    }
}
