package dev.xdark.blw.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

public final class SingletonIterator<E> implements Iterator<E> {
	private boolean hasNext = true;
	private final E element;

	public SingletonIterator(E element) {
		this.element = element;
	}

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public E next() {
		if (hasNext) {
			hasNext = false;
			return element;
		}
		throw new NoSuchElementException();
	}

	@Override
	public void remove() {
		if (hasNext) {
			hasNext = false;
			return;
		}
		throw new NoSuchElementException();
	}
}
