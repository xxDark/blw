package dev.xdark.blw.classfile;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.util.Builder;
import org.jetbrains.annotations.Nullable;

public sealed interface FieldBuilder extends MemberBuilder permits FieldBuilder.Root, FieldBuilder.Nested {

	FieldBuilder defaultValue(@Nullable Constant value);

	non-sealed interface Root extends FieldBuilder, MemberBuilder.Root<Field> {

		FieldBuilder.Root type(ClassType type);

		@Override
		FieldBuilder.Root defaultValue(@Nullable Constant value);

		@Override
		FieldBuilder.Root accessFlags(int accessFlags);

		@Override
		FieldBuilder.Root name(String name);

		@Override
		FieldBuilder.Root signature(@Nullable String signature);

		@Override
		@Nullable AnnotationBuilder.Nested<FieldBuilder.Root> visibleRuntimeAnnotation(InstanceType type);

		@Override
		@Nullable AnnotationBuilder.Nested<FieldBuilder.Root> invisibleRuntimeAnnotation(InstanceType type);
	}

	non-sealed interface Nested<U extends Builder> extends FieldBuilder, MemberBuilder.Nested<U> {

		@Override
		FieldBuilder.Nested<U> defaultValue(@Nullable Constant value);

		@Override
		FieldBuilder.Nested<U> signature(@Nullable String signature);

		@Override
		@Nullable AnnotationBuilder.Nested<FieldBuilder.Nested<U>> visibleRuntimeAnnotation(InstanceType type);

		@Override
		@Nullable AnnotationBuilder.Nested<FieldBuilder.Nested<U>> invisibleRuntimeAnnotation(InstanceType type);
	}
}
