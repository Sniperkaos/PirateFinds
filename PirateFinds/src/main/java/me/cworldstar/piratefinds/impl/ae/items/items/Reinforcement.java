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

public class Reinforcement extends AbstractPFItem {

	public Reinforcement(String id) {
		super(id);
	}

	private static ItemStack item = new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE);
	private static PFItemType type = PFItemType.DRAG_AND_DROP;
	private NamespacedKey REINFORCEMENT_KEY = new NamespacedKey(PirateFinds.getThisPlugin(), "REINFORCEMENT_KEY");
	private NamespacedKey REINFORCEMENT_KEY_MODIFIER = new NamespacedKey(PirateFinds.getThisPlugin(), "REINFORCEMENT_KEY_MODIFIER");
	private final String REINFORCEMENT_LORE_LINE = ChatUtils.apply("&b&l*** REINFORCED %level% ***");
	
	@Override
	public PFItemType getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	
	static {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ColorUtils.format("&f&lReinforcement"));
		meta.setLore(List.of(new String[] {
				"",
				ColorUtils.format("&7[ &f&lREINFORCEMENT&r &7]"),
				ColorUtils.format("&7Dragging and dropping this onto an item"),
				ColorUtils.format("&7will increase the armor of an item,"),
				ColorUtils.format("&7by X points, where X is the level of"),
				ColorUtils.format("&7reinforcement * 3. Max level of 3."),
		}));
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
	}
	
	
	public String handleLore(PersistentDataContainer container, String lore) {
		return lore.replace("%level%", RomanNumeral.toRoman(container.get(REINFORCEMENT_KEY, PersistentDataType.INTEGER)));
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	
	public final String pf_item_id = "REINFORCEMENT";
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		this.make(citem);
		return citem;
	}
	

	public final EquipmentSlot[] slots = new EquipmentSlot[] {
			EquipmentSlot.CHEST,
			EquipmentSlot.LEGS,
			EquipmentSlot.FEET,
			EquipmentSlot.HEAD
	};
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		if(!EnchantmentTarget.ARMOR.includes(on.getType())) {
			p.sendMessage(ChatUtils.createBroadcast("&7You may only use reinforcements on armors."));
			return;
		}
		
		YamlConfiguration itemConfig = PirateFinds.getThisPlugin().getItemConfigFile();
		ConfigurationSection items = itemConfig.getConfigurationSection("items");
		ConfigurationSection this_item = items.getConfigurationSection("reinforcement");
		
		
		ItemMeta meta = on.getItemMeta();
		List<String> lore = meta.getLore();
		if(lore == null) {
			lore = new ArrayList<String>();
		}
		PersistentDataContainer container = meta.getPersistentDataContainer();
		if(container.has(REINFORCEMENT_KEY)) {
			int reinforcement_lvl = container.get(REINFORCEMENT_KEY, PersistentDataType.INTEGER);
			if(reinforcement_lvl >= this_item.getInt("max-level")) {
				return;
			}
			container.set(REINFORCEMENT_KEY, PersistentDataType.INTEGER, reinforcement_lvl + 1);
		} else {
			container.set(REINFORCEMENT_KEY, PersistentDataType.INTEGER, 1);
		}
		
		AttributeModifier modifier = new AttributeModifier(REINFORCEMENT_KEY, this_item.getInt("amount-per-level") * container.get(REINFORCEMENT_KEY, PersistentDataType.INTEGER), Operation.ADD_NUMBER, EquipmentSlotGroup.ARMOR);
		if(container.has(REINFORCEMENT_KEY_MODIFIER, DataType.ATTRIBUTE_MODIFIER)) {
			meta.removeAttributeModifier(Attribute.GENERIC_ARMOR, container.get(REINFORCEMENT_KEY_MODIFIER, DataType.ATTRIBUTE_MODIFIER));
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
		meta.addAttributeModifier(Attribute.GENERIC_ARMOR, modifier);
		int index = 0;
		boolean triggered = false;
		for(String string : lore.toArray(new String[0])) {
			if(string.contains(ChatUtils.apply("&b&l*** REINFORCED"))) {
				triggered = true;
				lore.set(index, handleLore(container, ChatUtils.apply("&b&l*** REINFORCED %level% ***")));
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
	public boolean checkExpend(Player p, ItemStack on) {
		
		if(!EnchantmentTarget.ARMOR.includes(on.getType())) {
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
