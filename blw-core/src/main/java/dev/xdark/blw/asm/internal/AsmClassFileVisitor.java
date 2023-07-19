package dev.xdark.blw.asm.internal;

import dev.xdark.blw.classfile.ClassBuilder;
import dev.xdark.blw.classfile.attribute.generic.GenericInnerClass;
import dev.xdark.blw.type.TypeReader;
import dev.xdark.blw.type.Types;
import dev.xdark.blw.version.JavaVersion;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

final class AsmClassFileVisitor extends ClassVisitor {
	private final ClassBuilder classBuilder;

	AsmClassFileVisitor(ClassBuilder classBuilder) {
		super(Opcodes.ASM9);
		this.classBuilder = classBuilder;
	}

	@Override
	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
		classBuilder
				.version(JavaVersion.of(version & 0xFFFF, (version >>> 16) & 0xFFFF))
				.accessFlags(access)
				.type(Types.instanceTypeFromInternalName(name))
				.signature(signature)
				.superClass(superName == null ? null : Types.instanceTypeFromInternalName(superName))
				.interfaces(Arrays.stream(interfaces).map(Types::instanceTypeFromInternalName).toList());
	}

	@Override
	public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
		var method = classBuilder.method(access, name, Types.methodType(descriptor));
		if (method == null) {
			return null;
		}
		method.exceptionTypes(exceptions == null ? List.of() : Arrays.stream(exceptions).map(Types::instanceTypeFromInternalName).toList());
		return new AsmMethodVisitor(method, !Modifier.isAbstract(access) && !Modifier.isNative(access));
	}

	@Override
	public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
		var field = classBuilder.field(access, name, new TypeReader(descriptor).requireClassType());
		if (field == null) {
			return null;
		}
		field.defaultValue(value == null ? null : Util.wrapConstant(value));
		return new AsmFieldVisitor(field);
	}

	@Override
	public void visitInnerClass(String name, String outerName, String innerName, int access) {
		classBuilder.innerClass(new GenericInnerClass(
				access,
				Types.instanceTypeFromInternalName(name),
				outerName == null ? null : Types.instanceTypeFromInternalName(outerName),
				innerName
		));
	}

	@Override
	public void visitNestHost(String nestHost) {
		classBuilder.nestHost(Types.instanceTypeFromInternalName(nestHost));
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		return Util.visitAnnotation(classBuilder, descriptor, visible);
	}
}
