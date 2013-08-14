package com.solt.mdseever;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;

import java.nio.charset.Charset;
import java.util.List;

import com.solt.mdseever.protocol.Command;
import com.solt.mdseever.protocol.FileID;
import com.solt.mdseever.protocol.GetCommand;
import com.solt.mdseever.protocol.InitStreamCommand;

public class CommandDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if (in.readableBytes() < 5) {
			return;
		}
		// Check the magic number.
		int magicNumber = in.readUnsignedByte();
		Command cmd = null;
		switch (magicNumber) {
		case Command.GET:
			if (in.readableBytes() < 12) {
				in.resetReaderIndex();
				return;
			}
			int pieceIdx = in.readInt();
			int offset = in.readInt();
			int length = in.readInt();
			cmd = new GetCommand(pieceIdx, offset, length);
			break;
		case Command.INIT_STREAM:
			int dataLength = in.readInt();
			if (dataLength <= 21) {
				throw new CorruptedFrameException("Invalid command format: datalength=" + dataLength + " is too short");
			} else if (in.readableBytes() < dataLength) {
				in.resetReaderIndex();
				return;
			}
			int pieceSize = in.readInt();
			long fileOffset = in.readLong();
			FileID fileId = FileID.readBytes(in);
			String file = in.readBytes(dataLength - 21).toString(Charset.forName("UTF-8"));
			cmd = new InitStreamCommand(fileId, file, pieceSize, fileOffset);
			break;
		default:
			in.resetReaderIndex();
			throw new CorruptedFrameException("Invalid magic number: "
					+ magicNumber);
		}
		out.add(cmd);
	}

}
