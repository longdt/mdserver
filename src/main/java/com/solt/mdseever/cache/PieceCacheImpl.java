package com.solt.mdseever.cache;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel.MapMode;

import com.google.common.cache.Cache;
import com.solt.mdseever.config.ConfigurationManager;
import com.solt.mdseever.protocol.FileID;

public class PieceCacheImpl implements PieceCache {
	private static String ROOT_DIR;
	private Cache<PieceID, byte[]> cache;
	private StripedLockProvider lockProvider;
	private CacheLoader loader;
	private FileID fileId;
	private File file;
	private long fileOffset;
	private int pieceSize;
	
	static {
		ROOT_DIR = ConfigurationManager.getInstance().get("server.rootDir");
	}
	
	public PieceCacheImpl(Cache<PieceID, byte[]> cache,
			StripedLockProvider lockProvider, FileID fileId, String filePath,
			int pieceSize, long fileOffset) throws FileNotFoundException {
		this.cache = cache;
		this.lockProvider = lockProvider;
		this.fileId = fileId;
		this.file = new File(ROOT_DIR, filePath);
		if (!file.isFile()) {
			throw new FileNotFoundException(file.getAbsolutePath() + " not found");
		}
		this.pieceSize = pieceSize;
		this.fileOffset = fileOffset;
		loader = new CacheLoaderImpl();
	}

	@Override
	public byte[] getPiece(int pieceIdx) throws Exception {
		PieceID key = new PieceID(fileId, pieceIdx);
		synchronized (lockProvider.getLock(key)) {
			byte[] value = cache.getIfPresent(key);
			if (value == null) {
				value = loader.load(key);
				cache.put(key, value);
			}
			return value;
		}
	}
	
	private class CacheLoaderImpl implements CacheLoader {

		@Override
		public byte[] load(PieceID pieceId) throws Exception {
			long start = ((long)pieceSize) * pieceId.getIndex() - fileOffset;
			long end = start + pieceSize;
			int offset = 0;
			if (start < 0) {
				offset = (int) -start;
				start = 0;
			}
			if (end > file.length()) {
				end = file.length();
			}
			RandomAccessFile in = null;
			try {
				in = new RandomAccessFile(file, "r");
				MappedByteBuffer map = in.getChannel().map(MapMode.READ_ONLY, start, end - start);
				byte[] data = new byte[pieceSize];
				map.get(data, offset, (int) (end -start));
				return data;
			} finally {
				if (in != null) {
					in.close();
				}
			}
		}
		
	}

}
