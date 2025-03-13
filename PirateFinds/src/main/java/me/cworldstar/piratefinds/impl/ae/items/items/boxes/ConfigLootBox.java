package me.cworldstar.piratefinds.impl.ae.items.items.boxes;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.ae.items.items.AbstractLootBox;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward;
import me.cworldstar.piratefinds.impl.ui.test.LootboxUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class ConfigLootBox extends AbstractLootBox {

	public ItemStack cfg_item;
	private ArrayList<LootboxReward<?>> rewards = new ArrayList<LootboxReward<?>>();
	
	public ConfigLootBox(ItemStack item, ArrayList<LootboxReward<?>> rewards, String id) {
		super(id);
		
		rewards.forEach((LootboxReward<?> reward) -> {
			reward.setBoxName(id);
		});
		
		this.cfg_item = item;
		this.rewards.addAll(rewards);
	}
	
	@Override
	public ItemStack build() {
		return this.cfg_item.clone();
	}
	
	@Override
	public String getPFItemID() {
		return this.pf_item_id;
	}
	
	public void addReward(LootboxReward<?> reward, String rewardDisplay) {
		this.rewards.add(reward);
		ItemMeta meta = this.item.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add(ChatUtils.apply("&f&l* " + Integer.toString(reward.getAmount()) + "x " + rewardDisplay));
		meta.setLore(lore);
		this.item.setItemMeta(meta);
	}
	
	
	
	public void addReward(LootboxReward<?> reward) {
		this.rewards.add(reward);
		ItemMeta meta = this.cfg_item.getItemMeta();
		List<String> lore = meta.getLore();
		lore.add(ChatUtils.apply("&f&l* " + Integer.toString(reward.getAmount()) + "x " + reward.getPlaceholder().getItemMeta().getItemName()));
		meta.setLore(lore);
		this.cfg_item.setItemMeta(meta);
	}
	
	public ArrayList<LootboxReward<?>> getRewards() {
		return rewards;
	}
	
	@Override
	public ItemStack getPFItem() {
		return this.cfg_item;
	}

	@Override
	public void onLoad() {
		
	}

	@Override
	public void onBuild() {
		
	}

	@Override
	public void use(Player p) {
		new LootboxUI(p, 5, this.getRewards(), this.cfg_item).open();
	}

}
