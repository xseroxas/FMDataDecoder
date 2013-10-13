package lt.gzeska.fmdatadecoder.packet;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author gzeska
 */
public class BasePacket {
    protected short length = 0;
    protected byte[] payload = null;
    protected int crc16 = 0;
    protected long imei = 0;
    protected byte command = 0;
    protected int crc16Calculated = 0;

    public int getCrc16Calculated() {
        return crc16Calculated;
    }
    
    public long getImei() {
        return imei;
    }

    public byte getCommand() {
        return command;
    }
    
    public int getCrc16() {
        return crc16;
    }

    public void setCrc16(char crc16) {
        this.crc16 = crc16;
    }
    
    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public byte[] getPayload() {
        return payload;
    }

    public void setPayload(byte[] payload) {
        this.payload = payload;
    }
    
    public void parse() throws IOException{
        DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(payload));
        this.imei = dataStream.readLong();
        this.command = dataStream.readByte();
        this.crc16Calculated = this.crc16(payload);
        
    } 
    
    @Override
    public String toString() {
        return "Packet lenght: "+this.getLength()+" bytes: "
                +Arrays.toString(payload)+" CRC16 Received: "+this.getCrc16()
                + "CRC16 calculated: "+this.getCrc16Calculated()
                +" IMEI: "+this.getImei()
                +" Command: "+this.getCommand();
    }
    
    private int crc16(byte[] buffer) {
       int  i;
       short ucBit, ucCarry;
       int usPoly = (int)0x8408 ;//reversed 0x1021
       int usCRC  = 0;
       int lenght =  buffer.length;
       for (i = 0; i < lenght; i++) {
           usCRC ^= (buffer[i]& 0xff);
           for (ucBit = 0; ucBit < 8; ucBit++) {
               ucCarry = (short)(usCRC & 1);
               usCRC >>= 1;
               if (ucCarry == 1) {
                   usCRC ^= usPoly;
               }
           }
       }
       return usCRC;
    } 
}
