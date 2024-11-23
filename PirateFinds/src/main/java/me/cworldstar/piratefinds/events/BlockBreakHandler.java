package me.cworldstar.piratefinds.events;

import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;
import me.cworldstar.piratefinds.PirateFinds;

public class BlockBreakHandler implements Listener {
	public BlockBreakHandler() {
		PirateFinds.getThisPlugin().getServer().getPluginManager().registerEvents(this, PirateFinds.getThisPlugin());
	}
	
	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent e) {
		if (e.getBlock() instanceof CreatureSpawner) {
			PirateFinds.log("CreatureSpawner dropping items.");
			Player who_broke = e.getPlayer();
			ItemStack item_in_hand = who_broke.getInventory().getItemInMainHand();
			if(!item_in_hand.containsEnchantment(Enchantment.SILK_TOUCH)) {
				PirateFinds.log("It wasn't broken with a silk touch tool.");
				return;
			}
			ItemStack spawner = new ItemStack(Material.SPAWNER);
			BlockStateMeta meta = (BlockStateMeta) spawner.getItemMeta();
			CreatureSpawner c_spawner = (CreatureSpawner) e.getBlock().getState();
			meta.setBlockState(c_spawner);
			spawner.setItemMeta(meta);
			who_broke.getWorld().dropItem(e.getBlock().getLocation(), spawner);
		}
	}
}
