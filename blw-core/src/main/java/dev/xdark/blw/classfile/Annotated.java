package dev.xdark.blw.classfile;

import dev.xdark.blw.annotation.Annotation;

import java.util.List;

public interface Annotated {

	List<Annotation> visibleRuntimeAnnotations();

	List<Annotation> invisibleRuntimeAnnotations();
}
