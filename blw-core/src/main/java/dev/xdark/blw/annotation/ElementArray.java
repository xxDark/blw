package dev.xdark.blw.annotation;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public final class ElementArray implements Element, Iterable<Element> {
	private final List<Element> elements;

	public ElementArray(List<Element> elements) {
		this.elements = elements;
	}

	public ElementArray(Stream<Element> elements) {
		this(elements.toList());
	}

	public <E extends Element> E get(int index) {
		return (E) elements.get(index);
	}

	public int count() {
		return elements.size();
	}

	public Stream<Element> stream() {
		return elements.stream();
	}

	@Override
	public Iterator<Element> iterator() {
		return elements.iterator();
	}
}
