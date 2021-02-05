package kind.table.writers;

import kind.table.Table;

/**
 * Interface for
 *
 * @see {@link Markdown}, {@link Csv},
 * {@link Tsv}
 */
public interface TableWriter {
    String NULL_VALUE = "{null}";
    void write(Table table);
}
