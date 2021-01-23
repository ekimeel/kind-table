package kind.table.cols;

import org.junit.Test;

import static org.junit.Assert.*;

public class BoolColumnTest {

    @Test
    public void test_copy(){
        final Column a = new BoolColumn("col", 1);
        final Column b = (Column) a.copy();

        assertEquals(a.getName(), b.getName());
        assertEquals(b.getIndex(), b.getIndex());
    }

}