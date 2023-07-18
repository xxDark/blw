package dev.xdark.blw.type;

import dev.xdark.blw.constant.Constant;
import dev.xdark.blw.type.generic.MethodHandle;

import java.util.List;

public interface DynamicAlike {

	String name();

	Type type();

	MethodHandle methodHandle();

	List<Constant> args();
}
