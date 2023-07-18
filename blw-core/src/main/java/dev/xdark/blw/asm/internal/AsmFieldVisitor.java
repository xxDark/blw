package dev.xdark.blw.asm.internal;

import dev.xdark.blw.classfile.FieldBuilder;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

final class AsmFieldVisitor extends FieldVisitor {
	private final FieldBuilder field;

	AsmFieldVisitor(FieldBuilder field) {
		super(Opcodes.ASM9);
		this.field = field;
	}

	@Override
	public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
		return Util.visitAnnotation(field, descriptor, visible);
	}
}
