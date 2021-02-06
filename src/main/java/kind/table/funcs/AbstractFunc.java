package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

/**
 * The type Abstract func.
 *
 * @param <T> the type parameter
 */
public abstract class AbstractFunc<T> implements Func<T> {

    /**
     * Before eval.
     *
     * @param table the table
     */
    protected void beforeEval(Table table){
        // do nothing by default
    }

    /**
     * After eval.
     *
     * @param result the result
     */
    protected void afterEval(T result) {
        // do nothing by default
    }

    /**
     * Error if null.
     *
     * @param table the table
     */
    protected void errorIfNull(Table table) {
        if (table == null) throw new NullPointerException("null table");
    }

    /**
     * Error if null.
     *
     * @param ref the ref
     */
    protected void errorIfNull(ColRef ref) {
        if (ref == null) throw new NullPointerException("no column ref provided");
    }

    /**
     * Error if col ref not found.
     *
     * @param table the table
     * @param ref   the ref
     */
    protected void errorIfColRefNotFound(Table table, ColRef ref) {
        if (table.getColByRef(ref) == null) {
            throw new IllegalArgumentException(String.format("no col found for [%s].", ref));
        }
    }


    /**
     * Eval template t.
     *
     * @param table the table
     * @return the t
     */
    protected abstract T evalTemplate(Table table);

    @Override
    public T eval(Table table) {

        beforeEval(table);
        final T t = evalTemplate(table);
        afterEval(t);
        return t;
    }

    @Override
    public boolean acceptCol(Col col) {
        return false;
    }
}
