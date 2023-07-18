package dev.xdark.blw.version;

final class JavaVersionImpl implements JavaVersion {
	private static final int PREVIEW_FEATURES = 0xFFFF;
	private final int majorVersion, minorVersion;

	JavaVersionImpl(int majorVersion, int minorVersion) {
		this.majorVersion = majorVersion;
		this.minorVersion = minorVersion;
	}

	@Override
	public int majorVersion() {
		return majorVersion;
	}

	@Override
	public int minorVersion() {
		return minorVersion;
	}

	@Override
	public int pack() {
		return majorVersion | minorVersion << 16;
	}

	@Override
	public boolean arePreviewFeaturesEnabled() {
		return minorVersion == PREVIEW_FEATURES;
	}

	@Override
	public JavaVersion withPreviewFeatures() {
		if (arePreviewFeaturesEnabled()) return this;
		return new JavaVersionImpl(majorVersion, PREVIEW_FEATURES);
	}
}
