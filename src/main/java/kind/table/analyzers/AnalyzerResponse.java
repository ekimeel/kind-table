package kind.table.analyzers;

import kind.table.Table;

public final class AnalyzerResponse {

    private Table table;
    private long time;
    private Object err;

    public Table getTable() {
        return null;
    }

    void setTable(Table table) {
        this.table = table;
    }

    public long getResponseTime() {
        return 0;
    }

    void setTime(long time) {
        this.time = time;
    }

    public Object getErr() {
        return this.err;
    }

    void setErr(Object err) {
        this.err = err;
    }
}
