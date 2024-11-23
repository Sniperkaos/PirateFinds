package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public abstract class AbstractLootBox extends AbstractPFItem {
	public ItemStack item = new ItemStack(Material.ENDER_CHEST);
	private PFItemType type = PFItemType.RIGHT_CLICK;
	

	private String lootboxName;
	private ArrayList<LootboxReward<?>> rewards = new ArrayList<LootboxReward<?>>();
	
	@Override
	public PFItemType getType() {
		// TODO Auto-generated method stub
		return type;
	}
	
	
	public AbstractLootBox() {
		this.onLoad();
	}
	
	public abstract void onLoad();
	
	/**
	 * Call this before addReward. It applies the RANDOM_LOOT lore line as well.
	 * @param name
	 */
	
	public void setItemFlavor(String name, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		meta.setItemName(ChatUtils.apply("&f&lLootbox: " + name));
		ArrayList<String> new_lore = new ArrayList<String>();
		new_lore.add(" ");
		ArrayList<String> mutableList = new ArrayList<String>();
		mutableList.addAll(lore);
		mutableList.replaceAll(loreLine -> ChatUtils.apply(loreLine));
		new_lore.addAll(mutableList);
		new_lore.add(" ");
		new_lore.add(ChatUtils.apply("&f&lRandom Loot: (&75 items&f&l)"));
		meta.setLore(new_lore);
		item.setItemMeta(meta);
	}
	
	public void addReward(LootboxReward<?> reward, String rewardDisplay) {
		this.rewards.add(reward);
		ItemMeta meta = item.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add(ChatUtils.apply("&f&l* " + Integer.toString(reward.getAmount()) + "x " + rewardDisplay));
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	
	public ArrayList<LootboxReward<?>> getRewards() {
		return this.rewards;
	}
	
	
	public ArrayList<LootboxReward<?>> buildFromConfig() {
		//TODO: create
		return null;
	}
	
	@Override
	public ItemStack build() {
		onBuild();
		return item.clone();
	}
	
	abstract public void onBuild();
	abstract public void use(Player p);
	
	@Override
	public void onItemUse(Player p, ItemStack on) {
		use(p);
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return true;
	}
	
}
