package me.cworldstar.piratefinds.impl.ae.souls;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.impl.ae.events.SoulGemUpdateEvent;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.items.items.SoulGem;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class Souls {

	private int lastQueriedSoulAmount = 0;
	private Player player;
	
	private Souls(Player player) {
		this.player = player;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public int getLastSoulQuery() {
		return lastQueriedSoulAmount;
	}
	
	public int querySouls() {
		int souls = 0;
		for(ItemStack i : player.getInventory().getContents()) {
			if(i == null ) continue;
			if(PFItemClass.compare(PFItemClass.getItem(i), PFItemClass.getItem("SoulGem")) == true) {
				ItemMeta meta = i.getItemMeta();
				PersistentDataContainer pdc = meta.getPersistentDataContainer();
				boolean enabled = pdc.get(SoulGem.ENABLED_KEY, PersistentDataType.BOOLEAN);
				if(!enabled) continue;
				Integer soulsOnItem = pdc.get(SoulGem.SOUL_KEY, PersistentDataType.INTEGER);
				souls += soulsOnItem;
			}
		}
		
		this.lastQueriedSoulAmount = souls;
		
		return souls;
	}
	
	public void expendSouls(int toExpend) {
		for(ItemStack i : player.getInventory().getContents()) {
			if(i == null || toExpend <= 0 ) continue;
			if(PFItemClass.compare(PFItemClass.getItem(i), PFItemClass.getItem("SoulGem")) == true) {
				ItemMeta meta = i.getItemMeta();
				PersistentDataContainer pdc = meta.getPersistentDataContainer();
				boolean enabled = pdc.get(SoulGem.ENABLED_KEY, PersistentDataType.BOOLEAN);
				if(!enabled) continue;
				Integer soulsOnItem = pdc.get(SoulGem.SOUL_KEY, PersistentDataType.INTEGER);
				// wtf? why did I do this?
				if(soulsOnItem == 0) {
					continue;
				}
				int last = (toExpend - soulsOnItem);
				new SoulGemUpdateEvent(player, PFItemClass.getItem("SoulGem"), i, Math.signum(-last) == -1 ? 0 : -last);
				pdc.set(SoulGem.SOUL_KEY, PersistentDataType.INTEGER, Math.signum(-last) == -1 ? 0 : -last);
				i.setItemMeta(meta);
				toExpend = last;
			}
		}
	}

	public static Souls create(Player toInit) {
		return new Souls(toInit);
	}

	public void addSouls(Integer valueOf) {
		for(ItemStack i : player.getInventory().getContents()) {
			if(i == null ) continue;
			if(PFItemClass.compare(PFItemClass.getItem(i), PFItemClass.getItem("SoulGem")) == true) {
				ItemMeta meta = i.getItemMeta();
				PersistentDataContainer pdc = meta.getPersistentDataContainer();
				// wtf? why did I do this?
				new SoulGemUpdateEvent(player, PFItemClass.getItem("SoulGem"), i, valueOf);
				Integer soulsOnItem = pdc.get(SoulGem.SOUL_KEY, PersistentDataType.INTEGER);
				pdc.set(SoulGem.SOUL_KEY, PersistentDataType.INTEGER, soulsOnItem + valueOf);
				i.setItemMeta(meta);
				player.sendMessage(ChatUtils.apply("&7[ &fSouls &7]: You have recieved &c" + Integer.toString(valueOf) + "&7 souls."));
				break;
			}
		}
		player.sendMessage(ChatUtils.apply("&7[ &fSouls &7]: You did not have a soul gem, so no souls were added."));
	}

}
