package kind.table.cols;

import org.junit.Test;

import static org.junit.Assert.*;

public class TsColTest {

    @Test
    public void test_copy(){
        final Col a = new TsCol("col", 1);
        final Col b = (Col) a.copy();

        assertEquals(a.getName(), b.getName());
        assertEquals(b.getIndex(), b.getIndex());
    }

}