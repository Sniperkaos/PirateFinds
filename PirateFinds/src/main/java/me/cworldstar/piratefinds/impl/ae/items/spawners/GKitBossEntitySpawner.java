package me.cworldstar.piratefinds.impl.ae.items.spawners;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.impl.ae.items.items.AbstractEntitySpawner;
import me.cworldstar.piratefinds.impl.entities.PFEntities;

public class GKitBossEntitySpawner extends AbstractEntitySpawner {

	public GKitBossEntitySpawner(String id) {
		super(id, PFEntities.get("GKIT_SPAWNER"));
	}

	@Override
	protected List<String> apply_tags(List<String> toApply) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void spawnEntity(AbstractEntitySpawner spawner, ItemStack used, Player who_used, Location at) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ItemStack build() {
		// TODO Auto-generated method stub
		return null;
	}

}
