package kind.table;

import kind.table.cols.*;
import kind.table.readers.CsvReader;
import kind.table.readers.TableReader;

import java.nio.file.Path;
import java.util.*;

import static kind.support.Coalesce.coalesce;

public class TableBuilder {

    private TableSettings settings;
    private TableReader reader;
    private final List<Col> cols = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();

    public TableBuilder withSettings(TableSettings settings) {
        this.settings = settings;
        return this;
    }
    public TableBuilder withCol(Col col) {
        this.cols.add(col);
        return this;
    }

    /**
     * Adds the column names in order as StCol
     * @param names names of columns
     * @see this.withStrCol
     * @return
     */
    public TableBuilder withCols(Collection<String> names) {
        for(String name : names) {
            this.withStrCol(name);
        }
        return this;
    }

    public TableBuilder withStrCol(String name) {
        return withCol(StrCol.of(name));
    }

    public TableBuilder withIntCol(String name) {
        return withCol(IntCol.of(name));
    }

    public TableBuilder withDbCol(String name) {
        return withCol(DblCol.of(name));
    }

    public TableBuilder withTsCol(String name) {
        return withCol(TsCol.of(name));
    }

    public TableBuilder withLngCol(String name) {
        return withCol(LngCol.of(name));
    }

    public TableBuilder withBoolCol(String name) {
        return withCol(BoolCol.of(name));
    }

    public TableBuilder withDateCol(String name) {
        return withCol(DateCol.of(name));
    }

    public TableBuilder withCsvFile(Path path) {
        this.reader = new CsvReader(path);
        return this;
    }
    public TableBuilder withTableReader(TableReader reader) {
        this.reader = reader;
        return this;
    }

    public TableBuilder addRow(Object[] row) {
        return this.addRow(Row.from((ArrayList) Arrays.asList(row)));
    }

    public TableBuilder addRow(Collection row) {
        return this.addRow(Row.from(row));
    }

    public TableBuilder addRow(Row row) {
        this.rows.add(row);
        return this;
    }


    public Table build() {
        Table table;

        if (this.reader != null) {
            table = this.reader.read();
        } else {
            table = new Table(coalesce(settings,
                    TableSettings.DEFAULT_SETTINGS));
            table.addCols(this.cols);
            table.addRows(this.rows);
        }




        return table;
    }

    public static Table empty() {
        return new TableBuilder().build();
    }


}
