package unsw.loopmania.util;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class CustomCollectors {
    
    /**
     * Returns a {@code Collector} which provides the single element received.
     * @param <T> the type of the input element(s)
     * @throws IllegalStateException when there is multiple or no input elements
     * @return a {@code Collector} which returns the single input element
     */
    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
            Collectors.toList(),
            list -> {
                if (list.size() != 1) {
                    throw new IllegalStateException();
                }
                return list.get(0);
            }
        );
    }
    
}
