package kind.table.writers;

import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Abstract table writer.
 */
public abstract class AbstractWriter implements Writer {

    protected final Map<ColRef, String> formats;
    protected final PrintStream stream;
    private Map<Integer, String> formatIndexMap;

    /**
     * Instantiates a new Abstract table writer.
     *
     * @param stream  the stream
     * @param formats the formats
     */
    AbstractWriter(PrintStream stream, Map<ColRef, String> formats) {
        this.stream = stream;
        this.formats = formats;
    }

    /**
     * Evaluate format indexes.
     *
     * @param table the table
     */
    protected void evaluateFormatIndexes(Table table) {
        if (formats == null) {
            this.formatIndexMap = new HashMap<>(0);
        };

        this.formatIndexMap = new HashMap<>();
        formats.forEach( (k, v) -> {
            final Col col = table.getColByRef(k);
            if (col != null) {
                formatIndexMap.put(col.getIndex(), v);
            }
        });

    }

    /**
     * Format string.
     *
     * @param index the index
     * @param value the value
     * @return the string
     */
    protected String format(int index, Object value) {
        if (value == null) return NULL_VALUE;

        final String format = this.formatIndexMap.get(index);
        if (format == null) {
            return String.valueOf(value);
        } else {
            return String.format(format, value);
        }
    }

    /**
     * Write template.
     *
     * @param table the table
     */
    protected abstract void writeTemplate(Table table);

    @Override
    public void write(Table table) {
        this.evaluateFormatIndexes(table);
        writeTemplate(table);

    }
}
