package dev.xdark.blw.code.generic;

import dev.xdark.blw.code.CodeElement;
import dev.xdark.blw.code.CodeList;
import dev.xdark.blw.code.Label;
import dev.xdark.blw.code.MutableCodeList;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public final class GenericCodeList implements MutableCodeList {
	private final List<CodeElement> list;

	public GenericCodeList(List<CodeElement> list) {
		this.list = list;
	}

	@Override
	public CodeElement at(int index) {
		return list.get(index);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public int indexOf(CodeElement element) {
		return list.indexOf(element);
	}

	@Override
	public void add(CodeElement element) {
		list.add(element);
		recalculateIndices();
	}

	@Override
	public void addAll(CodeList list) {
		List<CodeElement> c = list.stream().toList();
		if (!c.isEmpty()) {
			this.list.addAll(c);
			recalculateIndices();
		}
	}

	@Override
	public void add(int index, CodeElement element) {
		list.add(index, element);
		recalculateIndices();
	}

	@Override
	public void addAll(int index, CodeList list) {
		List<CodeElement> c = list.stream().toList();
		if (!c.isEmpty()) {
			this.list.addAll(index, c);
			recalculateIndices();
		}
	}

	@Override
	public Iterator<CodeElement> iterator() {
		return list.iterator();
	}

	@Override
	public Stream<CodeElement> stream() {
		return list.stream();
	}

	private void recalculateIndices() {
		List<CodeElement> l = list;
		for (int i = l.size(); i != 0; ) {
			if (l.get(--i) instanceof Label label) {
				label.index(i);
			}
		}
	}
}
