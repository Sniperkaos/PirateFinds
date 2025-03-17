package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.entities.AbstractPFEntity;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public abstract class AbstractEntitySpawner extends AbstractPFItem {

	private ItemStack item = new ItemStack(Material.BONE);
	protected AbstractPFEntity entity;
	private String name = ChatUtils.apply("&x&F&B&A&1&0&7&lA&x&F&6&A&4&0&D&ln&x&F&0&A&8&1&2&lc&x&E&B&A&B&1&8&li&x&E&5&A&F&1&D&le&x&E&0&B&2&2&3&ln&x&D&B&B&5&2&9&lt &x&D&5&B&9&2&E&lB&x&D&0&B&C&3&4&lo&x&C&A&C&0&3&9&ln&x&C&5&C&3&3&F&le");
	private List<String> lore = ChatUtils.apply(Arrays.asList(new String[] {
			"",
			"&6&oThe bone shakes, dark magics long since",
			"&6&oforgotten barely containing unbridled rage.",
			"",
			"&7 * &cContained: %boss%",
			"",
			"&7( Right-click to spawn this boss! &7)"
	}));
	
	public ItemStack getPFItem() {
		return this.item;
	}
	
	public AbstractEntitySpawner(String id, AbstractPFEntity entity) {
		super(id);
		
		ItemMeta meta = this.item.getItemMeta();
		meta.setItemName(name);
		meta.setDisplayName(name);
		meta.setLore(apply_tags(this.lore));
		this.item.setItemMeta(meta);
		this.entity = entity;
	}

	protected abstract List<String> apply_tags(List<String> toApply);

	public abstract void spawnEntity(AbstractEntitySpawner spawner, ItemStack used, Player who_used, Location at);
	
	@Override
	public void onItemUse(Player player, ItemStack item, PFItemType rcob, Block on) {
		this.spawnEntity(this, item, player, on.getLocation());
	};
	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return true;
	};
	
	
	@Override
	public PFItemType getType() {
		return PFItemType.RIGHT_CLICK_BLOCK;
	}
	
}
