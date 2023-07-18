package dev.xdark.blw.asm;

import dev.xdark.blw.classfile.ClassFileView;
import dev.xdark.blw.version.JavaVersion;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

public interface ClassWriterProvider {

	ClassWriter newClassWriterFor(ClassReader classReader, ClassFileView classFileView);

	ClassWriter newClassWriterFor(ClassFileView classFileView);

	static ClassWriterProvider flags(int writeFlags) {
		return new ClassWriterProvider() {
			@Override
			public ClassWriter newClassWriterFor(ClassReader classReader, ClassFileView classFileView) {
				return new ClassWriter(classReader, correctFlags(classFileView));
			}

			@Override
			public ClassWriter newClassWriterFor(ClassFileView classFileView) {
				return new ClassWriter(correctFlags(classFileView));
			}

			int correctFlags(ClassFileView classFileView) {
				JavaVersion version = classFileView.version();
				int flags = writeFlags;
				if (version.majorVersion() <= Opcodes.V1_5) {
					flags &= ~ClassWriter.COMPUTE_FRAMES;
				}
				return flags;
			}
		};
	}
}
