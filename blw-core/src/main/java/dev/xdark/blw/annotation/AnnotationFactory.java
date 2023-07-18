package dev.xdark.blw.annotation;

import dev.xdark.blw.type.InstanceType;

import java.util.List;
import java.util.stream.Stream;

public interface AnnotationFactory {

	ElementString stringElement(String value);

	ElementDouble doubleElement(double value);

	ElementLong longElement(long value);

	ElementInt intElement(int value);

	ElementFloat floatElement(float value);

	ElementChar charElement(char value);

	ElementShort shortElement(short value);

	ElementByte byteElement(byte value);

	ElementBoolean booleanElement(boolean value);

	ElementEnum enumElement(InstanceType type, String name);

	ElementArray arrayElement(List<Element> elements);

	ElementArrayBuilder.Root array();

	AnnotationBuilder.Root annotation(InstanceType type);
}
