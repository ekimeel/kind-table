package kind.table.analyzers;

import kind.table.Table;

import java.util.Map;

public interface AnalyzerRequest {
    /**/
    Table getTable();
    void setTable(Table table);
    /**/
    String getLang();
    void setLang(String lang);
    /**/
    Map<String, Object> getParams();
    void setParams(Map<String, Object> params);
}
