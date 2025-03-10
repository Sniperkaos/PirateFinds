package me.cworldstar.piratefinds.impl.ae.items.items.masks;

import java.util.ArrayList;
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
import com.jeff_media.morepersistentdatatypes.DataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ParticleUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class SantaMask extends AbstractMask {

	private static final String MASK_DISPLAY_NAME = ChatUtils.apply("&x&F&B&0&0&0&0S&x&F&D&7&1&7&1a&x&F&F&E&3&E&3n&x&B&4&E&0&F&Ft&x&5&1&B&6&F&Fa &x&5&1&A&B&D&7C&x&B&4&B&E&8&6l&x&F&8&B&8&5&2a&x&D&D&6&6&7&1u&x&C&1&1&4&9&1s");	
	private static final List<String> SANTA_MASK_LORE = Arrays.asList(new String[] {
			"&f&oA mask of holiday cheer,",
			"&f&oenhancing your looks,",
			"&f&oand increasing your luck.",
			"",
			"&c&l&oWARNING: &f&omay come with a side effect",
			"&f&oof permanent winter.",
			"",
			"&c&lMASK EFFECTS:",
			"&7-> &a&lLuck II",
			"&7-> &6&lOverload V",
			"&7-> &f&lSnow Effect"
	});
	
	static {
		SANTA_MASK_LORE.replaceAll(loreLine -> ChatUtils.apply(loreLine));
	}
	
	public static final String HEAD_ID = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVmNmY0ODRjY2YzOTQ2NGRhZGQwMDMzYzQyZThkNzFkYzZiMTkzMzQ3M2Q1NDU1YzlkZTMyYzgzMDhmNDI3MyJ9fX0=";
	public static ItemStack HEAD = SkullCreator.itemFromBase64(HEAD_ID);
	
	public static void remove(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(meta == null)
			return;
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		String[] maskEffects = container.get(PirateFinds.createKey("MASK_EFFECT"), DataType.STRING_ARRAY);
		if(maskEffects == null)
			return;
		List<String> effects = new ArrayList<String>();
		effects.addAll(Arrays.asList(maskEffects));
		
		if(effects.contains("SANTA_MASK_EFFECT")) {
			effects.remove("SANTA_MASK_EFFECT");
		}
		
		maskEffects = effects.toArray(new String[0]);
		container.set(PirateFinds.createKey("MASK_EFFECT"), DataType.STRING_ARRAY, maskEffects);
		item.setItemMeta(meta);
	}
	
	
	static {
		ItemMeta meta = HEAD.getItemMeta();
		meta.setDisplayName(ChatUtils.apply("&f&lMask:&r ") + MASK_DISPLAY_NAME);
		
		meta.setLore(SANTA_MASK_LORE);
		
		/*Multimap<Attribute, AttributeModifier> NH_MODIFIER = Material.NETHERITE_HELMET.getDefaultAttributeModifiers(EquipmentSlot.HEAD);
		for(Entry<Attribute, AttributeModifier> modifier : NH_MODIFIER.entries()) {
			AttributeModifier old_modifier = modifier.getValue();
			AttributeModifier edited_modifier = new AttributeModifier(SANTA_MASK_MODIFIER_KEY, old_modifier.getAmount(), Operation.ADD_NUMBER, EquipmentSlotGroup.HEAD);
			meta.addAttributeModifier(modifier.getKey(), edited_modifier);
		}
		*/
		
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
	public boolean checkExpend(Player p, ItemStack on) {
		ItemMeta meta = on.getItemMeta();
		if(meta == null)
			return false;
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		String[] maskEffects = container.get(PirateFinds.createKey("MASK_EFFECT"), DataType.STRING_ARRAY);
		if(maskEffects == null) {
			maskEffects = new String[0];
		}
		List<String> effects = new ArrayList<String>();
		effects.addAll(Arrays.asList(maskEffects));
		
		if(effects.contains("SANTA_MASK_EFFECT")) {
			p.sendMessage(ChatUtils.createBroadcast("&7This armor already contains a mask effect."));
			return false;
		}
		
		effects.add("SANTA_MASK_EFFECT");
		container.set(PirateFinds.createKey("MASK_EFFECT"), DataType.STRING_ARRAY, effects.toArray(new String[0]));
		
		return true;
	}
	
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		ItemMeta meta = on.getItemMeta();
		if(meta == null)
			return;
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PirateFinds.createKey("ARMOR_EQUIP"), DataType.ITEM_STACK, HEAD);
		String[] maskEffects = container.get(PirateFinds.createKey("MASK_EFFECT"), DataType.STRING_ARRAY);
		if(maskEffects == null) {
			maskEffects = new String[] {
					"SANTA_MASK_EFFECT"
			};
		} else {
			List<String> effects = new ArrayList<String>();
			effects.addAll(Arrays.asList(maskEffects));
			
			if(effects.contains("SANTA_MASK_EFFECT")) {
				p.sendMessage(ChatUtils.createBroadcast("&7This armor already contains a mask effect."));
				return;
			}
			
			effects.add("SANTA_MASK_EFFECT");
			maskEffects = effects.toArray(new String[0]);
		}
		container.set(PirateFinds.createKey("MASK_EFFECT"), DataType.STRING_ARRAY, maskEffects);
				
		List<String> lore = meta.getLore();
		if(lore == null) {
			lore = new ArrayList<String>();
		}
		
		
		
		lore.add(ChatUtils.apply("&f&l&nApplied Masks:"));
		lore.add(ChatUtils.apply("&fMask: &x&F&B&0&0&0&0S&x&F&D&7&1&7&1a&x&F&F&E&3&E&3n&x&B&4&E&0&F&Ft&x&5&1&B&6&F&Fa &x&5&1&A&B&D&7C&x&B&4&B&E&8&6l&x&F&8&B&8&5&2a&x&D&D&6&6&7&1u&x&C&1&1&4&9&1s"));
		meta.setLore(lore);
		on.setItemMeta(meta);
	};
	

}
