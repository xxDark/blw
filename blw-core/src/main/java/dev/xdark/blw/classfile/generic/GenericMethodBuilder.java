package dev.xdark.blw.classfile.generic;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.classfile.Method;
import dev.xdark.blw.classfile.MethodBuilder;
import dev.xdark.blw.classfile.attribute.Parameter;
import dev.xdark.blw.code.Code;
import dev.xdark.blw.code.CodeBuilder;
import dev.xdark.blw.code.generic.GenericCodeBuilder;
import dev.xdark.blw.internal.BuilderShadow;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.util.Builder;
import dev.xdark.blw.util.Reflectable;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class GenericMethodBuilder extends GenericMemberBuilder implements BuilderShadow<Method> permits GenericMethodBuilder.Root, GenericMethodBuilder.Nested {
	protected MethodType type;
	protected int accessFlags;
	protected String name;
	protected List<InstanceType> exceptionTypes = List.of();
	private List<Parameter> parameters = List.of();
	private Reflectable<Code> code;
	private Reflectable<? extends Element> annotationDefault;

	public MethodBuilder exceptionTypes(List<InstanceType> exceptionTypes) {
		this.exceptionTypes = exceptionTypes;
		return (MethodBuilder) this;
	}

	public MethodBuilder parameters(List<Parameter> parameters) {
		this.parameters = parameters;
		return (MethodBuilder) this;
	}

	public MethodBuilder parameter(Parameter parameter) {
		List<Parameter> parameters = this.parameters;
		if (parameters.isEmpty()) {
			parameters = new ArrayList<>();
			this.parameters = parameters;
		}
		parameters.add(parameter);
		return (MethodBuilder) this;
	}

	public MethodBuilder code(Code code) {
		this.code = code;
		return (MethodBuilder) this;
	}

	public CodeBuilder.Nested<? extends MethodBuilder> code() {
		Reflectable<Code> code = this.code;
		if (!(code instanceof CodeBuilder)) {
			code = new GenericCodeBuilder.Nested<>((Builder) this);
			this.code = code;
		}
		//noinspection unchecked
		return (CodeBuilder.Nested<? extends MethodBuilder>) code;
	}

	public MethodBuilder annotationDefault(Element annotationDefault) {
		this.annotationDefault = Reflectable.wrap(annotationDefault);
		return (MethodBuilder) this;
	}

	public MethodBuilder annotationDefault(Reflectable<? extends Element> annotationDefault) {
		this.annotationDefault = annotationDefault;
		return (MethodBuilder) this;
	}

	@Override
	public Method build() {
		Reflectable<Code> code;
		Reflectable<? extends Element> annotationDefault;
		return new GenericMethod(
				accessFlags,
				name,
				signature,
				visibleRuntimeAnnotations(),
				invisibleRuntimeAnnotation(),
				type,
				(code = this.code) == null ? null : code.reflectAs(),
				exceptionTypes,
				parameters,
				(annotationDefault = this.annotationDefault) == null ? null : annotationDefault.reflectAs()
		);
	}

	public static final class Root extends GenericMethodBuilder implements MethodBuilder.Root {

		@Override
		public MethodBuilder.Root type(MethodType type) {
			this.type = type;
			return this;
		}

		@Override
		public MethodBuilder.Root accessFlags(int accessFlags) {
			this.accessFlags = accessFlags;
			return this;
		}

		@Override
		public MethodBuilder.Root name(String name) {
			this.name = name;
			return this;
		}

		@Override
		public MethodBuilder.Root exceptionTypes(List<InstanceType> exceptionTypes) {
			return (MethodBuilder.Root) super.exceptionTypes(exceptionTypes);
		}

		@Override
		public MethodBuilder.Root parameters(List<Parameter> parameters) {
			return (MethodBuilder.Root) super.parameters(parameters);
		}

		@Override
		public MethodBuilder.Root parameter(Parameter parameter) {
			return (MethodBuilder.Root) super.parameter(parameter);
		}

		@Override
		public MethodBuilder.Root signature(@Nullable String signature) {
			return (MethodBuilder.Root) super.signature(signature);
		}

		@Override
		public @Nullable AnnotationBuilder.Nested<MethodBuilder.Root> visibleRuntimeAnnotation(InstanceType type) {
			//noinspection unchecked
			return (AnnotationBuilder.Nested<MethodBuilder.Root>) super.visibleRuntimeAnnotation(type);
		}

		@Override
		public @Nullable AnnotationBuilder.Nested<MethodBuilder.Root> invisibleRuntimeAnnotation(InstanceType type) {
			//noinspection unchecked
			return (AnnotationBuilder.Nested<MethodBuilder.Root>) super.invisibleRuntimeAnnotation(type);
		}

		@Override
		public CodeBuilder.Nested<MethodBuilder.Root> code() {
			//noinspection unchecked
			return (CodeBuilder.Nested<MethodBuilder.Root>) super.code();
		}

		@Override
		public MethodBuilder.Root annotationDefault(Element annotationDefault) {
			return (MethodBuilder.Root) super.annotationDefault(annotationDefault);
		}

		@Override
		public MethodBuilder.Root annotationDefault(Reflectable<? extends Element> annotationDefault) {
			return (MethodBuilder.Root) super.annotationDefault(annotationDefault);
		}

		@Override
		public Method reflectAs() {
			return super.reflectAs();
		}
	}

	public static final class Nested<U extends Builder> extends GenericMethodBuilder implements MethodBuilder.Nested<U> {
		private final U upstream;

		public Nested(int accessFlags, String name, MethodType type, U upstream) {
			this.accessFlags = accessFlags;
			this.name = name;
			this.type = type;
			this.upstream = upstream;
		}

		@Override
		public MethodBuilder.Nested<U> parameters(List<Parameter> parameters) {
			//noinspection unchecked
			return (MethodBuilder.Nested<U>) super.parameters(parameters);
		}

		@Override
		public MethodBuilder.Nested<U> parameter(Parameter parameter) {
			//noinspection unchecked
			return (MethodBuilder.Nested<U>) super.parameter(parameter);
		}

		@Override
		public CodeBuilder.Nested<MethodBuilder.Nested<U>> code() {
			//noinspection unchecked
			return (CodeBuilder.Nested<MethodBuilder.Nested<U>>) super.code();
		}

		@Override
		public MethodBuilder.Nested<U> signature(@Nullable String signature) {
			//noinspection unchecked
			return (MethodBuilder.Nested<U>) super.signature(signature);
		}

		@Override
		public @Nullable AnnotationBuilder.Nested<MethodBuilder.Nested<U>> visibleRuntimeAnnotation(InstanceType type) {
			//noinspection unchecked
			return (AnnotationBuilder.Nested<MethodBuilder.Nested<U>>) super.visibleRuntimeAnnotation(type);
		}

		@Override
		public @Nullable AnnotationBuilder.Nested<MethodBuilder.Nested<U>> invisibleRuntimeAnnotation(InstanceType type) {
			//noinspection unchecked
			return (AnnotationBuilder.Nested<MethodBuilder.Nested<U>>) super.invisibleRuntimeAnnotation(type);
		}

		@Override
		public MethodBuilder.Nested<U> annotationDefault(Element annotationDefault) {
			//noinspection unchecked
			return (MethodBuilder.Nested<U>) super.annotationDefault(annotationDefault);
		}

		@Override
		public MethodBuilder.Nested<U> annotationDefault(Reflectable<? extends Element> annotationDefault) {
			//noinspection unchecked
			return (MethodBuilder.Nested<U>) super.annotationDefault(annotationDefault);
		}

		@Override
		public U __() {
			return upstream;
		}
	}
}
