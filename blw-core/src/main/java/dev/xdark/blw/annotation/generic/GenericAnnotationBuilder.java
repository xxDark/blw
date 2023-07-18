package dev.xdark.blw.annotation.generic;

import dev.xdark.blw.annotation.Annotation;
import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.annotation.ElementArrayBuilder;
import dev.xdark.blw.annotation.MapAnnotation;
import dev.xdark.blw.internal.BuilderShadow;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.util.Builder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public abstract sealed class GenericAnnotationBuilder implements BuilderShadow<Annotation> permits GenericAnnotationBuilder.Root, GenericAnnotationBuilder.Nested {
	protected final Map<String, Object> elements = new HashMap<>();
	protected final InstanceType type;

	public GenericAnnotationBuilder(InstanceType type) {
		this.type = type;
	}

	public AnnotationBuilder element(String name, Element element) {
		elements.put(name, element);
		return (AnnotationBuilder) this;
	}

	@Override
	public final Annotation build() {
		return new MapAnnotation(type, elements.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> {
			Object value = e.getValue();
			return value instanceof Element element ? element : ((BuilderShadow<Element>) value).build();
		})));
	}

	public static final class Root extends GenericAnnotationBuilder implements AnnotationBuilder.Root {

		public Root(InstanceType type) {
			super(type);
		}

		@Override
		public AnnotationBuilder.Root element(String name, Element element) {
			return (AnnotationBuilder.Root) super.element(name, element);
		}

		@Override
		public AnnotationBuilder.Nested<AnnotationBuilder.Root> annotation(String name, InstanceType type) {
			AnnotationBuilder.Nested<AnnotationBuilder.Root> builder = new GenericAnnotationBuilder.Nested<>(type, this);
			elements.put(name, builder);
			return builder;
		}

		@Override
		public ElementArrayBuilder.Nested<AnnotationBuilder.Root> array(String name) {
			var builder = new GenericArrayBuilder.Nested<AnnotationBuilder.Root>(this);
			elements.put(name, builder);
			return builder;
		}

		@Override
		public Annotation reflectAs() {
			return super.reflectAs();
		}
	}

	public static final class Nested<U extends Builder> extends GenericAnnotationBuilder implements AnnotationBuilder.Nested<U> {
		private final U upstream;

		public Nested(InstanceType type, U upstream) {
			super(type);
			this.upstream = upstream;
		}

		@Override
		public AnnotationBuilder.Nested<U> element(String name, Element element) {
			//noinspection unchecked
			return (AnnotationBuilder.Nested<U>) super.element(name, element);
		}

		@Override
		public AnnotationBuilder.Nested<AnnotationBuilder.Nested<U>> annotation(String name, InstanceType type) {
			AnnotationBuilder.Nested<AnnotationBuilder.Nested<U>> builder = new GenericAnnotationBuilder.Nested<>(type, this);
			elements.put(name, builder);
			return builder;
		}

		@Override
		public ElementArrayBuilder.Nested<AnnotationBuilder.Nested<U>> array(String name) {
			var builder = new GenericArrayBuilder.Nested<AnnotationBuilder.Nested<U>>(this);
			elements.put(name, builder);
			return builder;
		}

		@Override
		public U __() {
			return upstream;
		}
	}
}
