package me.cworldstar.piratefinds.impl.protocols;

import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

import me.cworldstar.piratefinds.PirateFinds;

public class ArmorHideProtocol {
	public ArmorHideProtocol() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(PirateFinds.getThisPlugin(), PacketType.Play.Server.ENTITY_EQUIPMENT) {
			@Override
			public void onPacketSending(PacketEvent event) {
				PacketContainer packet = event.getPacket();
				StructureModifier<List<ItemStack>> items = packet.getItemListModifier();
			}
		});
	}
}
