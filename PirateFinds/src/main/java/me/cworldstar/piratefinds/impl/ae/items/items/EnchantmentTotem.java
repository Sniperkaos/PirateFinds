package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Random;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.DatapackHandler;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.StringEditor;
import net.advancedplugins.ae.api.AEAPI;

public class EnchantmentTotem extends AbstractPFItem {
	
	public static final NamespacedKey NSKEY_ENCHANTMENT = PirateFinds.createKey("enchantment");
	public static final NamespacedKey NSKEY_TIER = PirateFinds.createKey("tier");
	public static final NamespacedKey NSKEY_CHANCE = PirateFinds.createKey("chance");
	public static final NamespacedKey NSKEY_USAGES = PirateFinds.createKey("usages");
	
	public ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
	private List<String> lore;
	private List<String> enchantments;
	private Map<String, String> messages;
	private int max_level = 10;
	private int per_use = 1;
	private int min_chance = 0;
	private int max_chance = 100;
	private int usages = 1;
	
	
	private static PFItemType type = PFItemType.DRAG_AND_DROP;
	
	public String apply_tags(String s, ItemStack i) {
		
		ItemMeta meta = i.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		String enchant = pdc.get(NSKEY_ENCHANTMENT, PersistentDataType.STRING);
		int usages = pdc.get(NSKEY_USAGES, PersistentDataType.INTEGER);
		int tier = pdc.get(NSKEY_TIER, PersistentDataType.INTEGER);
		double chance = pdc.get(NSKEY_CHANCE, PersistentDataType.DOUBLE);
		Random random = new Random();
		
		String displayName;
		String appliesTo;
		
		if(AEAPI.isAnEnchantment(enchant)) {
			displayName = AEAPI.getEnchantmentInstance(enchant).getDisplay();
			appliesTo = AEAPI.getEnchantmentInstance(enchant).getAppliesTo();
		} else {
			StringEditor strEditor = new StringEditor(enchant);
			strEditor.replace("_", " ");
			strEditor.capitalize();
			displayName = strEditor.finishSingle();
			appliesTo = getAppliesTo(Registry.ENCHANTMENT.match(enchant));
		}
		
		if(displayName == null) {
			displayName = "NULL";
		}
		
		if(appliesTo == null) {
			appliesTo = "NULL";
		}
		
		StringEditor editor = new StringEditor(s);
		editor.replace("%tier%", Integer.toString(tier));
		editor.replace("%enchant_display_name%", displayName);
		editor.replace("%enchant_applies_to%", appliesTo);
		editor.replace("%enchant_level%", Integer.toString(this.per_use));
		editor.replace("%totem_chance%", Double.toString(Math.floor(chance * 100) / 100));
		editor.replace("%max_chance%", Integer.toString(max_chance));
		editor.replace("%min_chance%", Integer.toString(min_chance));
		editor.replace("%addition%", Integer.toString(tier * per_use));
		editor.replace("%enchant_max_level%", Integer.toString(max_level));
		editor.replace("%random_enchantment%", enchantments.get(random.nextInt(enchantments.size())));
		editor.replace("%usages%", Long.toString(usages));

		
		return editor.finishSingle();
	}
	
	public static enum EnchantApplication {
		
		HELMET(
				new ItemStack(Material.DIAMOND_HELMET),
				"Helmet"
		),
		
		CHESTPLATE(
				new ItemStack(Material.DIAMOND_CHESTPLATE),
				"Chestplate"
		),
		
		LEGGINGS(
				new ItemStack(Material.DIAMOND_LEGGINGS),
				"Leggings"
		),
		
		BOOTS(
				new ItemStack(Material.DIAMOND_BOOTS),
				"Boots"
		),
		
		SWORD(
				new ItemStack(Material.DIAMOND_SWORD),
				"Sword"
		),
		
		PICKAXE(
				new ItemStack(Material.DIAMOND_PICKAXE),
				"Pickaxe"
		),
		
