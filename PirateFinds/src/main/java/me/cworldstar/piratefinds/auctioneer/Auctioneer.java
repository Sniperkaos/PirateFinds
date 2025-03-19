package me.cworldstar.piratefinds.auctioneer;

import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.lang3.NumberRange;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.Zrips.CMI.CMI;

import me.clip.placeholderapi.PlaceholderAPI;
import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.events.TickerTickEvent;
import me.cworldstar.piratefinds.impl.ui.BaseUIObject;
import me.cworldstar.piratefinds.impl.ui.MenuHandler;
import me.cworldstar.piratefinds.impl.utils.ChatUtils;
import me.cworldstar.piratefinds.impl.utils.StringEditor;
import net.advancedplugins.ae.impl.utils.SkullCreator;

public class Auctioneer implements Listener {
	
	// create a list of deals to choose from
	private static List<ConfigurationDeal> configDeals;
	private static Map<String, Boolean> settings;
	private static Map<String, ConfigSound> sounds;
	private static Map<String, String> messages;
	private static String nameplate;
	
	public static Map<String, ConfigSound> getSounds() {
		return sounds;
	}
	
	public static Map<String, Boolean> getSettings() {
		return settings;
	}
	
	public static Map<String, String> getMessages() {
		return messages;
	}
	
	public static String getNameplate() {
		return nameplate;
	}
	
	private static long endTime = 0;
	private static long startTime = 0;
	
	public Auctioneer() {
		if(configDeals != null) {
			PirateFinds.log("You cannot instance Auctioneer another time.");
		}
		settings = new HashMap<String, Boolean>();
		sounds = new HashMap<String, ConfigSound>();
		configDeals = new ArrayList<ConfigurationDeal>();
		messages = new HashMap<String, String>();

		YamlConfiguration merchantConfig = PirateFinds.getMerchantConfig();
		nameplate = merchantConfig.getString(ChatUtils.apply("nameplate"));
		ConfigurationSection soundsSection = merchantConfig.getConfigurationSection("sounds");
		ConfigurationSection dealsSection = merchantConfig.getConfigurationSection("deals");
		ConfigurationSection messagesSection = merchantConfig.getConfigurationSection("messages");
		for(String dealKey : dealsSection.getKeys(false)) {
			ConfigurationSection deal = dealsSection.getConfigurationSection(dealKey);
			ConfigurationSection amount = deal.getConfigurationSection("amount");
			ConfigurationSection discount = deal.getConfigurationSection("discount");
			ConfigurationSection commandSection = deal.getConfigurationSection("purchase");
			
			configDeals.add(new ConfigurationDeal(
				deal.getDouble("base"), // the base price of the deal	
				discount.getDouble("min"), // min discount
				discount.getDouble("max"), // max discount
				deal.getConfigurationSection("display"),
				commandSection.getStringList("value"),
				new NumberRange<Integer>((Integer) amount.getInt("min"), (Integer) amount.getInt("max"), Comparator.naturalOrder())
			));
		}
		
		for(Entry<String, Object> setting : merchantConfig.getConfigurationSection("settings").getValues(false).entrySet()) {
			if(setting.getValue() instanceof Boolean) {
				settings.put(setting.getKey(), (Boolean) setting.getValue());
			}
		}
		
		for(String soundKey : soundsSection.getKeys(false)) {
			ConfigurationSection sound = soundsSection.getConfigurationSection(soundKey);
			sounds.put(soundKey, new ConfigSound(Sound.valueOf(sound.getString("sound")), sound.getDouble("pitch"), sound.getDouble("volume")));	
		}
		
		for(String messageKey : messagesSection.getKeys(false)) {
			messages.put(messageKey, ChatUtils.apply(messagesSection.getString(messageKey)));	
		}
		
	}
	
	private static Map<Player, List<ConfigurationDeal>> deals = new HashMap<Player, List<ConfigurationDeal>>();
	
	public static void flagPlayerForReset(OfflinePlayer p) {
		ConfigurationSection has_prior_deals_information = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").getConfigurationSection(p.getUniqueId().toString());
		if(has_prior_deals_information != null) {
			has_prior_deals_information.set("reset", true);
		}
	}
	
