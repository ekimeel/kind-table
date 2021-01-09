package kind.table.funcs;

import kind.table.Table;

public interface Func<T> {

     T eval(Table table);
}
