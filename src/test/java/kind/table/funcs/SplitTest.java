package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntColumn;
import kind.table.cols.StrColumn;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SplitTest {

    @Test
    public void test_eval(){
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

        final List<Table> result = table.eval(Split.of("team"));

        assertNotNull(result);
        assertEquals(3, result.size());

        for (int i = 0; i < 3; i++) {
            assertTrue(result.get(i).hasCol("team"));
            assertTrue(result.get(i).hasCol("player"));
            assertTrue(result.get(i).hasCol("points"));
            assertFalse(result.get(i).isEmpty());
            assertNotNull(result.get(i).getSettings());
        }

    }

}