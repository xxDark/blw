package dev.xdark.blw.asm.internal;


import dev.xdark.blw.code.Label;

public interface LabelMapping {

	org.objectweb.asm.Label getLabel(Label label);
}
