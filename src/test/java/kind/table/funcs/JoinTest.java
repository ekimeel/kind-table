package kind.table.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

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

        final Table b = new TableBuilder()
                .withStrCol("key")
                .withIntCol("value")
                .build();

        b.addRow("a", "1-b");
        b.addRow("b", "2-b");
        b.addRow("c", "3-b");

        final Table res = a.eval(Join.from("key", b));
        res.print(System.out);


    }
}