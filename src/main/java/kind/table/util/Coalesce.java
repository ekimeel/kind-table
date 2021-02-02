package kind.table.util;

import java.util.Optional;
import java.util.function.Supplier;

import static java.util.Arrays.asList;

public final class Coalesce {

    public static <T> T coalesce(Supplier<T>... next) {
        return asList(next)
                .stream()
                .map(n -> n.get())
                .filter(n -> n != null)
                .findFirst()
                .orElse(null);
    }


    public static <T> Optional<T> coalesce(NextSupplier<Optional<T>>... next) {
        return asList(next)
                .stream()
                .map(n -> n.get())
                .filter(t -> t.isPresent())
                .findFirst()
                .orElse(Optional.<T>empty());
    }

    public static <T> Optional<T> coalesce(Optional<T>... ts) {
        return asList(ts)
                .stream()
                .filter(t -> t.isPresent())
                .findFirst()
                .orElse(Optional.<T>empty());
    }

    public static <T> T coalesce(T... ts) {
        return asList(ts)
                .stream()
                .filter(t -> t != null)
                .findFirst()
                .orElse(null);
    }

    public interface NextSupplier<T> extends Supplier<T> {}
}