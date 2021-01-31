package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.*;
import kind.table.cols.Col;
import kind.table.cols.RowCol;

import java.util.*;
import java.util.stream.Collectors;

public final class GroupBy implements Func<Table> {

    private static final String COLUMN_POSTFIX = " Group";
    private final ColRef colRef;
    private final List<SummaryCol> aggs;

    public static GroupBy from(int col, String col1, Func func1) { return new GroupBy(ColRef.of(col), SummaryCol.of(col1,func1)); }
    public static GroupBy from(int col, SummaryCol... summaryCols) { return new GroupBy(ColRef.of(col), summaryCols); }
    public static GroupBy from(String col, String col1, Func func1) { return new GroupBy(ColRef.of(col), SummaryCol.of(col1,func1)); }
    public static GroupBy from(String col, SummaryCol... summaryCols) { return new GroupBy(ColRef.of(col), summaryCols); }

    private GroupBy(ColRef colRef) {
        this.colRef = colRef;
        this.aggs = new ArrayList<>();
    }

    public GroupBy(ColRef colRef, SummaryCol... agg) {
        this.colRef = colRef;
        this.aggs = Arrays.asList(agg);
    }


    @Override
    public boolean acceptCol(Col col) {
        return true;
    }

    @Override
    public Table eval(Table table) {

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
            result.addRow(Row.of(entry.getKey(), entry.getValue()));
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
