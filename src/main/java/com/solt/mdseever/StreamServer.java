package com.solt.mdseever;

import com.solt.mdseever.config.ConfigurationManager;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class StreamServer {
	private int port;
	
	public StreamServer(int port) {
		this.port = port;
	}
	
	public void run() throws Exception {
		EventLoopGroup bossGroup =  new NioEventLoopGroup();
		EventLoopGroup workerGroup =  new NioEventLoopGroup();
		try {
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			.channel(NioServerSocketChannel.class)
			.childHandler(new ChannelInitializer<SocketChannel>() {
				private ResultEncoder encoder = new ResultEncoder();
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast(encoder, new CommandDecoder(), new StreamHandler());
				}
			}).option(ChannelOption.SO_BACKLOG, 128)
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			
			ChannelFuture f = b.bind(port).sync();
			
			f.channel().closeFuture().sync();
		} finally {
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) throws Exception {
		int port;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
		} else {
			port = ConfigurationManager.getInstance().getInt("server.port", 8080);
		}
		new StreamServer(port).run();
	}
}
