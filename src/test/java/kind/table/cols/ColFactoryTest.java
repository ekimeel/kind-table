package kind.table.cols;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class ColFactoryTest {

    @Test
    public void test_of_withNull() {
        assertNull(ColFactory.from("test", null));
    }

    @Test
    public void test_of_withDouble() {
        assertEquals(DblCol.class, ColFactory.from("test", 10.0).getClass());
        assertEquals(DblCol.class, ColFactory.from("test", Double.MAX_VALUE).getClass());
        assertEquals(DblCol.class, ColFactory.from("test", Double.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withInteger() {
        assertEquals(IntCol.class, ColFactory.from("test", 10).getClass());
        assertEquals(IntCol.class, ColFactory.from("test", Integer.MAX_VALUE).getClass());
        assertEquals(IntCol.class, ColFactory.from("test", Integer.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withLong() {
        assertEquals(LngCol.class, ColFactory.from("test", 10L).getClass());
        assertEquals(LngCol.class, ColFactory.from("test", Long.MAX_VALUE).getClass());
        assertEquals(LngCol.class, ColFactory.from("test", Long.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withBoolean() {
        assertEquals(BoolCol.class, ColFactory.from("test", true).getClass());
        assertEquals(BoolCol.class, ColFactory.from("test", false).getClass());
    }

    @Test
    public void test_of_withString() {
        assertEquals(StrCol.class, ColFactory.from("test", "foo").getClass());
        assertEquals(StrCol.class, ColFactory.from("test", "").getClass());
        assertEquals(StrCol.class, ColFactory.from("test", " ").getClass());
    }

    @Test
    public void test_of_withJavaUtilDate() {
        assertEquals(DateCol.class, ColFactory.from("test", new java.util.Date()).getClass());
    }

    @Test
    public void test_of_withInstant() {
        assertEquals(TsCol.class, ColFactory.from("test", Instant.now()).getClass());
    }
}