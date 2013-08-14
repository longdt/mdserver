package com.solt.mdseever.protocol;

public abstract class Command {
	public static final byte INIT_STREAM = 1;
	public static final byte GET = 2;
	protected byte type;
	
	protected Command(byte type) {
		this.type = type;
	}
	
	public byte getType() {
		return type;
	}

}
