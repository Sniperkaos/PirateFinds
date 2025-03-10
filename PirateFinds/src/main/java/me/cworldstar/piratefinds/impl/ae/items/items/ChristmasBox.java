package me.cworldstar.piratefinds.impl.ae.items.items;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.cworldstar.piratefinds.events.TickerTickEvent;
import me.cworldstar.piratefinds.impl.ae.items.AbstractPFItem;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;
import me.cworldstar.piratefinds.impl.ae.items.items.masks.SantaMask;
import me.cworldstar.piratefinds.impl.food.ChristmasCookie;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward;
import me.cworldstar.piratefinds.impl.lootbox.LootboxReward.LootboxRewardType;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;
import me.cworldstar.piratefinds.impl.ui.StyleFunction;
import me.cworldstar.piratefinds.impl.ui.test.LootboxUI;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.ExperienceUtils;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class ChristmasBox extends AbstractLootBox {

	public static String CHANCE_VERY_LOW = ChatUtils.apply("&c&lCHANCE: &7Very low.");
	public static String CHANCE_LOW = ChatUtils.apply("&c&lCHANCE: &7Low.");
	public static String CHANCE_NORMAL = ChatUtils.apply("&c&lCHANCE: &7Average.");
	public static String CHANCE_HIGH = ChatUtils.apply("&c&lCHANCE: &7High.");
	public static String CHANCE_VERY_HIGH = ChatUtils.apply("&c&lCHANCE: &7Very High.");

	private static String[] DEFAULT_LORE = new String[] { "" };

	public ChristmasBox() {		
		super("CHRISTMAS_CHEST");
		this.item = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk4MGU4ZmNjZGFjYzI4ZjY0OTFjYmM0OGJjNmEyZWQ5ZjAzOTQzZDY0ODViZTI0ZjRhZTQ1OGExOTAyYWMwYyJ9fX0=");
		this.load();
	}

	public ItemStack getPFItem() {
		return item;
	}

	private static ItemStack christmas_cookie = new ItemStack(Material.COOKIE);
	private static ItemStack christmas_milk = new ItemStack(Material.MILK_BUCKET);

	static {
		ItemMeta cookiemeta = christmas_cookie.getItemMeta();
		ItemMeta milkmeta = christmas_milk.getItemMeta();

		cookiemeta.setItemName(
				ChatUtils.apply("&x&6&8&E&7&E&0&lC&x&7&E&E&9&C&7&lh&x&9&3&E&C&A&D&lr&x&A&9&E&E&9&4&li&x&B&E&F&0&7&A&ls&x&D&4&F&2&6&1&lt&x&E&9&F&5&4&7&lm&x&F&F&F&7&2&E&la&x&E&9&F&5&4&7&ls &x&D&4&F&2&6&1&lC&x&B&E&F&0&7&A&lo&x&A&9&E&E&9&4&lo&x&9&3&E&C&A&D&lk&x&7&E&E&9&C&7&li&x&6&8&E&7&E&0&le"));
		cookiemeta.setLore(Arrays.asList(new String[] 
				{ 
					ChatUtils.apply(""), 
					ChatUtils.apply("&7A cookie made by special christmas elves,"),
					ChatUtils.apply("&7the festivity inside will fill you up.") 
				}
			)
		);
		FoodComponent cookiecomponent = cookiemeta.getFood();
		cookiecomponent.setSaturation(10);
		cookiecomponent.setNutrition(10);
		cookiecomponent.addEffect(new PotionEffect(PotionEffectType.HASTE, 2, 120), 0.2F);
		cookiemeta.setFood(cookiecomponent);
		cookiemeta.setMaxStackSize(64);
		christmas_cookie.setItemMeta(cookiemeta);

		// --------------------------------------------------------

		milkmeta.setItemName(ChatUtils.apply("&x&6&8&E&7&E&0&lC&x&8&1&E&B&E&5&lh&x&9&A&E&F&E&A&lr&x&B&4&F&3&F&0&li&x&C&D&F&7&F&5&ls&x&E&6&F&B&F&A&lt&x&F&F&F&F&F&F&lm&x&E&6&F&B&F&A&la&x&C&D&F&7&F&5&ls &x&B&4&F&3&F&0&lM&x&9&A&E&F&E&A&li&x&8&1&E&B&E&5&ll&x&6&8&E&7&E&0&lk"));
		milkmeta.setLore(Arrays.asList(new String[] 
				{ 
					ChatUtils.apply(""), 
					ChatUtils.apply("&7Milked from special christmas cows,"),
					ChatUtils.apply("&7the festivity inside will fill you up.") 
				}
			)
		);
		milkmeta.setFood(cookiecomponent);
		milkmeta.setMaxStackSize(8);
		christmas_milk.setItemMeta(milkmeta);

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
	
	private static ItemStack makeEXPPlaceholder(ItemStack i, int amount) {
		ItemMeta fk_meta = i.getItemMeta();
		fk_meta.setItemName(ChatUtils.apply("&c&l" + Integer.toString(amount) + " Levels"));
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

	public final String pf_item_id = "ENDER_DRAGON_LOOTBOX";

	@Override
	public void onLoad() {
		
	}
	
	public void load() {
		this.setItemFlavor(
				"&x&6&8&E&7&E&0&lC&x&7&5&D&7&C&8&lh&x&8&3&C&7&B&0&lr&x&9&0&B&7&9&8&li&x&9&D&A&7&7&F&ls&x&A&B&9&7&6&7&lt&x&B&8&8&7&4&F&lm&x&C&5&7&7&3&7&la&x&C&0&7&1&2&B&ls &x&A&9&7&5&2&A&lL&x&9&2&7&A&2&9&lo&x&7&B&7&E&2&8&lo&x&6&4&8&2&2&7&lt&x&4&D&8&6&2&6&lb&x&3&6&8&B&2&5&lo&x&1&F&8&F&2&4&lx",
				List.of(new String[] { 
						"&7&oMerry christmas! &x&F&B&0&0&0&0&oS&x&F&D&7&1&7&1&oa&x&F&F&E&3&E&3&on&x&B&4&E&0&F&F&ot&x&5&1&B&6&F&F&oa &x&5&1&A&B&D&7&oC&x&B&4&B&E&8&6&ol&x&F&8&B&8&5&2&oa&x&D&D&6&6&7&1&ou&x&C&1&1&4&9&1&os &7&ohas a new", 
						"&7&opresent for you!" }));

		ItemStack cookieReward = christmas_cookie.clone();
		cookieReward.setAmount(8);

		ItemStack milkReward = christmas_milk.clone();
		milkReward.setAmount(2);

		// weapons and what not
		
		this.addReward(new LootboxReward<ItemStack>(List.of(new ItemStack[] { cookieReward, milkReward }),
				LootboxRewardType.ITEM), "&x&E&7&3&C&0&6&lC&x&E&3&4&5&0&C&lh&x&D&E&4&D&1&2&lr&x&D&A&5&6&1&9&li&x&D&5&5&E&1&F&ls&x&D&1&6&7&2&5&lt&x&C&C&6&F&2&B&lm&x&A&E&7&4&2&5&la&x&9&0&7&A&1&F&ls &x&7&3&7&F&1&9&lM&x&5&5&8&4&1&2&le&x&3&7&8&A&0&C&la&x&1&9&8&F&0&6&ll");
		this.addReward(new LootboxReward<String>(List.of(new String[] { 
				"pf givepfitem %player_name% CandyCane 1"
			}),
			LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.DIAMOND_SWORD), "&x&F&F&F&F&F&F&lC&x&B&A&6&0&6&0&la&x&A&B&0&0&0&0&ln&x&E&E&0&0&0&0&ld&x&C&7&0&0&0&0&ly &x&9&2&1&2&1&2&lC&x&9&2&6&D&6&D&la&x&B&B&B&B&B&B&ln&x&F&F&F&F&F&F&le", DEFAULT_LORE)).setAmount(1).setChance(20));
		
		//perm gkits
		
		this.addReward(new LootboxReward<String>(List.of(new String[] {
				"pf givepfitem %player_name% JollyGkitVoucher 1"
		}), LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.DIAMOND), "&x&C&B&2&D&3&E&lG&x&D&B&8&3&8&D&lk&x&E&B&D&9&D&B&li&x&F&3&9&F&9&F&lt&x&F&8&3&5&3&5&l: &x&F&8&3&5&3&5&lJ&x&F&3&9&F&9&F&lo&x&E&B&D&9&D&B&ll&x&D&B&8&3&8&D&ll&x&C&B&2&D&3&E&ly", DEFAULT_LORE)).setAmount(1).setChance(5).setBroadcast(true));
		
		this.addReward(new LootboxReward<String>(List.of(new String[] {
				"pf givepfitem %player_name% SantaMask 1"
		}), LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(SantaMask.HEAD, SantaMask.HEAD.getItemMeta().getDisplayName(), SantaMask.HEAD.getItemMeta().getLore().toArray(new String[0]))).setChance(10).setBroadcast(true));
		
		// misc awful rewards
		
		
		// books
		
		this.addReward(new LootboxReward<String>(List.of(new String[] { "ae givercbook FESTIVE  %player_name% 1" }),
				LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.ENCHANTED_BOOK), "&c&lFestive Book", DEFAULT_LORE)).setAmount(2));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "ae givercbook LEGENDARY  %player_name% 1" }),
				LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.ENCHANTED_BOOK), "&6&lLegendary Book", DEFAULT_LORE)).setAmount(8));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "ae givercbook ELITE  %player_name% 1" }),
				LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.ENCHANTED_BOOK), "&e&lUltimate Book", DEFAULT_LORE)).setAmount(16));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "ae givercbook FABLED  %player_name% 1" }),
				LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.ENCHANTED_BOOK), "&d&lFabled Book", DEFAULT_LORE)).setAmount(4));
		
		// dust
		
		this.addReward(new LootboxReward<String>(List.of(new String[] { "ae giveitem %player_name% secret 1 FESTIVE" }),
				LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.FIRE_CHARGE), "&c&lFestive Dust", DEFAULT_LORE)).setAmount(4));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "ae giveitem %player_name% secret 1 LEGENDARY" }),
				LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.FIRE_CHARGE), "&6&lLegendary Dust", DEFAULT_LORE)).setAmount(10));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "ae giveitem %player_name% secret 1 ULTIMATE" }),
				LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.FIRE_CHARGE), "&e&lUltimate Dust", DEFAULT_LORE)).setAmount(18));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "ae giveitem %player_name% secret 1 FABLED" }),
				LootboxRewardType.COMMAND).setPlaceholder(makePlaceholder(new ItemStack(Material.FIRE_CHARGE), "&d&lFabled Dust", DEFAULT_LORE)).setAmount(6));
		
		
		this.addReward(new LootboxReward<String>(List.of(new String[] { "cmi money give %player_name% 50000" }),
				LootboxRewardType.COMMAND).setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.PAPER),50000)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "cmi money give %player_name% 75000" }),
				LootboxRewardType.COMMAND).setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.PAPER),75000)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "cmi money give %player_name% 90000" }),
				LootboxRewardType.COMMAND).setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.PAPER),90000)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "cmi money give %player_name% 120000" }),
				LootboxRewardType.COMMAND).setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.PAPER),120000)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "cmi money give %player_name% 150000" }),
				LootboxRewardType.COMMAND).setPlaceholder(makeMoneyPlaceholder(new ItemStack(Material.PAPER),150000)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "exp %player_name% add  " + Integer.toString(ExperienceUtils.getExpAtLevel(100)) }),
				LootboxRewardType.COMMAND).setPlaceholder(makeEXPPlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE),100)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "exp %player_name% add " + Integer.toString(ExperienceUtils.getExpAtLevel(125)) }),
				LootboxRewardType.COMMAND).setPlaceholder(makeEXPPlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE),125)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "exp %player_name% add " + Integer.toString(ExperienceUtils.getExpAtLevel(150)) }),
				LootboxRewardType.COMMAND).setPlaceholder(makeEXPPlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE),150)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "exp %player_name% add " + Integer.toString(ExperienceUtils.getExpAtLevel(175)) }),
				LootboxRewardType.COMMAND).setPlaceholder(makeEXPPlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE),175)).setAmount(1));
		this.addReward(new LootboxReward<String>(List.of(new String[] { "exp %player_name% add " + Integer.toString(ExperienceUtils.getExpAtLevel(200)) }),
				LootboxRewardType.COMMAND).setPlaceholder(makeEXPPlaceholder(new ItemStack(Material.EXPERIENCE_BOTTLE),200)).setAmount(1));
		

		

	
	}

	public ItemStack strip(ItemStack i) {
		ItemMeta meta = i.getItemMeta();
		meta.setItemName(" ");
		i.setItemMeta(meta);
		return i;
	}

	@Override
	public void onBuild() {

	}

	@Override
	public void use(Player p) {
		LootboxUI ui = new LootboxUI(p, 5, this.getRewards(), this.item);
		ui.createProperty("offset", 0);
		ui.addTicker(new MenuHandler<TickerTickEvent>((TickerTickEvent e) -> {
			int offset = ui.getProperty("offset");
			for (Integer slot : ui.getBarrierSlots()) {
				if ((slot + offset) % 4 == 0) {
					ui.setItem(slot, strip(new ItemStack(Material.RED_STAINED_GLASS_PANE)));
				} else if ((slot + offset) % 2 == 0) {
					ui.setItem(slot, strip(new ItemStack(Material.CYAN_STAINED_GLASS_PANE)));
				} else {
					ui.setItem(slot, strip(new ItemStack(Material.LIME_STAINED_GLASS_PANE)));
				}
			}
			ui.increaseProperty("offset");
		}));
		ui.open();
	}

}
