package kind.table.analyzers;

/**
 * The interface Analyzer.
 */
public interface Analyzer {
    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();

    /**
     * Exec analyzer response.
     *
     * @param request the request
     * @return the analyzer response
     */
    AnalyzerResponse exec(AnalyzerRequest request);
}