		AXE(
				new ItemStack(Material.DIAMOND_AXE),
				"Axe"
		),
		
		SHOVEL(
				new ItemStack(Material.DIAMOND_SHOVEL),
				"Shovel"
		),
		
		SHIELD(
				new ItemStack(Material.SHIELD),
				"Shield"
		);
		

		
		
		private ItemStack material;
		private String name;
		
		private EnchantApplication(ItemStack m, String s) {
			this.material = m;
			this.name = s;
		}
		
		public String getDisplayName() {
			return name;
		}
		
		public boolean canApply(Enchantment e) {
			return e.canEnchantItem(material);
		}
		
	}
	
	public String getAppliesTo(Enchantment e) {
		
		if(e == null) {
			return "";
		}
		
		String applies = null;
		
		EnchantApplication[] applications = new EnchantApplication[] {
				EnchantApplication.HELMET,
				EnchantApplication.CHESTPLATE,
				EnchantApplication.LEGGINGS,
				EnchantApplication.BOOTS,
				EnchantApplication.SWORD,
				EnchantApplication.PICKAXE,
				EnchantApplication.AXE,
				EnchantApplication.SHOVEL
		};
		
		
		
		for(EnchantApplication application : applications) {
			if(application.canApply(e)) {
				if(applies == null) {
					applies = application.getDisplayName();
					continue;
				}
				applies = applies.concat(", ").concat(application.getDisplayName());
			}
		}
		
		return applies;
	}
	
	
	public List<String> apply_tags_lore(List<String> lore, ItemStack i) {
		
		ItemMeta meta = i.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		String enchant = pdc.get(NSKEY_ENCHANTMENT, PersistentDataType.STRING);
		int tier = pdc.get(NSKEY_TIER, PersistentDataType.INTEGER);
		int usages = pdc.get(NSKEY_USAGES, PersistentDataType.INTEGER);
		double chance = pdc.get(NSKEY_CHANCE, PersistentDataType.DOUBLE);
		Random random = new Random();
		String displayName;
		String appliesTo;
		
		if(AEAPI.isAnEnchantment(enchant)) {
			displayName = AEAPI.getEnchantmentInstance(enchant).getDisplay();
			appliesTo = AEAPI.getEnchantmentInstance(enchant).getAppliesTo();
		} else {
			StringEditor strEditor = new StringEditor(enchant);
			strEditor.replace("_", " ");
			strEditor.capitalize();
			displayName = strEditor.finishSingle();
			appliesTo = getAppliesTo(Registry.ENCHANTMENT.match(enchant));
		}
		
		if(displayName == null) {
			displayName = "NULL";
		}
		
		if(appliesTo == null) {
			appliesTo = "NULL";
		}
		
		
		StringEditor loreEditor = new StringEditor(lore);
		loreEditor.replace("%tier%", Integer.toString(tier));
		loreEditor.replace("%enchant_display_name%", displayName);
		loreEditor.replace("%enchant_applies_to%", appliesTo);
		loreEditor.replace("%enchant_level%", Integer.toString(this.per_use));
		loreEditor.replace("%totem_chance%", Double.toString(Math.floor(chance * 100) / 100));
		loreEditor.replace("%max_chance%", Integer.toString(max_chance));
		loreEditor.replace("%min_chance%", Integer.toString(min_chance));
		loreEditor.replace("%addition%", Integer.toString(tier * per_use));
		loreEditor.replace("%enchant_max_level%", Integer.toString(max_level));
		loreEditor.replace("%random_enchantment%", enchantments.get(random.nextInt(enchantments.size())));
		loreEditor.replace("%usages%", Long.toString(usages));
		
		return loreEditor.finish();
	}
	
