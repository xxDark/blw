package dev.xdark.blw.annotation;

public sealed interface Element permits Annotation, ElementArray, ElementBoolean, ElementByte, ElementDouble, ElementFloat, ElementInt, ElementLong, ElementShort, ElementString, ElementType, ElementChar, ElementEnum {
}
