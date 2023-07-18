package dev.xdark.blw.util;

import java.util.NoSuchElementException;
import java.util.function.Consumer;

final class EitherLeft<L, R> implements Either<L, R> {
	private final L left;

	EitherLeft(L left) {
		this.left = left;
	}

	@Override
	public L left() {
		return left;
	}

	@Override
	public R right() {
		throw new NoSuchElementException("right");
	}

	@Override
	public boolean isLeft() {
		return true;
	}

	@Override
	public boolean isRight() {
		return false;
	}

	@Override
	public Either<L, R> ifLeft(Consumer<? super L> consumer) {
		consumer.accept(left);
		return this;
	}

	@Override
	public Either<L, R> ifRight(Consumer<? super R> consumer) {
		return this;
	}

	@Override
	public Either<L, R> accept(Consumer<? super L> left, Consumer<? super R> right) {
		left.accept(this.left);
		return this;
	}

	@Override
	public Either<R, L> swap() {
		return new EitherRight<>(left);
	}
}
