package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Column;

public interface Func<T> {

     T eval(Table table);

     /**
      * Test if the provided column is supported by the [[func]]
      *
      * @param column A valid column
      * @return Returns true if the provided column is supported by the [[func]], otherwise false
      */
     boolean acceptColumn(Column column);
}
