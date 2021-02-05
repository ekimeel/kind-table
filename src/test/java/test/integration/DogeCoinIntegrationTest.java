package test.integration;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.cols.SummaryCol;
import kind.table.cols.funcs.Weekday;
import kind.table.funcs.Convert;
import kind.table.funcs.GroupBy;
import kind.table.funcs.Mean;
import kind.table.writers.Markdown;
import org.junit.Test;

import java.nio.file.Paths;
import java.time.DayOfWeek;

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

        table = table.eval(GroupBy.of("Weekday", SummaryCol.of("AverageVolume", Mean.of("Volume"))));

        table.writeTo(new Markdown(System.out));





    }

}
