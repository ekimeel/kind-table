package kind.table.funcs;

import kind.table.*;
import kind.table.cols.*;

import java.util.stream.Stream;

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
        final Stream stream = (table.allowParallelProcessing())?
                table.getVals(this.colRef).parallelStream() :
                table.getVals(this.colRef).stream();

        if (col instanceof DblCol){
            return (T)sumDouble(stream);
        } else if (col instanceof IntCol){
            return (T)sumInteger(stream);
        } else if (col instanceof LngCol){
            return (T)sumLong(stream);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %s.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }


    private Double sumDouble(Stream<Number> stream) {
        return stream
                .reduce(0, NaiveMath::sumDbl)
                .doubleValue();
    }

    private Integer sumInteger(Stream<T> stream) {
        return stream
                .mapToInt( x -> x.intValue())
                .sum();
    }

    private Long sumLong(Stream<T> stream) {
        return stream
                .mapToLong( x -> x.longValue())
                .sum();
    }
}
