package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * The type Count if.
 */
public final class CountIf extends AbstractFunc<Integer> {

    /**
     * Of count if.
     *
     * @param col       the col
     * @param predicate the predicate
     * @return the count if
     */
    public static CountIf of(String col, Predicate predicate) { return new CountIf(ColRef.of(col), predicate); }

    /**
     * Of count if.
     *
     * @param col       the col
     * @param predicate the predicate
     * @return the count if
     */
    public static CountIf of(int col, Predicate predicate) { return new CountIf(ColRef.of(col), predicate); }

    /**/
    private final ColRef colRef;
    private final Predicate predicate;

    /**
     * Instantiates a new Count if.
     *
     * @param colRef    the col ref
     * @param predicate the predicate
     */
    private CountIf(ColRef colRef, Predicate predicate) {
        this.colRef = colRef;
        this.predicate = predicate;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfColRefNotFound(table, this.colRef);
    }


    @Override
    public Integer evalTemplate(Table table) {

        final Col col = table.getColByRef(this.colRef);
        final int index = col.getIndex();
        final Spliterator<Row> spliterator = table.rowSpliterator( (r) -> r.get(index) != null );

        final AtomicInteger count = new AtomicInteger(0);
        spliterator.forEachRemaining( (i) -> {
            if (predicate.test(i.get(index))) { count.set(count.get() + 1); }
        });

        return count.get();

    }

}
