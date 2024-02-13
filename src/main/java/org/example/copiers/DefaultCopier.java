package org.example.copiers;

import org.example.Copier;
import org.example.CopyException;
import org.example.CopyUtils;
import sun.reflect.ReflectionFactory;

import java.lang.constant.Constable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.function.Consumer;

public class DefaultCopier implements Copier {

    @Override
    public <T> T copy(CopyUtils.MainCopier mainCopier, T from) {
        Class<?> clazz = from.getClass();

        if (clazz.isPrimitive() || from instanceof Constable) {
            return from;
        }

        Object copy = createNewInstance(clazz);
        mainCopier.putToCache(copy);

        forEachSuperclasses(clazz, superClass -> {
            Field[] fields = superClass.getDeclaredFields();
            for (Field f : fields) {
                int modifiers = f.getModifiers();

                if (f.isSynthetic()
                        || Modifier.isAbstract(modifiers)
                        || Modifier.isTransient(modifiers)
                        || Modifier.isStatic(modifiers)) {
                    continue;
                }

                f.setAccessible(true);
                try {
                    f.set(copy, mainCopier.copy(f.get(from)));
                } catch (IllegalAccessException e) {
                    throw new CopyException(e);
                }
            }
        });

        //noinspection unchecked
        return (T) copy;
    }

    private <T> void forEachSuperclasses(Class<T> clazz, @SuppressWarnings("BoundedWildcard") Consumer<Class<? super T>> runnable) {
        for (Class<? super T> superClass = clazz; superClass != null; superClass = superClass.getSuperclass()) {
            runnable.accept(superClass);
        }
    }

    private <T> T createNewInstance(Class<T> clazz) {
        ReflectionFactory rf = ReflectionFactory.getReflectionFactory();

        Constructor<Object> objCons = getObjectDefaultConstructor();
        Constructor<?> initConstr = rf.newConstructorForSerialization(clazz, objCons);
        try {
            return clazz.cast(initConstr.newInstance());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new CopyException("Copying objects of type <" + clazz + "> is not supported", e);
        }
    }

    private static Constructor<Object> getObjectDefaultConstructor() {
        Constructor<Object> objCons;
        try {
            objCons = Object.class.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new AssertionError(e); // should not get here
        }
        return objCons;
    }

}
