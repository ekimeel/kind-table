package kind.table.cols.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class HourOfDayTest {

    @Test
    public void test_eval_withDateColumn() throws ParseException {

        final Table tableA = new TableBuilder()
                .withDateCol("DateTime")
                .withIntCol("Value")
                .build();

        final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));

        tableA.addRow(sdf.parse("01/18/2021 06:00:00"), 100);
        tableA.addRow(sdf.parse("01/19/2021 06:59:00"), 200);
        tableA.addRow(sdf.parse("01/20/2021 07:12:55"), 300);
        tableA.addRow(sdf.parse("01/21/2021 18:05:55"), 400);
        tableA.addRow(sdf.parse("01/21/2021 23:59:59"), 400);

        assertEquals(2, tableA.getColCount());
        tableA.addCol(HourOfDay.from("DateTime")); // Weekday column function
        assertEquals(3, tableA.getColCount());

        tableA.print(System.out);
        assertEquals((Integer)6, tableA.get(0, 2));
        assertEquals((Integer)6, tableA.get(1, 2));
        assertEquals((Integer)7, tableA.get(2, 2));
        assertEquals((Integer)18, tableA.get(3, 2));
        assertEquals((Integer)23, tableA.get(4, 2));



    }

}