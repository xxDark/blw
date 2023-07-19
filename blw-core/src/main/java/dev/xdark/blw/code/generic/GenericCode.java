package dev.xdark.blw.code.generic;

import dev.xdark.blw.code.Code;
import dev.xdark.blw.code.CodeElement;
import dev.xdark.blw.code.CodeWalker;
import dev.xdark.blw.code.Label;
import dev.xdark.blw.code.attribute.Local;
import dev.xdark.blw.code.TryCatchBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class GenericCode implements Code {
	private final int maxStack;
	private final int maxLocals;
	private final List<CodeElement> elements;
	private final List<TryCatchBlock> tryCatchBlocks;
	private final List<Local> localVariables;

	public GenericCode(int maxStack, int maxLocals, List<CodeElement> elements, List<TryCatchBlock> tryCatchBlocks, List<Local> localVariables) {
		this.maxStack = maxStack;
		this.maxLocals = maxLocals;
		this.elements = elements;
		this.tryCatchBlocks = tryCatchBlocks;
		this.localVariables = localVariables;
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
		List<CodeElement> stream = this.elements;
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
				List<CodeElement> s;
				if (index < 0 || index >= (s = stream).size()) {
					element = null;
					return;
				}
				element = s.get(index);
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
	public List<CodeElement> elements() {
		return elements;
	}

	@Override
	public List<Local> localVariables() {
		return localVariables;
	}

	@Override
	public List<TryCatchBlock> tryCatchBlocks() {
		return tryCatchBlocks;
	}
}
