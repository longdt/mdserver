package com.solt.mdseever.protocol;

public class GetCommand extends Command {
	private int pieceIdx;
	private int offset;
	private int length;
	
	public GetCommand(int pieceIdx, int offset, int length) {
		super(GET);
		this.pieceIdx = pieceIdx;
		this.offset = offset;
		this.length = length;
	}

	public int getPieceIdx() {
		return pieceIdx;
	}

	public int getOffset() {
		return offset;
	}

	public int getLength() {
		return length;
	}
}
