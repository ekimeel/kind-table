package kind.table.writers;

/**
 * The type Table writer builder.
 */
public class WriterBuilder {

    private Writer tableWriter;

    /**
     * Markdown markdown builder.
     *
     * @return the markdown builder
     */
    public MarkdownBuilder Markdown() {
        return new MarkdownBuilder();
    }


}
