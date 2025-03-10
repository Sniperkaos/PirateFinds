package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ParticleUtils;
import me.cworldstar.piratefinds.impl.utils.StringEditor;
import net.advancedplugins.ae.impl.utils.ColorUtils;

public class SoulGem extends AbstractPFItem {
	public ItemStack item = new ItemStack(Material.EMERALD);
	private static PFItemType type = PFItemType.RIGHT_CLICK;
	public static final String pf_item_id = "SoulGem";
	
	public static final NamespacedKey SOUL_KEY = PirateFinds.createKey("souls");
	private int max_souls;
	
	public String apply_tags(String s, ItemStack i) {
		
		ItemMeta meta = i.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		int souls = pdc.get(SOUL_KEY, PersistentDataType.INTEGER);
		
		StringEditor editor = new StringEditor(s);
		editor.replace("%souls%", Integer.toString(souls));
		
		return editor.finishSingle();
	}
	
	public List<String> apply_tags_lore(List<String> s, ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		int souls = pdc.get(SOUL_KEY, PersistentDataType.INTEGER);
		
		StringEditor editor = new StringEditor(s);
		editor.replace("%souls%", Integer.toString(souls));
		
		return editor.finish();
	}
	
	public SoulGem(String id, String name, int data, List<String> lore, int max_souls) {
		super(id);
		
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, pf_item_id);
		meta.setItemName(name);
		meta.setLore(lore);
		item.setItemMeta(meta);
	
		this.max_souls = max_souls;
	}
	
	public int getMaxSouls() {
		return this.max_souls;
	}
	
	@Override
	public PFItemType getType() {
		return type;	
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	

	
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		ItemMeta meta = citem.getItemMeta();
		meta.setItemName(apply_tags(meta.getItemName(), citem));
		meta.setLore(apply_tags_lore(meta.getLore(), citem));
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(SOUL_KEY, PersistentDataType.INTEGER, 0);
		citem.setItemMeta(meta);
		this.make(citem);
		return citem;
	}
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.RIGHT_CLICK,
				PFItemType.TICK
		});
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		ItemMeta meta = on.getItemMeta();
		ItemMeta orgmeta = item.getItemMeta();
		switch(type) {
			case RIGHT_CLICK:
				if(meta.getEnchantmentGlintOverride() == true) {
					meta.setEnchantmentGlintOverride(false);
					p.sendMessage("&7( &c&l! &7) &cYou have disabled this soul gem. Soul enchants will no longer work.");
				} else {
					meta.setEnchantmentGlintOverride(true);
					p.sendMessage("&7( &c&l! &7) &aYou have enabled this soul gem. Soul enchants will now work!");
				}
				break;
			case TICK:
				meta.setItemName(apply_tags(orgmeta.getItemName(), on));
				meta.setLore(apply_tags_lore(orgmeta.getLore(), on));
				on.setItemMeta(meta);
			default:
				break;
		}
		
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return false; // never expend
	}

	/**
	 *   name: "&6&lSoul Gem &7(&c&l%souls%&7)"
  data: 0
  lore:
  - "&c&oThis gem contains the souls"
  - "&c&oof thousands of captured entities."
  - ""
  - "&6&oGem Information:"
  - "&c * &7Souls: %souls%"
  - ""
  - "&7( &6&oRight-click in hand to enable souls. &7)"
	 * @param gem
	 */
	
	public static void buildFromConfig(ConfigurationSection gem) {
		String name = gem.getString("name");
		int data = gem.getInt("data");
		int max_souls = gem.getInt("max_souls");
		List<String> lore = gem.getStringList("lore");
		
		
		PFItemClass.registerAnyItem(new SoulGem("SoulGem", name, data, lore, max_souls));
		
	}
}
