package kind.table.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import static org.junit.Assert.*;

public class JoinTest {

    @Test
    public void test_join(){
        final Table a = new TableBuilder()
                .withStrCol("key")
                .withStrCol("value")
                .build();

        a.addRow("a", "1-a");
        a.addRow("b", "2-a");
        a.addRow("c", "3-a");
        a.addRow("e", "4-a");

        final Table b = new TableBuilder()
                .withStrCol("key")
                .withIntCol("value")
                .build();

        b.addRow("a", "1-b");
        b.addRow("b", "2-b");
        b.addRow("c", "3-b");
        b.addRow("d", "4-b"); // no join

        final Table result = a.eval(Join.from("key", b));

        assertEquals(4, result.getRowCount());
        assertEquals(3, result.getColCount());
        assertEquals("a,1-a,1-b", result.getRow(0).toCsv());
        assertEquals("b,2-a,2-b", result.getRow(1).toCsv());
        assertEquals("c,3-a,3-b", result.getRow(2).toCsv());
        assertEquals("e,4-a,null", result.getRow(3).toCsv());


    }
}