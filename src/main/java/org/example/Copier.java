package org.example;

public interface Copier {
    <T> T copy(CopyUtils.MainCopier mainCopier, T from);
}
