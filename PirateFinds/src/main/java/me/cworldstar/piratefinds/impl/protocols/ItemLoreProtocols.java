package me.cworldstar.piratefinds.impl.protocols;

import java.lang.reflect.InvocationTargetException;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

public class ItemLoreProtocols {
	public static void addLore(Player p, ItemStack i) {
		try {
			
			PacketContainer container = new PacketContainer(PacketType.Play.Server.SET_SLOT);
			
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, container);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
