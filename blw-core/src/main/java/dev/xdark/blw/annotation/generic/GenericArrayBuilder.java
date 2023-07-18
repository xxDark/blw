package dev.xdark.blw.annotation.generic;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.annotation.ElementArray;
import dev.xdark.blw.annotation.ElementArrayBuilder;
import dev.xdark.blw.internal.BuilderShadow;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.util.Builder;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class GenericArrayBuilder implements BuilderShadow<ElementArray> permits GenericArrayBuilder.Root, GenericArrayBuilder.Nested {
	protected final List<Object> values = new ArrayList<>();

	public ElementArrayBuilder element(Element element) {
		values.add(element);
		return (ElementArrayBuilder) this;
	}

	public ElementArrayBuilder elements(List<Element> elements) {
		values.addAll(elements);
		return (ElementArrayBuilder) this;
	}

	@Override
	public final ElementArray build() {
		return new ElementArray(values.stream().map(v -> {
			//noinspection unchecked
			return v instanceof Element element ? element : ((BuilderShadow<Element>) v).build();
		}).toList());
	}

	public static final class Root extends GenericArrayBuilder implements ElementArrayBuilder.Root {

		@Override
		public ElementArrayBuilder.Root element(Element element) {
			return (ElementArrayBuilder.Root) super.element(element);
		}

		@Override
		public ElementArrayBuilder.Root elements(List<Element> elements) {
			return (ElementArrayBuilder.Root) super.elements(elements);
		}

		@Override
		public AnnotationBuilder.Nested<ElementArrayBuilder.Root> annotation(InstanceType type) {
			var builder = new GenericAnnotationBuilder.Nested<ElementArrayBuilder.Root>(type, this);
			values.add(builder);
			return builder;
		}


		@Override
		public ElementArrayBuilder.Nested<ElementArrayBuilder.Root> array() {
			var builder = new GenericArrayBuilder.Nested<ElementArrayBuilder.Root>(this);
			values.add(builder);
			return builder;
		}

		@Override
		public ElementArray reflectAs() {
			return super.reflectAs();
		}
	}

	static final class Nested<U extends Builder> extends GenericArrayBuilder implements ElementArrayBuilder.Nested<U> {
		private final U upstream;

		Nested(U upstream) {
			this.upstream = upstream;
		}

		@Override
		public ElementArrayBuilder.Nested<U> element(Element element) {
			return (ElementArrayBuilder.Nested<U>) super.element(element);
		}

		@Override
		public ElementArrayBuilder.Nested<U> elements(List<Element> elements) {
			return (ElementArrayBuilder.Nested<U>) super.elements(elements);
		}

		@Override
		public AnnotationBuilder.Nested<ElementArrayBuilder.Nested<U>> annotation(InstanceType type) {
			var builder = new GenericAnnotationBuilder.Nested<ElementArrayBuilder.Nested<U>>(type, this);
			values.add(builder);
			return builder;
		}

		@Override
		public ElementArrayBuilder.Nested<ElementArrayBuilder.Nested<U>> array() {
			var builder = new GenericArrayBuilder.Nested<ElementArrayBuilder.Nested<U>>(this);
			values.add(builder);
			return builder;
		}

		@Override
		public U __() {
			return upstream;
		}
	}
}
