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
	public static final NamespacedKey ENABLED_KEY = PirateFinds.createKey("souls_enabled");
	
	private int max_souls;
	
	public String apply_tags(String s, ItemStack i) {
		
		ItemMeta meta = i.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		int souls = pdc.get(SOUL_KEY, PersistentDataType.INTEGER);
		boolean enabled = pdc.get(ENABLED_KEY, PersistentDataType.BOOLEAN);
		
		StringEditor editor = new StringEditor(s);
		editor.replace("%souls%", Integer.toString(souls));
		editor.replace("%enabled%", Boolean.toString(enabled));
		
		return ChatUtils.apply(editor.finishSingle());
	}
	
	public List<String> apply_tags_lore(List<String> s, ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		int souls = pdc.get(SOUL_KEY, PersistentDataType.INTEGER);
		boolean enabled = pdc.get(ENABLED_KEY, PersistentDataType.BOOLEAN);
		
		StringEditor editor = new StringEditor(s);
		editor.replace("%souls%", Integer.toString(souls));
		editor.replace("%enabled%", enabled == true ? "&a"+Boolean.toString(enabled)+"&7" : "&c"+Boolean.toString(enabled)+"&7");
		
		return ChatUtils.apply(editor.finish());
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
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(SOUL_KEY, PersistentDataType.INTEGER, 1337);
		pdc.set(ENABLED_KEY, PersistentDataType.BOOLEAN, false);
		citem.setItemMeta(meta);
		meta.setItemName(apply_tags(meta.getItemName(), citem));
		meta.setLore(apply_tags_lore(meta.getLore(), citem));
		meta.setMaxStackSize(1);
		citem.setItemMeta(meta);
		
		this.make(citem);
		return citem;
	}
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.RIGHT_CLICK,
				PFItemType.TICK,
				PFItemType.DRAG_AND_DROP
		});
	}
	
	@Override
	public boolean checkExpendWithItem(Player player, ItemStack clicked, ItemStack cursor) {
		if(PFItemClass.compare(PFItemClass.getItem(clicked), PFItemClass.getItem("SoulGem"))) {
			updateSouls(clicked, getSouls(cursor));
		}
		return true;
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		ItemMeta meta = on.getItemMeta();
		ItemMeta orgmeta = item.getItemMeta();
		boolean enabled = meta.getPersistentDataContainer().get(ENABLED_KEY, PersistentDataType.BOOLEAN);
		switch(type) {
			case RIGHT_CLICK:
				if(enabled) {
					meta.setEnchantmentGlintOverride(false);
					meta.getPersistentDataContainer().set(ENABLED_KEY, PersistentDataType.BOOLEAN, false);
					p.sendMessage(ChatUtils.apply("&7( &c&l! &7) &cYou have disabled this soul gem. Soul enchants will no longer work."));
					p.playSound(p, Sound.UI_LOOM_SELECT_PATTERN, 1f, 1f);
					on.setItemMeta(meta);
				} else {
					meta.getPersistentDataContainer().set(ENABLED_KEY, PersistentDataType.BOOLEAN, true);
					meta.setEnchantmentGlintOverride(true);
					p.sendMessage(ChatUtils.apply("&7( &c&l! &7) &aYou have enabled this soul gem. Soul enchants will now work!"));
					p.playSound(p, Sound.UI_LOOM_SELECT_PATTERN, 1f, 1f);
					on.setItemMeta(meta);
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

	public static void updateSouls(ItemStack slotItem, int souls) {
		ItemMeta meta = slotItem.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(SOUL_KEY, PersistentDataType.INTEGER, pdc.get(SOUL_KEY, PersistentDataType.INTEGER) + souls);
		slotItem.setItemMeta(meta);
	}
	
	public static int getSouls(ItemStack slotItem) {
		ItemMeta meta = slotItem.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		return pdc.get(SOUL_KEY, PersistentDataType.INTEGER);
	}

	public static void setSouls(ItemStack mainHand, int i) {
		ItemMeta meta = mainHand.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(SOUL_KEY, PersistentDataType.INTEGER, i);
		mainHand.setItemMeta(meta);
	}
}
