package me.cworldstar.piratefinds.impl.ae.items.items.masks.effects;

import java.util.Collection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import com.jeff_media.morepersistentdatatypes.DataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.events.TickerTickEvent;

public class MaskListener implements Listener {
	public MaskListener() {
		PirateFinds.registerListener(this);
	}
	
	@EventHandler
	public void onPFTick(TickerTickEvent e) {
		Collection<? extends Player> players = PirateFinds.getServerStatic().getOnlinePlayers();
		players.forEach((Player player) -> {
			for(ItemStack item : player.getInventory().getArmorContents()) {
				if(item == null) continue;
				ItemMeta meta = item.getItemMeta();
				if(meta == null) continue;
				PersistentDataContainer container = meta.getPersistentDataContainer();
				String[] maskEffects = container.get(PirateFinds.createKey("MASK_EFFECT"), DataType.STRING_ARRAY);
				if(maskEffects == null) continue;
				for(String maskEffect : maskEffects) {
					AbstractMaskEffect AMEffect = MaskEffects.getMaskEffect(maskEffect);
					AMEffect.run(player, item);
				}
			}
			
		});
	}
}
