package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Particle.DustOptions;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ParticleUtils;
import net.advancedplugins.ae.impl.utils.ColorUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class Magnet extends AbstractPFItem {

	private static ItemStack item = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODU0NmI5Yjk2NjNmMGVhMjNlMTQ4ODQxYTQ2OTllZjAwN2Q5ZGNmZDUwZTE5YTQwNGVlZjU4NGFhNTBjMjJmNCJ9fX0=");
	
	
	public Magnet(String id) {
		super(id);
	}

	static {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ColorUtils.format("&c&lInventory Magnet"));
		meta.setLore(List.of(new String[] {
				ColorUtils.format("&eRight-Click&7 to teleport to you every"),
				ColorUtils.format("&7dropped item in a &c20 block&7 radius."),
				ColorUtils.format("&7"),
				ColorUtils.format("&7Cooldown: %cooldown%")
		}));
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "MAGNET");
		item.setItemMeta(meta);
	}
	
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		ItemMeta meta = citem.getItemMeta();
		this.make(citem);
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "MAGNET");
		meta.setMaxStackSize(1);
		citem.setItemMeta(meta);
		return citem;
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return false; // never
	}


	public void tryPickup(Player p, Item item) {
		item.teleport(p.getLocation());
	}
	
	public String replaceCooldown(ItemStack item, String loreLine) {
		return loreLine.replace("%cooldown%", Integer.toString(getCooldown(item)));
	}
	
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.RIGHT_CLICK,
				PFItemType.TICK
		});
	}
	
	
	public void setCooldown(ItemStack cdItem, int cooldown) {
		ItemMeta meta = cdItem.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		lore.replaceAll(loreLine -> replaceCooldown(cdItem, loreLine));
		meta.setLore(lore);
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(COOLDOWN_KEY, PersistentDataType.INTEGER, cooldown);
		cdItem.setItemMeta(meta);
	}
	
	public int getCooldown(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(meta == null) return 0;
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		if(pdc.has(COOLDOWN_KEY)) {
			return pdc.get(COOLDOWN_KEY, PersistentDataType.INTEGER);
		}
		return 0;
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		int cooldown = getCooldown(on);

		switch(type) {
			case RIGHT_CLICK:
				if(cooldown > 0) {
					p.sendMessage(ChatUtils.createBroadcast("&7This item is on cooldown. Remaining time: " + Integer.toString(cooldown)));
					return;
				}
				p.sendMessage(ChatUtils.apply("&d&l*** MAGNET USED ***"));
				p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1F);
				ParticleUtils.summonCircle(p.getLocation().add(0, 1, 0), 20, new DustOptions(Color.FUCHSIA, 5));
				setCooldown(on, 60);
				List<Entity> entities = p.getNearbyEntities(20, 20, 20);
				for(Entity entity : entities.toArray(new Entity[0])) {
					if(entity instanceof Item) {
						tryPickup(p, (Item) entity);
					}
				}
				break;
			case TICK:
				if(cooldown > 0) {
					setCooldown(on,cooldown-1);
				}
				break;
			default:
				break;
		}
	}

	@Override
	public PFItemType getType() {
		return PFItemType.SHIFT_RIGHT_CLICK;
	};


}
