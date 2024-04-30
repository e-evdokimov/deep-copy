package org.example.copiers;

import org.example.Copier;
import org.example.CopyException;

import java.util.Map;

public class MapCopier implements Copier {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T> T copy(MainCopier mainCopier, T from) {
        Class<?> clazz = from.getClass();
        Map copy;
        try {
            //noinspection deprecation
            copy = (Map) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new CopyException("Copying collections of type <" + clazz + "> is not supported", e);
        }
        mainCopier.putToCache(copy);

        for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) from).entrySet()) {
            copy.put(mainCopier.copy(entry.getKey()), mainCopier.copy(entry.getValue()));
        }

        return (T) copy;
    }

}
