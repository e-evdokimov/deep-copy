package org.example.copiers;

import org.example.Copier;
import org.example.CopyUtils;

import java.lang.reflect.Array;

public class ArrayCopier implements Copier {

    @Override
    public <T> T copy(CopyUtils.MainCopier mainCopier, T from) {
        Object[] origArray = (Object[]) from;

        int len = origArray.length;
        Object[] copy = (Object[]) Array.newInstance(origArray.getClass().getComponentType(), len);

        mainCopier.putToCache(copy);

        for (int i = 0; i < len; i++) {
            copy[i] = mainCopier.copy(origArray[i]);
        }

        //noinspection unchecked
        return (T)copy;
    }
}
