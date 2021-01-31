package kind.table;

import kind.table.cols.SummaryCol;
import kind.table.cols.IntCol;
import kind.table.cols.StrCol;
import kind.table.funcs.*;
import kind.table.funcs.writers.Markdown;
import org.junit.Test;

import java.time.Instant;

import static java.util.stream.IntStream.range;

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
                eval(KeepCols.from(0, 2)).
                eval(GroupBy.from(0,
                        SummaryCol.of("Total", Sum.from(1))));

        //result.print(System.out);

    }


}