	public static void onPlayerJoin(Player p) {
		if(!Auctioneer.deals.containsKey(p)) {
			List<ConfigurationDeal> deals = new ArrayList<ConfigurationDeal>();
			
			
			/*
			 * players:
			 *   sniperkaos_uuid:
			 *     '1':
			 *        item: itemstack
			 *        price: 100000
			 * 
			 */
			
			ConfigurationSection has_prior_deals_information = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").getConfigurationSection(p.getUniqueId().toString());
			if(has_prior_deals_information != null && has_prior_deals_information.getBoolean("reset") != true) {
				PirateFinds.logDebug("Player has prior information");
				for(String key : has_prior_deals_information.getKeys(false)) {
					PirateFinds.logDebug("Getting the deal");
					ConfigurationSection the_deal = has_prior_deals_information.getConfigurationSection(key);
					int amount = the_deal.getInt("amount");
					if(amount <= 0) {
						amount = 1;
					}
					ItemStack displayItem = the_deal.getItemStack("displayItem");
					PirateFinds.logDebug("ItemStack: " + displayItem.toString());
					int price = (Integer) the_deal.getInt("price");
					double discount = the_deal.getDouble("discount");
					deals.add(new ConfigurationDeal(price, discount, displayItem, the_deal.getStringList("commands"), new NumberRange<Integer>(amount, amount, Comparator.naturalOrder())));
				}
			} else {
				ConfigurationSection section = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").createSection(p.getUniqueId().toString());	
				section.set("reset", false);

				List<ConfigurationDeal> to_add = createDeals(7);
				
				to_add.forEach((ConfigurationDeal d) -> {
					String sectionPlace = Integer.toString(section.getKeys(false).size());
					ConfigurationSection dealSection = section.createSection(sectionPlace);
					dealSection.set("amount", d.getAmount());
					dealSection.set("commands", d.getCommands());
					dealSection.set("price", d.getPrice());
					dealSection.set("displayItem", d.getDisplayItem());
					dealSection.set("discount", d.getDiscount());
					dealSection.set("section", sectionPlace);
					d.setData("section", sectionPlace);
				});
				deals.addAll(to_add);
			}
			
			Auctioneer.deals.put(p, deals);
		}
	}
	
	private static List<ConfigurationDeal> createDeals(int i) {
		
		List<ConfigurationDeal> toReturn = new ArrayList<ConfigurationDeal>();
		
		for(int i2=0; i2<i; i2++) {
			
			ConfigurationDeal deal = configDeals.get((int) (Math.random() * configDeals.size())).clone();
			toReturn.add(deal);
		}
		
		return toReturn;
	}

