package kind.table;

import kind.table.cols.GroupColumn;
import kind.table.cols.IntColumn;
import kind.table.cols.StrColumn;
import kind.table.funcs.GroupBy;
import kind.table.funcs.KeepCols;
import kind.table.funcs.Sum;
import org.junit.Test;

public class IntegrationTest {

    @Test
    public void test(){
        final Table table = new Table();

        table.addCol(StrColumn.of("team"));
        table.addCol(StrColumn.of("player"));
        table.addCol(IntColumn.of("points"));

        table.addRow(new Row("a-team", "player-1", 1));
        table.addRow(new Row("a-team", "player-1", 2));
        table.addRow(new Row("a-team", "player-1", 1));

        table.addRow(new Row("a-team", "player-2", 4));
        table.addRow(new Row("a-team", "player-2", 10));

        table.addRow(new Row("b-team", "player-1", 4));
        table.addRow(new Row("b-team", "player-1", 1));

        table.addRow(new Row("c-team", "player-1", 10));
        table.addRow(new Row("c-team", "player-1", 8));
        table.addRow(new Row("c-team", "player-2", 15));
        table.addRow(new Row("c-team", "player-2", 12));


        Table result = table.
                eval(KeepCols.of(0, 2)).
                eval(new GroupBy(0,
                        GroupColumn.of("Total", Sum.of(1))));

        //result.print(System.out);



    }
}
