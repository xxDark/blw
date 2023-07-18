package dev.xdark.blw.asm.internal;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.annotation.ElementArray;
import dev.xdark.blw.annotation.ElementBoolean;
import dev.xdark.blw.annotation.ElementByte;
import dev.xdark.blw.annotation.ElementChar;
import dev.xdark.blw.annotation.ElementDouble;
import dev.xdark.blw.annotation.ElementEnum;
import dev.xdark.blw.annotation.ElementFloat;
import dev.xdark.blw.annotation.ElementInt;
import dev.xdark.blw.annotation.ElementLong;
import dev.xdark.blw.annotation.ElementShort;
import dev.xdark.blw.annotation.ElementString;
import dev.xdark.blw.classfile.AnnotatedBuilder;
import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.constant.OfDouble;
import dev.xdark.blw.constant.OfDynamic;
import dev.xdark.blw.constant.OfFloat;
import dev.xdark.blw.constant.OfInt;
import dev.xdark.blw.constant.OfLong;
import dev.xdark.blw.constant.OfMethodHandle;
import dev.xdark.blw.constant.OfString;
import dev.xdark.blw.constant.OfType;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.ObjectType;
import dev.xdark.blw.type.Type;
import dev.xdark.blw.type.TypeReader;
import dev.xdark.blw.type.Types;
import dev.xdark.blw.type.ConstantDynamic;
import dev.xdark.blw.type.MethodHandle;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Handle;

import java.util.List;
import java.util.stream.IntStream;

public final class Util {

	private Util() {
	}

	public static MethodHandle wrapMethodHandle(Handle handle) {
		ObjectType owner = Types.objectTypeFromInternalName(handle.getOwner());
		return new MethodHandle(
				handle.getTag(),
				owner,
				handle.getName(),
				new TypeReader(handle.getDesc()).required(),
				handle.isInterface()
		);
	}

	public static Handle unwrapMethodHandle(MethodHandle mh) {
		return new Handle(
				mh.kind(),
				mh.owner().internalName(),
				mh.name(),
				mh.type().descriptor(),
				mh.isInterface()
		);
	}

	public static ConstantDynamic wrapConstantDynamic(org.objectweb.asm.ConstantDynamic cd) {
		return new ConstantDynamic(
				cd.getName(),
				new TypeReader(cd.getDescriptor()).requireClassType(),
				wrapMethodHandle(cd.getBootstrapMethod()),
				IntStream.range(0, cd.getBootstrapMethodArgumentCount()).mapToObj(Util::wrapConstant).toList()
		);
	}

	public static org.objectweb.asm.ConstantDynamic unwrapConstantDynamic(ConstantDynamic cd) {
		return new org.objectweb.asm.ConstantDynamic(
				cd.name(),
				cd.type().descriptor(),
				unwrapMethodHandle(cd.methodHandle()),
				cd.args().stream().map(Util::unwrapConstant).toArray()
		);
	}

	public static Object unwrapType(Type type) {
		return org.objectweb.asm.Type.getType(type.descriptor());
	}

	public static Constant wrapConstant(Object value) {
		if (value instanceof String s) return new OfString(s);
		if (value instanceof Long l) return new OfLong(l);
		if (value instanceof Double d) return new OfDouble(d);
		if (value instanceof Integer i) return new OfInt(i);
		if (value instanceof Float f) return new OfFloat(f);
		if (value instanceof Handle h) return new OfMethodHandle(wrapMethodHandle(h));
		if (value instanceof org.objectweb.asm.Type t)
			return new OfType(new TypeReader(t.getDescriptor()).required());
		throw new IllegalArgumentException("Cannot convert " + value + " into library constant");
	}

	public static Object unwrapConstant(Constant constant) {
		return switch (constant) {
			case OfString c -> c.value();
			case OfMethodHandle c -> unwrapMethodHandle(c.value());
			case OfType c -> unwrapType(c.value());
			case OfDynamic c -> unwrapConstantDynamic(c.value());
			case OfLong c -> c.value();
			case OfDouble c -> c.value();
			case OfInt c -> c.value();
			case OfFloat c -> c.value();
		};
	}

	public static Element wrapElement(Object value) {
		if (value instanceof String s) return new ElementString(s);
		if (value instanceof Long l) return new ElementLong(l);
		if (value instanceof Double d) return new ElementDouble(d);
		if (value instanceof Integer i) return new ElementInt(i);
		if (value instanceof Float f) return new ElementFloat(f);
		if (value instanceof Character c) return new ElementChar(c);
		if (value instanceof Short s) return new ElementShort(s);
		if (value instanceof Byte b) return new ElementByte(b);
		if (value instanceof Boolean b) return new ElementBoolean(b);
		if (value instanceof String[] a) return new ElementEnum(
				Types.instanceTypeFromInternalName(a[0]), a[1]
		);
		if (value instanceof List<?> list) return new ElementArray(list.stream().map(Util::wrapElement));
		throw new IllegalArgumentException("Cannot convert " + value + " into annotation element");
	}

	static AnnotationVisitor visitAnnotation(AnnotatedBuilder annotations, String descriptor, boolean visible) {
		InstanceType type = Types.instanceTypeFromDescriptor(descriptor);
		AnnotationBuilder builder;
		if (visible) {
			builder = annotations.visibleRuntimeAnnotation(type);
		} else {
			builder = annotations.invisibleRuntimeAnnotation(type);
		}
		return builder == null ? null : new AsmAnnotationVisitor(builder);
	}
}
