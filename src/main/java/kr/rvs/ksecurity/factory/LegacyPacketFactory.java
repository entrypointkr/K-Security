package kr.rvs.ksecurity.factory;

import com.comphenix.protocol.Packets;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import kr.rvs.ksecurity.util.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.lang.reflect.Field;

/**
 * Created by Junhyeong Lim on 2017-10-20.
 */
@SuppressWarnings({"unchecked", "deprecation"})
public class LegacyPacketFactory implements PacketFactory {
    private PacketContainer createPacket(int type) {
        return ProtocolLibrary.getProtocolManager().createPacket(type);
    }

    private Class<Object> getGameModeCls() {
        return (Class<Object>) NMSUtils.getNMSClass("EnumGamemode");
    }

    private Object getGameMode(Class<Object> cls) {
        Field anyField = cls.getFields()[0];
        try {
            return anyField.get(null);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public PacketContainer createLoginPacket() {
        PacketContainer container = createPacket(Packets.Server.LOGIN);
        World world = Bukkit.getWorlds().get(0);
        Class<Object> gameModeCls = getGameModeCls();
        Object gameMode = getGameMode(gameModeCls);

        container.getIntegers()
                .write(0, NMSUtils.getNextEntityCount())
                .write(1, world.getEnvironment().getId());
        container.getWorldTypeModifier().write(0, world.getWorldType());
        container.getSpecificModifier(gameModeCls).write(0, gameMode);
        container.getBooleans().write(0, Bukkit.getServer().isHardcore());
        container.getBytes()
                .write(0, (byte) world.getDifficulty().getValue())
                .write(1, (byte) world.getMaxHeight())
                .write(2, (byte) Bukkit.getMaxPlayers());

        return container;
    }

    @Override
    public PacketContainer createTransactionPacket(int id, int value, boolean bool) {
        PacketContainer container = createPacket(Packets.Server.TRANSACTION);
        container.getIntegers().write(0, id);
        container.getShorts().write(0, (short) value);
        container.getBooleans().write(0, bool);

        return container;
    }
}
