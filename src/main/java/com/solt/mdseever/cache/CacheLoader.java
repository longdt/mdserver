package com.solt.mdseever.cache;


public interface CacheLoader {
	public byte[] load(PieceID pieceId) throws Exception;
}
