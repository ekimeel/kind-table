package kind.table.analyzers;

import java.time.Instant;

/**
 * The type Abstract analyzer.
 */
public abstract class AbstractAnalyzer implements Analyzer {
    /**
     * The Name.
     */
    protected final String name;
    private long startTime;
    private boolean stop = false;

    /**
     * Instantiates a new Abstract analyzer.
     *
     * @param name the name
     */
    protected AbstractAnalyzer(String name) {
        this.name = name;
    }

    /**
     * Start timer.
     */
    protected void startTimer() {
        this.startTime = Instant.now().toEpochMilli();
    }

    /**
     * Reset timer.
     */
    protected void resetTimer() {
        startTimer();
    }

    /**
     * Current time long.
     *
     * @return the long
     */
    protected long currentTime() {
        return Instant.now().toEpochMilli() - startTime;
    }

    /**
     * Stop.
     */
    protected void stop() {
        this.stop = true;
    }

    /**
     * Go.
     */
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

    /**
     * Before exec.
     *
     * @param request  the request
     * @param response the response
     */
    protected void beforeExec(AnalyzerRequest request, AnalyzerResponse response) {

    }

    /**
     * After exec.
     *
     * @param request  the request
     * @param response the response
     */
    protected void afterExec(AnalyzerRequest request, AnalyzerResponse response) {

    }

    /**
     * Exec template.
     *
     * @param request  the request
     * @param response the response
     */
    protected abstract void execTemplate(AnalyzerRequest request, AnalyzerResponse response);





}
