package org.example;

import org.example.copiers.MainCopier;
import org.example.test.Man;

import java.util.ArrayList;
import java.util.List;

/**
 * Non thread-safe
 */
public final class CopyUtils {

    private CopyUtils() {
        // private constructor
    }


    public static void main(String[] args) {
        Man orig = new Man("Alex", 20, new ArrayList<>(List.of("Book1", "Book2", "Book3")));

        Man copy = CopyUtils.deepCopy(orig);

        assert orig != copy;
        assert orig.equals(copy);
    }

    public static <T> T deepCopy(T original) {
        MainCopier mainCopier = new MainCopier();
        return mainCopier.copy(original);
    }

}
