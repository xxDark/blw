package dev.xdark.blw.util.arena;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public interface Arena<T> extends Closeable {

    void push(@NotNull T value);

    @Nullable T poll();

    default void push(@NotNull Stream<? extends T> s) {
        Iterator<? extends T> iterator = s.iterator();
        while (iterator.hasNext()) {
            push(iterator.next());
        }
    }

    default void push(@NotNull Collection<? extends T> c) {
        if (!c.isEmpty()) {
            for (T t : c) {
                push(t);
            }
        }
    }

    @Override
    void close();
}
