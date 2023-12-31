package dev.xdark.blw.code.generic;

import dev.xdark.blw.code.Code;
import dev.xdark.blw.code.CodeBuilder;
import dev.xdark.blw.code.CodeElement;
import dev.xdark.blw.code.CodeListBuilder;
import dev.xdark.blw.code.attribute.Local;
import dev.xdark.blw.code.TryCatchBlock;
import dev.xdark.blw.internal.BuilderShadow;
import dev.xdark.blw.util.Builder;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class GenericCodeBuilder implements BuilderShadow<Code> permits GenericCodeBuilder.Root, GenericCodeBuilder.Nested {
	int maxStack, maxLocals;
	List<TryCatchBlock> tryCatchBlocks = List.of();
	CodeListBuilder.Nested<? extends CodeBuilder> content;
	List<Local> localVariables = List.of();

	public CodeBuilder maxStack(int maxStack) {
		this.maxStack = maxStack;
		return (CodeBuilder) this;
	}

	public CodeBuilder maxLocals(int maxLocals) {
		this.maxLocals = maxLocals;
		return (CodeBuilder) this;
	}

	public CodeBuilder tryCatchBlocks(List<TryCatchBlock> tryCatchBlocks) {
		this.tryCatchBlocks = tryCatchBlocks;
		return (CodeBuilder) this;
	}

	public CodeBuilder tryCatchBlock(TryCatchBlock tryCatchBlock) {
		List<TryCatchBlock> tcbs = tryCatchBlocks;
		if (tcbs.isEmpty()) {
			tcbs = new ArrayList<>();
			tryCatchBlocks = tcbs;
		}
		tcbs.add(tryCatchBlock);
		return (CodeBuilder) this;
	}

	public CodeBuilder localVariables(List<Local> localVariables) {
		this.localVariables = localVariables;
		return (CodeBuilder) this;
	}

	public CodeBuilder localVariable(Local local) {
		List<Local> locals = localVariables;
		if (locals.isEmpty()) {
			locals = new ArrayList<>();
			localVariables = locals;
		}
		locals.add(local);
		return (CodeBuilder) this;
	}

	public CodeListBuilder.Nested<? extends CodeBuilder> codeList() {
		CodeListBuilder.Nested<? extends CodeBuilder> content = this.content;
		if (content == null) {
			content = new GenericCodeListBuilder.Nested<>((CodeBuilder & Builder) this);
			this.content = content;
		}
		return content;
	}

	@Override
	public Code build() {
		CodeListBuilder.Nested<? extends CodeBuilder> content;
		return new GenericCode(
				maxStack,
				maxLocals,
				(content = this.content) == null ? new ArrayList<>() : ((BuilderShadow<List<CodeElement>>) content).build(),
				tryCatchBlocks,
				localVariables);
	}

	public static final class Root extends GenericCodeBuilder implements CodeBuilder.Root {

		@Override
		public CodeBuilder.Root maxStack(int maxStack) {
			return (CodeBuilder.Root) super.maxStack(maxStack);
		}

		@Override
		public CodeBuilder.Root maxLocals(int maxLocals) {
			return (CodeBuilder.Root) super.maxLocals(maxLocals);
		}

		@Override
		public CodeBuilder.Root tryCatchBlocks(List<TryCatchBlock> tryCatchBlocks) {
			return (CodeBuilder.Root) super.tryCatchBlocks(tryCatchBlocks);
		}

		@Override
		public CodeBuilder.Root tryCatchBlock(TryCatchBlock tryCatchBlock) {
			return (CodeBuilder.Root) super.tryCatchBlock(tryCatchBlock);
		}

		@Override
		public CodeListBuilder.Nested<CodeBuilder.Root> codeList() {
			//noinspection unchecked
			return (CodeListBuilder.Nested<CodeBuilder.Root>) super.codeList();
		}

		@Override
		public CodeBuilder.Root localVariables(List<Local> localVariables) {
			return (CodeBuilder.Root) super.localVariables(localVariables);
		}

		@Override
		public CodeBuilder.Root localVariable(Local local) {
			return (CodeBuilder.Root) super.localVariable(local);
		}

		@Override
		public Code reflectAs() {
			return super.reflectAs();
		}
	}

	public static final class Nested<U extends Builder> extends GenericCodeBuilder implements CodeBuilder.Nested<U> {
		private final U upstream;

		public Nested(U upstream) {
			this.upstream = upstream;
		}

		@Override
		public CodeBuilder.Nested<U> maxStack(int maxStack) {
			//noinspection unchecked
			return (CodeBuilder.Nested<U>) super.maxStack(maxStack);
		}

		@Override
		public CodeBuilder.Nested<U> maxLocals(int maxLocals) {
			//noinspection unchecked
			return (CodeBuilder.Nested<U>) super.maxLocals(maxLocals);
		}

		@Override
		public CodeBuilder.Nested<U> tryCatchBlocks(List<TryCatchBlock> tryCatchBlocks) {
			//noinspection unchecked
			return (CodeBuilder.Nested<U>) super.tryCatchBlocks(tryCatchBlocks);
		}

		@Override
		public CodeBuilder.Nested<U> tryCatchBlock(TryCatchBlock tryCatchBlock) {
			//noinspection unchecked
			return (CodeBuilder.Nested<U>) super.tryCatchBlock(tryCatchBlock);
		}

		@Override
		public CodeListBuilder.Nested<CodeBuilder.Nested<U>> codeList() {
			//noinspection unchecked
			return (CodeListBuilder.Nested<CodeBuilder.Nested<U>>) super.codeList();
		}

		@Override
		public CodeBuilder.Nested<U> localVariables(List<Local> localVariables) {
			//noinspection unchecked
			return (CodeBuilder.Nested<U>) super.localVariables(localVariables);
		}

		@Override
		public CodeBuilder.Nested<U> localVariable(Local local) {
			//noinspection unchecked
			return (CodeBuilder.Nested<U>) super.localVariable(local);
		}

		@Override
		public U __() {
			return upstream;
		}
	}
}
