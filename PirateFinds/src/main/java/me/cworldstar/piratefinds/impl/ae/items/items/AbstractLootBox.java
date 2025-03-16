package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.items.items.boxes.ConfigLootBox;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward.LootboxRewardType;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

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
	
	
	@Override
	public List<PFItemType> getTypes() {
		return Arrays.asList(new PFItemType[] {
				PFItemType.RIGHT_CLICK,
				PFItemType.DROP_ITEM
		});
	}
	
	
	public AbstractLootBox(String id) {
		super(id);
		this.onLoad();
	}
	
	public abstract void onLoad();
	
	/**
	 * Call this before addReward. It applies the RANDOM_LOOT lore line as well.
	 * @param name
	 */
	
	public void setItemFlavor(String name, List<String> lore) {
		ItemMeta meta = this.item.getItemMeta();
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
		this.item.setItemMeta(meta);
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
		ItemMeta meta = this.item.getItemMeta();
		ItemMeta pmeta = reward.getPlaceholder().getItemMeta();
		if(pmeta == null) {
			return;
		}
		List<String> lore = meta.getLore();
		lore.add(ChatUtils.apply("&f&l* " + Integer.toString(reward.getAmount()) + "x " + pmeta.getItemName()));
		meta.setLore(lore);
		this.item.setItemMeta(meta);
	}
	
	public ArrayList<LootboxReward<?>> getRewards() {
		return this.rewards;
	}
	
	
	public static ConfigLootBox buildFromConfig(ConfigurationSection section) {
		
		ConfigurationSection itemSection = section.getConfigurationSection("item");
		ConfigurationSection rewardsSection = section.getConfigurationSection("rewards");
		
		String displayName = itemSection.getString("display-name");
		List<String> lore = itemSection.getStringList("lore");
		boolean glowing = itemSection.getBoolean("glowing");
		String material = itemSection.getString("material");
		
		
		
		ItemStack theItem = new ItemStack(Material.valueOf(material));
		

		
		if(theItem.getType().equals(Material.PLAYER_HEAD)) {
			theItem = SkullCreator.itemFromBase64(itemSection.getString("skull_id"));
		}
		
		ItemMeta theItemMeta = theItem.getItemMeta();
		
		Integer usages = section.getInt("clicks");
		if(usages == 0) {
			usages = 4;
		}
		
		String s = usages.toString();
		
		theItemMeta.setItemName(ChatUtils.apply("&f&lLootbox: " + section.getName()));
		ArrayList<String> new_lore = new ArrayList<String>();
		new_lore.add(" ");
		ArrayList<String> mutableList = new ArrayList<String>();
		mutableList.addAll(lore);
		mutableList.replaceAll(loreLine -> ChatUtils.apply(loreLine));
		new_lore.addAll(mutableList);
		new_lore.add(" ");
		new_lore.add(ChatUtils.apply("&f&lRandom Loot: (&7%usages% items&f&l)"));
		
		theItemMeta.setEnchantmentGlintOverride(glowing);
		new_lore.replaceAll(line -> ChatUtils.apply(line));
		new_lore.replaceAll(line -> line.replace("%usages%", s));
		
		theItemMeta.setItemName(ChatUtils.apply(displayName));
		PersistentDataContainer container = theItemMeta.getPersistentDataContainer();
		container.set(PFItemClass.PF_ITEM_KEY, PersistentDataType.STRING, "PF_BOX_"+section.getName());


		
		
		ArrayList<LootboxReward<?>> theRewards = new ArrayList<LootboxReward<?>>();
		for(String reward_id : rewardsSection.getKeys(false)) {
			ConfigurationSection reward = rewardsSection.getConfigurationSection(reward_id);
			ConfigurationSection placeholder = reward.getConfigurationSection("placeholder");
		
			
			String placeholderMaterial = placeholder.getString("material");
			String placeholderName = placeholder.getString("display-name");
			boolean placeholderGlowing = placeholder.getBoolean("glowing");
			

			
			//LootboxRewardType type = LootboxRewardType.valueOf(reward.getString("type"));
			int amount = reward.getInt("amount");
			String command = reward.getString("argument");
			boolean broadcast = reward.getBoolean("broadcast");
			String broadcastMessage = reward.getString("broadcast-message");
			int chance = reward.getInt("chance");
			
			ItemStack placeholderItem = new ItemStack(Material.valueOf(placeholderMaterial));
			if(placeholderItem.getType().equals(Material.PLAYER_HEAD)) {
				placeholderItem = SkullCreator.itemFromBase64(placeholder.getString("skull_id"));
			}
			ItemMeta placeholderItemMeta = placeholderItem.getItemMeta();
			placeholderItemMeta.setItemName(ChatUtils.apply(placeholderName));
			placeholderItemMeta.setEnchantmentGlintOverride(placeholderGlowing);
			placeholderItem.setItemMeta(placeholderItemMeta);
			
			
			
			LootboxReward<?> lreward = null;
			
			switch(LootboxRewardType.valueOf(reward.getString("type"))) {
				case COMMAND:
					lreward = new LootboxReward<String>(Arrays.asList(new String[] {
							command
					}), LootboxRewardType.COMMAND).setAmount(amount).setChance(chance).setPlaceholder(placeholderItem);
					break;
				case ITEM:
					
					ConfigurationSection itemReward = reward.getConfigurationSection("item");
					Material rewardMaterial = Material.valueOf(itemReward.getString("material"));
					ItemStack rewardItem = new ItemStack(rewardMaterial);
					if(rewardMaterial.equals(Material.PLAYER_HEAD)) {
						rewardItem = SkullCreator.itemFromBase64(itemReward.getString("skull_id"));
					}
					
					ItemMeta rewardItemMeta = rewardItem.getItemMeta();
					rewardItemMeta.setDisplayName(itemReward.getString("display-name"));
					rewardItemMeta.setItemName(itemReward.getString("display-name"));
					rewardItem.setItemMeta(rewardItemMeta);
					
					lreward = new LootboxReward<ItemStack>(Arrays.asList(new ItemStack[] {
							rewardItem
					}), LootboxRewardType.ITEM).setAmount(amount).setChance(chance).setPlaceholder(placeholderItem);
					break;
				default:
					break;
			}
			
			
			if(reward.contains("broadcast")) {
				lreward.setBroadcast(broadcast);
			}
			
			if(reward.contains("broadcast-message")) {
				lreward.setBroadcastMessage(broadcastMessage);
			}
			
			
			new_lore.add(ChatUtils.apply("&f&l* " + Integer.toString(lreward.getAmount()) + "x " + placeholderItemMeta.getItemName()));
			
			theRewards.add(lreward);
			
		}
		
		
		theItemMeta.setLore(new_lore);
		theItem.setItemMeta(theItemMeta);
		
		ConfigLootBox lootBox  = new ConfigLootBox(theItem, theRewards, section.getName(), usages);
		PirateFinds.log(lootBox.toString());
		PirateFinds.log(lootBox.getPFItem().toString());
		PirateFinds.log(theItem.toString());
		PFItemClass.registerItem(lootBox);
		
		return lootBox;
	}
	
	
	
	
	@Override
	public ItemStack build() {
		onBuild();
		ItemStack citem = this.item.clone();
		this.make(citem);
		return citem;
	}
	
	abstract public void onBuild();
	abstract public void use(Player p);
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type, PlayerDropItemEvent e) {
		if(type == PFItemType.DROP_ITEM) {
			e.setCancelled(true);
			return;
		}
		use(p);
	}
	
	@Override
	public void onItemUse(Player p, ItemStack on, PFItemType type) {
		use(p);
	}

	@Override
	public boolean checkExpend(Player p, ItemStack on) {
		return false;
	}
	
}
