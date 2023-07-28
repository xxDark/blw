package dev.xdark.blw.asm.internal;

import dev.xdark.blw.annotation.ElementArrayBuilder;
import dev.xdark.blw.annotation.ElementEnum;
import dev.xdark.blw.type.Types;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Opcodes;

class AsmArrayVisitor extends AnnotationVisitor {
	private final ElementArrayBuilder builder;

	AsmArrayVisitor(ElementArrayBuilder builder) {
		super(Opcodes.ASM9);
		this.builder = builder;
	}

	@Override
	public void visit(String name, Object value) {
		builder.element(Util.wrapElement(value));
	}

	@Override
	public void visitEnum(String name, String descriptor, String value) {
		builder.element(new ElementEnum(Types.instanceTypeFromDescriptor(descriptor), value));
	}

	@Override
	public AnnotationVisitor visitArray(String name) {
		return new AsmArrayVisitor(builder.array());
	}

	@Override
	public AnnotationVisitor visitAnnotation(String name, String descriptor) {
		return new AsmAnnotationVisitor(builder.annotation(Types.instanceTypeFromDescriptor(descriptor)));
	}
}
