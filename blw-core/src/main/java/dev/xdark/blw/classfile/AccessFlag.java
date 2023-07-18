package dev.xdark.blw.classfile;

public final class AccessFlag {

	public static final int ACC_PUBLIC = 0x0001;
	public static final int ACC_PRIVATE = 0x0002;
	public static final int ACC_PROTECTED = 0x0004;
	public static final int ACC_STATIC = 0x0008;
	public static final int ACC_FINAL = 0x0010;
	public static final int ACC_SUPER = 0x0020;
	public static final int ACC_SYNCHRONIZED = 0x0020;
	public static final int ACC_OPEN = 0x0020;
	public static final int ACC_TRANSITIVE = 0x0020;
	public static final int ACC_VOLATILE = 0x0040;
	public static final int ACC_BRIDGE = 0x0040;
	public static final int ACC_STATIC_PHASE = 0x0040;
	public static final int ACC_VARARGS = 0x0080;
	public static final int ACC_TRANSIENT = 0x0080;
	public static final int ACC_NATIVE = 0x0100;
	public static final int ACC_INTERFACE = 0x0200;
	public static final int ACC_ABSTRACT = 0x0400;
	public static final int ACC_STRICT = 0x0800;
	public static final int ACC_SYNTHETIC = 0x1000;
	public static final int ACC_ANNOTATION = 0x2000;
	public static final int ACC_ENUM = 0x4000;
	public static final int ACC_MANDATED = 0x8000;
	public static final int ACC_MODULE = 0x8000;

	public static final int RECOGNIZED_CLASS_MODIFIERS = (ACC_PUBLIC |
			ACC_FINAL |
			ACC_SUPER |
			ACC_INTERFACE |
			ACC_ABSTRACT |
			ACC_ANNOTATION |
			ACC_ENUM |
			ACC_SYNTHETIC);
	public static final int RECOGNIZED_FIELD_MODIFIERS = (ACC_PUBLIC |
			ACC_PRIVATE |
			ACC_PROTECTED |
			ACC_STATIC |
			ACC_FINAL |
			ACC_VOLATILE |
			ACC_TRANSIENT |
			ACC_ENUM |
			ACC_SYNTHETIC);
	public static final int RECOGNIZED_METHOD_MODIFIERS = ACC_PUBLIC |
			ACC_PRIVATE |
			ACC_PROTECTED |
			ACC_STATIC |
			ACC_FINAL |
			ACC_SYNCHRONIZED |
			ACC_BRIDGE |
			ACC_VARARGS |
			ACC_NATIVE |
			ACC_ABSTRACT |
			ACC_STRICT |
			ACC_SYNTHETIC;

	private AccessFlag() {
	}
}
