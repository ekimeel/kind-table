package kind.table;

import java.util.Comparator;

public class ColComparator implements Comparator<Row> {

    private final int col;
    private final boolean reverse;

    public ColComparator(int col, boolean reverse){
        this.col = col;
        this.reverse = reverse;
    }

    public ColComparator(int col){
        this.col = col;
        this.reverse = false;
    }

    protected int getCol(){
        return col;
    }

    protected boolean isReverse(){
        return reverse;
    }

    @Override
    public int compare(Row r1, Row r2) {

        final Comparable a1 = (Comparable) r1.get(getCol());
        final Comparable a2 = (Comparable) r2.get(getCol());

        return (isReverse()) ? a2.compareTo(a1) : a1.compareTo(a2);
    }

}