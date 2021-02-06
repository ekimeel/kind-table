package kind.table.funcs;

import com.google.common.util.concurrent.AtomicDouble;
import kind.table.*;
import kind.table.cols.*;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

/**
 * The type Sum.
 *
 * @param <T> the type parameter
 */
public final class Sum<T extends Number> extends AbstractFunc<T> {

    /**
     * Of sum.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the sum
     */
    public static <E extends Number> Sum<E> of(String col) { return new Sum<>(ColRef.of(col)); }

    /**
     * Of sum.
     *
     * @param <E> the type parameter
     * @param col the col
     * @return the sum
     */
    public static <E extends Number> Sum<E> of(int col) { return new Sum<>(ColRef.of(col)); }
    /**/
    private final ColRef colRef;

    /**
     * Instantiates a new Sum.
     *
     * @param colRef the col ref
     */
    public Sum(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfNotNumCol(this.colRef, table);
        errorIfColRefNotFound(table, this.colRef);
    }

    @Override
    public T evalTemplate(Table table) {
        if (table.isEmpty()) return null;

        final Col col = table.getColByRef(this.colRef);
        final int index = col.getIndex();
        final Spliterator<Row> spliterator = table.rowSpliterator( x -> (x.get(index) != null) );

        if (col instanceof DblCol){
            return (T)sumDouble(spliterator, index);
        } else if (col instanceof IntCol){
            return (T)sumInteger(spliterator, index);
        } else if (col instanceof LngCol){
            return (T)sumLong(spliterator, index);
        } else {
            throw new UnsupportedOperationException(String.format("%s does not support col type %s.",
                    this.getClass().getSimpleName(),
                    col.getClass().getSimpleName()));
        }

    }

    private Double sumDouble(Spliterator<Row> rowSpliterator, int index) {
        final AtomicDouble sum = new AtomicDouble(0.0);

        rowSpliterator.forEachRemaining( (i) -> {
            final Double v = i.get(index);
            if (v != null) {  sum.set(sum.get() + v); }
        });
        return sum.get();
    }

    private Integer sumInteger(Spliterator<Row> rowSpliterator, int index) {
        final AtomicInteger sum = new AtomicInteger(0);

        rowSpliterator.forEachRemaining( (i) -> {
            final Integer v = i.get(index);
            if (v != null) {  sum.set(sum.get() + v); }
        });
        return sum.get();

    }

    private Long sumLong(Spliterator<Row> rowSpliterator, int index) {
        final AtomicLong sum = new AtomicLong(0L);

        rowSpliterator.forEachRemaining( (i) -> {
            final Long v = i.get(index);
            if (v != null) {  sum.set(sum.get() + v); }
        });
        return sum.get();
    }
}
