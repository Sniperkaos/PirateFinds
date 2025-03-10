package me.cworldstar.piratefinds.impl.listeners.listeners;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.ItemSlot;
import com.jeff_media.morepersistentdatatypes.DataType;

import me.cworldstar.piratefinds.PirateFinds;
import net.advancedplugins.ae.impl.effects.armorutils.ArmorEquipEvent;

public class OnArmorEquipEvent implements Listener {
	public OnArmorEquipEvent() {
		PirateFinds.getServerStatic().getPluginManager().registerEvents(this, PirateFinds.getThisPlugin());
	}
	
	@EventHandler
	public void onArmorEquip(ArmorEquipEvent e) {
		ItemStack armorPiece = e.getNewArmorPiece();
		if(armorPiece == null) return;
		if(armorPiece.getItemMeta() == null) return;
		PersistentDataContainer container = armorPiece.getItemMeta().getPersistentDataContainer();
		ItemStack replacement = container.get(PirateFinds.createKey("ARMOR_EQUIP"), DataType.ITEM_STACK);
		if(replacement == null) return;
		
		replacement = replacement.clone();
		
		ItemMeta meta = armorPiece.getItemMeta();
		List<String> lore = meta.getLore();
		String name = meta.getItemName();
		
		ItemMeta replace_meta = replacement.getItemMeta();
		replace_meta.setItemName(name);
		replace_meta.setLore(lore);
		replacement.setItemMeta(meta);
		
		
		for(Player player : PirateFinds.getServerStatic().getOnlinePlayers()) {
			PacketContainer fakeItem = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
			if(player.equals(e.getPlayer())) return;
		    fakeItem.getItemSlots().write(0, ItemSlot.HEAD);
		    fakeItem.getItemModifier().write(0, replacement);
		    fakeItem.getIntegers().write(0, e.getPlayer().getEntityId());
		    try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(player, fakeItem);
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
			
	}
}
