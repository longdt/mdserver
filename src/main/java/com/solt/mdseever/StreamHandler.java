package com.solt.mdseever;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.solt.mdseever.cache.CacheManager;
import com.solt.mdseever.cache.CacheManagerImpl;
import com.solt.mdseever.cache.PieceCache;
import com.solt.mdseever.protocol.Command;
import com.solt.mdseever.protocol.FileID;
import com.solt.mdseever.protocol.GetCommand;
import com.solt.mdseever.protocol.InitStreamCommand;
import com.solt.mdseever.protocol.Result;

public class StreamHandler extends SimpleChannelInboundHandler<Command> {
	private static CacheManager cacheManager = new CacheManagerImpl();
	private PieceCache cache;
	private FileID fileId;
	private String filePath;
	private int pieceSize;
	private long fileOffset;

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Command cmd)
			throws Exception {
		switch (cmd.getType()) {
		case Command.GET:
			get(ctx, (GetCommand) cmd);
			break;
		case Command.INIT_STREAM:
			initStream((InitStreamCommand) cmd);
			break;
		default:
			break;
		}
	}
	
	private void get(ChannelHandlerContext ctx, GetCommand cmd) {
		if (filePath == null) {
			ctx.writeAndFlush(Result.MISSING_INIT_STREAM);
			return;
		}
		try {
			byte[] pieceData = cache.getPiece(cmd.getPieceIdx());
			ctx.writeAndFlush(new Result(pieceData, cmd.getOffset(), cmd.getLength()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initStream(InitStreamCommand isc) {
		fileId = isc.getFileId();
		filePath = isc.getFilePath();
		pieceSize = isc.getPieceSize();
		fileOffset = isc.getFileOffset();
		cache = cacheManager.getCache(fileId, filePath, pieceSize, fileOffset);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
