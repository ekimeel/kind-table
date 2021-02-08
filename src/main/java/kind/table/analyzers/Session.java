package kind.table.analyzers;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public final class Session {

    public static final long DEFAULT_TIMEOUT = 30;

    public static Session from(AnalyzerRequest request, AnalyzerResponse response) {
        return new Session(Instant.now(), DEFAULT_TIMEOUT * 1000, request, response);
    }

    /**/
    private final Instant start;
    private final long timeout;
    private final AnalyzerRequest request;
    private final AnalyzerResponse response;
    private final Map<String, Object> data = new HashMap<>();


    private Session(Instant start, long timeout, AnalyzerRequest request, AnalyzerResponse response) {
        this.start = start;
        this.timeout = timeout;
        this.request = request;
        this.response = response;
    }

    public Instant getStart() {
        return start;
    }

    public long getTimeout() {
        return timeout;
    }

    public AnalyzerRequest getRequest() {
        return request;
    }

    public AnalyzerResponse getResponse() {
        return response;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
