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
        assertEquals(DoubleColumn.class, ColumnFactory.from("test", 10.0).getClass());
        assertEquals(DoubleColumn.class, ColumnFactory.from("test", Double.MAX_VALUE).getClass());
        assertEquals(DoubleColumn.class, ColumnFactory.from("test", Double.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withInteger() {
        assertEquals(IntegerColumn.class, ColumnFactory.from("test", 10).getClass());
        assertEquals(IntegerColumn.class, ColumnFactory.from("test", Integer.MAX_VALUE).getClass());
        assertEquals(IntegerColumn.class, ColumnFactory.from("test", Integer.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withLong() {
        assertEquals(LongColumn.class, ColumnFactory.from("test", 10L).getClass());
        assertEquals(LongColumn.class, ColumnFactory.from("test", Long.MAX_VALUE).getClass());
        assertEquals(LongColumn.class, ColumnFactory.from("test", Long.MIN_VALUE).getClass());
    }

    @Test
    public void test_of_withBoolean() {
        assertEquals(BooleanColumn.class, ColumnFactory.from("test", true).getClass());
        assertEquals(BooleanColumn.class, ColumnFactory.from("test", false).getClass());
    }

    @Test
    public void test_of_withString() {
        assertEquals(StringColumn.class, ColumnFactory.from("test", "foo").getClass());
        assertEquals(StringColumn.class, ColumnFactory.from("test", "").getClass());
        assertEquals(StringColumn.class, ColumnFactory.from("test", " ").getClass());
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