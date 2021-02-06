package kind.table.writers;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.*;

public class MarkdownTest {


    @Test
    public void test_write() throws UnsupportedEncodingException {

        final String expected =
                "| str_col| int_col| bool_col|" + System.lineSeparator() +
                "| ---| ---| ---|" + System.lineSeparator() +
                "| hello| 100| true|" + System.lineSeparator() +
                "| world| 200| false|" + System.lineSeparator();

        final Table table = new TableBuilder()
                .withStrCol("str_col")
                .withIntCol("int_col")
                .withBoolCol("bool_col")
                .build();

        table.addRow("hello", 100, true);
        table.addRow("world", 200, false);



        final ByteArrayOutputStream os = new ByteArrayOutputStream();
        final PrintStream ps = new PrintStream(os);
        table.writeTo(new TableWriterBuilder().Markdown().usingStream(ps).build());

        String actual = os.toString("UTF8");

        assertEquals(expected, actual);



    }

}