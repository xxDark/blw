package dev.xdark.blw.code.generic;

import dev.xdark.blw.code.Code;
import dev.xdark.blw.code.CodeElement;
import dev.xdark.blw.code.CodeList;
import dev.xdark.blw.code.CodeWalker;
import dev.xdark.blw.code.Label;
import dev.xdark.blw.code.TryCatchBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GenericCode implements Code {
	private final int maxStack;
	private final int maxLocals;
	private final CodeList stream;
	private final List<TryCatchBlock> tryCatchBlocks;

	public GenericCode(int maxStack, int maxLocals, CodeList stream, List<TryCatchBlock> tryCatchBlocks) {
		this.maxStack = maxStack;
		this.maxLocals = maxLocals;
		this.stream = stream;
		this.tryCatchBlocks = tryCatchBlocks;
	}

	@Override
	public int maxStack() {
		return maxStack;
	}

	@Override
	public int maxLocals() {
		return maxLocals;
	}

	@Override
	public CodeWalker walker() {
		CodeList stream = this.stream;
		return new CodeWalker() {
			CodeElement element;
			int index = -1;

			@Override
			public int index() {
				return index;
			}

			@Override
			public @Nullable CodeElement element() {
				return element;
			}

			@Override
			public void advance() {
				int index = this.index + 1;
				CodeList s;
				if (index < 0 || index >= (s = stream).size()) {
					element = null;
					return;
				}
				element = s.at(index);
				this.index = index;
			}

			@Override
			public void set(Label label) {
				int index = label.index();
				if (index == Label.UNSET) {
					throw new IllegalStateException("Label index is not set");
				}
				this.index = index - 1;
				element = null;
			}
		};
	}

	@Override
	public CodeList codeStream() {
		return stream;
	}

	@Override
	public List<TryCatchBlock> tryCatchBlocks() {
		return tryCatchBlocks;
	}
}
