package com.solt.mdserver.protocol;


public class InitStreamCommand extends Command {
	private FileID fileId;
	private String filePath;
	private int pieceSize;
	private long fileOffset;
	
	public InitStreamCommand(FileID fileId, String filePath, int pieceSize, long fileOffset) {
		super(INIT_STREAM);
		this.fileId = fileId;
		this.filePath = filePath;
		this.pieceSize = pieceSize;
		this.fileOffset = fileOffset;
	}
	
	public FileID getFileId() {
		return fileId;
	}

	public String getFilePath() {
		return filePath;
	}

	public int getPieceSize() {
		return pieceSize;
	}

	public long getFileOffset() {
		return fileOffset;
	}

}
