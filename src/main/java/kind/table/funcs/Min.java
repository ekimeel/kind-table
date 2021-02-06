package kind.table.funcs;

import com.google.common.util.concurrent.AtomicDouble;
import kind.table.*;
import kind.table.cols.*;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * The type Min.
 *
 * @param <T> the type parameter
 */
public final class Min<T extends Number> implements Func<T> {

    /**
     * Of min.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the min
     */
    public static <E extends Number> Min<E> of(String col) { return new Min(ColRef.of(col)); }

    /**
     * Of min.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the min
     */
    public static <E extends Number> Min<E> of(int col) { return new Min(ColRef.of(col)); }
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
        final Spliterator<Row> spliterator = table.rowSpliterator( (x) -> (x.get(index) != null));

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
        final AtomicDouble x = new AtomicDouble(Double.MAX_VALUE);
        rowSpliterator.forEachRemaining((i) -> {
            final Double v = i.get(index);
            if (v != null) { x.set(Math.min(x.get(), v)); }
        });
        return x.get();
    }

    private Integer minInteger(Spliterator<Row> rowSpliterator, int index) {
        final AtomicInteger x = new AtomicInteger(Integer.MAX_VALUE);
        rowSpliterator.forEachRemaining((i) -> {
            final Integer v = i.get(index);
            if (v != null) { x.set(Math.min(x.get(), v)); }
        });
        return x.get();
    }

    private Long minLong(Spliterator<Row> rowSpliterator, int index) {
        final AtomicLong x = new AtomicLong(Long.MAX_VALUE);

        rowSpliterator.forEachRemaining((i) -> {
            final Long v = i.get(index);
            if (v != null) { x.set(Math.min(x.get(), v)); }
        });
        return x.get();
    }
}
