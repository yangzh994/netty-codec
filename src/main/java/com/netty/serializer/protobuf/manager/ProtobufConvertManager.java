/*
 *  Copyright 1999-2019 Seata.io Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.netty.serializer.protobuf.manager;

import com.netty.serializer.protobuf.convertor.MessageRequestConvertor;
import com.netty.serializer.protobuf.convertor.PbConvertor;
import com.netty.serializer.protobuf.transaction.MessageRequest;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author leizhiyuan
 */
public class ProtobufConvertManager {

    private Map<String, PbConvertor> convertorMap = new ConcurrentHashMap<>();

    private Map<String, PbConvertor> reverseConvertorMap = new ConcurrentHashMap<>();

    private Map<String, Class> protoClazzMap = new ConcurrentHashMap<>();

    private static class SingletonHolder {
        private static final ProtobufConvertManager INSTANCE;

        static {
            final ProtobufConvertManager protobufConvertManager = new ProtobufConvertManager();

            protobufConvertManager.convertorMap.put(MessageRequest.class.getName(),
                    new MessageRequestConvertor());

            protobufConvertManager.protoClazzMap.put(com.netty.serializer.protobuf.generated.MessageRequest.getDescriptor().getFullName(),
                    com.netty.serializer.protobuf.generated.MessageRequest.class);

            protobufConvertManager.reverseConvertorMap.put(com.netty.serializer.protobuf.generated.MessageRequest.class.getName(),
                    new MessageRequestConvertor());

            INSTANCE = protobufConvertManager;
        }

    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static final ProtobufConvertManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public PbConvertor fetchConvertor(String clazz) {
        return convertorMap.get(clazz);
    }

    public PbConvertor fetchReversedConvertor(String clazz) {
        return reverseConvertorMap.get(clazz);
    }

    public Class fetchProtoClass(String clazz) {
        return protoClazzMap.get(clazz);
    }

}