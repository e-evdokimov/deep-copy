package org.example;

import org.example.copiers.ArrayCopier;
import org.example.copiers.CollectionCopier;
import org.example.copiers.DefaultCopier;
import org.example.test.Man;

import java.util.*;

/**
 * Non thread-safe
 */
public class CopyUtils {

    private CopyUtils() {
        // private constructor
    }


    public static void main(String[] args) {
        Man friend1 = new Man("Fred", 22, new ArrayList<>(List.of("Book1")), null);
        Man friend2 = new Man("Lucy", 19, new ArrayList<>(List.of("Book2")), null);
        Man orig = new Man("Alex", 20, new ArrayList<>(List.of("Book3")), null);

        friend1.setFriends(List.of(friend2, orig));
        friend2.setFriends(List.of(friend1, orig));
        orig.setFriends(List.of(friend1, friend2));

        Man copy = CopyUtils.deepCopy(orig);

        assert orig != copy;
        assert orig.equals(copy);
    }

    public static <T> T deepCopy(T original) {
        MainCopier mainCopier = new MainCopier();
        return mainCopier.copy(original);
    }


    public static class MainCopier {

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

        public <T> void putToCache(T newObj) {
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

}
