package kind.table.analyzers;

import kind.table.Table;
import kind.table.writers.Markdown;
import org.junit.Test;
import test.util.TestTableFactory;

public class M1Test {

    @Test
    public void test_() {


    }
    @Test
    public void test_execTemplate() {

        final Table table = TestTableFactory.hw_25000();

        M1 analyzer = new M1();
        AnalyzerRequest request = new AnalyzerRequest();
        request.setTable(table);
        request.addParam(AnalyzerParams.x_cols.getKey(), "Index");

        AnalyzerResponse response = analyzer.exec(request);

        //response.getTable().writeTo(new Markdown(System.out));



    }
}