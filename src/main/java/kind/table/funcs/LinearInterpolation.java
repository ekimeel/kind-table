package kind.table.funcs;

import kind.table.cols.Column;
import kind.table.Table;
import kind.table.cols.IntegerColumn;
import kind.table.cols.NumberColumn;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class LinearInterpolation implements Func<Table> {

    private static final short DEFAULT_DECIMAL_PRECISION = 4;
    private static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_UP;

    private final Integer col;
    private Table table;
    private short decimalPrecision;
    private RoundingMode roundingMode;

    public LinearInterpolation(int col) {
        this.col = col;
        this.decimalPrecision = DEFAULT_DECIMAL_PRECISION;
        this.roundingMode = DEFAULT_ROUNDING_MODE;
    }

    public LinearInterpolation(int col, short decimalPrecision, RoundingMode roundingMode) {
        this.col = col;
        this.decimalPrecision = decimalPrecision;
        this.roundingMode = roundingMode;
    }

    @Override
    public boolean acceptColumn(Column column) {
        return (column instanceof NumberColumn);
    }

    @Override
    public Table eval(Table table) {
        final Table copy = table.copy();
        final Column column = table.getColumn(this.col);

        Number priorInstance = null;

        final List<Integer> unitOfWork = new ArrayList<>();
        final List<Number> values = table.getValues(this.col);

        for (int i = 0; i < values.size(); i++) {
            final Number value = values.get(i);

            if ((value == null) && priorInstance != null) {
                unitOfWork.add(i);
            } else if (value != null) {

                if (!unitOfWork.isEmpty() && priorInstance != null) {
                    final int a = unitOfWork.size();
                    final Double start = priorInstance.doubleValue();
                    final Double end = value.doubleValue();
                    final Double diff = end - start;
                    final int gaps = a == 1 ? 2 : a + 1;
                    final Double dist = diff / gaps;

                    int mult = 1;
                    for (Integer e : unitOfWork) {

                        final Double val = new BigDecimal(start + (dist * mult))
                                .setScale(this.decimalPrecision, this.roundingMode).doubleValue();

                        copy.set(e.intValue(), this.col, column.cast(val));
                        mult++;

                    }

                    unitOfWork.clear();
                }
                priorInstance = value;
            }
        }

        return copy;


    }

}