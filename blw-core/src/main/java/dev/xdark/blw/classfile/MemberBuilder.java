package dev.xdark.blw.classfile;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.type.InstanceType;
import dev.xdark.blw.util.Builder;
import org.jetbrains.annotations.Nullable;

public interface MemberBuilder extends AnnotatedBuilder, SignedBuilder {

	@Override
	MemberBuilder signature(@Nullable String signature);

	@Override
	@Nullable AnnotationBuilder.Nested<? extends MemberBuilder> visibleRuntimeAnnotation(InstanceType type);

	@Override
	@Nullable AnnotationBuilder.Nested<? extends MemberBuilder> invisibleRuntimeAnnotation(InstanceType type);

	interface Root<M extends Member> extends MemberBuilder, AccessibleBuilder, Builder.Root<M> {

		@Override
		MemberBuilder.Root<M> accessFlags(int accessFlags);

		MemberBuilder.Root<M> name(String name);
	}

	interface Nested<U extends Builder> extends MemberBuilder, Builder.Nested<U> {
	}
}
