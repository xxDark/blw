package dev.xdark.blw.resolution;

@SuppressWarnings("rawtypes")
public enum ResolutionError implements ResolutionResult {
    ACC_INTERFACE_SET,
    ACC_ABSTRACT_SET,
    ACC_STATIC_SET,
    ACC_INTERFACE_UNSET,
    ACC_ABSTRACT_UNSET,
    ACC_STATIC_UNSET,
    NO_SUCH_METHOD,
    NO_SUCH_FIELD
}
