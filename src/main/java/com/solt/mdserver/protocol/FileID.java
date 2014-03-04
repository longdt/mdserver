package com.solt.mdserver.protocol;

import io.netty.buffer.ByteBuf;

/**
 * FileID present id of a given file. It use 9bytes
 * @author thienlong
 *
 */
public class FileID {
	private long major;
	private byte minor;
	
	private FileID(long major, byte minor) {
		this.major = major;
		this.minor = minor;
	}
	
	@Override
	public int hashCode() {
		return (int) (major << 8) | minor;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof FileID) {
			FileID other = (FileID) obj;
			return (major == other.major && minor == other.minor);
		}
		return false;
	}
	
	public static FileID readBytes(ByteBuf buf) {
		if (buf.readableBytes() < 9) {
			return null;
		}
		long major = buf.readLong();
		byte minor = buf.readByte();
		return new FileID(major, minor);
	}
}
