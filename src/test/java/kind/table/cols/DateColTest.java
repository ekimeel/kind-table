package kind.table.cols;

import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static org.junit.Assert.*;

public class DateColTest {

    @Test
    public void test_cast_withNull(){
        final DateCol column = new DateCol("undefined");

        assertNull(column.convert(null));
    }

    @Test
    public void test_cast_withLong(){
        final DateCol column = new DateCol("undefined");

        final long nowLong = Instant.now().toEpochMilli();
        final Date nowDate = column.convert(nowLong);

        assertEquals(nowLong, nowDate.getTime());
    }

    @Test
    public void test_cast_withSqlDate(){
        final DateCol column = new DateCol("undefined");

        final java.sql.Date nowSql = new java.sql.Date(Instant.now().toEpochMilli());
        final Date nowDate = column.convert(nowSql);

        assertEquals(nowSql.getTime(), nowDate.getTime());
    }

    @Test
    public void test_copy(){
        final Col a = new DateCol("col", 1);
        final Col b = (Col) a.copy();

        assertEquals(a.getName(), b.getName());
        assertEquals(b.getIndex(), b.getIndex());
    }

}