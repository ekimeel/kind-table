package kind.table.funcs.writers;


import kind.table.Table;
import java.io.PrintStream;

public final class Markdown implements TableWriter {


    private PrintStream stream;

    public Markdown(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void write(Table table) {
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
            row.values().forEach( (v) -> sb.append("| ").append( (v == null)? NULL_VALUE : v ));
            sb.append("|"); // end cap
            stream.println(sb.toString());
            sb.setLength(0);
        });
        sb.setLength(0);
    }
}
