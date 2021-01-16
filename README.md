## Kind Tables
![Build Status](https://github.com/ekimeel/kind-table/workflows/Build/badge.svg?branch=main)
![Publish Status](https://github.com/ekimeel/kind-table/workflows/Publish/badge.svg?branch=main)

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
| Class         | Data Type  | Examples|   |   |
|---------------|---|---|---|---|
| IntColumn |java.lang.Integer| 1, 10, 1000 |   |   |
| DblColumn  |java.lang.Double| 1.1, 10.01, 1000.0001 |   |   |
| LngColumn    |java.lang.Long| 290902910101091 |   |   |
| StrColumn  |java.lang.String| "Hello", "World"|   |   |
| BoolColumn |java.lang.Boolean| true, false|   |   |
| RowColumn     |java.util.List| ```List<kind.table.Row>``` |

#### Table Functions
Table functions accept a table and produce an output. 

Examples:
```java
// returns the average of column 0
final Double max = table.eval(new Mean(0));

// returns a new table grouped by column 2
final Table group = table.eval(new GroupBy(2));
```


| Function         | Output   | Description   |   
|---------------|---|---|
| Copy |Table |Creates a copy of a table|
| First |Any|Returns the first value in a column |   |
| GroupBy  |Table| |
| KeepCols    |Table|   | 
| Last  |Any|   |
| LinearInterpolation |Table|   |
| LOCF |Table|Last observation carried forward (LOCF)| 
| Max | Number | Returns the max value in a column |
| Mean | Double | Returns the mean (average) value in a column |
| Min | Number | Returns the min value in a column |
| StandardDeviation | Double | Returns the entire population's standard deviation |
| Sum | Number | Returns the sum (total) value in a column |

### Advanced
