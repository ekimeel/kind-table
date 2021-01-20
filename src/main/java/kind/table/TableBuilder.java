package kind.table;

import kind.table.cols.*;

import java.util.*;

public class TableBuilder {

    private final List<Column> cols = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();

    public TableBuilder withCol(Column column) {
        this.cols.add(column);
        return this;
    }

    public TableBuilder withStrCol(String name) {
        return withCol(StrColumn.of(name));
    }

    public TableBuilder withIntCol(String name) {
        return withCol(IntColumn.of(name));
    }

    public TableBuilder withLngCol(String name) {
        return withCol(LngColumn.of(name));
    }

    public TableBuilder withBoolCol(String name) {
        return withCol(BoolColumn.of(name));
    }

    public TableBuilder withDateCol(String name) {
        return withCol(DateColumn.of(name));
    }

    public TableBuilder addRow(Object[] row) {
        return this.addRow(new Row(Arrays.asList((Object[]) row)));
    }

    public TableBuilder addRow(Row row) {
        this.rows.add(row);
        return this;
    }

    public Table build() {

        final Table table = new Table(this.cols);

        return table;
    }

    public static Table empty() {
        return new TableBuilder().build();
    }


}
