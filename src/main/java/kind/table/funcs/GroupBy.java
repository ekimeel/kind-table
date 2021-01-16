package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.Column;
import kind.table.cols.ColumnFactory;
import kind.table.cols.GroupColumn;
import kind.table.cols.RowColumn;

import java.util.*;
import java.util.stream.Collectors;

public final class GroupBy implements Func<Table> {

    private static final String COLUMN_POSTFIX = " Group";
    private final int col;
    private final List<GroupColumn> aggs;

    public static GroupBy of(int col, String col1, Func func1) {
        return new GroupBy(col, GroupColumn.of(col1,func1));
    }
    public static GroupBy of(int col, GroupColumn... groupColumns) { return new GroupBy(col, groupColumns); }

    public GroupBy(int col) {
        this.col = col;
        this.aggs = new ArrayList<>();
    }

    public GroupBy(int col, GroupColumn... agg) {
        this.col = col;
        this.aggs = Arrays.asList(agg);
    }


    @Override
    public boolean acceptColumn(Column column) {
        return true;
    }

    @Override
    public Table eval(Table table) {

        final Column column = table.getCol(col);

        final Map<Object, List<Row>> grouping = table.
                getRows().
                stream().
                collect(Collectors.groupingBy((r) -> r.get(column.getIndex())));

        final Table result = new Table(table.getSettings());
        result.addCol((Column) column.copy());

        final RowColumn rowColumn = new RowColumn(column.getName() + COLUMN_POSTFIX);
        result.addCol(rowColumn);

        for(Map.Entry<Object, List<Row>> entry : grouping.entrySet()) {
            result.addRow(new Row(entry.getKey(), entry.getValue()));
        }

        doAg(result, rowColumn, table.getCols());

        return result;

    }

    private void doAg(Table table, RowColumn rowColumn, Collection<Column> cols) {

        final List<Column> columns = table.getCols().
                stream().filter( (i) -> i.getIndex() != this.col).
                collect(Collectors.toList());

        for(GroupColumn agg : aggs) {
            final Func aggFunc = agg.getFunc();
            final String aggName = agg.getName();

            final List<List<Row>> groups = table.getVals(rowColumn);
            for (int rowIndex = 0; rowIndex < groups.size(); rowIndex++) {
                final List<Row> group = groups.get(rowIndex);
                final Table subject = new Table(cols, table.getSettings());
                subject.addRows(group);
                final Object eval = subject.eval(aggFunc);
                final Column aggCol = ColumnFactory.from(aggName, eval);
                if (aggCol != null) {
                    table.addCol(aggCol);
                    int col = table.getColIndex(aggCol.getName());
                    table.set(rowIndex, col, eval);
                }

            }
            table.removeCol(rowColumn);
        }
    }


}
