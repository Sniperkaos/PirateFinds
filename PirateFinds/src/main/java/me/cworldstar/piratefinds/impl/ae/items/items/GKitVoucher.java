package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem.PFItemType;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class GKitVoucher extends AbstractPFItem {

	private ItemStack item = new ItemStack(Material.DIAMOND);
	private String gkit;
	
	private final NamespacedKey VOUCHER_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "GKIT_VOUCHER_FOR");
	
	
	/**
	 *
	 * @param gkit | The name of the gkit. EG: sn.gkits.tamer becomes tamer.
	 */
	
	public GKitVoucher(String gkit) {
		super("GKitVoucher");
		
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ChatUtils.apply("&x&1&6&E&A&F&F&lG&x&B&9&E&E&F&3&lk&x&7&E&B&D&C&3&li&x&4&6&A&3&A&C&lt&x&E&F&E&F&E&F&l: &c&l" + gkit.substring(0, 1).toUpperCase() + gkit.substring(1)));
		meta.setDisplayName(meta.getItemName());
		meta.setLore(Arrays.asList(new String[] {
				"",
				ChatUtils.apply("&6Right-click&7 to claim this gkit."),
				ChatUtils.apply("&7If you already own it, you will not be"),
				ChatUtils.apply("&7able to use this."),
		}));
		meta.setEnchantmentGlintOverride(true);
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "GKITVOUCHER");
		item.setItemMeta(meta);
		
		this.gkit = gkit;
	}

	public ItemStack getPFItem() {
		return item;
	}
	
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		ItemMeta meta = citem.getItemMeta();
		meta.setItemName(ChatUtils.apply("&x&1&6&E&A&F&F&lG&x&B&9&E&E&F&3&lk&x&7&E&B&D&C&3&li&x&4&6&A&3&A&C&lt&x&E&F&E&F&E&F&l: &c&l" + gkit.substring(0, 1).toUpperCase() + gkit.substring(1)));
		meta.setDisplayName(meta.getItemName());
		meta.setLore(Arrays.asList(new String[] {
				"",
				ChatUtils.apply("&6Right-click&7 to claim this gkit."),
				ChatUtils.apply("&7If you already own it, you will not be"),
				ChatUtils.apply("&7able to use this."),
		}));
		
		meta.setEnchantmentGlintOverride(true);
		
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "GKITVOUCHER");
		pdc.set(VOUCHER_KEY, PersistentDataType.STRING, gkit);
		citem.setItemMeta(meta);
		this.make(citem);
		return citem;
	}

	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.RIGHT_CLICK,
		});
	}
	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		if(p.hasPermission("sn.gkits." + on.getItemMeta().getPersistentDataContainer().get(VOUCHER_KEY,PersistentDataType.STRING))) {
			p.sendMessage(ChatUtils.createBroadcast("&7You already own this gkit!"));
			return false;
		}
		return true;
	}

	@Override
	public PFItemType getType() {
		return PFItemType.RIGHT_CLICK;
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		
		String agkit = on.getItemMeta().getPersistentDataContainer().get(VOUCHER_KEY,PersistentDataType.STRING);
		
		if(p.hasPermission("sn.gkits." + agkit)) {
			p.sendMessage(ChatUtils.createBroadcast("&7You already own this gkit!"));
			return;
		}
		
		p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1F, 1.2F);
		p.sendMessage(ChatUtils.createBroadcast("You have redeemed the " + agkit + " gkit!"));
		PirateFinds.getServerStatic().dispatchCommand(Bukkit.getConsoleSender(), PlaceholderAPI.setPlaceholders(p, "lp user %player_name% perm set sn.gkits."+agkit+" true"));
	}
}
