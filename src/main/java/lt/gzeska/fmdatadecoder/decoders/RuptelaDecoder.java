package lt.gzeska.fmdatadecoder.decoders;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;
import lt.gzeska.fmdatadecoder.packet.BasePacket;

/**
 *
 * @author gzeska
 */
public class RuptelaDecoder extends ByteToMessageDecoder{
    BasePacket packet = new BasePacket();
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        short length = 0;
        //Reading package length
        if(in.readableBytes() >= 2){
            length = in.readShort();
            packet.setLength(length);
        }else{
            return;
        }
        
        byte[] payload = new byte[length];
        
        if(in.readableBytes() >=length){
            in.readBytes(payload);
            packet.setPayload(payload);
        }else{
            return;
        }
        
        char crc16 = 0;
        if(in.readableBytes() >= 2){
            byte[] crcBuffer = new byte[2];
            crcBuffer[0] = in.readByte();
            crcBuffer[1] = in.readByte();
            int firstByte = (0x000000FF & ((int) crcBuffer[0]));
            int secondByte = (0x000000FF & ((int) crcBuffer[1]));
            crc16 = (char) (firstByte << 8 | secondByte);      
            packet.setCrc16(crc16);
            packet.parse();
        }else{
            return;
        }
        out.add(packet);
    }
    
}
