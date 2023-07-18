package dev.xdark.blw.util.arena;

import org.jetbrains.annotations.NotNull;

public interface ArenaAllocator<T> {

    @NotNull Arena<T> push();
}
