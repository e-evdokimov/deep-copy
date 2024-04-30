package org.example.copiers;

import org.example.Copier;

import java.util.*;

public class MainCopier {

    private final Map<Object, Object> copyCache = new IdentityHashMap<>();
    private Object forCache;

    private static final Map<Class<?>, Copier> copierRegistry = Map.of(
        Collection.class, new CollectionCopier(),
        Date.class, new DateCopier(),
        Map.class, new MapCopier()
    );
    private static final Copier ARRAY_COPIER = new ArrayCopier();
    private static final Copier DEFAULT_COPIER = new DefaultCopier();


    public <T> T copy(T original) {
        if (original == null) {
            return null;
        }

        Object copy = copyCache.get(original);
        if (copy != null) {
            //noinspection unchecked
            return (T)copy;
        }

        forCache = original;

        Copier copier = getCopier(original.getClass());
        T newObj = copier.copy(this, original);
        putToCache(newObj);

        return newObj;
    }

    public void putToCache(Object newObj) {
        if (forCache != null) {
            Objects.requireNonNull(newObj);
            copyCache.put(forCache, newObj);
            forCache = null;
        }
    }

    private static Copier getCopier(Class<?> origClass) {
        if (origClass.isArray()) {
            return ARRAY_COPIER;
        }

        // TODO Enums
        // TODO Proxy
        // TODO etc., etc.
        return findCopier(origClass).orElse(DEFAULT_COPIER);
    }

    private static Optional<Copier> findCopier(Class<?> origClass) {
        return copierRegistry.entrySet().stream()
            .filter(e -> e.getKey().isAssignableFrom(origClass))
            .map(Map.Entry::getValue)
            .findFirst();
    }
}
