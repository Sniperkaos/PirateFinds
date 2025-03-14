package me.cworldstar.piratefinds.impl.protocols;

import org.bukkit.Sound;
import org.bukkit.World.Environment;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;

import me.cworldstar.piratefinds.PirateFinds;

public class EnderDragonSilencedProtocol {
	public EnderDragonSilencedProtocol() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(PirateFinds.getThisPlugin(), ListenerPriority.NORMAL, PacketType.Play.Server.NAMED_SOUND_EFFECT) {
			
			@Override
			public void onPacketSending(PacketEvent event) {
				if (event.getPacketType() == PacketType.Play.Server.NAMED_SOUND_EFFECT) {
	                PacketContainer packet = event.getPacket();
	                StructureModifier<Sound> sounds = packet.getSoundEffects();
	                if(sounds.size() == 0) {
	                	PirateFinds.logDebug("sound was empty");
	                	return;
	                }
	                
	                if(
	                		sounds.read(0) == Sound.ENTITY_ENDER_DRAGON_DEATH && 
	                		!(event.getPlayer().getWorld().getEnvironment().equals(Environment.THE_END))
	                ) {
	                	event.setCancelled(true);
	                }
				}
			}
		});
	}
}
