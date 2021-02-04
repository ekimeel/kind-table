package kind.table.readers;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CsvReaderTest {


    @Test
    public void test_read() {
        final CsvReader reader = new CsvReader(Paths.get("./test-data/pub/", "hw_25000.csv"));

        final Table table = new TableBuilder()
                .withTableReader(reader)
                .build();

        assertNotNull(table);
        assertTrue(table.getColCount() > 1);
        assertTrue(table.getRowCount() > 1);


    }

}