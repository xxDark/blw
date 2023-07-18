package dev.xdark.blw.version;

public sealed interface JavaVersion permits JavaVersionImpl {
	int CLASS_VERSION_OFFSET = 44;

	int majorVersion();

	int minorVersion();

	int pack();

	boolean arePreviewFeaturesEnabled();

	JavaVersion withPreviewFeatures();

	static JavaVersion of(int majorVersion, int minorVersion) {
		return new JavaVersionImpl(majorVersion, minorVersion);
	}

	static JavaVersion of(int majorVersion) {
		return of(majorVersion, 0);
	}
}
