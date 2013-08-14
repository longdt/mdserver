package com.solt.mdseever.cache;

import java.io.FileNotFoundException;

import com.solt.mdseever.protocol.FileID;

public interface CacheManager {
	public PieceCache getCache(FileID fileId, String filePath, int pieceSize, long fileOffset) throws FileNotFoundException;
}
