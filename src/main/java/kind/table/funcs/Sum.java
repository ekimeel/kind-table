package kind.table.funcs;

import com.google.common.util.concurrent.AtomicDouble;
import kind.table.*;
import kind.table.cols.*;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public final class Sum<T extends Number> implements Func<T> {

    public static <E extends Number> Sum<E> of(String col) { return new Sum<>(ColRef.of(col)); }
    public static <E extends Number> Sum<E> of(int col) { return new Sum<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    public Sum(ColRef colRef) {
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
        final Spliterator<Row> stream = (table.allowParallelProcessing())?
                table.getRows().parallelStream().spliterator() :
                table.getRows().stream().spliterator();

        if (col instanceof DblCol){
            return (T)sumDouble(stream, index);
        } else if (col instanceof IntCol){
            return (T)sumInteger(stream, index);
        } else if (col instanceof LngCol){
            return (T)sumLong(stream, index);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %s.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }

    private Double sumDouble(Spliterator<Row> rowSpliterator, int index) {
        final AtomicDouble sum = new AtomicDouble(0.0);

        rowSpliterator.forEachRemaining( (i) -> {
            sum.set(sum.get() + (Double)i.get(index));
        });
        return sum.get();
    }

    private Integer sumInteger(Spliterator<Row> rowSpliterator, int index) {
        final AtomicInteger sum = new AtomicInteger(0);

        rowSpliterator.forEachRemaining( (i) -> {
            sum.set(sum.get() + (Integer)i.get(index));
        });
        return sum.get();

    }

    private Long sumLong(Spliterator<Row> rowSpliterator, int index) {
        final AtomicLong sum = new AtomicLong(0L);

        rowSpliterator.forEachRemaining( (i) -> {
            sum.set(sum.get() + (Long)i.get(index));
        });
        return sum.get();
    }
}
