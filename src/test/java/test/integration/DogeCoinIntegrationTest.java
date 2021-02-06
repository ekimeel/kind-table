package test.integration;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.analyzers.AnalyzerRequest;
import kind.table.analyzers.AnalyzerResponse;
import kind.table.analyzers.M1;
import kind.table.analyzers.m1.M1Pkg;
import kind.table.cols.ColRef;
import kind.table.cols.SummaryCol;
import kind.table.cols.funcs.Weekday;
import kind.table.funcs.Convert;
import kind.table.funcs.GroupBy;
import kind.table.funcs.Histogram;
import kind.table.funcs.Mean;
import kind.table.writers.WriterBuilder;
import org.junit.Test;

import java.nio.file.Paths;

public class DogeCoinIntegrationTest {


    @Test
    public void test() {
        Table table = new TableBuilder()
                .withCsvFile(Paths.get("./test-data/pub/", "doge-usd-2016-2021.csv"))
                .build()
                .eval(Convert.toDateCol("Date", "yyyy-MM-dd"))
                .eval(Convert.toDblCol("High"))
                .eval(Convert.toDblCol("Low"))
                .eval(Convert.toDblCol("Open"))
                .eval(Convert.toDblCol("Close"))
                .eval(Convert.toLngCol("Volume"));

        table.addCol(Weekday.from("Weekday", "Date"));



        M1 m1 = new M1();

        final AnalyzerRequest request = new AnalyzerRequest();
        request.setTable(table);

        final AnalyzerResponse response = m1.exec(request);
        if (response.isOk()) {
            Table m1Table = response.getTable();

            m1Table.writeTo(new WriterBuilder()
                    .Markdown()
                    .usingStream(System.out)
                    .build()
            );
        }



        final Table summary = table.eval(GroupBy.of("Weekday",
                SummaryCol.of("AverageVolume", Mean.of("Volume"))));


        System.out.println("Volume by day of the week");
        summary.writeTo( new WriterBuilder()
                .Markdown()
                .usingStream(System.out)
                .withFormat(ColRef.of("AverageVolume"), "%.3f")
                .build()
        );

        final Table histogram = table.eval(Histogram.of("Close", 10));
        histogram.writeTo( new WriterBuilder()
                .Markdown()
                .usingStream(System.out)
                .build()
        );

    }

}
