package dev.xdark.blw.util;

import java.util.Objects;
import java.util.function.Consumer;

public interface Either<L, R> {

	L left();

	R right();

	boolean isLeft();

	boolean isRight();

	Either<L, R> ifLeft(Consumer<? super L> consumer);

	Either<L, R> ifRight(Consumer<? super R> consumer);

	Either<L, R> accept(Consumer<? super L> left, Consumer<? super R> right);

	Either<R, L> swap();

	static <L, R> Either<L, R> left(L left) {
		return new EitherLeft<>(left);
	}

	static <L, R> Either<L, R> right(R right) {
		return new EitherRight<>(right);
	}

	static <L, R> Either<L, R> nonNull(L left, R right) {
		return left == null ? new EitherRight<>(Objects.requireNonNull(right, "right")) : new EitherLeft<>(left);
	}
}
