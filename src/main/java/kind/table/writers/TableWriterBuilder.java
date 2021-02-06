package kind.table.writers;

public class TableWriterBuilder {

    private TableWriter tableWriter;

    public MarkdownBuilder Markdown() {
        return new MarkdownBuilder();
    }


}
