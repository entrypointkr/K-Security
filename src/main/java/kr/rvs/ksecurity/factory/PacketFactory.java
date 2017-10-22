package kr.rvs.ksecurity.factory;

import com.comphenix.protocol.events.PacketContainer;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
public interface PacketFactory {
    PacketContainer createLoginPacket();

    PacketContainer createTransactionPacket(int id, int value, boolean bool);
}
