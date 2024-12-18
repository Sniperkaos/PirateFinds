package me.cworldstar.piratefinds.impl.ae.items.items.masks;

import java.util.Arrays;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Particle;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.EquipmentSlotGroup;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.common.collect.Multimap;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ParticleUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class SantaMask extends AbstractMask {

	private static final NamespacedKey SANTA_MASK_MODIFIER_KEY = PirateFinds.createKey("SANTA_MASK");
	private static final List<String> SANTA_MASK_LORE = Arrays.asList(new String[] {
			"&f&oA mask of holiday cheer,",
			"&f&oenhancing your looks,",
			"&f&oand increasin	g your luck.",
			"",
			"&c&oWARNING: &f&omay come with a side effect",
			"&f&oof permanent winter.",
			"",
			"&c&lMASK EFFECTS:",
			"&7-> &a&lLuck I",
			"&7-> &f&lSnow Effect"
	});
	
	static {
		SANTA_MASK_LORE.replaceAll(loreLine -> ChatUtils.apply(loreLine));
	}
	
	public static final String HEAD_ID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVmNmY0ODRjY2YzOTQ2NGRhZGQwMDMzYzQyZThkNzFkYzZiMTkzMzQ3M2Q1NDU1YzlkZTMyYzgzMDhmNDI3MyJ9fX0=";
	public static ItemStack HEAD = SkullCreator.itemFromBase64(HEAD_ID);
	
	
	
	static {
		ItemMeta meta = HEAD.getItemMeta();
		meta.setDisplayName(ChatUtils.apply("&f&lMask:&r &x&F&B&0&0&0&0S&x&F&D&7&1&7&1a&x&F&F&E&3&E&3n&x&B&4&E&0&F&Ft&x&5&1&B&6&F&Fa &x&5&1&A&B&D&7C&x&B&4&B&E&8&6l&x&F&8&B&8&5&2a&x&D&D&6&6&7&1u&x&C&1&1&4&9&1s"));
		
		meta.setLore(SANTA_MASK_LORE);
		
		Multimap<Attribute, AttributeModifier> NH_MODIFIER = Material.NETHERITE_HELMET.getDefaultAttributeModifiers(EquipmentSlot.HEAD);
		for(Entry<Attribute, AttributeModifier> modifier : NH_MODIFIER.entries()) {
			AttributeModifier old_modifier = modifier.getValue();
			AttributeModifier edited_modifier = new AttributeModifier(SANTA_MASK_MODIFIER_KEY, old_modifier.getAmount(), Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
			meta.addAttributeModifier(modifier.getKey(), edited_modifier);
		}
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "SANTA_MASK");
		
		HEAD.setItemMeta(meta);
	}
	
	public SantaMask() {
		super("SANTA");
	}

	@Override
	public ItemStack makeMask() {
		return HEAD.clone();
	}

	@Override
	public ItemStack getPFItem() {
		return HEAD;
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		// it will always be Tick here
		ItemStack HEAD_ITEM = p.getInventory().getItem(EquipmentSlot.HEAD);
		if(HEAD_ITEM == null) return;
		if(PFItemClass.isPFItemSimilar(on, HEAD_ITEM)) {
			ParticleUtils.summonCircle(p.getLocation(), 1, Particle.SNOWFLAKE, new Location(p.getWorld(), 0, 1.5, 0), -0.05);
			p.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 60, 0));
		}
	};
	

}
