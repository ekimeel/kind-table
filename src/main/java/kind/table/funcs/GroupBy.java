package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.*;
import kind.table.cols.Col;
import kind.table.cols.RowCol;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The type Group by.
 */
public final class GroupBy extends AbstractFunc<Table> {

    private static final String COLUMN_POSTFIX = " Group";

    /**
     * Of group by.
     *
     * @param col   the col
     * @param col1  the col 1
     * @param func1 the func 1
     * @return the group by
     */
    public static GroupBy of(int col, String col1, Func func1) { return new GroupBy(ColRef.of(col), SummaryCol.of(col1,func1)); }

    /**
     * Of group by.
     *
     * @param col         the col
     * @param summaryCols the summary cols
     * @return the group by
     */
    public static GroupBy of(int col, SummaryCol... summaryCols) { return new GroupBy(ColRef.of(col), summaryCols); }

    /**
     * Of group by.
     *
     * @param col   the col
     * @param col1  the col 1
     * @param func1 the func 1
     * @return the group by
     */
    public static GroupBy of(String col, String col1, Func func1) { return new GroupBy(ColRef.of(col), SummaryCol.of(col1,func1)); }

    /**
     * Of group by.
     *
     * @param col         the col
     * @param summaryCols the summary cols
     * @return the group by
     */
    public static GroupBy of(String col, SummaryCol... summaryCols) { return new GroupBy(ColRef.of(col), summaryCols); }
    /**/
    private final ColRef colRef;
    private final List<SummaryCol> aggs;

    /**
     * Instantiates a new Group by.
     *
     * @param colRef the col ref
     * @param agg    the agg
     */
    public GroupBy(ColRef colRef, SummaryCol... agg) {
        this.colRef = colRef;
        this.aggs = Arrays.asList(agg);
    }

    @Override
    protected void beforeEval(Table table) {
        errorIfNull(table);
        errorIfNull(this.colRef);
        errorIfColRefNotFound(table, this.colRef);
    }

    @Override
    public Table evalTemplate(Table table) {

        final Col keyCol = table.getColByRef(this.colRef);
        final Map<Object, List<Row>> grouping = table.
                getRows().
                stream().
                collect(Collectors.groupingBy((r) -> r.get(keyCol.getIndex())));

        final Table result = new Table(table.getSettings());
        result.addCol((Col) keyCol.copy());

        final RowCol rowCol = new RowCol(keyCol.getName() + COLUMN_POSTFIX);
        result.addCol(rowCol);

        for(Map.Entry<Object, List<Row>> entry : grouping.entrySet()) {
            result.addRow(Row.from(entry.getKey(), entry.getValue()));
        }

        doAg(result, rowCol, table.getCols());

        return result;

    }

    private void doAg(Table table, RowCol rowCol, Collection<Col> cols) {


        final List<List<Row>> groups = table.getVals(rowCol);
        for(SummaryCol agg : aggs) {
            final Func aggFunc = agg.getFunc();
            final String aggName = agg.getName();

            for (int rowIndex = 0; rowIndex < groups.size(); rowIndex++) {

                final List<Row> group = groups.get(rowIndex);
                final Table subject = new Table(cols, table.getSettings());
                subject.addRows(group);
                final Object eval = subject.eval(aggFunc);
                final Col aggCol = ColFactory.from(aggName, eval);
                if (aggCol != null) {
                    table.addCol(aggCol);
                    int col = table.getColIndex(aggCol.getName());
                    table.set(rowIndex, col, eval);
                }

            }
            table.removeCol(rowCol);
        }
    }


}
