package kind.table.funcs;

import kind.table.Table;
import kind.table.cols.Col;

public interface Func<T> {

     T eval(Table table);

     /**
      * Test if the provided col is supported by the [[func]]
      *
      * @param col A valid col
      * @return Returns true if the provided col is supported by the [[func]], otherwise false
      */
     boolean acceptCol(Col col);
}
