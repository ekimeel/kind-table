package kind.table;

import kind.table.cols.IntColumn;
import kind.table.cols.StrColumn;
import org.junit.Test;
import static org.junit.Assert.*;

public class TableBuilderTest {


    @Test
    public void test() {

        Table t = new TableBuilder().
                withStrCol("team").
                withStrCol("player").
                withIntCol("score").
                build();

        assertNotNull(t);

    }

}