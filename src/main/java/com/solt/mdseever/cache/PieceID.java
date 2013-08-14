package com.solt.mdseever.cache;

import com.solt.mdseever.protocol.FileID;

public class PieceID {
	private FileID fileId;
	private int index;
	
	public PieceID(FileID fileId, int index) {
		this.fileId = fileId;
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}
	
	@Override
	public int hashCode() {
		return fileId.hashCode() | index << 4;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj instanceof PieceID) {
			PieceID other = (PieceID) obj;
			return (fileId.equals(other.fileId) && index == other.index);
		}
		return false;
	}
}
