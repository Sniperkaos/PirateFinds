package me.cworldstar.piratefinds.impl.drop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class DropImpl {

	private Player player;
	private boolean pressed_q = false;
	private ItemStack last_item;
	private BukkitTask delay;
	private boolean enabled = true;
	
	private final String DROP_ITEM_STRING = ChatUtils.createBroadcast("&7(&c&l!&7) &c&lHey!&r &7Did you really mean to drop that? Try again if you did!");
	private final String DROP_ITEM_STRING_CONFIRMED = ChatUtils.createBroadcast("&7(&c&l!&7) &aDrop successful!");
	
	
	public DropImpl(Player player) {
		this.player = player;
	}
	
	public void toggleQPress() {
		this.pressed_q = true;
	}
	
	public boolean wasQPressed() {
		return this.pressed_q;
	}
	
	public boolean drop() {
		
		if(!this.enabled) return false;
		
		DropImpl self = this;
		
		if(last_item!=null) {
			if(!(player.getInventory().getItemInMainHand().isSimilar(last_item))) {
				reset();
				return drop();
			}
		}

		if(this.pressed_q == true) {
            player.sendMessage(DROP_ITEM_STRING_CONFIRMED);
			reset();
			return false;
		}
		
		delay = new BukkitRunnable() {
			@Override
			public void run() {
				if(self.pressed_q) {
					self.player.sendMessage(ChatUtils.createBroadcast("You did not confirm, so the drop has been canceled."));
					reset();
				}
			}
			
		}.runTaskLater(PirateFinds.getThisPlugin(), 100L);
		
		this.pressed_q = true;
		last_item = player.getInventory().getItemInMainHand();
		
        player.sendMessage(DROP_ITEM_STRING);
		
		return true;
	}
	
	public void reset() {
		this.pressed_q = false;
		this.last_item = null;
		if(delay != null) {
			delay.cancel();
			delay = null;
		}
	}

	public void setEnabled(boolean bool) {
		this.enabled = bool;
	}
	
	public boolean isEnabled() {
		return enabled;
	}

}
