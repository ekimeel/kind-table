package kind.table.writers;


import kind.table.Table;
import kind.table.cols.Col;
import kind.table.cols.ColRef;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * The type Markdown.
 */
public final class Markdown extends AbstractTableWriter {


    Markdown(PrintStream stream, Map<ColRef, String> formats) {
        super(stream, formats);
    }


    @Override
    public void writeTemplate(Table table) {
        this.evaluateFormatIndexes(table);

        final StringBuilder sb = new StringBuilder(500);

        table.getCols().forEach( (i) -> sb.append("| ").append(i.getName()) );
        sb.append("|"); // end cap
        stream.println(sb.toString());
        sb.setLength(0);

        table.getCols().forEach( (i) -> sb.append("| ").append("---") );
        sb.append("|"); // end cap
        stream.println(sb.toString());
        sb.setLength(0);

        table.rowIterator().forEachRemaining( row -> {
            for (int i = 0; i < row.size(); i++) {
                final Object obj = row.get(i);
                final String value = this.format(i, obj);
                sb.append("| ").append(value);
            };
            sb.append("|"); // end cap
            stream.println(sb.toString());
            sb.setLength(0);
        });
        sb.setLength(0);
    }

}
