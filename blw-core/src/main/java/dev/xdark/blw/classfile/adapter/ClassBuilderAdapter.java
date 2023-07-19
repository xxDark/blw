package dev.xdark.blw.classfile.adapter;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.classfile.ClassBuilder;
import dev.xdark.blw.classfile.ClassFileView;
import dev.xdark.blw.classfile.Field;
import dev.xdark.blw.classfile.FieldBuilder;
import dev.xdark.blw.classfile.attribute.InnerClass;
import dev.xdark.blw.classfile.Method;
import dev.xdark.blw.classfile.MethodBuilder;
import dev.xdark.blw.constantpool.ConstantPool;
import dev.xdark.blw.type.ClassType;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.MethodType;
import dev.xdark.blw.version.JavaVersion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class ClassBuilderAdapter implements ClassBuilder {
	protected final ClassBuilder delegate;

	protected ClassBuilderAdapter(ClassBuilder delegate) {
		this.delegate = delegate;
	}

	@Override
	public ClassBuilder accessFlags(int accessFlags) {
		delegate.accessFlags(accessFlags);
		return this;
	}

	@Override
	public ClassBuilder signature(@Nullable String signature) {
		delegate.signature(signature);
		return this;
	}

	@Override
	public AnnotationBuilder.@Nullable Nested<ClassBuilder> visibleRuntimeAnnotation(InstanceType type) {
		return delegate.visibleRuntimeAnnotation(type);
	}

	@Override
	public AnnotationBuilder.@Nullable Nested<ClassBuilder> invisibleRuntimeAnnotation(InstanceType type) {
		return delegate.invisibleRuntimeAnnotation(type);
	}

	@Override
	public ClassBuilder constantPool(@Nullable ConstantPool constantPool) {
		delegate.constantPool(constantPool);
		return this;
	}

	@Override
	public ClassBuilder type(InstanceType type) {
		delegate.type(type);
		return this;
	}

	@Override
	public ClassBuilder superClass(@Nullable InstanceType superClass) {
		delegate.superClass(superClass);
		return this;
	}

	@Override
	public ClassBuilder interfaces(List<InstanceType> interfaces) {
		delegate.interfaces(interfaces);
		return this;
	}

	@Override
	public ClassBuilder version(JavaVersion version) {
		delegate.version(version);
		return this;
	}

	@Override
	public MethodBuilder.@Nullable Nested<ClassBuilder> method(int accessFlags, String name, MethodType type) {
		return delegate.method(accessFlags, name, type);
	}

	@Override
	public FieldBuilder.@Nullable Nested<ClassBuilder> field(int accessFlags, String name, ClassType type) {
		return delegate.field(accessFlags, name, type);
	}

	@Override
	public ClassBuilder method(MethodBuilder.Root method) {
		delegate.method(method);
		return this;
	}

	@Override
	public ClassBuilder field(FieldBuilder.Root field) {
		delegate.field(field);
		return this;
	}

	@Override
	public ClassBuilder method(Method method) {
		delegate.method(method);
		return this;
	}

	@Override
	public ClassBuilder field(Field field) {
		delegate.field(field);
		return this;
	}

	@Override
	public ClassBuilder innerClasses(List<InnerClass> innerClasses) {
		delegate.innerClasses(innerClasses);
		return this;
	}

	@Override
	public ClassBuilder innerClass(InnerClass innerClass) {
		delegate.innerClass(innerClass);
		return this;
	}

	@Override
	public ClassBuilder nestHost(@Nullable InstanceType nestHost) {
		delegate.nestHost(nestHost);
		return this;
	}

	@Override
	public ClassFileView build() {
		return delegate.build();
	}
}
