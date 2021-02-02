package kind.table.analyzers;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.funcs.writers.Markdown;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;

public class AzPk20210127aTest {

    @Test
    public void test_execTemplate() {

        final Table table = new TableBuilder()
                .withCsvFile(Path.of("./test-data/pub/hw_25000.csv"))
                .build();

        AzPk20210127a analyzer = new AzPk20210127a();
        AnalyzerRequest request = new AnalyzerRequest();
        request.setTable(table);
        request.addParam(AnalyzerParams.x_cols.getKey(), "Index");

        AnalyzerResponse response = analyzer.exec(request);

        response.getTable().writeTo(new Markdown(System.out));



    }
}