package kind.table.cols;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class DateColumnTest {

    @Test
    public void test_cast_withNull(){
        final DateColumn column = new DateColumn("undefined");

        assertNull(column.cast(null));
    }

    @Test
    public void test_cast_withLong(){
        final DateColumn column = new DateColumn("undefined");

        final long nowLong = Instant.now().toEpochMilli();
        final Date nowDate = column.cast(nowLong);

        assertEquals(nowLong, nowDate.getTime());
    }

    @Test
    public void test_cast_withSqlDate(){
        final DateColumn column = new DateColumn("undefined");

        final java.sql.Date nowSql = new java.sql.Date(Instant.now().toEpochMilli());
        final Date nowDate = column.cast(nowSql);

        assertEquals(nowSql.getTime(), nowDate.getTime());
    }

    @Test
    public void test_copy(){
        final Column a = new DateColumn("col", 1);
        final Column b = (Column) a.copy();

        assertEquals(a.getName(), b.getName());
        assertEquals(b.getIndex(), b.getIndex());
    }

}