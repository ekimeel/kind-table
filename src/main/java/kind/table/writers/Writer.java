package kind.table.writers;

import kind.table.Table;

/**
 * Interface for
 *
 * @see Markdown Csv
 * {@link Tsv}
 */
public interface Writer {
    String NULL_VALUE = "{null}";
    void write(Table table);
}
