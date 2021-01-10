package kind.table;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class RowCopier {

    private final int THREAD_MODE = 9999;

    void copyRows(Table from, Table to){
        final TableSettings settings = from.getSettings();

        if (settings != null && from.getRowCount() > THREAD_MODE) {
            final List<List<Row>> partitions = Lists.partition(from.getRows(),
                    from.getRowCount() / settings.getMaxAllowableThreads());

            final ExecutorService executor = Executors.newFixedThreadPool(settings.getMaxAllowableThreads());

            for(List<Row> partition : partitions) {
                final Runnable task = () -> {
                    for(Row row : partition) {
                        to.addRow(row);
                    }
                };
                executor.execute(task);
            }
            executor.shutdown();

            try {
                executor.awaitTermination(settings.getEvalTimeout(), TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                throw new RuntimeException("InterruptedException", e);
            }
        } else {
            for (Row row : from.getRows()) {
                to.addRow(row.copy());
            }
        }

    }
}
