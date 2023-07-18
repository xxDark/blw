package dev.xdark.blw.classfile.generic;

import dev.xdark.blw.annotation.Annotation;
import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.generic.GenericAnnotationBuilder;
import dev.xdark.blw.classfile.MemberBuilder;
import dev.xdark.blw.internal.BuilderShadow;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.util.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericMemberBuilder implements MemberBuilder {
	protected final List<AnnotationBuilder> visibleRuntimeAnnotations = new ArrayList<>();
	protected final List<AnnotationBuilder> invisibleRuntimeAnnotation = new ArrayList<>();
	protected String signature;

	@Override
	public MemberBuilder signature(@Nullable String signature) {
		this.signature = signature;
		return this;
	}

	@Override
	public @Nullable AnnotationBuilder.Nested<? extends MemberBuilder> visibleRuntimeAnnotation(InstanceType type) {
		var builder = new GenericAnnotationBuilder.Nested<>(type, (Builder) this);
		visibleRuntimeAnnotations.add(builder);
		//noinspection unchecked
		return (AnnotationBuilder.Nested) builder;
	}

	@Override
	public @Nullable AnnotationBuilder.Nested<? extends MemberBuilder> invisibleRuntimeAnnotation(InstanceType type) {
		var builder = new GenericAnnotationBuilder.Nested<>(type, (Builder) this);
		invisibleRuntimeAnnotation.add(builder);
		//noinspection unchecked
		return (AnnotationBuilder.Nested) builder;
	}

	protected final List<Annotation> visibleRuntimeAnnotations() {
		//noinspection unchecked
		return visibleRuntimeAnnotations.stream().map(builder -> ((BuilderShadow<Annotation>) builder).build()).toList();
	}

	protected final List<Annotation> invisibleRuntimeAnnotation() {
		//noinspection unchecked
		return invisibleRuntimeAnnotation.stream().map(builder -> ((BuilderShadow<Annotation>) builder).build()).toList();
	}
}
