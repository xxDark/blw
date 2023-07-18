package dev.xdark.blw.classfile.generic;

import dev.xdark.blw.annotation.Annotation;
import dev.xdark.blw.classfile.ClassFileView;
import dev.xdark.blw.classfile.Field;
import dev.xdark.blw.classfile.Method;
import dev.xdark.blw.constantpool.ConstantPool;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.version.JavaVersion;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GenericClassFileView implements ClassFileView {
	private final JavaVersion version;
	private final ConstantPool pool;
	private final int accessFlags;
	private final InstanceType type;
	private final InstanceType superClass;
	private final String signature;
	private final List<InstanceType> interfaces;
	private final List<Method> methods;
	private final List<Field> fields;
	private final List<Annotation> visibleRuntimeAnnotations;
	private final List<Annotation> invisibleRuntimeAnnotations;

	public GenericClassFileView(JavaVersion version, ConstantPool pool, int accessFlags, InstanceType type, InstanceType superClass, String signature, List<InstanceType> interfaces, List<Method> methods, List<Field> fields, List<Annotation> visibleRuntimeAnnotations, List<Annotation> invisibleRuntimeAnnotations) {
		this.version = version;
		this.pool = pool;
		this.accessFlags = accessFlags;
		this.type = type;
		this.superClass = superClass;
		this.signature = signature;
		this.interfaces = interfaces;
		this.methods = methods;
		this.fields = fields;
		this.visibleRuntimeAnnotations = visibleRuntimeAnnotations;
		this.invisibleRuntimeAnnotations = invisibleRuntimeAnnotations;
	}

	@Override
	public int accessFlags() {
		return accessFlags;
	}

	@Override
	public List<Annotation> visibleRuntimeAnnotations() {
		return visibleRuntimeAnnotations;
	}

	@Override
	public List<Annotation> invisibleRuntimeAnnotations() {
		return invisibleRuntimeAnnotations;
	}

	@Override
	public @Nullable ConstantPool constantPool() {
		return pool;
	}

	@Override
	public InstanceType type() {
		return type;
	}

	@Override
	public @Nullable InstanceType superClass() {
		return superClass;
	}

	@Override
	public List<InstanceType> interfaces() {
		return interfaces;
	}

	@Override
	public JavaVersion version() {
		return version;
	}

	@Override
	public List<Method> methods() {
		return methods;
	}

	@Override
	public List<Field> fields() {
		return fields;
	}

	@Override
	public @Nullable String signature() {
		return signature;
	}
}
