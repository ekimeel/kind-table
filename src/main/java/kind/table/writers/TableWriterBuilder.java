package kind.table.writers;

/**
 * The type Table writer builder.
 */
public class TableWriterBuilder {

    private TableWriter tableWriter;

    /**
     * Markdown markdown builder.
     *
     * @return the markdown builder
     */
    public MarkdownBuilder Markdown() {
        return new MarkdownBuilder();
    }


}
