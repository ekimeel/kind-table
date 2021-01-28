package kind.table.analyzers;

import java.time.Instant;

public abstract class AbstractAnalyzer implements Analyzer {
    protected final String name;
    private long startTime;
    private boolean stop = false;

    protected AbstractAnalyzer(String name) {
        this.name = name;
    }

    protected void startTimer() {
        this.startTime = Instant.now().toEpochMilli();
    }

    protected void resetTimer() {
        startTimer();
    }

    protected long currentTime() {
        return Instant.now().toEpochMilli() - startTime;
    }

    protected void stop() {
        this.stop = true;
    }

    protected void go() {
        this.stop = false;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public AnalyzerResponse exec(AnalyzerRequest request) {

        if (request == null) { return null; }

        final AnalyzerResponse response = new AnalyzerResponse();
        this.startTimer();

        beforeExec(request, response);
        response.setTime(currentTime());
        if (stop) { return response; };

        execTemplate(request, response);
        response.setTime(currentTime());
        if (stop) { return response; };

        afterExec(request, response);
        response.setTime(currentTime());

        return response;

    }

    protected void beforeExec(AnalyzerRequest request, AnalyzerResponse response) {

    }

    protected void afterExec(AnalyzerRequest request, AnalyzerResponse response) {

    }

    protected abstract void execTemplate(AnalyzerRequest request, AnalyzerResponse response);





}
