package kind.table.cols.funcs;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static org.junit.Assert.*;

public class WeekdayTest {

    @Test
    public void test_eval_withDateColumn() throws ParseException {

        final Table tableA = new TableBuilder()
                .withDateCol("Date")
                .withIntCol("Value")
                .build();

        final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        tableA.addRow(sdf.parse("01/18/2021"), 100); // monday
        tableA.addRow(sdf.parse("01/19/2021"), 200); // tuesday
        tableA.addRow(sdf.parse("01/20/2021"), 300); // wednesday
        tableA.addRow(sdf.parse("01/21/2021"), 400); // thursday
        tableA.addRow(sdf.parse("01/22/2021"), 500); // friday
        tableA.addRow(sdf.parse("01/23/2021"), 600); // saturday
        tableA.addRow(sdf.parse("01/24/2021"), 700); // sunday

        assertEquals(2, tableA.getColumnCount());
        tableA.addCol(Weekday.from("Date")); // Weekday column function
        assertEquals(3, tableA.getColumnCount());

        assertEquals("Monday", tableA.get(0, 2));
        assertEquals("Tuesday", tableA.get(1, 2));
        assertEquals("Wednesday", tableA.get(2, 2));
        assertEquals("Thursday", tableA.get(3, 2));
        assertEquals("Friday", tableA.get(4, 2));
        assertEquals("Saturday", tableA.get(5, 2));
        assertEquals("Sunday", tableA.get(6, 2));

    }

}