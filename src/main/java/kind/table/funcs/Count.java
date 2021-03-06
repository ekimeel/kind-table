package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * The type Count.
 */
public final class Count extends AbstractFunc<Integer> {

    /**
     * Of count.
     *
     * @param col the col
     * @return the count
     */
    public static Count of(String col) { return new Count(ColRef.of(col)); }

    /**
     * Of count.
     *
     * @param col the col
     * @return the count
     */
    public static Count of(int col) { return new Count(ColRef.of(col)); }

    /**/
    private final ColRef colRef;


    /**
     * Instantiates a new Count.
     *
     * @param colRef the col ref
     */
    public Count(ColRef colRef) {
        this.colRef = colRef;
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfColRefNotFound(table, this.colRef);
    }

    @Override
    public Integer evalTemplate(Table table) {
        if (table == null) {
            return null;
        }

        final Stream<Object> stream = (table.allowParallelProcessing())?
                table.getVals(this.colRef).parallelStream() :
                table.getVals(this.colRef).stream();

        return (int) stream.filter(Objects::nonNull).count();

    }

}
