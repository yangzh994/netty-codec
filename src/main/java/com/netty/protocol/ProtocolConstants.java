package com.netty.protocol;

public interface ProtocolConstants {

    /**
     * Magic code
     */
    byte[] MAGIC_CODE_BYTES = {(byte) 0xda, (byte) 0xda};

    /**
     * Protocol version
     */
    byte VERSION = 1;

    /**
     * Max frame length
     */
    int MAX_FRAME_LENGTH = 8 * 1024 * 1024;

    /**
     * HEAD_LENGTH of protocol v1
     */
    int V1_HEAD_LENGTH = 9;
}
