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

}
