package kind.table.cols;

import org.junit.Test;

import static org.junit.Assert.*;

public class ColRefTest {

    @Test
    public void test_equals() {
        assertEquals(ColRef.of("col 1"), ColRef.of("col 1"));
        assertEquals(ColRef.of(2), ColRef.of(2));
    }

    @Test
    public void test_equals_whenNot() {
        assertNotEquals(ColRef.of("col 2"), ColRef.of("col 1"));
        assertNotEquals(ColRef.of(1), ColRef.of(2));
    }

}