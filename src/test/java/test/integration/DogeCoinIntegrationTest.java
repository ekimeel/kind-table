package test.integration;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.funcs.Convert;
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


    }

}
