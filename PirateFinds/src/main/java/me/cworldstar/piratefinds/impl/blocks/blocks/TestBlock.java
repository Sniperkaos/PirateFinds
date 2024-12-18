package me.cworldstar.piratefinds.impl.blocks.blocks;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import de.oliver.fancyholograms.api.hologram.Hologram;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.blocks.PFBlock;
import me.cworldstar.piratefinds.impl.blocks.PFBlockData;
import me.cworldstar.piratefinds.impl.ui.test.RepairMasterUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class TestBlock extends PFBlock {

	private PFBlockData data;
	
	public TestBlock(Location l, Block block) {
		super("TestBlock", l, block);
		data = new PFBlockData();
	}
	
	public TestBlock(Location l, Block block, UUID id) {
		super("TestBlock", l, block, id);
		data = new PFBlockData();
	}
	
	
	@Nullable
	public Hologram getHologram() {
		Optional<Hologram> hologram = PirateFinds.getHologramCompat().getHologramManager().getHologram(getUUID().toString());
		if(hologram.isPresent()) {
			return hologram.get();
		}
		return null;
	}

	@Override
	protected void build(Block b) {
		
	}
	
	@Override
	protected boolean onLoad() {
		PirateFinds.getHologramCompat().create_hologram(getUUID().toString(), getLocation().clone().add(0.5, 2.0, 0.5), List.of(new String[] {
				"&6&lTest Block!"
		}));
		return false;
	}
	
	@Override
	protected boolean onBreak(BlockBreakEvent e) {
		if(e.getPlayer().isSneaking()) {
			Hologram hologram = getHologram();
			if(hologram == null) return false;
			PirateFinds.getHologramCompat().getHologramManager().removeHologram(hologram);
			return false;
		}
		e.getPlayer().sendMessage(ChatUtils.createBroadcast("&7You must be shifting to break this block!"));
		return true;
	}

	@Override
	public PFBlockType getPFBlock() {
		return null;
	}
	
	@Override
	public void onRightClick(PlayerInteractEvent e) {
		new RepairMasterUI(e.getPlayer()).open();
	}

}
