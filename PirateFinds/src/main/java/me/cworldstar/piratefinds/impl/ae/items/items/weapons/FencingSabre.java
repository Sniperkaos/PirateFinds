package me.cworldstar.piratefinds.impl.ae.items.items.weapons;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle.DustOptions;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ParticleUtils;
import net.advancedplugins.ae.impl.utils.ColorUtils;

public class FencingSabre extends AbstractPFItem {
	private static ItemStack item = new ItemStack(Material.MACE);
	private static PFItemType type = PFItemType.RIGHT_CLICK;
	
	public FencingSabre(String id) {
		super(id);
	}
	
	@Override
	public PFItemType getType() {
		return type;	
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	static {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ColorUtils.format("&6&lGround Pounder"));
		meta.setLore(List.of(new String[] {
				"",
				ColorUtils.format("&7[ &6&lGROUND POUNDER&r &7]"),
				ColorUtils.format("&eRight-clicking with this item"),
				ColorUtils.format("&ewill cause you to strike the ground,"),
				ColorUtils.format("&edealing dmg and knocking up nearby entities."),
				"",
				ColorUtils.format("&6&lCooldown: &r&f%cooldown%"),
		}));
		meta.setMaxStackSize(1);
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "HAMMER");
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
	}
	

	public final String pf_item_id = "HAMMER";
	
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		ItemMeta meta = citem.getItemMeta();
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "HAMMER");
		citem.setItemMeta(meta);
		this.make(citem);
		return citem;
	}
	
	public String replaceCooldown(ItemStack item, String loreLine) {
		return loreLine.replace("%cooldown%", Integer.toString(getCooldown(item)));
	}
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.RIGHT_CLICK,
				PFItemType.TICK
		});
	}
	
	
	public void setCooldown(ItemStack cdItem, int cooldown) {
		ItemMeta meta = cdItem.getItemMeta();
		List<String> lore = item.getItemMeta().getLore();
		lore.replaceAll(loreLine -> replaceCooldown(cdItem, loreLine));
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
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		int cooldown = getCooldown(on);

		switch(type) {
			case RIGHT_CLICK:
				if(cooldown > 0) {
					p.sendMessage(ChatUtils.createBroadcast("&7This item is on cooldown. Remaining time: " + Integer.toString(cooldown)));
					return;
				}
				p.sendMessage(ChatUtils.apply("&6&l*** HAMMER USED ***"));
				p.playSound(p, Sound.BLOCK_ANVIL_PLACE, 1F, 0.5F);
				List<Entity> entities = p.getNearbyEntities(22, 22, 22);
				for(int size=2; size<22; size++) {
					ParticleUtils.summonCircle(p.getLocation(), size, new DustOptions(Color.fromRGB(214, 203, 207), 22-size));
				}
				for(Entity entity : entities) {
					if(entity instanceof LivingEntity && !entity.equals(p) && !entity.isInvulnerable()) {
						LivingEntity lentity = (LivingEntity) entity;
						lentity.damage(20.0, p);
						lentity.setVelocity(new Vector(0, 1, 0).normalize().multiply(p.getLocation().getDirection()).multiply(12));

					}
				}
				setCooldown(on, 180);
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
}