	public static void display(BaseUIObject object) {
		
		object.getOwner().sendMessage(Auctioneer.tags(object.getOwner(), messages.get("open")));
		sounds.get("open").play(object.getOwner());
		
		object.addMenuCloseHandler(new MenuHandler<InventoryCloseEvent>((InventoryCloseEvent e) -> {
			object.getOwner().sendMessage(Auctioneer.tags(object.getOwner(),messages.get("close")));
			sounds.get("close").play(object.getOwner());
		}));
		
		List<ConfigurationDeal> player_deals = deals.get(object.getOwner());
		PirateFinds.logDebug(player_deals.toString());
		
		ItemStack head = SkullCreator.itemFromBase64("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTcxYTIyODVjOTFjNmM3Mjc0NzYwNDgxOWVlNTIyM2E5MGFhNTFlNmU3OWU0ZjlhZjY2MjhlYzhmMGRkN2RmYyJ9fX0=");
		

		object.addUnclickableItem(22, head);
		object.addEmptyClickHandler(new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
			e.setCancelled(true);
		}));
		object.addTicker(new MenuHandler<TickerTickEvent>((TickerTickEvent) -> {
			ItemStack stack = object.getInventory().getItem(22);
			ItemMeta stackMeta = stack.getItemMeta();
			List<String> stackLore = new ArrayList<String>();
			stackLore.add(" ");
			stackLore.add("&7 * &eThe next refresh is in: %time%.");
			StringEditor loreEditor = new StringEditor(stackLore);
			loreEditor.replace("%time%", DurationFormatUtils.formatDuration(endTime - Instant.now().toEpochMilli(), "H:mm:ss", true));
			stackMeta.setLore(ChatUtils.apply(loreEditor.finish()));
			stackMeta.setItemName(ChatUtils.apply("&f&lInformation"));
			stack.setItemMeta(stackMeta);
		}));
		
		for(ConfigurationDeal deal : player_deals) {
			PirateFinds.logDebug(deal.getDisplayItem().toString());
			int slot = object.getFirstClearSlot();
			if(slot == -1) continue;
			
			ItemStack item = deal.getDisplayItem().clone();
			ItemMeta meta = item.getItemMeta();
			List<String> lore = meta.getLore();
			lore.add("");
			lore.add("&7 * &6Price&7: &c%price%$");
			lore.add("&7 * &6Discount&7: %discount%");
			
			StringEditor editor = new StringEditor(lore);
			editor.replace("%price%", new DecimalFormat("#,###").format(Math.round(deal.getPrice() * 10) / 10));
			
			Double discountDouble = (double) (Math.round(deal.getDiscount() * 10) / 10);
			String discount = Double.toString(Math.abs(discountDouble));
			editor.replace("%discount%", Math.signum(discountDouble) == -1 ? "&a" + discount + "% less than normal." : "&c" + discount + "% more than normal.");

			
			meta.setLore(ChatUtils.apply(editor.finish()));
			
			meta.setItemName(ChatUtils.apply(meta.getItemName()));
			
			meta.setDisplayName(ChatUtils.apply(meta.getDisplayName()));
			
			item.setItemMeta(meta);
						
			object.addUnclickableItem(slot, item);
			
			 MenuHandler<InventoryClickEvent> dealHandler = new MenuHandler<InventoryClickEvent>((InventoryClickEvent e) -> {
				 if(object.getInventory().getItem(slot) == null) {
					 return;
				 }
				 boolean success = deal.apply(object.getOwner());
				 if(success) {
					 removeDeal(object.getOwner(), deal);
					 object.setItem(slot, null);
				 }
			});
			
			object.addMenuClickHandler(slot, dealHandler);
			
			if(!object.isOpen()) {
				object.open();
			}
			
		}
	}
	
	@EventHandler
	public void onTickerTick(TickerTickEvent e) {
		if(endTime < Instant.now().toEpochMilli()) {
			setStartTime(0);
			setEndTime(0);
			
			for(OfflinePlayer player : PirateFinds.getServerStatic().getOfflinePlayers()) {
				List<ConfigurationDeal> playerDeals = new ArrayList<ConfigurationDeal>();
				ConfigurationSection playerSection = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").getConfigurationSection(player.getUniqueId().toString());
				if(playerSection == null) continue;
				if(player.getPlayer() != null) {
					ConfigurationSection section = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").createSection(player.getUniqueId().toString());	
					List<ConfigurationDeal> to_add = createDeals(7);
					section.set("reset", false);
					to_add.forEach((ConfigurationDeal d) -> {
						String sectionPlace = Integer.toString(section.getKeys(false).size());
						ConfigurationSection dealSection = section.createSection(sectionPlace);
						dealSection.set("commands", d.getCommands());
						dealSection.set("price", d.getPrice());
						dealSection.set("displayItem", d.getDisplayItem());
						dealSection.set("discount", d.getDiscount());
						dealSection.set("section", sectionPlace);
						d.setData("section", sectionPlace);
					});
					playerDeals.addAll(to_add);
					Auctioneer.deals.put(player.getPlayer(), playerDeals);	
				} else {
					playerSection.set("reset", true);
				}
			}
		}
	}

	public static void refresh(Player player) {
		List<ConfigurationDeal> playerDeals = new ArrayList<ConfigurationDeal>();
		ConfigurationSection playerSection = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").getConfigurationSection(player.getUniqueId().toString());
		if(playerSection == null) return;
		if(player.getPlayer() != null) {
			ConfigurationSection section = PirateFinds.getAuctioneerConfig().getConfigurationSection("players").createSection(player.getUniqueId().toString());	
			List<ConfigurationDeal> to_add = createDeals(7);
			section.set("reset", false);
			to_add.forEach((ConfigurationDeal d) -> {
				String sectionPlace = Integer.toString(section.getKeys(false).size());
				ConfigurationSection dealSection = section.createSection(sectionPlace);
				dealSection.set("commands", d.getCommands());
				dealSection.set("price", d.getPrice());
				dealSection.set("displayItem", d.getDisplayItem());
				dealSection.set("discount", d.getDiscount());
				dealSection.set("section", sectionPlace);
				d.setData("section", sectionPlace);
			});
			playerDeals.addAll(to_add);
			Auctioneer.deals.put(player.getPlayer(), playerDeals);	
		}
	}
	
	public static void removeDeal(Player player, ConfigurationDeal deal) {
		PirateFinds.getAuctioneerConfig().getConfigurationSection("players").getConfigurationSection(player.getUniqueId().toString()).set((String) deal.getData("section"), null);;
		deals.get(player).remove(deal);
	}

	public static void setStartTime(long object) {
		if(object == 0) {
			long milis = Instant.now().toEpochMilli();
			PirateFinds.getAuctioneerConfig().getConfigurationSection("data").set("startTime", milis);
			object = milis;
		}
		startTime = object;
	}
	
	public static long getStartTime() {
		return startTime;
	}
	
	public static long getEndTime() {
		return endTime;
	}

	public static void setEndTime(long object) {
		if(object == 0) {
			long milis = Instant.now().toEpochMilli() + Duration.ofHours(24).toMillis();
			PirateFinds.getAuctioneerConfig().getConfigurationSection("data").set("endTime", milis);
			object = milis;
		}
		endTime = object;
	}

	public static String tags(Player p, String string) {
		return ChatUtils.apply(PlaceholderAPI.setPlaceholders(p, string.replace("%nameplate%", nameplate)));
	}
}
