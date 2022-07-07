package com.coderdianxia.coderc.combined;

import com.coderdianxia.coderc.decode.ByteToIntegerDecode;
import com.coderdianxia.coderc.encode.IntegerToByteEncode;
import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @ClassName: CombinedDecodeAndEncode
 * @Description:组合编码器和解码器，又提升编解码的可重用性。
 * @Author:coderDianxia
 * @Date: 2022/7/6 11:03
 */
public class CombinedDecodeAndEncode extends CombinedChannelDuplexHandler<ByteToIntegerDecode, IntegerToByteEncode> {

    public CombinedDecodeAndEncode() {
        super(new ByteToIntegerDecode(),new IntegerToByteEncode());
    }
}
