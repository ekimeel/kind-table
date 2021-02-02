package kind.table.funcs;

import kind.table.Row;
import kind.table.Table;
import kind.table.cols.IntCol;
import kind.table.cols.StrCol;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class SplitTest {

    @Test
    public void test_eval(){
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

        final List<Table> result = table.eval(Split.from("team"));

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