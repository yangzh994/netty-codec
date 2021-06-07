package com.netty.codec;

import com.netty.compressor.Compressor;
import com.netty.compressor.gzip.GzipCompressor;
import com.netty.message.RpcMessage;
import com.netty.protocol.ProtocolConstants;
import com.netty.serializer.Serializer;
import com.netty.serializer.protobuf.ProtobufSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


/**
 * proto 编码
 */
public class ProtoBufEncoderV1 extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (o instanceof RpcMessage) {
            int fullLength = ProtocolConstants.V1_HEAD_LENGTH;
            RpcMessage rpcMessage = (RpcMessage) o;
            byteBuf.writeBytes(ProtocolConstants.MAGIC_CODE_BYTES);
            byteBuf.writeByte(ProtocolConstants.VERSION);
            byteBuf.writerIndex(byteBuf.writerIndex() + 4);
            byteBuf.writeByte(rpcMessage.getMessageType());
            byteBuf.writeByte(rpcMessage.getCodecType());
            Serializer serializer = new ProtobufSerializer();
            byte[] bodyBytes = serializer.serialize(rpcMessage.getBody());
            Compressor compressor = new GzipCompressor();
            bodyBytes = compressor.compress(bodyBytes);

            fullLength += bodyBytes.length;
            if (bodyBytes != null) {
                byteBuf.writeBytes(bodyBytes);
            }

            int index = byteBuf.writerIndex();
            byteBuf.writerIndex(index - fullLength + 3);
            byteBuf.writeInt(fullLength);
            byteBuf.writerIndex(index);
        }
    }
}
