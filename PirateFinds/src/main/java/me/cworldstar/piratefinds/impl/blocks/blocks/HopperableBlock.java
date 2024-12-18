package me.cworldstar.piratefinds.impl.blocks.blocks;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Container;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import me.cworldstar.piratefinds.impl.blocks.PFBlock;
import me.cworldstar.piratefinds.impl.blocks.PFBlockData;

public abstract class HopperableBlock extends PFBlock implements Listener, Container {

	private Inventory blockInventory;
	private PFBlockData blockData;
	
	public HopperableBlock(String blockID, Location l, Block block) {
		super(blockID, l, block);
	}
	
	public HopperableBlock(String blockID, Location l, Block block, UUID uuid) {
		super(blockID, l, block, uuid);
	}


	@Override
	protected void build(Block b) {
		
	}

	@Override
	public PFBlockType getPFBlock() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean onLoad() {
		return false;
	}
	
	

}
