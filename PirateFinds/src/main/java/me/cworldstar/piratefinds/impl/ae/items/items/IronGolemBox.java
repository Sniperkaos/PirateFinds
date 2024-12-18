package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.lootbox.LootboxReward;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward.LootboxRewardType;
import me.cworldstar.piratefinds.impl.ui.test.LootboxUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class IronGolemBox extends AbstractLootBox {

	public static String CHANCE_VERY_LOW = ChatUtils.apply("&c&lCHANCE: &7Very low.");
	public static String CHANCE_LOW = ChatUtils.apply("&c&lCHANCE: &7Low.");
	public static String CHANCE_NORMAL = ChatUtils.apply("&c&lCHANCE: &7Average.");
	public static String CHANCE_HIGH = ChatUtils.apply("&c&lCHANCE: &7High.");
	public static String CHANCE_VERY_HIGH = ChatUtils.apply("&c&lCHANCE: &7Very High.");

	private static String[] DEFAULT_LORE = new String[] {
			""
	};
	
	public IronGolemBox() {
		super("IRON_GOLEM_BOX");
	}
	
	private static ItemStack makeMoneyPlaceholder(ItemStack i, int amount) {
		ItemMeta fk_meta = i.getItemMeta();
		fk_meta.setItemName(ChatUtils.apply("&c&l" + Integer.toString(amount) + "$"));
		List<String> lore = Arrays.asList(new String[] {
				
		});
		fk_meta.setLore(lore);
		i.setItemMeta(fk_meta);
		return i;
	}
	
	private static ItemStack makeMoneyPlaceholder(Material material, int amount) {
		ItemStack i = new ItemStack(material);
		ItemMeta fk_meta = i.getItemMeta();
		fk_meta.setItemName(ChatUtils.apply("&c&l" + Integer.toString(amount) + "$"));
		List<String> lore = Arrays.asList(new String[] {
				
		});
		fk_meta.setLore(lore);
		i.setItemMeta(fk_meta);
		return i;
	}
	
	public ItemStack getPFItem() {
		return item;
	}
	
	private static ItemStack makePlaceholder(ItemStack i, String name, String[] lore) {
		ItemMeta fk_meta = i.getItemMeta();
		fk_meta.setItemName(ChatUtils.apply(name));
		fk_meta.setLore(Arrays.asList(lore));
		i.setItemMeta(fk_meta);
		return i;
	}
	
	private static ItemStack makePlaceholder(Material material, String name, String[] lore) {
		ItemStack i = new ItemStack(material);
		ItemMeta fk_meta = i.getItemMeta();
		fk_meta.setItemName(ChatUtils.apply(name));
		fk_meta.setLore(Arrays.asList(lore));
		i.setItemMeta(fk_meta);
		return i;
	}
	
	public final String pf_item_id = "MOB_LOOTBOX";
	
	@Override
	public void onLoad() {
		this.setItemFlavor("&f&lIron Golem", List.of(new String[] {
				"&7Dropped from defeating iron golems,",
				"&7 this box contains rewards."
		}));
		
		LootboxReward<String> reward = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 1000"}), LootboxRewardType.COMMAND);
		reward.setAmount(1);
		reward.setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.PAPER), 1000));
		
		LootboxReward<String> two_fifty = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 2000"}), LootboxRewardType.COMMAND);
		two_fifty.setAmount(1);
		two_fifty.setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.MAP), 2000));
		
		LootboxReward<String> one_hundred_k = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 3000"}), LootboxRewardType.COMMAND);
		one_hundred_k.setAmount(1);
		one_hundred_k.setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.GOLD_NUGGET), 3000));
		
		LootboxReward<String> fiftyk_reward = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 4000"}), LootboxRewardType.COMMAND);
		fiftyk_reward.setAmount(1);
		fiftyk_reward.setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.GOLD_INGOT), 4000));

		
		LootboxReward<String> two_hundred_k = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 10000"}), LootboxRewardType.COMMAND);
		two_hundred_k.setAmount(1);
		two_hundred_k.setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.GOLD_BLOCK), 10000));

		
		LootboxReward<String> soul_book = new LootboxReward<String>(List.of(new String[] {"ae givercbook UNIQUE %player_name% 1"}), LootboxRewardType.COMMAND);
		soul_book.setAmount(8);
		soul_book.setPlaceholder(makePlaceholder(new ItemStack(Material.ENCHANTED_BOOK), "&a&lUnique Enchantment Book", DEFAULT_LORE));
		soul_book.setChance(75);
		
		LootboxReward<String> godlike_book = new LootboxReward<String>(List.of(new String[] {"ae givercbook SIMPLE %player_name% 1"}), LootboxRewardType.COMMAND);
		godlike_book.setAmount(24);
		godlike_book.setPlaceholder(makePlaceholder(new ItemStack(Material.ENCHANTED_BOOK), "&7&lSimple Enchantment Book", DEFAULT_LORE));
		godlike_book.setChance(100);
		
		LootboxReward<String> orb3 = new LootboxReward<String>(List.of(new String[] {"ae givercbook UNIQUE %player_name% 1"}), LootboxRewardType.COMMAND);
		orb3.setAmount(12);
		orb3.setPlaceholder(makePlaceholder(new ItemStack(Material.ENCHANTED_BOOK), "&a&lUnique Enchantment Book", DEFAULT_LORE));
		orb3.setChance(100);
		
		LootboxReward<String> orb4 = new LootboxReward<String>(List.of(new String[] {"ae givercbook ELITE %player_name% 1"}), LootboxRewardType.COMMAND);
		orb4.setAmount(6);
		orb4.setPlaceholder(makePlaceholder(new ItemStack(Material.ENCHANTED_BOOK), "&b&lElite Enchantment Book", DEFAULT_LORE));
		orb4.setChance(50);
		
		LootboxReward<String> orb7 = new LootboxReward<String>(List.of(new String[] {"pf givepfitem %player_name% Reinforcement 1"}), LootboxRewardType.COMMAND);
		orb7.setAmount(1);
		orb7.setPlaceholder(makePlaceholder(new ItemStack(Material.NETHERITE_UPGRADE_SMITHING_TEMPLATE), "&f&lReinforcement", DEFAULT_LORE));
		orb7.setChance(10);
		
		LootboxReward<String> orb5 = new LootboxReward<String>(List.of(new String[] {"pf givepfitem %player_name% UnsealScroll 1"}), LootboxRewardType.COMMAND);
		orb5.setAmount(1);
		orb5.setPlaceholder(makePlaceholder(new ItemStack(Material.PAPER), "&f&lUnlock Scroll", DEFAULT_LORE));
		orb5.setChance(50);
		
		LootboxReward<String> exp1 = new LootboxReward<String>(List.of(new String[] {"exp %player_name% add 25L"}), LootboxRewardType.COMMAND);
		exp1.setAmount(1);
		exp1.setPlaceholder(makePlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE), "&e&l25 Levels", DEFAULT_LORE));
		
		LootboxReward<String> exp2 = new LootboxReward<String>(List.of(new String[] {"exp  %player_name% add 75L"}), LootboxRewardType.COMMAND);
		exp2.setAmount(1);
		exp2.setPlaceholder(makePlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE), "&e&l75 Levels", DEFAULT_LORE));
		
		LootboxReward<String> exp3 = new LootboxReward<String>(List.of(new String[] {"exp  %player_name% add 100L"}), LootboxRewardType.COMMAND);
		exp3.setAmount(1);
		exp3.setPlaceholder(makePlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE), "&e&l100 Levels", DEFAULT_LORE));
		
		this.addReward(reward);
		this.addReward(two_fifty);
		this.addReward(two_hundred_k);
		this.addReward(one_hundred_k);
		this.addReward(fiftyk_reward);
		this.addReward(soul_book); 
		this.addReward(godlike_book);
		this.addReward(orb3);
		this.addReward(orb4);
		this.addReward(orb5);
		this.addReward(orb7);
		this.addReward(exp1);
		this.addReward(exp2);
		this.addReward(exp3);
	}

	@Override
	public void onBuild() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void use(Player p) {
		new LootboxUI(p, 5, this.getRewards(), this.item).open();
	}

}
