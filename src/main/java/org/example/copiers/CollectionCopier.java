package org.example.copiers;

import org.example.Copier;
import org.example.CopyException;

import java.util.Collection;

public class CollectionCopier implements Copier {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> T copy(MainCopier mainCopier, T from) {
        Class<?> clazz = from.getClass();
        Collection copy;
        try {
            //noinspection deprecation
            copy = (Collection) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CopyException("Copying collections of type <" + clazz + "> is not supported", e);
        }
        mainCopier.putToCache(copy);

        //noinspection OverlyStrongTypeCast
        for (Object el : (Collection<Object>) from) {
            copy.add(mainCopier.copy(el));
        }

        return (T) copy;
    }

}
