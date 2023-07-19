package dev.xdark.blw.util.arena;

import dev.xdark.blw.util.SingletonIterator;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Stream;

public final class FrameArenaAllocator<T> implements ArenaAllocator<T> {

    private int[] frames = new int[16];
    private int frameIndex;
    private final Impl impl = new Impl();

    @Override
    public @NotNull Arena<T> push() {
        int[] frames = this.frames;
        int nextFrame = frameIndex++;
        if (nextFrame == frames.length) {
            frames = Arrays.copyOf(frames, nextFrame + 16);
            this.frames = frames;
        }
        Impl impl = this.impl;
        frames[nextFrame] = impl.index;
        return impl;
    }

    private final class Impl implements Arena<T> {
        private static final Iterator<?>[] EMPTY_ARRAY = {};

        private Iterator<? extends T>[] cache = (Iterator<T>[]) EMPTY_ARRAY;
        private int index;

        @Override
        public void push(@NotNull T value) {
            implPush(new SingletonIterator<>(value));
        }

        @Override
        public void push(@NotNull Stream<? extends T> s) {
            implPush(s.iterator());
        }

        @Override
        public void push(@NotNull Collection<? extends T> c) {
            if (!c.isEmpty()) {
                implPush(c.iterator());
            }
        }

        @Override
        public T poll() {
            int index = this.index;
            int pollLimit = frames[frameIndex - 1];
            while (true) {
                if (index == pollLimit) {
                    this.index = index;
                    return null;
                }
                int previousIndex = index - 1;
                Iterator<? extends T> iterator = cache[previousIndex];
                if (iterator.hasNext()) {
                    this.index = previousIndex;
                    return iterator.next();
                }
                index = previousIndex;
            }
        }

        @Override
        public void close() {
            index = frames[--frameIndex];
        }

        private void implPush(Iterator<? extends T> iterator) {
            Iterator<? extends T>[] cache = this.cache;
            int index = this.index++;
            if (index == cache.length) {
                cache = Arrays.copyOf(cache, index + 16);
                this.cache = cache;
            }
            cache[index] = iterator;
        }
    }
}
