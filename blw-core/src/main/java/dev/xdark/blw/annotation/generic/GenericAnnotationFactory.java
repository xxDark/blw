package dev.xdark.blw.annotation.generic;

import dev.xdark.blw.annotation.AnnotationBuilder;
import dev.xdark.blw.annotation.AnnotationFactory;
import dev.xdark.blw.annotation.Element;
import dev.xdark.blw.annotation.ElementArray;
import dev.xdark.blw.annotation.ElementArrayBuilder;
import dev.xdark.blw.annotation.ElementBoolean;
import dev.xdark.blw.annotation.ElementByte;
import dev.xdark.blw.annotation.ElementChar;
import dev.xdark.blw.annotation.ElementDouble;
import dev.xdark.blw.annotation.ElementEnum;
import dev.xdark.blw.annotation.ElementFloat;
import dev.xdark.blw.annotation.ElementInt;
import dev.xdark.blw.annotation.ElementLong;
import dev.xdark.blw.annotation.ElementShort;
import dev.xdark.blw.annotation.ElementString;
import dev.xdark.blw.type.InstanceType;

import java.util.List;

public final class GenericAnnotationFactory implements AnnotationFactory {

	@Override
	public ElementString stringElement(String value) {
		return new ElementString(value);
	}

	@Override
	public ElementDouble doubleElement(double value) {
		return new ElementDouble(value);
	}

	@Override
	public ElementLong longElement(long value) {
		return new ElementLong(value);
	}

	@Override
	public ElementInt intElement(int value) {
		return new ElementInt(value);
	}

	@Override
	public ElementFloat floatElement(float value) {
		return new ElementFloat(value);
	}

	@Override
	public ElementChar charElement(char value) {
		return new ElementChar(value);
	}

	@Override
	public ElementShort shortElement(short value) {
		return new ElementShort(value);
	}

	@Override
	public ElementByte byteElement(byte value) {
		return new ElementByte(value);
	}

	@Override
	public ElementBoolean booleanElement(boolean value) {
		return new ElementBoolean(value);
	}

	@Override
	public ElementEnum enumElement(InstanceType type, String name) {
		return new ElementEnum(type, name);
	}

	@Override
	public ElementArray arrayElement(List<Element> elements) {
		return new ElementArray(elements);
	}

	@Override
	public ElementArrayBuilder.Root array() {
		return new GenericArrayBuilder.Root();
	}

	@Override
	public AnnotationBuilder.Root annotation(InstanceType type) {
		return new GenericAnnotationBuilder.Root(type);
	}
}
