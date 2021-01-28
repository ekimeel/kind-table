package kind.table.analyzers;

public interface Analyzer {
    String getName();

    AnalyzerResponse exec(AnalyzerRequest request);
}
