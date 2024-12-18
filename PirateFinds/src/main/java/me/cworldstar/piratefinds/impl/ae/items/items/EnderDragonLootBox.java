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

public class EnderDragonLootBox extends AbstractLootBox {

	public static String CHANCE_VERY_LOW = ChatUtils.apply("&c&lCHANCE: &7Very low.");
	public static String CHANCE_LOW = ChatUtils.apply("&c&lCHANCE: &7Low.");
	public static String CHANCE_NORMAL = ChatUtils.apply("&c&lCHANCE: &7Average.");
	public static String CHANCE_HIGH = ChatUtils.apply("&c&lCHANCE: &7High.");
	public static String CHANCE_VERY_HIGH = ChatUtils.apply("&c&lCHANCE: &7Very High.");

	private static String[] DEFAULT_LORE = new String[] {
			""
	};
	
	public EnderDragonLootBox() {
		super("ENDER_DRAGON_LOOTBOX");
	}
	
	private static ItemStack FIVE_HUNDRED_K = new ItemStack(Material.MAP);
	private static ItemStack TWO_HUNDRED_K = new ItemStack(Material.MAP);
	private static ItemStack TWO_FIFTY_K = new ItemStack(Material.PAPER);
	private static ItemStack ONE_HUNDRED_K = new ItemStack(Material.PAPER);
	private static ItemStack FIFTY_K = new ItemStack(Material.PAPER);
	private static ItemStack MASTERY_BOOK = new ItemStack(Material.BOOK);
	private static ItemStack SOUL_BOOK = new ItemStack(Material.BOOK);
	private static ItemStack ARMOR_ORB = new ItemStack(Material.ENDER_EYE);
	private static ItemStack WEAPON_ORB = new ItemStack(Material.ENDER_EYE);
	
