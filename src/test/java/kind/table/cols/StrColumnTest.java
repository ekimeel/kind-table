package kind.table.cols;

import org.junit.Test;

import static org.junit.Assert.*;

public class StrColumnTest {

    @Test
    public void test_copy(){
        final Column a = new StrColumn("col", 1);
        final Column b = (Column) a.copy();

        assertEquals(a.getName(), b.getName());
        assertEquals(b.getIndex(), b.getIndex());
    }

}