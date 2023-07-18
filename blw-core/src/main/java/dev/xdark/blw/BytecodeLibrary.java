package dev.xdark.blw;

import dev.xdark.blw.classfile.ClassBuilder;
import dev.xdark.blw.classfile.ClassFileView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface BytecodeLibrary {

	void read(InputStream in, ClassBuilder classBuilder) throws IOException;

	void write(ClassFileView classFileView, OutputStream os) throws IOException;
}
