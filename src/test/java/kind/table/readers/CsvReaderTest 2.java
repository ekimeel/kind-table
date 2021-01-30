package kind.table.readers;

import kind.table.Table;
import kind.table.TableBuilder;
import org.junit.Test;

import java.nio.file.Path;

import static org.junit.Assert.*;

public class CsvReaderTest {


    @Test
    public void test_() {
        CsvReader reader = new CsvReader(Path.of("./test-data/pub/", "hw_25000.csv"));

        Table table = new TableBuilder()
                .withTableReader(reader)
                .build();

        table.print(System.out);

    }

}