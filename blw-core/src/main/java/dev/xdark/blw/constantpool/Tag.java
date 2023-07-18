package dev.xdark.blw.constantpool;

public interface Tag {
	int Utf8 = 1;
	int Integer = 3;
	int Float = 4;
	int Long = 5;
	int Double = 6;
	int Class = 7;
	int String = 8;
	int Fieldref = 9;
	int Methodref = 10;
	int InterfaceMethodref = 11;
	int NameAndType = 12;
	int MethodHandle = 15;
	int MethodType = 16;
	int Dynamic = 17;
	int InvokeDynamic = 18;
	int Module = 19;
	int Package = 20;
}
