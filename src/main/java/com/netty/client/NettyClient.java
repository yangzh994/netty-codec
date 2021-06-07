package com.netty.client;

import com.netty.codec.ProtoBufDecoderV1;
import com.netty.codec.ProtoBufEncoderV1;
import com.netty.handler.ClientHandler;
import com.netty.message.RpcMessage;
import com.netty.serializer.protobuf.transaction.MessageRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class NettyClient {

    public static void main(String[] args) {

        try {
            EventLoopGroup workGroup = new NioEventLoopGroup(20);
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workGroup).channel(NioSocketChannel.class)
                    .option(
                            ChannelOption.TCP_NODELAY, true).option(ChannelOption.SO_KEEPALIVE, true).option(
                    ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000).option(
                    ChannelOption.SO_SNDBUF, 153600).option(ChannelOption.SO_RCVBUF,
                    153600)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new IdleStateHandler(0, 0, 0, TimeUnit.SECONDS))
                                    .addLast(new ProtoBufEncoderV1())
                                    .addLast(new ProtoBufDecoderV1())
                                    .addLast(new ClientHandler());
                        }
                    });
            Channel channel = bootstrap.connect("localhost", 8888).channel();
            System.out.println("连接成功");
            System.out.println(channel);
            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setCodecType((byte) 1);
            rpcMessage.setMessageType((byte) 2);
            MessageRequest messageRequest = new MessageRequest();
            messageRequest.setXid("10001");
            messageRequest.setBranchId(20002);
            messageRequest.setResourceId("30003");
            messageRequest.setApplicationData("40004");
            rpcMessage.setBody(messageRequest);
            channel.writeAndFlush(rpcMessage).addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println(future);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
