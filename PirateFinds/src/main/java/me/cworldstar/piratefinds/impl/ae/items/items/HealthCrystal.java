package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.profile.PlayerProfile;
import me.cworldstar.piratefinds.impl.profile.Profile;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class HealthCrystal extends AbstractPFItem {

	public static ItemStack EXPERIENCE_CRYSTAL = new ItemStack(Material.REDSTONE);
	public static final NamespacedKey EXPERIENCE_CRYSTAL_KEY = PirateFinds.createKey("experienceStored");
	private static final String EXPERIENCE_CRYSTAL_NAME = "&c&lHealth Crystal";
	static {
		ItemMeta meta = EXPERIENCE_CRYSTAL.getItemMeta();
		meta.setItemName(ChatUtils.apply(EXPERIENCE_CRYSTAL_NAME));
		meta.setDisplayName(ChatUtils.apply(EXPERIENCE_CRYSTAL_NAME));
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
			"",
			" &c&oA strange crystal, covered with dust...",
			" &c&oIt is glowing red... Perhaps I can use this?",
			"",
			"&7&l* &4Health + 1",
			"",
			"&7&o( &c&oRight-click to use! &7&o )"
		})));
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "health_crystal");
		EXPERIENCE_CRYSTAL.setItemMeta(meta);
	}
	
	public HealthCrystal() {
		super("health_crystal");
	}

	@Override
	public ItemStack build() {
		
		ItemStack clone = EXPERIENCE_CRYSTAL.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.setItemName(ChatUtils.apply(EXPERIENCE_CRYSTAL_NAME));
		meta.setDisplayName(ChatUtils.apply(EXPERIENCE_CRYSTAL_NAME));
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "health_crystal");
		
		return clone;
	}
	
	public ItemStack getPFItem() {
		return EXPERIENCE_CRYSTAL;
	}

	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		Profile profile = PlayerProfile.getPlayerProfile(p).get();
		profile.setStat("health", profile.getStat("health") + 1);
		p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 1.0f);
		p.sendMessage(ChatUtils.apply("&7[ &c&lHealth + 1 &7]"));
		
	};

	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return true;
	}

	@Override
	public PFItemType getType() {
		return PFItemType.RIGHT_CLICK;
	}

}
