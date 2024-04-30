package org.example.copiers;

import org.example.Copier;

import java.sql.Timestamp;
import java.util.Date;

public class DateCopier implements Copier {
    @Override
    public <T> T copy(MainCopier mainCopier, T from) {
        //noinspection unchecked
        Class<Date> clazz = (Class<Date>) from.getClass();
        Date origDate = (Date) from;

        Date copy = DefaultCopier.createNewInstance(clazz);
        mainCopier.putToCache(copy);

        copy.setTime(origDate.getTime());

        if (from.getClass() == Timestamp.class) {
            // need to handle Timestamp separately as it's not in a type-inheritance relationship with Date class
            Timestamp origTS = (Timestamp) origDate;
            ((Timestamp) copy).setNanos(origTS.getNanos());
        }

        //noinspection unchecked
        return (T) copy;
    }
}
