package kind.table;

import kind.table.cols.*;
import kind.table.readers.CsvReader;
import kind.table.readers.TableReader;

import java.nio.file.Path;
import java.util.*;

import static kind.table.util.Coalesce.coalesce;

/**
 * The type Table builder.
 */
public class TableBuilder {

    private TableSettings settings;
    private TableReader reader;
    private final List<Col> cols = new ArrayList<>();
    private final List<Row> rows = new ArrayList<>();

    /**
     * With settings table builder.
     *
     * @param settings the settings
     * @return the table builder
     */
    public TableBuilder withSettings(TableSettings settings) {
        this.settings = settings;
        return this;
    }

    /**
     * With col table builder.
     *
     * @param col the col
     * @return the table builder
     */
    public TableBuilder withCol(Col col) {
        this.cols.add(col);
        return this;
    }

    /**
     * Adds the column names in order as StCol
     *
     * @param names names of columns
     * @return table builder
     */
    public TableBuilder withCols(Collection<String> names) {
        for(String name : names) {
            this.withStrCol(name);
        }
        return this;
    }

    /**
     * With str col table builder.
     *
     * @param name the name
     * @return the table builder
     */
    public TableBuilder withStrCol(String name) {
        return withCol(StrCol.of(name));
    }

    /**
     * With int col table builder.
     *
     * @param name the name
     * @return the table builder
     */
    public TableBuilder withIntCol(String name) {
        return withCol(IntCol.of(name));
    }

    /**
     * With dbl col table builder.
     *
     * @param name the name
     * @return the table builder
     */
    public TableBuilder withDblCol(String name) {
        return withCol(DblCol.of(name));
    }

    /**
     * With ts col table builder.
     *
     * @param name the name
     * @return the table builder
     */
    public TableBuilder withTsCol(String name) {
        return withCol(TsCol.of(name));
    }

    /**
     * With lng col table builder.
     *
     * @param name the name
     * @return the table builder
     */
    public TableBuilder withLngCol(String name) {
        return withCol(LngCol.of(name));
    }

    /**
     * With bool col table builder.
     *
     * @param name the name
     * @return the table builder
     */
    public TableBuilder withBoolCol(String name) {
        return withCol(BoolCol.of(name));
    }

    /**
     * With date col table builder.
     *
     * @param name the name
     * @return the table builder
     */
    public TableBuilder withDateCol(String name) {
        return withCol(DateCol.of(name));
    }

    /**
     * With csv file table builder.
     *
     * @param path the path
     * @return the table builder
     */
    public TableBuilder withCsvFile(Path path) {
        this.reader = new CsvReader(path);
        return this;
    }

    /**
     * With table reader table builder.
     *
     * @param reader the reader
     * @return the table builder
     */
    public TableBuilder withTableReader(TableReader reader) {
        this.reader = reader;
        return this;
    }

    /**
     * Add row table builder.
     *
     * @param row the row
     * @return the table builder
     */
    public TableBuilder addRow(Object[] row) {
        return this.addRow(Row.from((ArrayList) Arrays.asList(row)));
    }

    /**
     * Add row table builder.
     *
     * @param row the row
     * @return the table builder
     */
    public TableBuilder addRow(Collection row) {
        return this.addRow(Row.from(row));
    }

    /**
     * Add row table builder.
     *
     * @param row the row
     * @return the table builder
     */
    public TableBuilder addRow(Row row) {
        this.rows.add(row);
        return this;
    }


    /**
     * Build table.
     *
     * @return the table
     */
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

    /**
     * Empty table.
     *
     * @return the table
     */
    public static Table empty() {
        return new TableBuilder().build();
    }


}
