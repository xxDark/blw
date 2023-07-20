package dev.xdark.blw.version;

public sealed interface JavaVersion permits JavaVersionImpl {
	int CLASS_VERSION_OFFSET = 44;
	JavaVersion V1 = jdkVersion(1);
	JavaVersion V2 = jdkVersion(2);
	JavaVersion V3 = jdkVersion(3);
	JavaVersion V4 = jdkVersion(4);
	JavaVersion V5 = jdkVersion(5);
	JavaVersion V6 = jdkVersion(6);
	JavaVersion V7 = jdkVersion(7);
	JavaVersion V8 = jdkVersion(8);
	JavaVersion V9 = jdkVersion(9);
	JavaVersion V10 = jdkVersion(10);
	JavaVersion V11 = jdkVersion(11);
	JavaVersion V12 = jdkVersion(12);
	JavaVersion V14 = jdkVersion(14);
	JavaVersion V15 = jdkVersion(15);
	JavaVersion V16 = jdkVersion(16);
	JavaVersion V17 = jdkVersion(17);
	JavaVersion V18 = jdkVersion(18);
	JavaVersion V19 = jdkVersion(19);
	JavaVersion V20 = jdkVersion(20);
	JavaVersion V21 = jdkVersion(21);
	JavaVersion V22 = jdkVersion(22);

	int majorVersion();

	int minorVersion();

	int pack();

	boolean arePreviewFeaturesEnabled();

	JavaVersion withPreviewFeatures();

	JavaVersion dropPreviewFeatures();

	int jdkVersion();

	static JavaVersion classVersion(int majorVersion, int minorVersion) {
		return new JavaVersionImpl(majorVersion, minorVersion);
	}

	static JavaVersion classVersion(int majorVersion) {
		return classVersion(majorVersion, 0);
	}

	static JavaVersion jdkVersion(int jdkVersion) {
		return classVersion(jdkVersion + CLASS_VERSION_OFFSET);
	}
}
