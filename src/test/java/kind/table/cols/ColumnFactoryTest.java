package kind.table.cols;

import org.junit.Test;

import java.time.Instant;

import static org.junit.Assert.*;

public class ColumnFactoryTest {

    @Test
    public void test_of_withNull() {
        assertNull(ColumnFactory.from("test", null));
    }

    @Test
    public void test_of_withDouble() {
        assertEquals(DblColumn.class, ColumnFactory.from("test", 10.0).getClass());
        assertEquals(DblColumn.class, ColumnFactory.from("test", Double.MAX_VALUE).getClass());
        assertEquals(DblColumn.class, ColumnFactory.from("test", Double.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withInteger() {
        assertEquals(IntColumn.class, ColumnFactory.from("test", 10).getClass());
        assertEquals(IntColumn.class, ColumnFactory.from("test", Integer.MAX_VALUE).getClass());
        assertEquals(IntColumn.class, ColumnFactory.from("test", Integer.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withLong() {
        assertEquals(LngColumn.class, ColumnFactory.from("test", 10L).getClass());
        assertEquals(LngColumn.class, ColumnFactory.from("test", Long.MAX_VALUE).getClass());
        assertEquals(LngColumn.class, ColumnFactory.from("test", Long.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withBoolean() {
        assertEquals(BoolColumn.class, ColumnFactory.from("test", true).getClass());
        assertEquals(BoolColumn.class, ColumnFactory.from("test", false).getClass());
    }

    @Test
    public void test_of_withString() {
        assertEquals(StrColumn.class, ColumnFactory.from("test", "foo").getClass());
        assertEquals(StrColumn.class, ColumnFactory.from("test", "").getClass());
        assertEquals(StrColumn.class, ColumnFactory.from("test", " ").getClass());
    }

    @Test
    public void test_of_withJavaUtilDate() {
        assertEquals(DateColumn.class, ColumnFactory.from("test", new java.util.Date()).getClass());
    }

    @Test
    public void test_of_withInstant() {
        assertEquals(InstantColumn.class, ColumnFactory.from("test", Instant.now()).getClass());
    }
}