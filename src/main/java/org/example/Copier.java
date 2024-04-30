package org.example;

import org.example.copiers.MainCopier;

public interface Copier {
    <T> T copy(MainCopier mainCopier, T from);
}
