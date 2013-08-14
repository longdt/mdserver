package com.solt.mdseever.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.solt.mdseever.config.ConfigurationManager;
import com.solt.mdseever.protocol.FileID;

public class CacheManagerImpl implements CacheManager {
	private static final long DEFAULT_CACHE_SIZE = 1000;
	private Cache<PieceID, byte[]> cache;
	private StripedLockProvider lockProvider;
	
	public CacheManagerImpl() {
		long size = ConfigurationManager.getInstance().getLong("cache.size", DEFAULT_CACHE_SIZE);
		if (size < 0) {
			size = DEFAULT_CACHE_SIZE;
		}
		cache = CacheBuilder.newBuilder().maximumSize(size ).<PieceID, byte[]>build();
		lockProvider = new StripedLockProvider();
	}

	@Override
	public PieceCache getCache(FileID fileId, String filePath, int pieceSize, long fileOffset) {
		return new PieceCacheImpl(cache, lockProvider, fileId, filePath, pieceSize, fileOffset);
	}

}
