package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;
import java.util.OptionalInt;

public final class Max<T extends Number> implements Func<T> {

    public static <E extends Number> Max<E> of(String col) { return new Max<>(ColRef.from(col)); }
    public static <E extends Number> Max<E> of(int col) { return new Max<>(ColRef.from(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    public Max(ColRef colRef) {
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
            return (T) maxDouble();
        } else if (column instanceof IntColumn){
            return (T) maxInteger();
        } else if (column instanceof LngColumn){
            return (T) maxLong();
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support column type %.",
                    this.getClass().getSimpleName(),
                    column.getClass().getSimpleName()));
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
