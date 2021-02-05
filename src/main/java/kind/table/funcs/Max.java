package kind.table.funcs;

import com.google.common.util.concurrent.AtomicDouble;
import kind.table.*;
import kind.table.cols.*;
import java.util.OptionalInt;
import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public final class Max<T extends Number> implements Func<T> {

    public static <E extends Number> Max<E> of(String col) { return new Max<>(ColRef.of(col)); }
    public static <E extends Number> Max<E> of(int col) { return new Max<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    public Max(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    public boolean acceptCol(Col col) {
        return (col instanceof NumCol);
    }

    @Override
    public T eval(Table table) {
        if (table.isEmpty()) return null;

        final Col col = table.getColByRef(this.colRef);
        final int index = col.getIndex();
        final Spliterator<Row> spliterator = (table.allowParallelProcessing())?
                table.getRows().parallelStream().spliterator() :
                table.getRows().stream().spliterator();

        if (col instanceof DblCol){
            return (T) maxDouble(spliterator, index);
        } else if (col instanceof IntCol){
            return (T) maxInteger(spliterator, index);
        } else if (col instanceof LngCol){
            return (T) maxLong(spliterator, index);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }

    private Double maxDouble(Spliterator<Row> rowSpliterator, int index) {
        final AtomicDouble max = new AtomicDouble(Double.MIN_VALUE);
        rowSpliterator.forEachRemaining((i) -> {
            final Double v = i.get(index);
            if (v != null) { max.set(Math.max(max.get(), v)); }
        });
        return max.get();
    }

    private Integer maxInteger(Spliterator<Row> rowSpliterator, int index) {
        final AtomicInteger max = new AtomicInteger(Integer.MIN_VALUE);
        rowSpliterator.forEachRemaining((i) -> {
            final Integer v = i.get(index);
            if (v != null) { max.set(Math.max(max.get(), v)); }
        });
        return max.get();
    }

    private Long maxLong(Spliterator<Row> rowSpliterator, int index) {
        final AtomicLong max = new AtomicLong(Long.MIN_VALUE);
        rowSpliterator.forEachRemaining((i) -> {
            final Long v = i.get(index);
            if (v != null) { max.set(Math.max(max.get(), v)); }
        });
        return max.get();
    }
}
