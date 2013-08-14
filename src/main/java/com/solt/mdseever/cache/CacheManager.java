package com.solt.mdseever.cache;

import com.solt.mdseever.protocol.FileID;

public interface CacheManager {
	public PieceCache getCache(FileID fileId, String filePath, int pieceSize, long fileOffset);
}
