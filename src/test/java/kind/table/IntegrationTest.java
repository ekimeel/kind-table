package kind.table;

import kind.table.cols.SummaryCol;
import kind.table.cols.IntCol;
import kind.table.cols.StrCol;
import kind.table.funcs.*;
import org.junit.Test;

import java.nio.file.Paths;

public class IntegrationTest {

    @Test
    public void test(){
        final Table table = new Table();

        table.addCol(StrCol.of("team"));
        table.addCol(StrCol.of("player"));
        table.addCol(IntCol.of("points"));

        table.addRow(Row.of("a-team", "player-1", 1));
        table.addRow(Row.of("a-team", "player-1", 2));
        table.addRow(Row.of("a-team", "player-1", 1));

        table.addRow(Row.of("a-team", "player-2", 4));
        table.addRow(Row.of("a-team", "player-2", 10));

        table.addRow(Row.of("b-team", "player-1", 4));
        table.addRow(Row.of("b-team", "player-1", 1));

        table.addRow(Row.of("c-team", "player-1", 10));
        table.addRow(Row.of("c-team", "player-1", 8));
        table.addRow(Row.of("c-team", "player-2", 15));
        table.addRow(Row.of("c-team", "player-2", 12));


        Table result = table.
                eval(KeepCols.of(0, 2)).
                eval(GroupBy.of(0,
                        SummaryCol.of("Total", Sum.of(1))));

        //result.print(System.out);

    }

    @Test
    public void test_occupancy_data(){
        final Table table = new TableBuilder()
                .withCsvFile(Paths.get("./test-data/pub/", "occupancy_data.csv"))
                .build()
                .eval(Remove.from("Index"))
                .eval(Convert.toDateCol("Date", "yyyy-MM-dd hh:mm:ss"))
                .eval(Convert.toDblCol("Temperature"))
                .eval(Convert.toDblCol("Humidity"))
                .eval(Convert.toDblCol("Light"))
                .eval(Convert.toDblCol("CO2"))
                .eval(Convert.toDblCol("HumidityRatio"))
                .eval(Convert.toDblCol("Occupancy"));






    }


}
