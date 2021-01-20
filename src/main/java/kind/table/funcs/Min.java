package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

public final class Min<T extends Number> implements Func<T> {

    public static <E extends Number> Min<E> of(String col) { return new Min(ColRef.from(col)); }
    public static <E extends Number> Min<E> of(int col) { return new Min(ColRef.from(col)); }
    /**/
    private final ColRef colRef;
    private Table table;

    private Min(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return (column instanceof NumberColumn);
    }

    @Override
    public T eval(Table table) {
        if (table == null) {
            return null;
        }
        this.table = table;
        final Column column = table.getColByRef(this.colRef);

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
