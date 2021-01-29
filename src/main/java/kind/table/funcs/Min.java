package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

import java.util.stream.Stream;

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
        final Stream<Object> stream = (table.allowParallelProcessing())?
                table.getVals(this.colRef).parallelStream() :
                table.getVals(this.colRef).stream();

        if (col instanceof DblCol){
            return (T) minDouble(stream);
        } else if (col instanceof IntCol){
            return (T) minInteger(stream);
        } else if (col instanceof LngCol){
            return (T)minLong(stream);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }

    private Double minDouble(Stream<Object> stream) {
        return stream
                .mapToDouble(x -> ((Number)x).doubleValue())
                .min().getAsDouble();
    }

    private Integer minInteger(Stream<Object> stream) {
        return stream
                .mapToInt(x -> ((Number)x).intValue())
                .min().getAsInt();
    }

    private Long minLong(Stream<Object> stream) {
        return stream
                .mapToLong(x -> ((Number)x).longValue())
                .min().getAsLong();
    }
}
