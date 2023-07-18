package dev.xdark.blw.code;

public interface MutableCodeList extends CodeList {

	void add(CodeElement element);

	void addAll(CodeList list);

	void add(int index, CodeElement element);

	void addAll(int index, CodeList list);
}
