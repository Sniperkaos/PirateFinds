package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle.DustOptions;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ParticleUtils;
import net.advancedplugins.ae.api.AEAPI;

public class CandyCane extends AbstractPFItem {
	//&x&F&F&F&F&F&F&lC&x&B&A&6&0&6&0&la&x&A&B&0&0&0&0&ln&x&E&E&0&0&0&0&ld&x&C&7&0&0&0&0&ly &x&9&2&1&2&1&2&lC&x&9&2&6&D&6&D&la&x&B&B&B&B&B&B&ln&x&F&F&F&F&F&F&le
	
	private static ItemStack item = new ItemStack(Material.STONE_SWORD);
	private static PFItemType type = PFItemType.RIGHT_CLICK;
	
	public String replaceCooldown(int cooldown, String loreLine) {
				
		
		String result = loreLine;
		result = loreLine.replace("%cooldown%", Integer.toString(cooldown));
		result = result.replaceAll("Cooldown: §f[0-9]{1,4}", "Cooldown: §f" + Integer.toString(cooldown));
		
		return result;
	}
	
	public void setCooldown(ItemStack cdItem, int cooldown) {
		ItemMeta meta = cdItem.getItemMeta();
		List<String> lore = meta.getLore();
		lore.replaceAll(loreLine -> replaceCooldown(cooldown, loreLine));
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
	
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.RIGHT_CLICK,
				PFItemType.TICK
		});
	}
	
	
	public CandyCane() {
		super("CANDY_CANE");
	}
	
	static {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ChatUtils.apply("&4&lCandy &f&lCane"));
		
		// enchants
		
		meta.addEnchant(Enchantment.SHARPNESS, 10, true);
		meta.addEnchant(Enchantment.SMITE, 10, true);
		meta.addEnchant(Enchantment.UNBREAKING, 10, true);
		
		meta.setLore(Arrays.asList(new String[] {
				"",
				ChatUtils.apply("&x&F&F&F&F&F&FT&x&E&B&D&1&D&1h&x&D&7&A&4&A&4i&x&C&4&7&6&7&6s &x&B&0&4&9&4&9c&x&9&C&1&B&1&Ba&x&9&8&0&0&0&0n&x&A&B&0&0&0&0d&x&B&E&0&0&0&0y &x&D&1&0&0&0&0c&x&E&4&0&0&0&0a&x&F&7&0&0&0&0n&x&E&C&0&0&0&0e &x&D&9&0&0&0&0h&x&C&6&0&0&0&0a&x&B&4&0&0&0&0s &x&A&1&0&0&0&0b&x&9&2&0&5&0&5e&x&9&2&1&F&1&Fe&x&9&2&3&9&3&9n &x&9&2&5&3&5&3s&x&9&2&6&E&6&Eh&x&9&2&8&8&8&8a&x&9&E&9&E&9&Er&x&B&1&B&1&B&1p&x&C&5&C&5&C&5e&x&D&8&D&8&D&8n&x&E&C&E&C&E&Ce&x&F&F&F&F&F&Fd"),
				ChatUtils.apply("&x&F&F&F&F&F&Fi&x&E&B&D&1&D&1n&x&D&7&A&4&A&4t&x&C&4&7&6&7&6o &x&B&0&4&9&4&9a &x&9&C&1&B&1&Bp&x&9&8&0&0&0&0o&x&A&B&0&0&0&0i&x&B&E&0&0&0&0n&x&D&1&0&0&0&0t&x&E&4&0&0&0&0. &x&F&7&0&0&0&0T&x&E&C&0&0&0&0h&x&D&9&0&0&0&0i&x&C&6&0&0&0&0s &x&B&4&0&0&0&0c&x&A&1&0&0&0&0o&x&9&2&0&5&0&5u&x&9&2&1&F&1&Fl&x&9&2&3&9&3&9d &x&9&2&5&3&5&3s&x&9&2&6&E&6&Ee&x&9&2&8&8&8&8r&x&9&E&9&E&9&Ei&x&B&1&B&1&B&1o&x&C&5&C&5&C&5u&x&D&8&D&8&D&8s&x&E&C&E&C&E&Cl&x&F&F&F&F&F&Fy"),
				ChatUtils.apply("&x&F&F&F&F&F&Fh&x&D&1&9&5&9&5u&x&A&3&2&B&2&Br&x&A&B&0&0&0&0t &x&D&7&0&0&0&0s&x&F&2&0&0&0&0o&x&C&7&0&0&0&0m&x&9&B&0&0&0&0e&x&9&2&3&1&3&1b&x&9&2&6&E&6&Eo&x&A&4&A&4&A&4d&x&D&2&D&2&D&2y&x&F&F&F&F&F&F.")
				,"",
				ChatUtils.apply("&f&lCooldown: &f%cooldown%")
		}));
		
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "CANDYCANE");
		item.setItemMeta(meta);		
		for(int i=0;i<15;i++) {
			Sharpener.APPLY_SHARPENER(item, false);
		}

		
	}
	
	public ItemStack getPFItem() {
		return item;
	}

	@Override
	public ItemStack build() {
		
		Random random = new Random();
		
		ItemStack this_item = item.clone();
		
		AEAPI.applyEnchant("sticky",random.nextInt(1, 5), this_item);
		AEAPI.applyEnchant("melting",random.nextInt(1, 10), this_item);
		
		return this_item;
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
				p.sendMessage(ChatUtils.apply("&c&l*** CANDY CANE USED ***"));
				p.playSound(p, Sound.BLOCK_SLIME_BLOCK_PLACE, 1F, 0.5F);
				List<Entity> entities = p.getNearbyEntities(22, 22, 22);
				for(int size=1; size<10; size++) {
					ParticleUtils.summonCircle(p.getLocation(), size, new DustOptions(Color.fromRGB(255, 255 - (255 / size), 255 - (255 / size)), 10-size));
				}
				for(Entity entity : entities) {
					if(entity instanceof LivingEntity && !entity.equals(p) && !entity.isInvulnerable()) {
						LivingEntity lentity = (LivingEntity) entity;
						lentity.damage(12.0, p);
						lentity.setVelocity(lentity.getLocation().getDirection().multiply(lentity.getLocation().distance(p.getLocation()) / 4));
					}
				}
				setCooldown(on, 30);
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
	public boolean checkExpend(Player p, ItemStack on) {
		return false; // never expend
	}

	@Override
	public PFItemType getType() {
		return type;
	}

}
