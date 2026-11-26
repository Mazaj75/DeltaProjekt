package di;

import java.util.HashMap;
import java.util.Map;

/**
 * Jednoduchý registr služeb: třída -> instance.
 */
public class Container {

    private final Map<Class<?>, Object> services = new HashMap<>();

    public <T> void register(Class<T> type, T instance) {
        services.put(type, instance);
    }

    public <T> T get(Class<T> type) {
        Object service = services.get(type);

        if (service == null) {
            throw new IllegalArgumentException("Služba není registrována: " + type.getName());
        }

        return type.cast(service);
    }
}