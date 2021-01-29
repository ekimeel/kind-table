package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;
import java.util.OptionalInt;
import java.util.stream.Stream;

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
        final Stream<Object> stream = (table.allowParallelProcessing())?
                table.getVals(this.colRef).parallelStream() :
                table.getVals(this.colRef).stream();

        if (col instanceof DblCol){
            return (T) maxDouble(stream);
        } else if (col instanceof IntCol){
            return (T) maxInteger(stream);
        } else if (col instanceof LngCol){
            return (T) maxLong(stream);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }

    private Double maxDouble(Stream<Object> stream) {
        return stream
                .mapToDouble(x -> ((Number)x).doubleValue())
                .max().getAsDouble();
    }

    private Integer maxInteger(Stream<Object> stream) {
        return stream
                .mapToInt( x -> ((Number)x).intValue())
                .max()
                .getAsInt();
    }

    private Long maxLong(Stream<Object> stream) {
        return stream
                .mapToLong(x -> ((Number)x).longValue())
                .max().getAsLong();
    }
}
