package dev.xdark.blw.annotation;

import dev.xdark.blw.annotation.Annotation;
import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.type.InstanceType;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

public final class MapAnnotation implements Annotation {
	private final InstanceType type;
	private final Map<String, Element> map;

	public MapAnnotation(InstanceType type, Map<String, Element> map) {
		this.type = type;
		this.map = map;
	}

	@Override
	public InstanceType type() {
		return type;
	}

	@Override
	public Collection<String> names() {
		return map.keySet();
	}

	@Override
	public <E extends Element> @Nullable E get(String name) {
		return (E) map.get(name);
	}

	@Override
	public Iterator<Map.Entry<String, Element>> iterator() {
		return map.entrySet().iterator();
	}
}
