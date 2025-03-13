package me.cworldstar.piratefinds.impl.ae.items.items.weapons;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Particle.DustOptions;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.listeners.Locked;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ParticleUtils;
import net.advancedplugins.ae.impl.utils.ColorUtils;

public class FencingSabre extends AbstractPFItem {
	private static ItemStack item = new ItemStack(Material.IRON_SWORD);
	private static PFItemType type = PFItemType.RIGHT_CLICK;
	
	public FencingSabre() {
		super("FencingSabre");
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
		meta.setItemName(ColorUtils.format("&f&lFencing Sabre"));
		meta.setLore(List.of(new String[] {
				ChatUtils.apply("&7Sharpness XV"),
				"",
				ColorUtils.format("&7[ &f&lFENCING SABRE&r &7]"),
				ColorUtils.format("&f * &eRight-clicking&7 with this item"),
				ColorUtils.format("&f * &7will cause you to teleport to,"),
				ColorUtils.format("&f * &7a random target in range, dealing"),
				ColorUtils.format("&f * &7medium damage."),
				"",
				ColorUtils.format("&6&lCooldown: &r&f%cooldown%"),
		}));
		meta.setMaxStackSize(1);
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "FENCING_SABRE");
		meta.setEnchantmentGlintOverride(true);
		item.setItemMeta(meta);
	}
	

	public final String pf_item_id = "FENCING_SABRE";
	
	@Override
	public ItemStack build() {
		ItemStack citem = item.clone();
		ItemMeta meta = citem.getItemMeta();
		citem.addUnsafeEnchantment(Enchantment.SHARPNESS, 20);
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		
		PersistentDataContainer pdc = meta.getPersistentDataContainer();
		pdc.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "FENCING_SABRE");
		citem.setItemMeta(meta);
		this.make(citem);
		Locked.lock(citem);
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

		//https://www.spigotmc.org/threads/teleport-the-player-behind-an-entity-on-attack.161254/
		switch(type) {
			case RIGHT_CLICK:
				
				if(cooldown > 0) {
					p.sendMessage(ChatUtils.createBroadcast("&7This item is on cooldown. Remaining time: " + Integer.toString(cooldown)));
					return;
				}
				
				p.playSound(p, Sound.BLOCK_AMETHYST_CLUSTER_PLACE, 1F, 0.5F);
				List<Entity> entities = p.getNearbyEntities(22, 22, 22);
				entities.removeIf(entity -> !(entity instanceof LivingEntity));
				if(entities.size() == 0) {
					return;
				}
				LivingEntity target = (LivingEntity) entities.get(0);
			
				ParticleUtils.summonCircle(target.getLocation().subtract(0, target.getHeight(), 0), 4, new DustOptions(Color.fromRGB(255, 255, 255), 4));
				
				double x;
				double z;
				
				Location targetLoc = target.getLocation();
				
				float nang = targetLoc.getYaw() + 90;
				
				x = Math.cos(Math.toRadians(nang));
				z = Math.sin(Math.toRadians(nang));
				
				Location toTeleport = new Location(targetLoc.getWorld(), targetLoc.getX() - x, targetLoc.getY(), targetLoc.getZ() - z, targetLoc.getYaw(), targetLoc.getPitch());
				p.teleport(toTeleport);
				setCooldown(on, 90);
				
				target.damage(10, p);
				
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
