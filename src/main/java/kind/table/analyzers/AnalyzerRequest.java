package kind.table.analyzers;

import kind.table.Table;

import java.util.HashMap;
import java.util.Map;

public class AnalyzerRequest {

    private Table table;
    private String lang;
    private Map<String, Object> params = new HashMap<>();

    public AnalyzerRequest() {
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }
}
