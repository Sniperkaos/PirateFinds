package me.cworldstar.piratefinds.impl.lands;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.angeschossen.lands.api.LandsIntegration;
import me.angeschossen.lands.api.flags.type.Flags;
import me.angeschossen.lands.api.land.LandWorld;
import me.cworldstar.piratefinds.PirateFinds;

public class LandsImpl {
	
	private LandsIntegration api;
	private boolean enabled = false;
	
	public LandsImpl() {
		enabled = true;
		api = LandsIntegration.of(PirateFinds.getThisPlugin());
	}
	
	public boolean canPlayerBuildHere(Player p, Location l, ItemStack i) {		
		if(!enabled) {
			return true;
		}		

		LandWorld world = api.getWorld(l.getWorld());

		if(world != null) {
			return world.hasRoleFlag(api.getLandPlayer(p.getUniqueId()), l, Flags.BLOCK_PLACE, i.getType(), true);
		}
		return true;
	}
}
