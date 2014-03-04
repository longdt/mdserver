package com.solt.mdserver.cache;

import java.io.FileNotFoundException;

import com.solt.mdserver.protocol.FileID;

public interface CacheManager {
	public PieceCache getCache(FileID fileId, String filePath, int pieceSize, long fileOffset) throws FileNotFoundException;
}
