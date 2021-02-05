package kind.table.writers;

import kind.table.Row;
import kind.table.Table;

import java.io.PrintStream;
import java.util.Iterator;

public final class Csv implements TableWriter {

    /**/
    private PrintStream stream;

    public Csv(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void write(Table table) {
        final StringBuilder sb = new StringBuilder(250);

        for (String col : table.getColNames()) {
            sb.append(col).append(",");
        }

        this.stream.println(sb.toString());

        final Iterator<Row> iterator = table.rowIterator();
        while (iterator.hasNext()) {
            sb.setLength(0);
            final Row row = iterator.next();
            for (Object data : row.values()) {
                sb.append(data).append(",");
            }
            this.stream.println(sb.toString());
        }

    }
}
