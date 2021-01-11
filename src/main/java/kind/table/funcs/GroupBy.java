package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.Column;
import kind.table.cols.ListColumn;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class GroupBy implements Func<Table> {

    private static final String COLUMN_POSTFIX = " Group";
    private final int col;

    public GroupBy(int col) {
        this.col = col;
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
                collect(Collectors.groupingBy((r) -> r.get(this.col)));

        final Table result = new Table(table.getSettings());
        result.addColumn((Column) column.copy());
        result.addColumn(new ListColumn(column.getName() + COLUMN_POSTFIX));

        for(Map.Entry<Object, List<Row>> entry : grouping.entrySet()){
            result.addRow(new Row(entry.getKey(), entry.getValue()));
        }

        return result;

    }


}
