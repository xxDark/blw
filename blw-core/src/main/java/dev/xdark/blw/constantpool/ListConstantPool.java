package dev.xdark.blw.constantpool;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

public final class ListConstantPool implements ConstantPool {
	private final List<Entry> entries;

	public ListConstantPool(List<Entry> entries) {
		this.entries = entries;
	}

	@Override
	public <E extends Entry> E get(int index) {
		return (E) entries.get(index);
	}

	@Override
	public IntStream indices() {
		List<Entry> entries = this.entries;
		return IntStream.range(0, entries.size())
				.filter(x -> entries.get(x) != null);
	}

	@NotNull
	@Override
	public Iterator<Entry> iterator() {
		Iterator<Entry> iterator = entries.iterator();
		return new Iterator<>() {
			Entry entry;

			@Override
			public boolean hasNext() {
				Entry entry = this.entry;
				if (entry != null) {
					return true;
				}
				Iterator<Entry> it = iterator;
				while (it.hasNext()) {
					entry = it.next();
					if (entry != null) {
						this.entry = entry;
						return true;
					}
				}
				return false;
			}

			@Override
			public Entry next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				}
				Entry entry = this.entry;
				this.entry = null;
				return entry;
			}
		};
	}
}
