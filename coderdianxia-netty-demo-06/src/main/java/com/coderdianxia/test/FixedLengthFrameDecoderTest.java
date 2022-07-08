package com.coderdianxia.test;

import com.coderdianxia.test.decode.FixedLengthFrameDecode;
import com.coderdianxia.test.encode.AbsIntegerEncoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.Assert;
import org.junit.Test;

/**
 * @ClassName: FixedLengthFrameDecoderTest
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/7 17:50
 */
public class FixedLengthFrameDecoderTest {

    @Test
    public  void testIn() {

        ByteBuf buffer = Unpooled.buffer();
        for (int i = 0; i <9;i++)
        {
            buffer.writeByte(i);
        }
        ByteBuf input = buffer.duplicate();

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new FixedLengthFrameDecode(3));

        boolean b = embeddedChannel.writeInbound(input.readBytes(2));//写入2个字节，无法读取
        Assert.assertFalse(b);
        boolean c = embeddedChannel.writeInbound(input.readBytes(3));//写入3个字节，可以读取
        Assert.assertTrue(c);

        Object o = embeddedChannel.readInbound();
        Assert.assertNotNull(o);

        boolean b1 = embeddedChannel.writeOutbound(input.readBytes(2));
        Assert.assertTrue(b1);

        Object o1 = embeddedChannel.readOutbound();
        Assert.assertNotNull(o1);


    }

    @Test
    public void testOut(){

        ByteBuf buffer = Unpooled.buffer();

        for (int i = 1; i <9;i++)
        {
            buffer.writeInt(i*-1);
        }

        EmbeddedChannel embeddedChannel = new EmbeddedChannel(new AbsIntegerEncoder());

        boolean b = embeddedChannel.writeOutbound(buffer); //写出站数据,不能写入出站数据，则异常
        Assert.assertTrue(b);



        for (int i = 1; i <9;i++)
        {
            Object o = embeddedChannel.readOutbound(); //读取出站数据，无法读取则为null
            System.out.println(o);
        }
//        Assert.assertNotNull(o);
//        System.out.println(o);

    }
}
