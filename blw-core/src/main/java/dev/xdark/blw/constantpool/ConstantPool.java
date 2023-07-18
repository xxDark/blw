package dev.xdark.blw.constantpool;

import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface ConstantPool extends Iterable<Entry> {

	<E extends Entry> E get(int index);

	IntStream indices();

	default Stream<Entry> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
}