	public ItemStack getPFItem() {
		return item;
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
	
	private static ItemStack makePlaceholder(ItemStack i, String name, String[] lore) {
		ItemMeta fk_meta = i.getItemMeta();
		fk_meta.setItemName(ChatUtils.apply(name));
		fk_meta.setLore(Arrays.asList(lore));
		i.setItemMeta(fk_meta);
		return i;
	}
	
	static {
		TWO_HUNDRED_K = makeMoneyPlaceholder(TWO_HUNDRED_K, 100000);
		FIVE_HUNDRED_K = makeMoneyPlaceholder(FIVE_HUNDRED_K, 10000);
		TWO_FIFTY_K = makeMoneyPlaceholder(TWO_FIFTY_K, 25000);
		ONE_HUNDRED_K = makeMoneyPlaceholder(ONE_HUNDRED_K, 30000);
		FIFTY_K = makeMoneyPlaceholder(FIFTY_K, 50000);
		MASTERY_BOOK = makePlaceholder(MASTERY_BOOK, "&6&lLegendary Enchantment Book", new String[] {
				""
		});
		SOUL_BOOK = makePlaceholder(SOUL_BOOK, "&e&lUltimate Enchantment Book", new String[] {
				""
		});
		ARMOR_ORB = makePlaceholder(ARMOR_ORB, "&6&lArmor Enchantment Orb [&718&6&l]", new String[] {
				""
		});
		WEAPON_ORB = makePlaceholder(WEAPON_ORB, "&6&lWeapon Enchantment Orb [&718&6&l]", new String[] {
				
		});
	}
	
	public final String pf_item_id = "ENDER_DRAGON_LOOTBOX";
	
	@Override
	public void onLoad() {
		this.setItemFlavor("&5&lEnder Dragon", List.of(new String[] {
				"&7Dropped from the ender dragon, this box",
				"&7contains long forgotten rewards."
		}));
		
		LootboxReward<String> reward = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 10000"}), LootboxRewardType.COMMAND);
		reward.setAmount(1);
		reward.setPlaceholder(FIVE_HUNDRED_K);
		
		LootboxReward<String> two_fifty = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 25000"}), LootboxRewardType.COMMAND);
		two_fifty.setAmount(1);
		two_fifty.setPlaceholder(TWO_FIFTY_K);
		
		LootboxReward<String> one_hundred_k = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 30000"}), LootboxRewardType.COMMAND);
		one_hundred_k.setAmount(1);
		one_hundred_k.setPlaceholder(ONE_HUNDRED_K);
		
		LootboxReward<String> fiftyk_reward = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 50000"}), LootboxRewardType.COMMAND);
		fiftyk_reward.setAmount(1);
		fiftyk_reward.setPlaceholder(FIFTY_K);
		
		LootboxReward<String> two_hundred_k = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 100000"}), LootboxRewardType.COMMAND);
		two_hundred_k.setAmount(1);
		two_hundred_k.setPlaceholder(TWO_HUNDRED_K);
		
		LootboxReward<String> soul_book = new LootboxReward<String>(List.of(new String[] {"ae givercbook ULTIMATE %player_name% 1"}), LootboxRewardType.COMMAND);
		soul_book.setAmount(18);
		soul_book.setPlaceholder(SOUL_BOOK);
		soul_book.setChance(50);
		
		LootboxReward<String> godlike_book = new LootboxReward<String>(List.of(new String[] {"ae givercbook LEGENDARY %player_name% 1"}), LootboxRewardType.COMMAND);
		godlike_book.setAmount(6);
		godlike_book.setPlaceholder(MASTERY_BOOK);
		godlike_book.setChance(20);
		
		LootboxReward<String> orb = new LootboxReward<String>(List.of(new String[] {"ae giveitem %player_name% orb 1 ARMOR 18 100 0"}), LootboxRewardType.COMMAND);
		orb.setAmount(1);
		orb.setPlaceholder(ARMOR_ORB);
		orb.setChance(50);
		
		LootboxReward<String> orb2 = new LootboxReward<String>(List.of(new String[] {"ae giveitem %player_name% orb 1 WEAPON 18 100 0"}), LootboxRewardType.COMMAND);
		orb2.setAmount(1);
		orb2.setPlaceholder(WEAPON_ORB);
		orb2.setChance(50);
		
		LootboxReward<String> orb6 = new LootboxReward<String>(List.of(new String[] {"ae giveitem %player_name% orb 1 WEAPON 18 100 0"}), LootboxRewardType.COMMAND);
		orb6.setAmount(1);
		orb6.setPlaceholder(makePlaceholder(new ItemStack(Material.ENDER_EYE), "&6&lTool Enchantment Orb [&718&6&l]", DEFAULT_LORE));
		orb6.setChance(50);
		
		LootboxReward<String> orb3 = new LootboxReward<String>(List.of(new String[] {"ae giveitem %player_name% secret 1 FABLED"}), LootboxRewardType.COMMAND);
		orb3.setAmount(6);
		orb3.setPlaceholder(makePlaceholder(new ItemStack(Material.PAPER), "&d&lFabled Enchantment Dust", DEFAULT_LORE));
		orb3.setChance(100);
		
		LootboxReward<String> orb4 = new LootboxReward<String>(List.of(new String[] {"ae giveitem %player_name% secret 1 MASTERY"}), LootboxRewardType.COMMAND);
		orb4.setAmount(2);
		orb4.setPlaceholder(makePlaceholder(new ItemStack(Material.SUGAR), "&4&lMastery Enchantment Dust", DEFAULT_LORE));
		orb4.setChance(50);
		
		LootboxReward<String> orb7 = new LootboxReward<String>(List.of(new String[] {"pf givepfitem %player_name% DragonScale 1"}), LootboxRewardType.COMMAND);
		orb7.setAmount(1);
		orb7.setPlaceholder(makePlaceholder(new ItemStack(Material.AMETHYST_SHARD), "&5&lDragon Scale", DEFAULT_LORE));
		orb7.setChance(50);
		
		
		LootboxReward<String> orb5 = new LootboxReward<String>(List.of(new String[] {"pf givepfitem %player_name% UnsealScroll 1"}), LootboxRewardType.COMMAND);
		orb5.setAmount(4);
		orb5.setPlaceholder(makePlaceholder(new ItemStack(Material.PAPER), "&f&lUnseal Scroll", DEFAULT_LORE));
		orb5.setChance(50);
		
		LootboxReward<String> exp1 = new LootboxReward<String>(List.of(new String[] {"exp  %player_name% add 50L"}), LootboxRewardType.COMMAND);
		exp1.setAmount(1);
		exp1.setPlaceholder(makePlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE), "&e&l50 Levels", DEFAULT_LORE));
		
		LootboxReward<String> exp2 = new LootboxReward<String>(List.of(new String[] {"exp  %player_name% add 150L"}), LootboxRewardType.COMMAND);
		exp2.setAmount(1);
		exp2.setPlaceholder(makePlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE), "&e&l150 Levels", DEFAULT_LORE));
		
		LootboxReward<String> exp3 = new LootboxReward<String>(List.of(new String[] {"exp  %player_name% add 200L"}), LootboxRewardType.COMMAND);
		exp3.setAmount(1);
		exp3.setPlaceholder(makePlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE), "&e&l200 Levels", DEFAULT_LORE));
		
		this.addReward(reward);
		this.addReward(two_fifty);
		this.addReward(two_hundred_k);
		this.addReward(one_hundred_k);
		this.addReward(fiftyk_reward);
		this.addReward(soul_book);
		this.addReward(godlike_book);
		this.addReward(orb6);
		this.addReward(orb);
		this.addReward(orb2);
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
		new LootboxUI(p, 5, this.getRewards(), this.item).open();;
	}

}
