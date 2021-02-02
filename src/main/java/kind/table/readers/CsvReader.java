package kind.table.readers;

import com.google.common.base.Splitter;
import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.TableSettings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public final class CsvReader implements TableReader {

    protected static final boolean DEFAULT_HAS_COLUMN_HEADERS = true;
    protected static final boolean DEFAULT_HAS_QUOTED_VALUES = false;
    protected static final Pattern SPLIT_PATTERN = Pattern.compile(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
    /**/
    private final Path path;
    private final boolean hasColHeaders;
    private final boolean hasQuotedValues;
    private final TableSettings tableSettings;

    public CsvReader(Path path) {
        this.path = path;
        this.hasColHeaders = DEFAULT_HAS_COLUMN_HEADERS;
        this.hasQuotedValues = DEFAULT_HAS_QUOTED_VALUES;
        this.tableSettings = TableSettings.DEFAULT_SETTINGS;
    }

    public CsvReader(Path path, TableSettings tableSettings) {
        this.path = path;
        this.tableSettings = tableSettings;
        this.hasColHeaders = DEFAULT_HAS_COLUMN_HEADERS;
        this.hasQuotedValues = DEFAULT_HAS_QUOTED_VALUES;
    }

    public CsvReader(Path path, TableSettings tableSettings, boolean hasColHeaders) {
        this.path = path;
        this.tableSettings = tableSettings;
        this.hasColHeaders = hasColHeaders;
        this.hasQuotedValues = DEFAULT_HAS_QUOTED_VALUES;
    }

    public CsvReader(Path path, TableSettings tableSettings, boolean hasColHeaders, boolean hasQuotedValue) {
        this.path = path;
        this.tableSettings = tableSettings;
        this.hasColHeaders = hasColHeaders;
        this.hasQuotedValues = hasQuotedValue;
    }

    public Table read() {

        return readData();
    }

    public boolean hasColumnHeaders() {
        return hasColHeaders;
    }

    private Table readData() {
        final TableBuilder tableBuilder = new TableBuilder();
        tableBuilder.withSettings(this.tableSettings);

        final int startReadingAtLine = hasColumnHeaders() ? 1 : 0;

        int i = 0;
        try (BufferedReader br = new BufferedReader((new FileReader(path.toFile())))) {
            String line;
            while ((line = br.readLine()) != null) {
                final Splitter splitter = Splitter.on(SPLIT_PATTERN);
                final List cells = splitter.splitToList(line);

                if (hasColumnHeaders() && i < startReadingAtLine) {
                    tableBuilder.withCols(cells);
                }
                if (i >= startReadingAtLine) {
                    tableBuilder.addRow(cells);
                }
                i++;
            }
        } catch (Exception ex) {
            throw new RuntimeException("Failed to parse file at line [" + i + "]." + ex.getMessage(), ex);
        }
        return tableBuilder.build();
    }

}

