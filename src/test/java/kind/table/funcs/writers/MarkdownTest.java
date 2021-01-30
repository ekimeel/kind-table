package kind.table.funcs.writers;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import java.util.Date;

import static org.junit.Assert.*;

public class MarkdownTest {

    String tz;

    @Before
    public void before(){
        //todo: move to gradle test jvm args
        this.tz = System.getProperty("user.timezone");
        System.setProperty("user.timezone", "UTC");
    }
    @After
    public void after() {
        System.setProperty("user.timezone", tz);
    }


    @Test
    public void test_write() throws UnsupportedEncodingException {

        final String expected =
                "| str_col| int_col| date_col| bool_col|" + System.lineSeparator() +
                "| ---| ---| ---| ---|" + System.lineSeparator() +
                "| hello| 100| Sat Jan 30 11:44:30 UTC 2021| true|" + System.lineSeparator() +
                "| world| 200| Sat Jan 30 11:44:40 UTC 2021| false|" + System.lineSeparator();

        final Table table = new TableBuilder()
                .withStrCol("str_col")
                .withIntCol("int_col")
                .withDateCol("date_col")
                .withBoolCol("bool_col")
                .build();

        final Date date1 = new Date(1612007070000L);
        final Date date2 = new Date(1612007080000L);

        table.addRow("hello", 100, date1, true);
        table.addRow("world", 200, date2, false);



        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(os);
        table.writeTo(new Markdown(ps));

        String actual = os.toString("UTF8");

        assertEquals(expected, actual);



    }

}