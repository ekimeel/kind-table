package kind.table;

import kind.table.cols.GroupCol;
import kind.table.cols.IntCol;
import kind.table.cols.StrCol;
import kind.table.funcs.GroupBy;
import kind.table.funcs.KeepCols;
import kind.table.funcs.Sum;
import org.junit.Test;

public class IntegrationTest {

    @Test
    public void test(){
        final Table table = new Table();

        table.addCol(StrCol.of("team"));
        table.addCol(StrCol.of("player"));
        table.addCol(IntCol.of("points"));

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
                eval(KeepCols.from(0, 2)).
                eval(new GroupBy(0,
                        GroupCol.of("Total", Sum.from(1))));

        //result.print(System.out);



    }
}
