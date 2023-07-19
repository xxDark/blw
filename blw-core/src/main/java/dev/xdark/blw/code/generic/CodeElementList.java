package dev.xdark.blw.code.generic;

import dev.xdark.blw.code.CodeElement;
import dev.xdark.blw.code.Label;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public final class CodeElementList extends AbstractList<CodeElement> implements List<CodeElement> {
	private final List<CodeElement> backing;

	public CodeElementList(List<CodeElement> backing) {
		this.backing = backing;
	}

	public CodeElementList() {
		this(new ArrayList<>());
	}

	@Override
	public boolean add(CodeElement element) {
		List<CodeElement> backing = this.backing;
		int size = backing.size();
		backing.add(element);
		index(element, size);
		return true;
	}

	@Override
	public CodeElement remove(int index) {
		CodeElement element = backing.remove(index);
		indexLabelsFrom(index);
		return element;
	}

	@Override
	public CodeElement set(int index, CodeElement element) {
		CodeElement previous = backing.set(index, element);
		if (previous instanceof Label l) {
			l.index(Label.UNSET);
		}
		if (element instanceof Label l) {
			l.index(index);
		}
		return previous;
	}

	@Override
	public CodeElement get(int index) {
		return backing.get(index);
	}

	@Override
	public int size() {
		return backing.size();
	}

	@Override
	public int indexOf(Object o) {
		return backing.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return backing.lastIndexOf(o);
	}

	@Override
	public ListIterator<CodeElement> listIterator(int index) {
		ListIterator<CodeElement> iterator = backing.listIterator(index);
		return new ListIterator<>() {
			@Override
			public boolean hasNext() {
				return iterator.hasNext();
			}

			@Override
			public CodeElement next() {
				return iterator.next();
			}

			@Override
			public boolean hasPrevious() {
				return iterator.hasNext();
			}

			@Override
			public CodeElement previous() {
				return iterator.previous();
			}

			@Override
			public int nextIndex() {
				return iterator.nextIndex();
			}

			@Override
			public int previousIndex() {
				return iterator.previousIndex();
			}

			@Override
			public void remove() {
				int index = iterator.previousIndex() - 1;
				iterator.remove();
				indexLabelsFrom(index);
			}

			@Override
			public void set(CodeElement element) {
				int index = iterator.previousIndex() - 1;
				CodeElement old = backing.get(index);
				iterator.set(element);
				if (old instanceof Label l) {
					l.index(Label.UNSET);
				}
				if (element instanceof Label l) {
					l.index(index);
				}
			}

			@Override
			public void add(CodeElement element) {
				int index = iterator.previousIndex() - 1;
				iterator.add(element);
				indexLabelsFrom(index);
			}
		};
	}

	private void index(CodeElement element, int idx) {
		if (element instanceof Label l) {
			l.index(idx);
		}
	}

	private void indexLabelsFrom(int from) {
		List<CodeElement> backing = this.backing;
		for (int end = backing.size(); from < end; from++) {
			if (backing.get(from) instanceof Label l) {
				l.index(from);
			}
		}
	}

	private void indexLabels() {
		List<CodeElement> backing = this.backing;
		for (int i = backing.size(); i != 0; ) {
			if (backing.get(--i) instanceof Label l) {
				l.index(i);
			}
		}
	}
}
