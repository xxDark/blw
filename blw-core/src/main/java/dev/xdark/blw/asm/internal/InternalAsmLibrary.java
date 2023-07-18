package dev.xdark.blw.asm.internal;

import dev.xdark.blw.BytecodeLibrary;
import dev.xdark.blw.annotation.Annotation;
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
import dev.xdark.blw.annotation.ElementType;
import dev.xdark.blw.asm.ClassWriterProvider;
import dev.xdark.blw.classfile.ClassBuilder;
import dev.xdark.blw.classfile.ClassFileView;
import dev.xdark.blw.classfile.Field;
import dev.xdark.blw.classfile.Method;
import dev.xdark.blw.code.Code;
import dev.xdark.blw.code.Label;
import dev.xdark.blw.code.Local;
import dev.xdark.blw.code.TryCatchBlock;
import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.constant.OfDouble;
import dev.xdark.blw.constant.OfDynamic;
import dev.xdark.blw.constant.OfFloat;
import dev.xdark.blw.constant.OfInt;
import dev.xdark.blw.constant.OfLong;
import dev.xdark.blw.constant.OfString;
import dev.xdark.blw.constantpool.Entry;
import dev.xdark.blw.constantpool.EntryClass;
import dev.xdark.blw.constantpool.EntryDouble;
import dev.xdark.blw.constantpool.EntryDynamic;
import dev.xdark.blw.constantpool.EntryFieldRef;
import dev.xdark.blw.constantpool.EntryFloat;
import dev.xdark.blw.constantpool.EntryInteger;
import dev.xdark.blw.constantpool.EntryInterfaceMethodRef;
import dev.xdark.blw.constantpool.EntryInvokeDynamic;
import dev.xdark.blw.constantpool.EntryLong;
import dev.xdark.blw.constantpool.EntryMethodHandle;
import dev.xdark.blw.constantpool.EntryMethodRef;
import dev.xdark.blw.constantpool.EntryMethodType;
import dev.xdark.blw.constantpool.EntryModule;
import dev.xdark.blw.constantpool.EntryNameAndType;
import dev.xdark.blw.constantpool.EntryPackage;
import dev.xdark.blw.constantpool.EntryString;
import dev.xdark.blw.constantpool.EntryUtf8;
import dev.xdark.blw.constantpool.ListConstantPool;
import dev.xdark.blw.constantpool.Tag;
import dev.xdark.blw.simulation.StraightforwardSimulation;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.type.ObjectType;
import dev.xdark.blw.type.Types;
import dev.xdark.blw.type.InvokeDynamic;
import dev.xdark.blw.type.MethodHandle;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InternalAsmLibrary implements BytecodeLibrary {

	private static final VarHandle BOOTSTRAP_METHODS;
	private final ClassWriterProvider classWriterProvider;

	public InternalAsmLibrary(ClassWriterProvider classWriterProvider) {
		this.classWriterProvider = classWriterProvider;
	}

	@Override
	public void read(InputStream in, ClassBuilder classBuilder) throws IOException {
		ClassReader cr = new ClassReader(in);
		int itemCount = cr.getItemCount();
		List<Entry> entries = new ArrayList<>(itemCount);
		char[] buf = new char[cr.getMaxStringLength()];
		for (int index = 1; index < itemCount; index++) {
			int offset = cr.getItem(index);
			int tag = cr.readByte(offset - 1);
			Entry entry = switch (tag) {
				case Tag.Utf8 -> new EntryUtf8(new OfString(readUtf(cr, offset, buf)));
				case Tag.Integer -> new EntryInteger(new OfInt(cr.readInt(offset)));
				case Tag.Float -> new EntryFloat(new OfFloat(Float.intBitsToFloat(cr.readInt(offset))));
				case Tag.Long -> {
					index++;
					yield new EntryLong(new OfLong(cr.readLong(offset)));
				}
				case Tag.Double -> {
					index++;
					yield new EntryDouble(new OfDouble(Double.longBitsToDouble(cr.readLong(offset))));
				}
				case Tag.Class -> new EntryClass(cr.readUnsignedShort(offset));
				case Tag.String -> new EntryString(cr.readUnsignedShort(offset));
				case Tag.Fieldref ->
						new EntryFieldRef(cr.readUnsignedShort(offset), cr.readUnsignedShort(offset + 2));
				case Tag.Methodref ->
						new EntryMethodRef(cr.readUnsignedShort(offset), cr.readUnsignedShort(offset + 2));
				case Tag.InterfaceMethodref ->
						new EntryInterfaceMethodRef(cr.readUnsignedShort(offset), cr.readUnsignedShort(offset + 2));
				case Tag.NameAndType ->
						new EntryNameAndType(cr.readUnsignedShort(offset), cr.readUnsignedShort(offset + 2));
				case Tag.MethodHandle ->
						new EntryMethodHandle(cr.readByte(offset) & 0xff, cr.readUnsignedShort(offset + 1));
				case Tag.MethodType -> new EntryMethodType(cr.readUnsignedShort(offset));
				case Tag.Dynamic -> {
					org.objectweb.asm.ConstantDynamic cd = (org.objectweb.asm.ConstantDynamic) cr.readConst(index, buf);
					yield new EntryDynamic(new OfDynamic(Util.wrapConstantDynamic(cd)));
				}
				case Tag.InvokeDynamic -> new EntryInvokeDynamic(readInvokeDynamic(cr, offset, buf));
				case Tag.Module -> new EntryModule(cr.readUnsignedShort(offset));
				case Tag.Package -> new EntryPackage(cr.readUnsignedShort(offset));
				default -> throw new IllegalStateException("Unexpected value: " + tag);
			};
			entries.add(entry);
		}
		classBuilder.constantPool(new ListConstantPool(entries));
		cr.accept(new AsmClassFileVisitor(classBuilder), ClassReader.SKIP_FRAMES);
	}

	@Override
	public void write(ClassFileView classFileView, OutputStream os) throws IOException {
		ClassWriter writer = classWriterProvider.newClassWriterFor(classFileView);
		{
			InstanceType superClass;
			writer.visit(
					classFileView.version().pack(),
					classFileView.accessFlags(),
					classFileView.type().internalName(),
					classFileView.signature(),
					(superClass = classFileView.superClass()) == null ? null : superClass.internalName(),
					classFileView.interfaces().stream().map(ObjectType::internalName).toArray(String[]::new)
			);
		}
		StraightforwardSimulation simulation = new StraightforwardSimulation();
		LabelMappingImpl mapping = new LabelMappingImpl();
		for (Method method : classFileView.methods()) {
			MethodVisitor mv = writer.visitMethod(method.accessFlags(), method.name(), method.type().descriptor(), method.signature(), method.exceptionTypes().stream().map(ObjectType::internalName).toArray(String[]::new));
			Code code = method.code();
			if (code != null) {
				mapping.mappings.clear();
				simulation.execute(new AsmDumpEngine(mapping, mv), method);
				for (TryCatchBlock tcb : code.tryCatchBlocks()) {
					InstanceType type;
					mv.visitTryCatchBlock(
							mapping.getLabel(tcb.start()),
							mapping.getLabel(tcb.end()),
							mapping.getLabel(tcb.handler()),
							(type = tcb.type()) == null ? null : type.internalName()
					);
				}
				for (Local local : code.localVariables()) {
					mv.visitLocalVariable(
							local.name(),
							local.type().descriptor(),
							local.signature(),
							mapping.getLabel(local.start()),
							mapping.getLabel(local.end()),
							local.index()
					);
				}
				mv.visitMaxs(code.maxStack(), code.maxLocals());
			}
			AnnotationDumper dumper = mv::visitAnnotation;
			dumpAnnotationList(dumper, method.visibleRuntimeAnnotations(), true);
			dumpAnnotationList(dumper, method.invisibleRuntimeAnnotations(), false);
			mv.visitEnd();
		}
		for (Field field : classFileView.fields()) {
			Constant cst;
			FieldVisitor fv = writer.visitField(field.accessFlags(), field.name(), field.type().descriptor(), field.signature(), (cst = field.defaultValue()) == null ? null : Util.unwrapConstant(cst));
			AnnotationDumper dumper = fv::visitAnnotation;
			dumpAnnotationList(dumper, field.visibleRuntimeAnnotations(), true);
			dumpAnnotationList(dumper, field.invisibleRuntimeAnnotations(), false);
			fv.visitEnd();
		}
		{
			AnnotationDumper writerDumper = writer::visitAnnotation;
			dumpAnnotationList(writerDumper, classFileView.visibleRuntimeAnnotations(), true);
			dumpAnnotationList(writerDumper, classFileView.invisibleRuntimeAnnotations(), false);
		}
		writer.visitEnd();
		os.write(writer.toByteArray());
	}

	public void visitElement(AnnotationVisitor av, String name, Element element) {
		if (element instanceof ElementEnum en) {
			av.visitEnum(name, en.type().descriptor(), en.name());
			return;
		}
		if (element instanceof ElementArray array) {
			av = av.visitArray(name);
			for (Element e : array) {
				visitElement(av, null, e);
			}
			return;
		}
		if (element instanceof Annotation annotation) {
			av = av.visitAnnotation(name, annotation.type().descriptor());
			for (Map.Entry<String, Element> entry : annotation) {
				visitElement(av, entry.getKey(), entry.getValue());
			}
			return;
		}
		av.visit(name, switch (element) {
			case ElementString e -> e.value();
			case ElementLong e -> e.value();
			case ElementDouble e -> e.value();
			case ElementInt e -> e.value();
			case ElementFloat e -> e.value();
			case ElementChar e -> e.value();
			case ElementShort e -> e.value();
			case ElementByte e -> e.value();
			case ElementBoolean e -> e.value();
			case ElementType e -> Type.getType(e.value().descriptor());
			default -> throw new IllegalStateException("Unexpected value: " + element);
		});
	}

	// Copy from ClassReader
	private static String readUtf(ClassReader cr, final int utfOffset, final int utfLength, final char[] charBuffer) {
		int currentOffset = utfOffset;
		int endOffset = currentOffset + utfLength;
		int strLength = 0;
		byte[] classBuffer = cr.b;
		while (currentOffset < endOffset) {
			int currentByte = classBuffer[currentOffset++];
			if ((currentByte & 0x80) == 0) {
				charBuffer[strLength++] = (char) (currentByte & 0x7F);
			} else if ((currentByte & 0xE0) == 0xC0) {
				charBuffer[strLength++] =
						(char) (((currentByte & 0x1F) << 6) + (classBuffer[currentOffset++] & 0x3F));
			} else {
				charBuffer[strLength++] =
						(char)
								(((currentByte & 0xF) << 12)
										+ ((classBuffer[currentOffset++] & 0x3F) << 6)
										+ (classBuffer[currentOffset++] & 0x3F));
			}
		}
		return new String(charBuffer, 0, strLength);
	}

	private static String readUtf(ClassReader cr, int utfOffset, final char[] charBuffer) {
		int length = cr.readUnsignedShort(utfOffset);
		utfOffset += 2;
		return readUtf(cr, utfOffset, length, charBuffer);
	}

	private InvokeDynamic readInvokeDynamic(ClassReader cr, final int cpInfoOffset, final char[] charBuffer) {
		int nameAndTypeCpInfoOffset = cr.getItem(cr.readUnsignedShort(cpInfoOffset + 2));
		String name = cr.readUTF8(nameAndTypeCpInfoOffset, charBuffer);
		String descriptor = cr.readUTF8(nameAndTypeCpInfoOffset + 2, charBuffer);
		int[] bootstrapMethodOffsets = (int[]) BOOTSTRAP_METHODS.get(cr);
		int bootstrapMethodOffset = bootstrapMethodOffsets[cr.readUnsignedShort(cpInfoOffset)];
		MethodHandle methodHandle = Util.wrapMethodHandle((Handle) cr.readConst(cr.readUnsignedShort(bootstrapMethodOffset), charBuffer));
		int argCount = cr.readUnsignedShort(bootstrapMethodOffset + 2);
		List<Constant> args = new ArrayList<>(argCount);
		bootstrapMethodOffset += 4;
		for (int i = 0; i < argCount; i++) {
			args.add(Util.wrapConstant(cr.readConst(cr.readUnsignedShort(bootstrapMethodOffset), charBuffer)));
			bootstrapMethodOffset += 2;
		}
		return new InvokeDynamic(name, Types.methodType(descriptor), methodHandle, args);
	}

	private interface AnnotationDumper {

		AnnotationVisitor visitAnnotation(String descriptor, boolean visible);
	}

	private void dumpAnnotationList(AnnotationDumper dumper, List<Annotation> annotations, boolean visible) {
		for (Annotation annotation : annotations) {
			AnnotationVisitor visitor = dumper.visitAnnotation(annotation.type().descriptor(), visible);
			for (Map.Entry<String, Element> entry : annotation) {
				visitElement(visitor, entry.getKey(), entry.getValue());
			}
		}
	}

	static {
		try {
			BOOTSTRAP_METHODS = MethodHandles.privateLookupIn(ClassReader.class, MethodHandles.lookup())
					.findVarHandle(ClassReader.class, "bootstrapMethodOffsets", int[].class);
		} catch (NoSuchFieldException | IllegalAccessException ex) {
			throw new ExceptionInInitializerError(ex);
		}
	}

	private static final class LabelMappingImpl implements LabelMapping {
		private final Map<Label, org.objectweb.asm.Label> mappings = new HashMap<>();

		@Override
		public org.objectweb.asm.Label getLabel(Label label) {
			return mappings.computeIfAbsent(label, __ -> new org.objectweb.asm.Label());
		}
	}
}
