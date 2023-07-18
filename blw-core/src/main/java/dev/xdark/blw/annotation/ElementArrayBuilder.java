package dev.xdark.blw.annotation;

import dev.xdark.blw.type.InstanceType;

import java.util.List;

sealed public interface ElementArrayBuilder permits ElementArrayBuilder.Root, ElementArrayBuilder.Nested {

	ElementArrayBuilder element(Element element);

	ElementArrayBuilder elements(List<Element> elements);

	AnnotationBuilder.Nested<? extends ElementArrayBuilder> annotation(InstanceType type);

	Nested<? extends ElementArrayBuilder> array();

	default ElementArrayBuilder elements(Element... elements) {
		return elements(List.of(elements));
	}

	non-sealed interface Root extends ElementArrayBuilder, dev.xdark.blw.util.Builder.Root<ElementArray> {

		@Override
		ElementArrayBuilder.Root element(Element element);

		@Override
		ElementArrayBuilder.Root elements(List<Element> elements);

		@Override
		AnnotationBuilder.Nested<ElementArrayBuilder.Root> annotation(InstanceType type);

		@Override
		ElementArrayBuilder.Nested<ElementArrayBuilder.Root> array();

		@Override
		default ElementArrayBuilder.Root elements(Element... elements) {
			return (ElementArrayBuilder.Root) ElementArrayBuilder.super.elements(elements);
		}
	}

	non-sealed interface Nested<U extends dev.xdark.blw.util.Builder> extends ElementArrayBuilder, dev.xdark.blw.util.Builder.Nested<U> {

		@Override
		ElementArrayBuilder.Nested<U> element(Element element);

		@Override
		ElementArrayBuilder.Nested<U> elements(List<Element> elements);

		@Override
		AnnotationBuilder.Nested<ElementArrayBuilder.Nested<U>> annotation(InstanceType type);

		@Override
		ElementArrayBuilder.Nested<ElementArrayBuilder.Nested<U>> array();

		@Override
		default ElementArrayBuilder.Nested<U> elements(Element... elements) {
			return (ElementArrayBuilder.Nested<U>) ElementArrayBuilder.super.elements(elements);
		}
	}
}
