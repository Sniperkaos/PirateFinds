package me.cworldstar.piratefinds.impl.blocks;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.events.TickerTickEvent;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;

public abstract class PFBlock implements Listener {

	public static enum PFBlockType {
		
	}
	
	private static HashMap<String, PFBlock> blocks = new HashMap<String, PFBlock>();
	
	public static void registerBlock(PFBlock block) {
		blocks.putIfAbsent(block.blockId, block);
	}
	
	
	private UUID uuid;
	private PFBlockData data;
	private Location loc;
	private Block block;
	private String blockId;
	
	public PFBlock(String blockID, Location l, Block block) {
		this.build(block);
		this.blockId = blockID;
		this.uuid = UUID.randomUUID();
		this.loc = l;
		this.block = block;
		this.data = new PFBlockData();
		PirateFinds.registerListener(this);
		PFBlock.registerBlock(this);
		onLoad();
	}
	
	public PFBlock(String blockID, Location l, Block block, UUID blockUUID) {
		this.build(block);
		this.blockId = blockID;
		this.uuid = blockUUID;
		this.loc = l;
		this.block = block;
		this.data = new PFBlockData();
		PirateFinds.registerListener(this);
		PFBlock.registerBlock(this);
		onLoad();
	}
	
	protected abstract void build(Block b);
	
	protected void tick() {
		
	}
	
	public void onRightClick(PlayerInteractEvent e) {
		
	}
	
	protected boolean onBreak(BlockBreakEvent e) {
		return true;
	}
	
	@EventHandler
	public void onTick(TickerTickEvent e) {
		this.tick();
	}
	
	@EventHandler
	public void onBlockBreak(BlockBreakEvent e) {
		if(e.getBlock().getLocation().equals(this.loc)) {
			boolean cancelled = this.onBreak(e);
			if(cancelled) {
				e.setCancelled(true);
				return;
			}
			e.getBlock().setType(Material.AIR);
			
			ItemStack item = PFItemClass.getItem(this.blockId).getPFItem();
			loc.getWorld().dropItem(loc, item);
			PirateFinds.getMadeBlockConfig().removeBlockAt(loc);
			
			BlockBreakEvent.getHandlerList().unregister(this);
			PlayerInteractEvent.getHandlerList().unregister(this);
			TickerTickEvent.getHandlerList().unregister(this);
		}
	}
	

	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if(e.getAction() != Action.RIGHT_CLICK_BLOCK) return;
		if(e.getClickedBlock().getLocation().equals(this.loc)) {
			onRightClick(e);
		}
	}
	
	public abstract PFBlockType getPFBlock();
	
	public Block getBlock() {
		return block;
	}
	
	public Location getLocation() {
		return loc;
	}

	public String getUUID() {
		return this.uuid.toString();
	}
	
	public String getPFBlockId() {
		return this.blockId;
	}

	public PFBlockData getPFData() {
		return this.data;
	}

	public static PFBlock getBlockById(String string) {
		return null;
	}


	public void setData(PFBlockData data) {
		this.data = data;
		
	}

	protected abstract boolean onLoad();
}
