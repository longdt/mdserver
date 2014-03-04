package com.solt.mdserver.protocol;

public class GetCommand extends Command {
	private int pieceIdx;
	private int offset;
	private int length;
	
	public GetCommand(int pieceIdx, int offset, int length) throws InvalidParameterException {
		super(GET);
		if (pieceIdx < 0 || offset < 0 || length <= 0) {
			throw new InvalidParameterException("Invalid value: pieceIdx=" + pieceIdx + ", offset=" + offset + ", length=" + length);
		}
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
