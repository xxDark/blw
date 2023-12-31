package dev.xdark.blw.classfile;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.classfile.attribute.Parameter;
import dev.xdark.blw.classfile.generic.GenericMethodBuilder;
import dev.xdark.blw.code.Code;
import dev.xdark.blw.code.CodeBuilder;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.util.Builder;
import dev.xdark.blw.util.Reflectable;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface MethodBuilder extends MemberBuilder {

	MethodBuilder exceptionTypes(List<InstanceType> exceptionTypes);

	MethodBuilder parameters(List<Parameter> parameters);

	MethodBuilder parameter(Parameter parameter);

	MethodBuilder code(Code code);

	MethodBuilder annotationDefault(Element annotationDefault);

	MethodBuilder annotationDefault(Reflectable<? extends Element> annotationDefault);

	@Nullable
	CodeBuilder.Nested<? extends MethodBuilder> code();

	interface Root extends MethodBuilder, MemberBuilder.Root<Method> {

		MethodBuilder.Root type(MethodType descriptor);

		@Override
		MethodBuilder.Root accessFlags(int accessFlags);

		@Override
		MethodBuilder.Root signature(@Nullable String signature);

		@Override
		@Nullable AnnotationBuilder.Nested<MethodBuilder.Root> visibleRuntimeAnnotation(InstanceType type);

		@Override
		@Nullable AnnotationBuilder.Nested<MethodBuilder.Root> invisibleRuntimeAnnotation(InstanceType type);

		@Override
		MethodBuilder.Root name(String name);

		@Override
		MethodBuilder.Root exceptionTypes(List<InstanceType> exceptionTypes);

		@Override
		MethodBuilder.Root parameters(List<Parameter> parameters);

		@Override
		MethodBuilder.Root parameter(Parameter parameter);

		@Override
		CodeBuilder.Nested<MethodBuilder.Root> code();

		@Override
		MethodBuilder.Root annotationDefault(Element annotationDefault);

		@Override
		MethodBuilder.Root annotationDefault(Reflectable<? extends Element> annotationDefault);
	}

	interface Nested<U extends Builder> extends MethodBuilder, MemberBuilder.Nested<U> {

		@Override
		MethodBuilder.Nested<U> signature(@Nullable String signature);

		@Override
		@Nullable AnnotationBuilder.Nested<MethodBuilder.Nested<U>> visibleRuntimeAnnotation(InstanceType type);

		@Override
		@Nullable AnnotationBuilder.Nested<MethodBuilder.Nested<U>> invisibleRuntimeAnnotation(InstanceType type);

		@Override
		MethodBuilder.Nested<U> parameters(List<Parameter> parameters);

		@Override
		MethodBuilder.Nested<U> parameter(Parameter parameter);

		@Override
		CodeBuilder.Nested<MethodBuilder.Nested<U>> code();

		@Override
		MethodBuilder.Nested<U> annotationDefault(Element annotationDefault);

		@Override
		MethodBuilder.Nested<U> annotationDefault(Reflectable<? extends Element> annotationDefault);
	}

	static MethodBuilder.Root builder() {
		return new GenericMethodBuilder.Root();
	}
}
