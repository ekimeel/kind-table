## Kind Tables
![Build Status](https://github.com/ekimeel/kind-table/workflows/Java%20CI%20with%20Gradle/badge.svg?branch=main)

### Table setup


Example: 
```java
    final Table table = new Table();
    table.addColumn(new IntegerColumn("Odd"));
    table.addColumn(new DoubleColumn("Even"));
    table.addColumn(new StringColumn("Name"));

    table.addRow(new Row(1, 2.0, "Foo"));
    table.addRow(new Row(3, 4.0, "Bar"));
    table.addRow(new Row(5, 6.0, "Foo Bar"));

    assertEquals(1, table.get(0,0));
    assertEquals(4.0, table.get(1,1), 0.0);
    assertEquals("Foo Bar", table.getLastRow().get(2));

```

#### Column Types
| Class         | Data Type  |   |   |   |
|---------------|---|---|---|---|
| IntegerColumn |java.lang.Integer|   |   |   |
| DoubleColumn  |java.lang.Double|   |   |   |
| LongColumn    |java.lang.Long|   |   |   |
| StringColumn  |java.lang.String|   |   |   |
| BooleanColumn |java.lang.Boolean|   |   |   |


### Advanced
