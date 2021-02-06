package kind.table.analyzers;

import kind.table.Table;

import java.util.HashMap;
import java.util.Map;

/**
 * The type Analyzer request.
 */
public class AnalyzerRequest {

    private Table table;
    private String lang;
    private Map<String, Object> params = new HashMap<>();

    /**
     * Instantiates a new Analyzer request.
     */
    public AnalyzerRequest() {
    }

    /**
     * Gets table.
     *
     * @return the table
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets table.
     *
     * @param table the table
     */
    public void setTable(Table table) {
        this.table = table;
    }

    /**
     * Gets lang.
     *
     * @return the lang
     */
    public String getLang() {
        return lang;
    }

    /**
     * Sets lang.
     *
     * @param lang the lang
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * Gets params.
     *
     * @return the params
     */
    public Map<String, Object> getParams() {
        return params;
    }

    /**
     * Sets params.
     *
     * @param params the params
     */
    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    /**
     * Add param.
     *
     * @param key   the key
     * @param value the value
     */
    public void addParam(String key, Object value) {
        this.params.put(key, value);
    }
}
