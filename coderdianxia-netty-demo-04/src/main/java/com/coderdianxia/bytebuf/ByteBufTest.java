package com.coderdianxia.bytebuf;

import io.netty.buffer.*;
import io.netty.util.ByteProcessor;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

/**
 * @ClassName: Test
 * @Description:TODO
 * @Author:coderDianxia
 * @Date: 2022/7/4 15:07
 */
public class ByteBufTest {
    public static void main(String[] args) {
        ByteBufTest test = new ByteBufTest();
        String msg ="测试数据";
        test.writeAndReadHeadByteBuf(msg);
        test.writeAndReadDirectByteBuf(msg);

        test.writeAndReadCompositeByteBuf();

        test.markIndex();

        test.query();

        test.expand();

        test.byteBufHolder();
    }

    /**
     * 写和读取堆缓冲区
     * 堆缓冲区存放于jvm堆中，可以快速分配与释放
     * @param desc
     */
    public void writeAndReadHeadByteBuf(String desc){

        ByteBuf byteBuf = Unpooled.copiedBuffer("测试堆缓冲区",CharsetUtil.UTF_8);

        //判断是否为堆缓冲区
        if(byteBuf.hasArray())
        {
            byte[] array = byteBuf.array();
            String desc2 = new String(array, CharsetUtil.UTF_8);
            System.out.println("读取堆缓存区数据"+desc2);
        }
        //遍历缓存区字节，不推进索引
        int capacity = byteBuf.capacity();
        for (int j = 0; j < capacity-1; j++) {
            byte aByte = byteBuf.getByte(j);
        }

        //遍历①缓存区字节，推进索引
        for (int i = 0; i < capacity; i++)
        {
            if(i==5)
            {
                break;
            }
            byte b = byteBuf.readByte();

        }
        //遍历②
        while(byteBuf.isReadable())
        {
            byte b = byteBuf.readByte();
        }

        //回收空间，丢弃已经读取的数据，扩大可读空间，方便容纳新数据
        ByteBuf discardReadBytes = byteBuf.discardReadBytes();
    }

    /**
     * 写和读取直接缓冲区
     * 直接缓冲区放与gc堆之外，存取复杂性高于堆缓冲区
     * 免去中间交换的数据拷贝，提示io速度
     * 数据不存放于堆中，如果需要传递处理数据，需要新增副本。
     * @param desc
     */
    public void writeAndReadDirectByteBuf(String desc){

        ByteBuf directBuffer = Unpooled.directBuffer();
        directBuffer.writeBytes(Unpooled.copiedBuffer("测试直接缓冲区",CharsetUtil.UTF_8));
        //判断是否为直接缓冲区
        if(!directBuffer.hasArray())
        {
            //读取数据长度
            int length = directBuffer.readableBytes();
            byte[] bytes = new byte[length];
            ByteBuf buf = directBuffer.getBytes(directBuffer.readerIndex(), bytes);
            System.out.println("直接缓冲区数据:"+new String(bytes,CharsetUtil.UTF_8));
        }
    }

    /**
     * 写和读取复核缓冲区
     * 复核缓冲区类似于存放的集合，可以存放不同类型的缓冲区，可以优化socket i/o,降低原生jdk的内存消耗和性能低下的问题。
     */
    public void writeAndReadCompositeByteBuf(){
        CompositeByteBuf compositeBuf = Unpooled.compositeBuffer();
        //堆缓冲区
        ByteBuf headBuf = Unpooled.copiedBuffer("请求头", CharsetUtil.UTF_8);

        //直接缓冲区
        ByteBuf directBuffer = Unpooled.directBuffer();
        directBuffer.writeBytes(Unpooled.copiedBuffer("请求体", CharsetUtil.UTF_8));
        //添加元素到复核缓冲区
        compositeBuf.addComponents(headBuf,directBuffer);

        for (int i = 0; i < compositeBuf.numComponents(); i++) {
            System.out.println("复核缓冲区元素:"+i+":"+compositeBuf.component(i).toString());
            ByteBuf component = compositeBuf.component(i);

            byte[] bytes = new byte[component.readableBytes()];
            component.getBytes(component.readerIndex(),bytes);
            System.out.println("获取缓冲区内容:"+new String(bytes,CharsetUtil.UTF_8));
        }
    }

    public void markIndex(){
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world",CharsetUtil.UTF_8);
        //增加readIndex索引
        byteBuf.readByte();
        byteBuf.readByte();
        //读取数据长度
        int length = byteBuf.readableBytes();
        byte[] bytes = new byte[length];
        byteBuf.getBytes(byteBuf.readerIndex(), bytes);
        System.out.println("测试索引:"+new String(bytes,CharsetUtil.UTF_8));
        //将可读索引复位为0；
        byteBuf.readerIndex(0);
        int length2 = byteBuf.readableBytes();
        byte[] bytes2 = new byte[length2];
        byteBuf.getBytes(byteBuf.readerIndex(), bytes2);
        System.out.println("索引复位:"+new String(bytes2,CharsetUtil.UTF_8));

        //增加readIndex索引
        byteBuf.readByte();
        byteBuf.readByte();

        //将可读/可写索引复位为0，不会引起组数的复制
        byteBuf.clear();
        int length3 = byteBuf.readableBytes();
        byte[] bytes3 = new byte[length2];
        byteBuf.getBytes(byteBuf.readerIndex(), bytes3);
        System.out.println("索引复位:"+new String(bytes3,CharsetUtil.UTF_8));
    }

    /**
     * 缓冲区的查询操作
     */
    public void query(){
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello world \r hello java",CharsetUtil.UTF_8);
        int i = byteBuf.forEachByte(ByteProcessor.FIND_CR);
        System.out.println("空格的索引位置"+i);
        int i9 = byteBuf.forEachByte(new ByteProcessor.IndexNotOfProcessor((byte)'h'));
        System.out.println("h的索引位置"+i9);
        int i1 = byteBuf.bytesBefore((byte) 'o');//在缓冲区中查找在该字节之前的字节数，返回查找到的第一个字节前的数据；
        System.out.println(i1);
        int i2 = byteBuf.bytesBefore(6, 11, (byte) 'o');
        System.out.println("查找范围内，在该字节前的字节数"+i2);
    }

    /**
     * 衍生的缓冲区
     * 通过copy获得新的缓冲区，拥有独立的读写索引
     * 通过slice可以操作某块数据，原始数据变更会影响slice出来的缓存区
     */
    public void expand() {
        Charset utf8 = Charset.forName("UTF-8");
        ByteBuf buf = Unpooled.copiedBuffer("Netty in Action rocks!", utf8); //1

        ByteBuf copy = buf.copy(0, buf.readableBytes());//copy的缓冲区数据不共享

        int length3 = copy.readableBytes();
        byte[] bytes3 = new byte[length3];
        copy.getBytes(copy.readerIndex(), bytes3);

        System.out.println("复制的副本:"+copy.toString(utf8));

        ByteBuf slice = buf.slice(0, 5);//数据共享

        System.out.println("slice复制的副本:"+slice.toString(utf8));
        //修改原始数据
        buf.setByte(0,(byte)'J');
        //数据随原始数据变更
        System.out.println("slice副本数据随之更改:"+slice.toString(utf8));
        slice.setByte(0,(byte)'A');
        System.out.println("buf:"+buf.toString(utf8));
        //复制的副本数据不会变更
        System.out.println("copy复制的副本:"+copy.toString(utf8));
    }

    public void byteBufHolder(){

    }
}
