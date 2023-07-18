package dev.xdark.blw.internal;

import dev.xdark.blw.util.Reflectable;

public interface BuilderShadow<E> extends Reflectable<E> {

	E build();

	@Override
	default E reflectAs() {
		return build();
	}
}
