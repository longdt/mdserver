package com.solt.mdserver.cache;


public interface CacheLoader {
	public byte[] load(PieceID pieceId) throws Exception;
}
