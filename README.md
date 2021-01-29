# Kind Tables
![Build Status](https://github.com/ekimeel/kind-table/workflows/Build/badge.svg?branch=main)
![Publish Status](https://github.com/ekimeel/kind-table/workflows/Publish/badge.svg?branch=main)

# About
The kind-table project is designed to be an in-memory table data structure (rows and cols) with robust functionality.  

## Quick Start

:warning:
Documentation is a work in progress!

### Basic Table: 
The below example creates a simple three col table with three rows:
```java
    final Table table = new Table();
    table.addCol( IntCol.of("Odd") );
    table.addCol( DblColumn.of("Even") );
    table.addCol( StrColumn.of("Name") );

    table.addRow(new Row(1, 2.0, "Foo"));
    table.addRow(new Row(3, 4.0, "Bar"));
    table.addRow(new Row(5, 6.0, "Foo Bar"));

    assertEquals(1, table.get(0,0));
    assertEquals(4.0, table.get(1,1), 0.0);
    assertEquals("Foo Bar", table.getLastRow().get(2));
```
### Table Functions

### Column Functions
Column functions are used to add a col to an existing table and append a new value to each row. When the col is added the function performs an 
evaluation to arrive at a new value insert into each row. 

For example, if you have an existing table with a DateColumn and IntColumn and you want to add quickly add a 
day-of-the-week (Monday-Sunday) col to the table. You could write your own function or you could use a built-in
Weekday ColumnFunc:

```java
        final Table tableA = new TableBuilder()
                .withDateCol("Date")
                .withIntCol("Value")
                .build();

        final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");

        tableA.addRow(sdf.parse("01/18/2021"), 100); // monday
        tableA.addRow(sdf.parse("01/19/2021"), 200); // tuesday
        tableA.addRow(sdf.parse("01/20/2021"), 300); // wednesday
        tableA.addRow(sdf.parse("01/21/2021"), 400); // thursday
        tableA.addRow(sdf.parse("01/22/2021"), 500); // friday
        tableA.addRow(sdf.parse("01/23/2021"), 600); // saturday
        tableA.addRow(sdf.parse("01/24/2021"), 700); // sunday

        assertEquals(2, tableA.getColumnCount());
        tableA.addCol(Weekday.from("Date")); // Weekday col function
        assertEquals(3, tableA.getColumnCount()); 

        assertEquals("Monday", tableA.get(0, 2));
        assertEquals("Tuesday", tableA.get(1, 2));
        assertEquals("Wednesday", tableA.get(2, 2));
        assertEquals("Thursday", tableA.get(3, 2));
        assertEquals("Friday", tableA.get(4, 2));
        assertEquals("Saturday", tableA.get(5, 2));
        assertEquals("Sunday", tableA.get(6, 2));
```
#### Column Functions
Below is a current list of Column Functions. You may also extend the ColFunc class to provide your own implementation.

| Column Function         | Description   | Accepts | Outputs | 
|---------------|---------------| ------ | ------ |
| **Weekday** | Appends a new StrColumn to the provided table with the day-of-week in long format |  DateColumn, InstantColumn | StrColumn |
| **HourOfDay** | Appends a new IntColumn to the provided table with the hour-of-day (0-23) in integer format | DateColumn, InstantColumn | IntColumn

#### Grouping and Sorting Example

```java 
        final Table table = new TableBuilder().
                withStrCol("team").
                withStrCol("player").
                withIntCol("score").
                build();

        table.addRow("team-a", "player #10", 1);
        table.addRow("team-a", "player #12", 2);
        table.addRow("team-a", "player #4", 1);
        table.addRow("team-b", "player #5", 1);
        table.addRow("team-b", "player #19", 1);

        final Table result = table.
                eval(GroupBy.of(0,"total score", Sum.of(2))).
                sortr("total score");

        result.print(System.out); // prints the table to the console
        
        assertEquals(2, result.getRowCount());
        assertEquals((Integer)4, result.get(0, 1)); // top score
        assertEquals("team-a", result.get(0, 0)); // top team
        
 ```

#### Column Types
| Class         | Data Type  | Examples|
|---------------|---|---|
| DateCol   | java.util.Date        | new Date()                    |
| IntCol    | java.lang.Integer     | 1, 10, 1000                   |
| DblCol    | java.lang.Double      | 1.1, 10.01, 1000.0001         |
| LngCol    | java.lang.Long        | 290902910101091               |
| StrCol    | java.lang.String      | "Hello", "World"              |
| BoolCol   | java.lang.Boolean     | true, false                   |
| RowCol    | java.util.List        | ```List<kind.table.Row>```    |
| TsCol     | java.time.Instant     | Instant.now()                 |

#### Table Functions
Table functions accept a table and produce an output. 

Examples:
```java
// returns the average of col 0
final Double max = table.eval(new Mean(0));

// returns a new table grouped by col 2
final Table group = table.eval(new GroupBy(2));
```

| Function            | Output | Description               |   
|---------------------|--------|---------------------------|
| **Copy**                | Table        | Creates a copy of a table|
| **Count**               | Integer      | Counts the number of non-null values in a col|
| **CountIf**             | Integer      | Counts the number of values meeting a condition a col|
| **First**               | Any          | Returns the first value in a col |   |
| **GroupBy**             | Table        | |
| **Join**                | Table        | Inner joins two tables on one key col
| **KeepCols**            | Table        |   | 
| **Last**                | Any          |   |
| **LinearInterpolation** | Table        |   |
| **Smear**               | Table        | Last non-null value carried forward over nulls (smear)| 
| **Split**               | List<Table>  | Splits a table into 1 or many tables based on a provide col to key and group on |
| **Max**                 | Number       | Returns the max value in a col |
| **Mean**                | Double       | Returns the mean (average) value in a col |
| **Min**                 | Number       | Returns the min value in a col |
| **StandardDeviation**   | Double       | Returns the entire population's standard deviation |
| **Sum**                 | Number       | Returns the sum (total) value in a col |

### Advanced
