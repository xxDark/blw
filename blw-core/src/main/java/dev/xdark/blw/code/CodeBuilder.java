package dev.xdark.blw.code;

import dev.xdark.blw.code.attribute.Local;
import dev.xdark.blw.util.Builder;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public sealed interface CodeBuilder permits CodeBuilder.Root, CodeBuilder.Nested {

	CodeBuilder maxStack(int maxStack);

	CodeBuilder maxLocals(int maxLocals);

	CodeBuilder tryCatchBlocks(List<TryCatchBlock> tryCatchBlocks);

	CodeBuilder tryCatchBlock(TryCatchBlock tryCatchBlock);

	@Nullable
	CodeListBuilder.Nested<? extends CodeBuilder> codeList();

	CodeBuilder localVariables(List<Local> locals);

	CodeBuilder localVariable(Local local);

	non-sealed interface Root extends CodeBuilder, Builder.Root<Code> {

		@Override
		CodeBuilder.Root maxStack(int maxStack);

		@Override
		CodeBuilder.Root maxLocals(int maxLocals);

		@Override
		CodeBuilder.Root tryCatchBlocks(List<TryCatchBlock> tryCatchBlocks);

		@Override
		CodeBuilder.Root tryCatchBlock(TryCatchBlock tryCatchBlock);

		@Override
		CodeListBuilder.@Nullable Nested<CodeBuilder.Root> codeList();

		@Override
		CodeBuilder.Root localVariables(List<Local> locals);

		@Override
		CodeBuilder.Root localVariable(Local local);
	}

	non-sealed interface Nested<U extends Builder> extends CodeBuilder, Builder.Nested<U> {

		@Override
		CodeBuilder.Nested<U> maxStack(int maxStack);

		@Override
		CodeBuilder.Nested<U> maxLocals(int maxLocals);

		@Override
		CodeBuilder.Nested<U> tryCatchBlocks(List<TryCatchBlock> tryCatchBlocks);

		@Override
		CodeBuilder.Nested<U> tryCatchBlock(TryCatchBlock tryCatchBlock);

		@Override
		CodeListBuilder.@Nullable Nested<CodeBuilder.Nested<U>> codeList();

		@Override
		CodeBuilder.Nested<U> localVariables(List<Local> locals);

		@Override
		CodeBuilder.Nested<U> localVariable(Local local);
	}
}
