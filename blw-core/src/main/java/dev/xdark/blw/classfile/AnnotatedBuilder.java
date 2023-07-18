package dev.xdark.blw.classfile;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.type.InstanceType;
import org.jetbrains.annotations.Nullable;

public interface AnnotatedBuilder {

	@Nullable
	AnnotationBuilder.Nested<? extends AnnotatedBuilder> visibleRuntimeAnnotation(InstanceType type);

	@Nullable
	AnnotationBuilder.Nested<? extends AnnotatedBuilder> invisibleRuntimeAnnotation(InstanceType type);
}
