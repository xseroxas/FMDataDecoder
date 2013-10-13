/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lt.gzeska.fmdatadecoder.decoders;

import lt.gzeska.fmdatadecoder.packet.BasePacket;

/**
 *
 * @author gzeska
 */
public interface PacketDecoder {
    /**
     * 
     * @param packet BasePacket 
     * @return return true if it possible to decode packet correctly.
     */
    public boolean decode(BasePacket packet);
}
