package dev.xdark.blw.classfile.generic;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.generic.GenericAnnotationBuilder;
import dev.xdark.blw.classfile.ClassBuilder;
import dev.xdark.blw.classfile.ClassFileView;
import dev.xdark.blw.classfile.Field;
import dev.xdark.blw.classfile.FieldBuilder;
import dev.xdark.blw.classfile.Method;
import dev.xdark.blw.classfile.MethodBuilder;
import dev.xdark.blw.constantpool.ConstantPool;
import dev.xdark.blw.internal.BuilderShadow;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.util.Reflectable;
import dev.xdark.blw.version.JavaVersion;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class GenericClassBuilder implements ClassBuilder {
	private final List<AnnotationBuilder> visibleRuntimeAnnotations = new ArrayList<>();
	private final List<AnnotationBuilder> invisibleRuntimeAnnotation = new ArrayList<>();
	private final List<Reflectable<Method>> methods = new ArrayList<>();
	private final List<Reflectable<Field>> fields = new ArrayList<>();
	private int accessFlags;
	private String signature;
	private ConstantPool pool;
	private InstanceType type;
	private InstanceType superClass;
	private List<InstanceType> interfaces = List.of();
	private JavaVersion version;

	@Override
	public ClassBuilder accessFlags(int accessFlags) {
		this.accessFlags = accessFlags;
		return this;
	}

	@Override
	public ClassBuilder signature(@Nullable String signature) {
		this.signature = signature;
		return this;
	}

	@Override
	public AnnotationBuilder.Nested<ClassBuilder> visibleRuntimeAnnotation(InstanceType type) {
		var builder = new GenericAnnotationBuilder.Nested<>(type, (ClassBuilder) this);
		visibleRuntimeAnnotations.add(builder);
		return builder;
	}

	@Override
	public AnnotationBuilder.Nested<ClassBuilder> invisibleRuntimeAnnotation(InstanceType type) {
		var builder = new GenericAnnotationBuilder.Nested<>(type, (ClassBuilder) this);
		invisibleRuntimeAnnotation.add(builder);
		return builder;
	}

	@Override
	public ClassBuilder constantPool(@Nullable ConstantPool constantPool) {
		pool = constantPool;
		return this;
	}

	@Override
	public ClassBuilder type(InstanceType type) {
		this.type = type;
		return this;
	}

	@Override
	public ClassBuilder superClass(@Nullable InstanceType superClass) {
		this.superClass = superClass;
		return this;
	}

	@Override
	public ClassBuilder interfaces(List<InstanceType> interfaces) {
		this.interfaces = interfaces;
		return this;
	}

	@Override
	public ClassBuilder version(JavaVersion version) {
		this.version = version;
		return this;
	}

	@Override
	public MethodBuilder.Nested<ClassBuilder> method(int accessFlags, String name, MethodType type) {
		GenericMethodBuilder.Nested<ClassBuilder> builder = new GenericMethodBuilder.Nested<>(accessFlags, name, type, this);
		methods.add(builder);
		return builder;
	}

	@Override
	public FieldBuilder.Nested<ClassBuilder> field(int accessFlags, String name, ClassType type) {
		var builder = new GenericFieldBuilder.Nested<>(accessFlags, name, type, (ClassBuilder) this);
		fields.add(builder);
		return builder;
	}

	@Override
	public ClassBuilder method(MethodBuilder.Root method) {
		methods.add(method);
		return this;
	}

	@Override
	public ClassBuilder field(FieldBuilder.Root field) {
		fields.add(field);
		return this;
	}

	@Override
	public ClassBuilder method(Method method) {
		methods.add(method);
		return this;
	}

	@Override
	public ClassBuilder field(Field field) {
		fields.add(field);
		return this;
	}

	@Override
	public ClassFileView build() {
		return new GenericClassFileView(
				version,
				pool,
				accessFlags,
				type,
				superClass,
				signature,
				interfaces,
				buildList(methods),
				buildList(fields),
				buildList(visibleRuntimeAnnotations),
				buildList(invisibleRuntimeAnnotation)
		);
	}

	private static <T> List<T> buildList(List<?> builders) {
		return builders.stream().map(x -> ((BuilderShadow<T>) x).build()).toList();
	}
}
