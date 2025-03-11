package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Waterlogged;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.lands.LandsImpl;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class InfiniteBucket extends AbstractPFItem {

	public static ItemStack INFINITE_WATER_BUCKET = new ItemStack(Material.WATER_BUCKET);
	private static final String INFINITE_WATER_BUCKET_NAME = "&b&lInfinite Bucket";
	
	static {
		ItemMeta meta = INFINITE_WATER_BUCKET.getItemMeta();
		meta.setEnchantmentGlintOverride(true);
		meta.setItemName(ChatUtils.apply(INFINITE_WATER_BUCKET_NAME));
		meta.setDisplayName(ChatUtils.apply(INFINITE_WATER_BUCKET_NAME));
		meta.setLore(ChatUtils.apply(Arrays.asList(new String[] {
			"",
			"&b&oThis water bucket seems to be",
			"&b&oinfinite in size. How? No idea.",
			"",
			"&7&o( &e&nRight-click&b to place water. &7&o)"
		})));
		
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "infinite_bucket");
		INFINITE_WATER_BUCKET.setItemMeta(meta);
	}
	
	public InfiniteBucket() {
		super("infinite_bucket");
	}

	@Override
	public ItemStack build() {
		
		ItemStack clone = INFINITE_WATER_BUCKET.clone();
		ItemMeta meta = clone.getItemMeta();
		meta.setItemName(ChatUtils.apply(INFINITE_WATER_BUCKET_NAME));
		meta.setDisplayName(ChatUtils.apply(INFINITE_WATER_BUCKET_NAME));
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "infinite_bucket");
		
		return clone;
	}
	
	public ItemStack getPFItem() {
		return INFINITE_WATER_BUCKET;
	}

	@Override
	public void onItemUse(Player player, ItemStack maybeBucket, PFItemType bucketUsed, PlayerBucketEmptyEvent e) {
		
		// if a different plugin cancelled then we dont want to overwrite it
		if(e.isCancelled()) {
			return;
		}
		
		Optional<LandsImpl> loaded = PirateFinds.getLandsImpl();
		
		if(loaded.isPresent()) {
			// do the check
			LandsImpl impl = loaded.get();
			if(!impl.canPlayerBuildHere(player, e.getBlockClicked().getLocation(), maybeBucket)) {
				e.setCancelled(true);
				return;
			}
		}
		// otherwise, don't worry about it
		
		Block clicked = e.getBlockClicked();
		BlockFace face = e.getBlockFace();
		
		Block to_replace = clicked.getRelative(face);
		if(clicked.getBlockData() instanceof Waterlogged) {
			Waterlogged data = (Waterlogged) clicked.getBlockData();
			data.setWaterlogged(true);
			clicked.setBlockData(data);
			
			BlockState state = clicked.getState();
			state.update();
			
		} else if (
				to_replace.getBlockData().getMaterial().equals(Material.AIR) ||
				to_replace.getBlockData().getMaterial().equals(Material.WATER) ||
				to_replace.getBlockData().getMaterial().equals(Material.TALL_GRASS) || 
				to_replace.getBlockData().getMaterial().equals(Material.SHORT_GRASS) || 
				to_replace.getBlockData().getMaterial().equals(Material.TORCH)
			) {
			to_replace.setType(Material.WATER);
		}
		
		
		player.playSound(to_replace.getLocation(), Sound.ITEM_BUCKET_EMPTY, 1.0f, 1.0f);
		e.setCancelled(true);
	};

	
	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return true;
	}

	@Override
	public PFItemType getType() {
		return PFItemType.BUCKET_USED;
	}
}
