package me.cworldstar.piratefinds.impl.protocols;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import me.cworldstar.piratefinds.PirateFinds;

public class ArmorHideProtocol {
	public ArmorHideProtocol() {
		
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(PirateFinds.getThisPlugin(), PacketType.Play.Server.ENTITY_EQUIPMENT) {
			@Override
			public void onPacketSending(PacketEvent event) {
				
			}
		});
	}
}
