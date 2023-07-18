package dev.xdark.blw.annotation;

import dev.xdark.blw.type.InstanceType;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public non-sealed interface Annotation extends Element, Iterable<Map.Entry<String, Element>> {

	InstanceType type();

	Collection<String> names();

	@Nullable <E extends Element> E get(String name);

	default Stream<Map.Entry<String, Element>> stream() {
		return StreamSupport.stream(spliterator(), false);
	}
}
