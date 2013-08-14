package com.solt.mdseever.protocol;

public class Result {
	public static final byte STATUS_OK = 0;
	public static final byte STATUS_INVALID = 1;
	public static final byte	STATUS_MISSING_INIT_STREAM = 2;
	public static final Result INVALID = new Result(STATUS_INVALID);
	public static final Result MISSING_INIT_STREAM = new Result(STATUS_MISSING_INIT_STREAM);
	private byte status;
	private int length;
	private int offset;
	private byte[] data;
	
	private Result(byte statErr) {
		this.status = statErr;
	}
	
	public Result(byte[] data, int offset, int length) {
		status = 0;
		this.data = data;
		this.length = length;
		this.offset = offset;
	}
	
	public byte getStatus() {
		return status;
	}
	
	public int getLength() {
		return length;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public int getOffset() {
		return offset;
	}
}
