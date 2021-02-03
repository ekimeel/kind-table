package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

public abstract class AbstractFunc<T> implements Func<T> {

    protected void beforeEval(Table table){
        // do nothing by default
    }

    protected void afterEval(T result) {
        // do nothing by default
    }

    protected void errorIfNull(Table table) {
        if (table == null) throw new NullPointerException("null table");
    }

    protected void errorIfNull(ColRef ref) {
        if (ref == null) throw new NullPointerException("no column ref provided");
    }

    protected void errorIfColRefNotFound(Table table, ColRef ref) {
        if (table.getColByRef(ref) == null) {
            throw new IllegalArgumentException(String.format("no col found for [%s].", ref));
        }
    }


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
