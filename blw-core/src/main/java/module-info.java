module dev.xdark.blw {
	requires static org.jetbrains.annotations;
	exports dev.xdark.blw;
	exports dev.xdark.blw.constant;
	exports dev.xdark.blw.code;
	exports dev.xdark.blw.code.generic;
	exports dev.xdark.blw.code.attribute;
	exports dev.xdark.blw.code.attribute.generic;
	exports dev.xdark.blw.code.instruction;
	exports dev.xdark.blw.classfile;
	exports dev.xdark.blw.classfile.generic;
	exports dev.xdark.blw.classfile.adapter;
	exports dev.xdark.blw.classfile.attribute;
	exports dev.xdark.blw.classfile.attribute.generic;
	exports dev.xdark.blw.annotation;
	exports dev.xdark.blw.annotation.generic;
	exports dev.xdark.blw.constantpool;
	exports dev.xdark.blw.resolution;
	exports dev.xdark.blw.resolution.jvm;
	exports dev.xdark.blw.simulation;
	exports dev.xdark.blw.type;
	exports dev.xdark.blw.util;
	exports dev.xdark.blw.version;

	requires static org.objectweb.asm;
	opens dev.xdark.blw.asm;
}
