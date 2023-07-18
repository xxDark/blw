package dev.xdark.blw.asm.internal;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.ElementEnum;
import dev.xdark.blw.type.Types;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

public final class AsmAnnotationVisitor extends AnnotationVisitor {
	private final AnnotationBuilder builder;

	AsmAnnotationVisitor(AnnotationBuilder builder) {
		super(Opcodes.ASM9);
		this.builder = builder;
	}

	@Override
	public void visit(String name, Object value) {
		builder.element(name, Util.wrapElement(value));
	}

	@Override
	public void visitEnum(String name, String descriptor, String value) {
		builder.element(name, new ElementEnum(Types.instanceTypeFromDescriptor(descriptor), value));
	}

	@Override
	public AnnotationVisitor visitArray(String name) {
		return new AsmArrayVisitor(builder.array(name));
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String descriptor) {
		return new AsmAnnotationVisitor(builder.annotation(name, Types.instanceTypeFromDescriptor(descriptor)));
	}
}
