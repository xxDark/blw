package dev.xdark.blw.annotation;

import dev.xdark.blw.type.InstanceType;

sealed public interface AnnotationBuilder permits AnnotationBuilder.Root, AnnotationBuilder.Nested {

	AnnotationBuilder element(String name, Element element);

	Nested<? extends AnnotationBuilder> annotation(String name, InstanceType type);

	ElementArrayBuilder.Nested<? extends AnnotationBuilder> array(String name);

	non-sealed interface Root extends AnnotationBuilder, dev.xdark.blw.util.Builder.Root<Annotation> {

		@Override
		AnnotationBuilder.Root element(String name, Element element);

		@Override
		AnnotationBuilder.Nested<AnnotationBuilder.Root> annotation(String name, InstanceType type);

		@Override
		ElementArrayBuilder.Nested<AnnotationBuilder.Root> array(String name);
	}

	non-sealed interface Nested<U extends dev.xdark.blw.util.Builder> extends AnnotationBuilder, dev.xdark.blw.util.Builder.Nested<U> {

		@Override
		AnnotationBuilder.Nested<U> element(String name, Element element);

		@Override
		AnnotationBuilder.Nested<AnnotationBuilder.Nested<U>> annotation(String name, InstanceType type);

		@Override
		ElementArrayBuilder.Nested<AnnotationBuilder.Nested<U>> array(String name);
	}
}
