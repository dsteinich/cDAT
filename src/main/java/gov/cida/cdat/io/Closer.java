package gov.cida.cdat.io;

import java.io.Closeable;

public class Closer {
	public static void close(Closeable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			// TODO I know, I know. Never swallow. But really?
		}
	}
	public static void close(AutoCloseable closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			// TODO I know, I know. Never swallow. But really?
		}
	}
	public static void close(QuietClose closeable) {
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (Exception e) {
			// TODO I know, I know. Never swallow. But really?
		}
	}
}
