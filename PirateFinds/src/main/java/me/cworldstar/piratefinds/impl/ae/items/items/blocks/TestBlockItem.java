package me.cworldstar.piratefinds.impl.ae.items.items.blocks;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.blocks.blocks.TestBlock;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class TestBlockItem extends AbstractPFBlockItem {

	public static ItemStack TEST_BLOCK_ITEM = new ItemStack(Material.WHITE_STAINED_GLASS);
	static {
		ItemMeta meta = TEST_BLOCK_ITEM.getItemMeta();
		meta.setItemName(ChatUtils.apply("&c&lTest Block"));
		PersistentDataContainer container = meta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING,  "TEST_BLOCK");
		
		List<String> lore = Arrays.asList(new String[] {
				"",
				"&c&oTest block. You really shouldn't",
				"&c&ohave this unless you're an operator."
		});
		
		lore.replaceAll(loreLine -> ChatUtils.apply(loreLine));
		
		meta.setLore(lore);
		
		TEST_BLOCK_ITEM.setItemMeta(meta);
	}
	
	
	public TestBlockItem() {
		super("TEST_BLOCK_ITEM");
	}

	@Override
	public void blockPlaced(BlockPlaceEvent e) {
		PirateFinds.log("test block placed");
		TestBlock block = new TestBlock(e.getBlock().getLocation(), e.getBlock());
		PirateFinds.log("test block made");
		PirateFinds.getMadeBlockConfig().createBlockAt(e.getBlock().getLocation(), block);
		
	}

	@Override
	public ItemStack getItem() {
		return TEST_BLOCK_ITEM;
	}

}
