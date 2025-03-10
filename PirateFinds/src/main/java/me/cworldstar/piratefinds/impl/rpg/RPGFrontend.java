package me.cworldstar.piratefinds.impl.rpg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.random.RandomGenerator;

import javax.annotation.Nonnull;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.joml.Random;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.utils.StringEditor;

public class RPGFrontend {

	public static final NamespacedKey RPG_PREFIX_KEY = PirateFinds.createKey("RPG_PREFIX_KEY");
	public static final NamespacedKey RPG_SUFFIX_KEY = PirateFinds.createKey("RPG_SUFFIX_KEY");
	public static final NamespacedKey RPG_NAME_KEY = PirateFinds.createKey("RPG_NAME_KEY");

	
	private static Map<String, List<String>> namelists = new HashMap<String, List<String>>();
	private static List<String> randomNames = Arrays.asList(new String[] {
			"Sarek",
			"Minetta",
			"Karyk",
			"Bren",
			"Alathic",
			"Evarius",
			"Cassemir",
			"Yanthus",
			"Alisud",
			"Cog",
			"Naeve",
			"Novius",
			"Rhanes",
			"Pyke",
			"Angar"
	});
	
	
	static {
		namelists.put("default", Arrays.asList(new String[] {
				"%random_name%'s %prefix% %item_name% of %suffix%"
		}));
	}
	
	@Nonnull
	public static List<String> getRandomNamelist() {
		Random random = new Random();
		int select = random.nextInt(namelists.size());
		int i = 0;
		for(Entry<String, List<String>> entry : namelists.entrySet()) {
			if(select == i) {
				return entry.getValue();
			}
			i++;
		}
		return namelists.get("default");
	}
	
	
	public static String getRandomName() {
		Random random = new Random();
		int select = random.nextInt(randomNames.size());
		return randomNames.get(select);
	}

	
	public static String applyRPGTags(String s, ItemStack i) {
		
		ItemMeta meta = i.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();
		String name = container.get(RPG_NAME_KEY, PersistentDataType.STRING);
		RPGPrefix prefix = container.get(RPG_PREFIX_KEY, PFDataType.RPGPREFIX);
		
		
		StringEditor editor = new StringEditor(s);
		editor.replace("%random_name%", getRandomName());
		editor.replace("%prefix%", getRandomName());
		editor.replace("%item_name%", name);
		editor.replace("%random_name%", getRandomName());
		
		return editor.finishSingle();
	}

	public static String applyRPGTagsToList(List<String> s) {
		return null;
	}
	
	public static void createRPGItem(ItemStack item) {
		List<String> namelist = getRandomNamelist();
		
	}
	
}
