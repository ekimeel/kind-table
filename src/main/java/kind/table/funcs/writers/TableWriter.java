package kind.table.funcs.writers;

import kind.table.Table;

/**
 * Interface for
 *
 * @see {@link kind.table.funcs.writers.Markdown}, {@link kind.table.funcs.writers.Csv},
 * {@link kind.table.funcs.writers.Tsv}
 */
public interface TableWriter {
    String NULL_VALUE = "{null}";
    void write(Table table);
}
