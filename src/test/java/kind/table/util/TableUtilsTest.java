package kind.table.util;

import kind.table.Table;
import kind.table.TableBuilder;
import kind.table.cols.StrCol;
import org.junit.Test;

import static org.junit.Assert.*;

public class TableUtilsTest {

    @Test
    public void test_EnumerateColName(){
        final Table table = new TableBuilder()
                .withStrCol("My Column")
                .build();

        assertEquals("My Column_1", TableUtils.enumerateColName(table, "My Column"));

        table.addCol(new StrCol(TableUtils.enumerateColName(table, "My Column")));
        assertEquals("My Column_2", TableUtils.enumerateColName(table, "My Column"));

        table.addCol(new StrCol(TableUtils.enumerateColName(table, "My Column")));
        assertEquals("My Column_3", TableUtils.enumerateColName(table, "My Column"));

        table.addCol(new StrCol(TableUtils.enumerateColName(table, "My Column")));
        assertEquals("My Column_3_1", TableUtils.enumerateColName(table, "My Column_3"));
    }

}