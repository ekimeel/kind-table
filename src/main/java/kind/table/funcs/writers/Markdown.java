package kind.table.funcs.writers;


import kind.table.Table;

import java.io.PrintStream;

public final class Markdown implements TableWriter {

    public static final String NULL_VALUE = "{null}";
    /**/
    private PrintStream stream;

    public Markdown(PrintStream stream) {
        this.stream = stream;
    }

    @Override
    public void write(Table table) {
        final StringBuilder sb = new StringBuilder(500);

        table.getCols().forEach( (i) -> sb.append("| ").append(i.getName()).append(" |") );
        stream.println(sb.toString());
        sb.setLength(0);

        table.getCols().forEach( (i) -> sb.append("| ").append("--").append(" |") );
        stream.println(sb.toString());
        sb.setLength(0);

        table.rowIterator().forEachRemaining( row -> {
            row.values().forEach( (v) -> sb.append("| ").append( (v == null)? NULL_VALUE : v ).append(" |"));
            stream.println(sb.toString());
            sb.setLength(0);
        });
        sb.setLength(0);
    }
}
