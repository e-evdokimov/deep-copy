package org.example.copiers;

import org.example.Copier;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Objects;

public class MainCopier {

    private final Map<Object, Object> copyCache = new IdentityHashMap<>();

    private Object forCache;


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
            return new ArrayCopier();
        }
        if (Collection.class.isAssignableFrom(origClass)) {
            return new CollectionCopier();
        }
        // TODO Maps
        // TODO Enums
        // TODO Proxy
        // TODO etc., etc.
        return new DefaultCopier();
    }
}
