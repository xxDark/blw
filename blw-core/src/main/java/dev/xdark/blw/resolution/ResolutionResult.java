package dev.xdark.blw.resolution;

import dev.xdark.blw.classfile.Accessible;

public sealed interface ResolutionResult<M extends Accessible> permits ResolutionError, ResolutionSuccess {
}
