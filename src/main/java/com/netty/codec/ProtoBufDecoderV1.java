package com.netty.codec;


import com.netty.compressor.Compressor;
import com.netty.compressor.gzip.GzipCompressor;
import com.netty.message.RpcMessage;
import com.netty.protocol.ProtocolConstants;
import com.netty.serializer.Serializer;
import com.netty.serializer.protobuf.ProtobufSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * proto 解码
 */
public class ProtoBufDecoderV1 extends LengthFieldBasedFrameDecoder {

    public ProtoBufDecoderV1() {
        super(ProtocolConstants.MAX_FRAME_LENGTH, 3, 4, -7, 0);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        Object decode = super.decode(ctx, in);
        System.out.println(decode);
        if (decode instanceof ByteBuf) {
            ByteBuf frame = (ByteBuf) decode;

            byte b0 = frame.readByte();
            byte b1 = frame.readByte();
            if (ProtocolConstants.MAGIC_CODE_BYTES[0] != b0
                    || ProtocolConstants.MAGIC_CODE_BYTES[1] != b1) {
                throw new IllegalArgumentException("Unknown magic code: " + b0 + ", " + b1);
            }

            byte version = frame.readByte();
            // TODO  check version compatible here

            int fullLength = frame.readInt();
            byte messageType = frame.readByte();
            byte codecType = frame.readByte();


            RpcMessage rpcMessage = new RpcMessage();
            rpcMessage.setCodecType(codecType);
            rpcMessage.setMessageType(messageType);

            int bodyLength = fullLength - ProtocolConstants.V1_HEAD_LENGTH;
            if (bodyLength > 0) {
                byte[] bs = new byte[bodyLength];
                frame.readBytes(bs);
                Compressor compressor = new GzipCompressor();
                bs = compressor.decompress(bs);
                Serializer serializer = new ProtobufSerializer();
                rpcMessage.setBody(serializer.deserialize(bs));
            }

            return rpcMessage;
        }
        return decode;
    }
}
