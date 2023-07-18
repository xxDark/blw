package dev.xdark.blw.code;

import dev.xdark.blw.util.Builder;

public sealed interface CodeListBuilder permits CodeListBuilder.Root, CodeListBuilder.Nested {

	CodeListBuilder element(Instruction instruction);

	CodeListBuilder element(Label label);

	non-sealed interface Root extends CodeListBuilder, Builder.Root<CodeList> {

		@Override
		CodeListBuilder.Root element(Instruction instruction);

		@Override
		CodeListBuilder.Root element(Label label);
	}

	non-sealed interface Nested<U extends Builder> extends CodeListBuilder, Builder.Nested<U> {

		@Override
		CodeListBuilder.Nested<U> element(Instruction instruction);

		@Override
		CodeListBuilder.Nested<U> element(Label label);
	}
}
