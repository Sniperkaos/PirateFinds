package me.cworldstar.piratefinds.impl.ae.effects;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Modules.Worth.WorthItem;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.vault.VaultImpl;
import net.advancedplugins.ae.impl.effects.effects.actions.execution.ExecutionTask;
import net.advancedplugins.ae.impl.effects.effects.actions.handlers.DropsCollection;
import net.advancedplugins.ae.impl.effects.effects.actions.handlers.DropsHandler;
import net.advancedplugins.ae.impl.effects.effects.effects.AdvancedEffect;
import net.milkbowl.vault.economy.Economy;

public class AutoSell extends AdvancedEffect {
	public AutoSell(JavaPlugin arg0) {
		super(arg0, "AUTO_SELL");
		this.addArgument(1, Double.class); // this is the efficiency
	}
	
	private static List<Material> AUTO_SELL_MATERIALS = Arrays.asList(new Material[] {

	});
	
	static {
		for(Material mat : Material.values()) {
			if(mat.toString().contains("ORE")) {
				AUTO_SELL_MATERIALS.add(mat);
			} else if(mat.toString().contains("BLOCK")) {
				AUTO_SELL_MATERIALS.add(mat);
			}
		}
	}
	
	@Override
	public boolean executeEffect(ExecutionTask task, LivingEntity entity, String[] args) {
		Event event = task.getBuilder()
			.getEvent();
		if(event instanceof BlockBreakEvent) {
			BlockBreakEvent e = ((BlockBreakEvent) event);
			DropsHandler handler = task.getBuilder().getDrops();
			DropsCollection items = handler.getDrops(e.getBlock());
			List<ItemStack> itemList = items.getItems();
			
			Optional<Economy> econ = VaultImpl.getEconomy();
			if(econ.isPresent()) {
				Economy econ2 = econ.get();
				for(ItemStack item : itemList.toArray(new ItemStack[0])) {
					if(PirateFinds.getMadeBlockConfig().doesBlockExist(e.getBlock().getLocation())) continue;
					if(!AUTO_SELL_MATERIALS.contains(item.getType())) continue;
					
					
					WorthItem worth = CMI.getInstance().getWorthManager().getWorth(item);
					econ2.depositPlayer(e.getPlayer(), worth.getPlayerSellPrice(item, true, true));
				}
			}
			handler.clearDrops(e.getBlock());
		}
		return true;
	};
	
}
