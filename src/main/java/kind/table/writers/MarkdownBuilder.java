package kind.table.writers;

import kind.table.cols.ColRef;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Markdown builder.
 */
public final class MarkdownBuilder {

    private PrintStream printStream;
    private Map<ColRef, String> formats = new HashMap<>();

    /**
     * Using stream markdown builder.
     *
     * @param printStream the print stream
     * @return the markdown builder
     */
    public MarkdownBuilder usingStream(PrintStream printStream){
        this.printStream = printStream;
        return this;
    }

    /**
     * With format markdown builder.
     *
     * @param colRef the col ref
     * @param format the format
     * @return the markdown builder
     */
    public MarkdownBuilder withFormat(ColRef colRef, String format) {
        formats.put(colRef, format);
        return this;
    }

    /**
     * Build markdown.
     *
     * @return the markdown
     */
    public Markdown build() {
        return new Markdown(
                printStream,
                formats
        );
    }

}
