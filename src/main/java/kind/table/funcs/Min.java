package kind.table.funcs;

import com.google.common.util.concurrent.AtomicDouble;
import kind.table.*;
import kind.table.cols.*;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public final class Min<T extends Number> implements Func<T> {

    public static <E extends Number> Min<E> from(String col) { return new Min(ColRef.of(col)); }
    public static <E extends Number> Min<E> from(int col) { return new Min(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    private Min(ColRef colRef) {
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
            return (T) minDouble(spliterator, index);
        } else if (col instanceof IntCol){
            return (T) minInteger(spliterator, index);
        } else if (col instanceof LngCol){
            return (T)minLong(spliterator, index);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }

    private Double minDouble(Spliterator<Row> rowSpliterator, int index) {
        final AtomicDouble max = new AtomicDouble(Double.MAX_VALUE);
        rowSpliterator.forEachRemaining((i) -> {
            max.set(Math.min(max.get(), i.get(index)));
        });
        return max.get();
    }

    private Integer minInteger(Spliterator<Row> rowSpliterator, int index) {
        final AtomicInteger max = new AtomicInteger(Integer.MAX_VALUE);
        rowSpliterator.forEachRemaining((i) -> {
            max.set(Math.min(max.get(), i.get(index)));
        });
        return max.get();
    }

    private Long minLong(Spliterator<Row> rowSpliterator, int index) {
        final AtomicLong max = new AtomicLong(Long.MAX_VALUE);

        rowSpliterator.forEachRemaining((i) -> {
            max.set(Math.min(max.get(), i.get(index)));
        });
        return max.get();
    }
}
