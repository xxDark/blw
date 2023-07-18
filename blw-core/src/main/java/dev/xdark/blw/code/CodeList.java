package dev.xdark.blw.code;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public interface CodeList extends Iterable<CodeElement> {

	CodeElement at(int index);

	int size();

	default int indexOf(CodeElement element) {
		for (int i = 0, j = size(); i < j; i++) {
			if (element == at(i)) {
				return i;
			}
		}
		return -1;
	}

	default Stream<CodeElement> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
}
