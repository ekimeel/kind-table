package kind.table;

import kind.table.funcs.GroupBy;
import kind.table.funcs.Sum;
import org.junit.Test;
import static org.junit.Assert.*;

public class TableBuilderTest {


    @Test
    public void test() {

        final Table table = new TableBuilder().
                withStrCol("team").
                withStrCol("player").
                withIntCol("score").
                build();

        table.addRow("team-a", "player #10", 1);
        table.addRow("team-a", "player #12", 2);
        table.addRow("team-a", "player #4", 1);
        table.addRow("team-b", "player #5", 1);
        table.addRow("team-b", "player #19", 1);

        final Table result = table.
                eval(GroupBy.of(0,"total score", Sum.of(2))).
                sortr("total score");

        result.print(System.out); // prints the table to the console

        assertEquals(2, result.getRowCount());
        assertEquals((Integer)4, result.get(0, 1)); // top score
        assertEquals("team-a", result.get(0, 0)); // top team


    }

}