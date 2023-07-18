package dev.xdark.blw.code.generic;

import dev.xdark.blw.code.CodeElement;
import dev.xdark.blw.code.CodeListBuilder;
import dev.xdark.blw.code.Instruction;
import dev.xdark.blw.code.Label;
import dev.xdark.blw.internal.BuilderShadow;
import dev.xdark.blw.util.Builder;

import java.util.ArrayList;
import java.util.List;

public abstract sealed class GenericCodeListBuilder implements BuilderShadow<List<CodeElement>> permits GenericCodeListBuilder.Root, GenericCodeListBuilder.Nested {
	protected final List<CodeElement> elements = new ArrayList<>();

	@Override
	public List<CodeElement> build() {
		return elements;
	}

	public static final class Root extends GenericCodeListBuilder implements CodeListBuilder.Root {

		@Override
		public CodeListBuilder.Root element(Instruction instruction) {
			elements.add(instruction);
			return this;
		}

		@Override
		public CodeListBuilder.Root element(Label label) {
			List<CodeElement> elements = this.elements;
			label.index(elements.size());
			elements.add(label);
			return this;
		}

		@Override
		public List<CodeElement> reflectAs() {
			return super.reflectAs();
		}
	}

	public static final class Nested<U extends Builder> extends GenericCodeListBuilder implements CodeListBuilder.Nested<U> {
		private final U upstream;

		public Nested(U upstream) {
			this.upstream = upstream;
		}

		@Override
		public CodeListBuilder.Nested<U> element(Instruction instruction) {
			elements.add(instruction);
			return this;
		}

		@Override
		public CodeListBuilder.Nested<U> element(Label label) {
			List<CodeElement> elements = this.elements;
			label.index(elements.size());
			elements.add(label);
			return this;
		}

		@Override
		public U __() {
			return upstream;
		}
	}
}
