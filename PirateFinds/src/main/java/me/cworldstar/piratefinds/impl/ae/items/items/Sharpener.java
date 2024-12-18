package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map.Entry;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import com.jeff_media.morepersistentdatatypes.DataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.RomanNumeral;
import net.advancedplugins.ae.impl.utils.ColorUtils;

public class Sharpener extends AbstractPFItem {

	public Sharpener(String id) {
		super(id);
	}

	private static ItemStack item = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
	private static PFItemType type = PFItemType.DRAG_AND_DROP;
	private static NamespacedKey REINFORCEMENT_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "PF_SHARPENER_KEY");
	private static NamespacedKey REINFORCEMENT_KEY_MODIFIER = new NamespacedKey(PirateFinds.getThisPlugin(), "PF_SHARPENER_KEY_MODIFER");
	private static final String REINFORCEMENT_LORE_LINE = ChatUtils.apply("&f&l*** SHARPENED %level% ***");
	
	@Override
	public PFItemType getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	
	static {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ColorUtils.format("&f&lSharpener"));
		meta.setLore(List.of(new String[] {
				"",
				ColorUtils.format("&7[ &f&lSHARPENER&r &7]"),
				ColorUtils.format("&7Dragging and dropping this onto an item"),
				ColorUtils.format("&7will increase the damage of an item,"),
				ColorUtils.format("&7by X points, where X is the level of"),
				ColorUtils.format("&7sharpened * 1. Max level of 10."),
		}));
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
	}
	
	
	public static String handleLore(PersistentDataContainer container, String lore) {
		return lore.replace("%level%", RomanNumeral.toRoman(container.get(REINFORCEMENT_KEY, PersistentDataType.INTEGER)));
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	
	public final String pf_item_id = "SHARPENER";
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		this.make(citem);
		return citem;
	}
	

	public static final EquipmentSlot[] slots = new EquipmentSlot[] {
			EquipmentSlot.HAND
	};
	
	public static void APPLY_SHARPENER(ItemStack on, boolean respect_max_level) {
		
		YamlConfiguration itemConfig = PirateFinds.getThisPlugin().getItemConfigFile();
		ConfigurationSection items = itemConfig.getConfigurationSection("items");
		ConfigurationSection this_item = items.getConfigurationSection("sharpener");
		
		ItemMeta meta = on.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null) {
			lore = new ArrayList<String>();
		}
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if(container.has(REINFORCEMENT_KEY)) {
			int reinforcement_lvl = container.get(REINFORCEMENT_KEY, PersistentDataType.INTEGER);
			if(reinforcement_lvl >= this_item.getInt("max-level") && respect_max_level) {
				return;
			}
			container.set(REINFORCEMENT_KEY, PersistentDataType.INTEGER, reinforcement_lvl + 1);
		} else {
			container.set(REINFORCEMENT_KEY, PersistentDataType.INTEGER, 1);
		}
		
		AttributeModifier modifier = new AttributeModifier(REINFORCEMENT_KEY, this_item.getInt("amount-per-level") * container.get(REINFORCEMENT_KEY, PersistentDataType.INTEGER), Operation.ADD_NUMBER, EquipmentSlotGroup.MAINHAND);
		if(container.has(REINFORCEMENT_KEY_MODIFIER, DataType.ATTRIBUTE_MODIFIER)) {
			meta.removeAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, container.get(REINFORCEMENT_KEY_MODIFIER, DataType.ATTRIBUTE_MODIFIER));
		} else {
			for(EquipmentSlot slot : slots) {
				for(Entry<Attribute, Collection<AttributeModifier>> default_modifier : on.getType().getDefaultAttributeModifiers(slot).asMap().entrySet()) {
					for(AttributeModifier modifier_actual : default_modifier.getValue()) {
						meta.addAttributeModifier(default_modifier.getKey(), modifier_actual);
					}
				}	
			}

		}
		container.set(REINFORCEMENT_KEY_MODIFIER, DataType.ATTRIBUTE_MODIFIER, modifier);
		meta.addAttributeModifier(Attribute.GENERIC_ATTACK_DAMAGE, modifier);
		int index = 0;
		boolean triggered = false;
		for(String string : lore.toArray(new String[0])) {
			if(string.contains(ChatUtils.apply("&f&l*** SHARPENED"))) {
				triggered = true;
				lore.set(index, handleLore(container, ChatUtils.apply("&f&l*** SHARPENED %level% ***")));
			}
			index++;
		}
		if(triggered == false) {
			lore.add(REINFORCEMENT_LORE_LINE);
		}
		lore.replaceAll(loreLine -> handleLore(container, loreLine));
		
		meta.setLore(lore);
		on.setItemMeta(meta);
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		if(!EnchantmentTarget.WEAPON.includes(on.getType())) {
			p.sendMessage(ChatUtils.createBroadcast("&7You may only use a sharpener on weapons."));
			return;
		}
		
		APPLY_SHARPENER(on, true);
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		
		if(!EnchantmentTarget.WEAPON.includes(on.getType())) {
			return false;
		}
		
		ItemMeta meta = on.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if(container.has(REINFORCEMENT_KEY)) {
			int reinforcement_lvl = container.get(REINFORCEMENT_KEY, PersistentDataType.INTEGER);
			if(reinforcement_lvl >= 3) {
				return false;
			}
		}
		return true;
	}
	
}
