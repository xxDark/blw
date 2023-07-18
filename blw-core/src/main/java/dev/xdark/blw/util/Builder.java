package dev.xdark.blw.util;

public sealed interface Builder permits Builder.Root, Builder.Nested {

	non-sealed interface Root<E> extends Builder, Reflectable<E> {

		E build();

		@Override
		default E reflectAs() {
			return build();
		}
	}

	non-sealed interface Nested<U extends Builder> extends Builder {

		U __();
	}
}
