package com.netty.serializer.protobuf.convertor;

import com.netty.serializer.protobuf.transaction.MessageRequest;

public class MessageRequestConvertor implements PbConvertor<MessageRequest, com.netty.serializer.protobuf.generated.MessageRequest> {
    @Override
    public com.netty.serializer.protobuf.generated.MessageRequest convert2Proto(MessageRequest messageRequest) {
        com.netty.serializer.protobuf.generated.MessageRequest.Builder builder = com.netty.serializer.protobuf.generated.MessageRequest.newBuilder();
        builder.setXid(messageRequest.getXid()).setBranchId(messageRequest.getBranchId()).setResourceId(messageRequest.getResourceId()).setApplicationData(messageRequest.getApplicationData());
        return builder.build();
    }

    @Override
    public MessageRequest convert2Model(com.netty.serializer.protobuf.generated.MessageRequest messageRequest) {
        MessageRequest request = new MessageRequest();
        request.setXid(messageRequest.getXid());
        request.setBranchId(messageRequest.getBranchId());
        request.setResourceId(messageRequest.getResourceId());
        request.setApplicationData(messageRequest.getApplicationData());
        return request;
    }
}
