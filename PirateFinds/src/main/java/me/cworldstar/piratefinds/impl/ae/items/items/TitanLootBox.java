package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.cworldstar.piratefinds.impl.armorer.ArmorSets;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward.LootboxRewardType;
import me.cworldstar.piratefinds.impl.ui.test.LootboxUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;

public class TitanLootBox extends AbstractLootBox {

	public static String CHANCE_VERY_LOW = ChatUtils.apply("&c&lCHANCE: &7Very low.");
	public static String CHANCE_LOW = ChatUtils.apply("&c&lCHANCE: &7Low.");
	public static String CHANCE_NORMAL = ChatUtils.apply("&c&lCHANCE: &7Average.");
	public static String CHANCE_HIGH = ChatUtils.apply("&c&lCHANCE: &7High.");
	public static String CHANCE_VERY_HIGH = ChatUtils.apply("&c&lCHANCE: &7Very High.");

	
	
	
	private static ItemStack FIVE_HUNDRED_K = new ItemStack(Material.MAP);
	private static ItemStack TWO_FIFTY_K = new ItemStack(Material.PAPER);
	private static ItemStack ONE_HUNDRED_K = new ItemStack(Material.PAPER);
	private static ItemStack FIFTY_K = new ItemStack(Material.PAPER);
	private static ItemStack IRON_VOUCHER = new ItemStack(Material.DIAMOND);
	private static ItemStack TITAN_SET_PLACEHOLDER = new ItemStack(Material.NETHERITE_CHESTPLATE);
	private static ItemStack TITAN_SET_PIECE_PLACEHOLDER = new ItemStack(Material.NETHERITE_SCRAP);

	
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
		FIVE_HUNDRED_K = makeMoneyPlaceholder(FIVE_HUNDRED_K, 500000);
		TWO_FIFTY_K = makeMoneyPlaceholder(TWO_FIFTY_K, 250000);
		ONE_HUNDRED_K = makeMoneyPlaceholder(ONE_HUNDRED_K, 100000);
		FIFTY_K = makeMoneyPlaceholder(FIFTY_K, 50000);
		IRON_VOUCHER = makePlaceholder(IRON_VOUCHER, "&f&lIron Rank Voucher", new String[] {
				""
		});
		TITAN_SET_PLACEHOLDER = makePlaceholder(TITAN_SET_PLACEHOLDER, "&4&lTitan Set", new String[] {
				""
		});
		TITAN_SET_PIECE_PLACEHOLDER = makePlaceholder(TITAN_SET_PIECE_PLACEHOLDER, "&4&lTitan Set Piece", new String[] {
				""
		});
	}
	
	@Override
	public void onLoad() {
		this.setItemFlavor("&4&lTitan", List.of(new String[] {
				"&7Dropped from the skies, this otherworldly",
				"&7crate contains insane loot."
		}));
		
		LootboxReward<String> reward = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 500000"}), LootboxRewardType.COMMAND);
		reward.setAmount(1);
		reward.setPlaceholder(FIVE_HUNDRED_K);
		
		LootboxReward<String> two_fifty = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 250000"}), LootboxRewardType.COMMAND);
		two_fifty.setAmount(1);
		two_fifty.setPlaceholder(TWO_FIFTY_K);
		
		LootboxReward<String> one_hundred_k = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 100000"}), LootboxRewardType.COMMAND);
		one_hundred_k.setAmount(1);
		one_hundred_k.setPlaceholder(ONE_HUNDRED_K);
		
		LootboxReward<String> fiftyk_reward = new LootboxReward<String>(List.of(new String[] {"cmi money give %player_name% 50000"}), LootboxRewardType.COMMAND);
		fiftyk_reward.setAmount(1);
		fiftyk_reward.setPlaceholder(FIFTY_K);
		
		LootboxReward<String> ranked_voucher_iron = new LootboxReward<String>(List.of(new String[] {"vouchers give %player_name% iron_rank"}), LootboxRewardType.COMMAND);
		ranked_voucher_iron.setAmount(1);
		ranked_voucher_iron.setPlaceholder(IRON_VOUCHER);
		ranked_voucher_iron.setChance(1);
		
		LootboxReward<String> titan_set = new LootboxReward<String>(List.of(new String[] {"asets give %player_name% titan"}), LootboxRewardType.COMMAND);
		titan_set.setAmount(1);
		titan_set.setPlaceholder(TITAN_SET_PLACEHOLDER);
		titan_set.setChance(15);
		
		LootboxReward<String> titan_set_piece = new LootboxReward<String>(List.of(new String[] {"asets givepiece %player_name% titan " + ArmorSets.randomPiece()}), LootboxRewardType.COMMAND);
		titan_set_piece.setAmount(1);
		titan_set_piece.setPlaceholder(TITAN_SET_PIECE_PLACEHOLDER);
		titan_set_piece.setChance(50);
		
		this.addReward(reward, "&c&l500,000$");
		this.addReward(fiftyk_reward, "&c&l50,000$");
		this.addReward(one_hundred_k, "&c&l100,000$");
		this.addReward(one_hundred_k, "&c&l100,000$");
		this.addReward(two_fifty, "&c&l250,000$");
		this.addReward(ranked_voucher_iron, "&f&lIron Rank Voucher");
		this.addReward(titan_set, "&4&lFull Titan Set");
		this.addReward(titan_set, "&4&lTitan Armor Piece");
	}

	@Override
	public void onBuild() {
		
	}

	@Override
	public void use(Player p) {
		new LootboxUI(p, 5, this.getRewards(), this.item).open();;
	}
	
}
