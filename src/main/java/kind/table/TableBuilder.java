package kind.table;

import kind.table.cols.Column;

import java.util.*;

public class TableBuilder {

    private String name;
    private final List<Column> columns = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();


    public TableBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public TableBuilder withColumn(Column column) {
        this.columns.add(column);
        return this;
    }

    public TableBuilder addRow(Object[] row) {
        return this.addRow(new Row(Arrays.asList((Object[]) row)));
    }

    public TableBuilder addRow(Row row) {
        this.rows.add(row);
        return this;
    }

    public Table build() {

        final Table table = new Table(this.columns);

        return table;
    }

    public static Table empty() {
        return new TableBuilder().build();
    }


}