	public boolean enchant(Player p, ItemStack i, ItemStack on) {
		
		ItemMeta meta = i.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		Random random = new Random();
		String enchant = pdc.get(NSKEY_ENCHANTMENT, PersistentDataType.STRING);
		boolean exists = AEAPI.isAnEnchantment(enchant);
		int tier = pdc.get(NSKEY_TIER, PersistentDataType.INTEGER);
		double chance = pdc.get(NSKEY_CHANCE, PersistentDataType.DOUBLE);
		int usages = 1;
		
		if(pdc.has(NSKEY_USAGES)) {
			usages = pdc.get(NSKEY_USAGES, PersistentDataType.INTEGER);
		}
		
		
		
		double gnumber = (random.nextInt(max_chance) + chance);
		boolean success = gnumber >= max_chance;
		
		Enchantment ench = Registry.ENCHANTMENT.match(enchant);
		if(ench == null && !exists) {
			// iterating over the enchantment registry is expensive but there's literally no other way
			for(Enchantment e : Registry.ENCHANTMENT.stream().toList()) {
				if(e.getKey().getKey().contains(enchant)) {
					ench = e;
				}
			}
			if(ench == null) {
				p.sendMessage(ChatUtils.apply("&7[&c&l!&7] This enchantment is invalid! How did you get this?"));
				return false;
			}
		}
		
		if(ench != null && !ench.canEnchantItem(on)) {
			p.sendMessage(ChatUtils.apply(apply_tags(messages.get("invalid_item"), i)));
			p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1.0F, 1.0F);
			return false;
		}
		
		pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, gnumber);
		i.setItemMeta(meta);
		

		
		if(!success) {
			// determine if the success was a critical failure
			double distance = max_chance - gnumber;
			if (distance > (max_chance / 2)) {
				// check white scroll status
				if(!AEAPI.hasHolyWhiteScroll(on)) {
					// no white scroll- sorry dude
					p.sendMessage(ChatUtils.apply(apply_tags(messages.get("shard_major_failure"), i)));
					p.getInventory().remove(on);
				} else {
					// critical failure & a white scroll, we're good- remove the white scroll
					AEAPI.removeHolyWhiteScroll(on);
					// send message
					p.sendMessage(ChatUtils.apply(apply_tags(messages.get("shard_white_scroll_protection"), i)));
				}
				pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
				i.setItemMeta(meta);
				return true;
			} else {
				p.sendMessage(ChatUtils.apply(apply_tags(messages.get("shard_minor_failure"), i)));
				if(usages <= 1) {
					return true;
				} else {
					pdc.set(NSKEY_USAGES, PersistentDataType.INTEGER, usages - 1);
					i.setItemMeta(meta);
					meta.setLore(ChatUtils.apply(apply_tags_lore(this.lore, i)));
					i.setItemMeta(meta);
					pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
					i.setItemMeta(meta);
					return false;
				}
			}
		}
		
		int addition = per_use * tier;
		
		if(max_level == -2 && !exists) {
			max_level = ench.getMaxLevel();
		} else if(max_level == -2 && exists) {
			max_level = AEAPI.getHighestEnchantmentLevel(enchant);
		}
		
		if(exists) {
			int ae_max_level = AEAPI.getHighestEnchantmentLevel(enchant);
			
			if(!AEAPI.isApplicable(on.getType(), enchant)) {
				p.sendMessage(ChatUtils.apply(apply_tags(messages.get("invalid_item"), i)));
				pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
				i.setItemMeta(meta);
				return false;
			}
			
			if(AEAPI.getEnchantLevel(enchant, on) >= ae_max_level) {
				p.sendMessage(ChatUtils.apply(apply_tags(messages.get("enchantment_maxed"), i)));
				pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
				i.setItemMeta(meta);
				return false;
			}
			if(AEAPI.getEnchantLevel(enchant, on) + addition > ae_max_level) {
				p.sendMessage(ChatUtils.apply(apply_tags(messages.get("enchantment_maxed"), i)));
				pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
				i.setItemMeta(meta);
				return false;
			}
			AEAPI.applyEnchant(enchant, AEAPI.getEnchantLevel(enchant, on) + addition, on);
			p.sendMessage(ChatUtils.apply(apply_tags(messages.get("shard_success"), i)));
			p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
			if(usages <= 1) {
				return true;
			} else {
				pdc.set(NSKEY_USAGES, PersistentDataType.INTEGER, usages - 1);
				pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
				i.setItemMeta(meta);
				meta.setLore(ChatUtils.apply(apply_tags_lore(this.lore, i)));
				i.setItemMeta(meta);
				return false;
			}
		}
		
		if(on.containsEnchantment(ench)) {
			if(on.getEnchantmentLevel(ench) >= max_level) {
				p.sendMessage(ChatUtils.apply(apply_tags(messages.get("enchantment_maxed"), i)));
				pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
				i.setItemMeta(meta);
				return false;
			}
			if(on.getEnchantmentLevel(ench) + addition > max_level) {
				p.sendMessage(ChatUtils.apply(apply_tags(messages.get("enchantment_maxed"), i)));
				pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
				i.setItemMeta(meta);
				return false;
			}
			on.addUnsafeEnchantment(ench, on.getEnchantmentLevel(ench) + addition);
		} else {
			if(addition > max_level) {
				addition = max_level;
				p.sendMessage(ChatUtils.apply("&7( &c&l!&r &7) &c&lYou cannot create a totem with a level higher than max_level."));
				pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
				i.setItemMeta(meta);
				return false;
			}
			on.addUnsafeEnchantment(ench, addition);
		}
		p.sendMessage(ChatUtils.apply(apply_tags(messages.get("shard_success"), i)));
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0F, 1.0F);
		if(usages <= 1) {
			return true;
		} else {
			pdc.set(NSKEY_USAGES, PersistentDataType.INTEGER, usages - 1);
			pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
			i.setItemMeta(meta);
			meta.setLore(ChatUtils.apply(apply_tags_lore(this.lore, i)));
			i.setItemMeta(meta);
			return false;
		}
	}
	
	public EnchantmentTotem(String id) {
		super(id);
	}
	
	public List<String> getEnchantments() {
		return this.enchantments;
	}
	
	public int getMaxLevel() {
		return this.max_level;
	}
	
	public int getPerUse() {
		return this.per_use;
	}
	
	/**
	 * 
	 * @param id
	 * @param pfItem
	 * @param max_level
	 * @param per_use
	 * @param enchantments
	 * @param messages
	 * @param min_chance
	 * @param max_chance
	 */
	
	public EnchantmentTotem(String id, ItemStack pfItem, int max_level, int per_use, List<String> enchantments, Map<String, String> messages, int min_chance, int max_chance, List<String> lore) {
		super(id);
		
		this.item = pfItem;
		this.messages = messages;
		ItemMeta meta = this.item.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "totem_"+this.getPFItemID());
		this.item.setItemMeta(meta);
		
		this.min_chance = min_chance;
		this.max_chance = max_chance;
		
		this.max_level = max_level;
		this.per_use = per_use;
		this.enchantments = enchantments;
	}

	public EnchantmentTotem(String id, ItemStack pfItem, int max_level, int per_use, List<String> enchantments, Map<String, String> messages, int min_chance, int max_chance, int usages, List<String> lore) {
		super(id);
		
		this.item = pfItem;
		this.messages = messages;
		ItemMeta meta = this.item.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "totem_"+this.getPFItemID());
		this.item.setItemMeta(meta);
		
		this.min_chance = min_chance;
		this.max_chance = max_chance;
		this.usages = usages;
		this.lore = lore;
		
		this.max_level = max_level;
		this.per_use = per_use;
		this.enchantments = enchantments;
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
		Random random = new Random();

		int nresult = random.nextInt(this.enchantments.size());
		String result = this.enchantments.get(nresult);
		ItemMeta meta = citem.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(NSKEY_ENCHANTMENT, PersistentDataType.STRING, result);
		pdc.set(NSKEY_TIER, PersistentDataType.INTEGER, 1);
		pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, Math.floor(Math.random() * (max_chance - min_chance + 1) + min_chance));
		pdc.set(NSKEY_USAGES, PersistentDataType.INTEGER, usages);
		
		citem.setItemMeta(meta);
		meta.setItemName(apply_tags(meta.getItemName(), citem));
		meta.setLore(apply_tags_lore(meta.getLore(), citem));
		citem.setItemMeta(meta);
		
		this.make(citem);
		return citem;
	}
	
	public ItemStack buildSpecific(String ench, int tier, double chance) {
		ItemStack citem = item.clone();
		ItemMeta meta = citem.getItemMeta();
		Random random = new Random();

		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		
		pdc.set(NSKEY_ENCHANTMENT, PersistentDataType.STRING, ench.replace("%random_enchantment%", enchantments.get(random.nextInt(enchantments.size()))));
		pdc.set(NSKEY_TIER, PersistentDataType.INTEGER, tier);
		pdc.set(NSKEY_CHANCE, PersistentDataType.DOUBLE, chance);
		pdc.set(NSKEY_USAGES, PersistentDataType.INTEGER, usages);

		citem.setItemMeta(meta);
		meta.setItemName(apply_tags(meta.getItemName(), citem));
		meta.setLore(apply_tags_lore(meta.getLore(), citem));
		citem.setItemMeta(meta);
		
		this.make(citem);
		return citem;
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return false;
	}
	
	@Override
	public boolean checkExpendWithItem(Player p, ItemStack on, ItemStack this_item) {
		return enchant(p, this_item, on);
	}
	
	public static void buildFromConfig(ConfigurationSection messages, ConfigurationSection totem) {
		List<String> enchantments = totem.getStringList("enchantments");
		int max_level = totem.getInt("max_level");
		int per_use = totem.getInt("per_use");
		String usages_rand_case = totem.getString("usages");
		int usages = totem.getInt("usages");
		if(usages_rand_case != null && usages_rand_case.contains(",")) {
			int min = 0;
			int max = 0;
			
			String[] split = usages_rand_case.split(",");
			min = Integer.parseInt(split[0]);
			max = Integer.parseInt(split[1]);
			
			usages = PirateFinds.getRandom().nextInt(min, max);
		}
		if(usages == 0) {
			usages = 1;
		}
		int min_chance = totem.getInt("min_chance");
		int max_chance = totem.getInt("max_chance");
		String name = totem.getString("name");
		List<String> lore = totem.getStringList("lore");
		Material material = Material.getMaterial(totem.getString("material"));
		if(material == null) {
			material = Material.PRISMARINE_SHARD;
		}
		
		ItemStack pfItem = new ItemStack(material);
		ItemMeta meta = pfItem.getItemMeta();
		meta.setCustomModelData(totem.getInt("data"));
		meta.setItemName(ChatUtils.apply(name));
		meta.setLore(ChatUtils.apply(lore));
		pfItem.setItemMeta(meta);
		
		if(enchantments.contains("ALL")) {
			enchantments.removeIf(str->str=="ALL");
			List<Enchantment> enchants = Registry.ENCHANTMENT.stream().toList();
			enchants.forEach((Enchantment ench) -> {
				enchantments.add(ench.getKey().getKey());
			});
		}
		
		if(enchantments.contains("ALL_AE")) {
			enchantments.removeIf(str->str=="ALL_AE");
			enchantments.addAll(AEAPI.getAllEnchantments());
		}
		
		Map<String, String> mapped_messages = new HashMap<String, String>();
		for (String key : messages.getKeys(false)) {
			mapped_messages.put(key, messages.getString(key));
		}
		
		EnchantmentTotem lootBox  = new EnchantmentTotem(totem.getName(), pfItem, max_level, per_use, enchantments, mapped_messages, min_chance, max_chance, usages, lore);
		PFItemClass.registerAnyItem(lootBox);
		
		
	}

}
