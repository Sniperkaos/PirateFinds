package me.cworldstar.piratefinds.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import me.cworldstar.piratefinds.PirateFinds;
import me.cworldstar.piratefinds.impl.ae.items.PFItemClass;


/**
 * 
 * The frontend methods that the PirateFinds plugin uses to
 * interface with Minecraft's crafting recipes without using
 * NMS.
 * 
 * @author cworldstar
 * @apiNote Almost every method in this {@link Class} are effectively never null.
 */

public class Crafting {
	
	public static void addShaped(CraftingRecipe recipe) {
		Bukkit.getServer().addRecipe(recipe);
	}
	
	
	
	/**
	 * 
	 * This method creates a {@link CraftingRecipe} from a {@link ItemStack}, a {@link String} array,  
	 * and a {@link Map} made from a {@link char} index and either a {@link RecipeChoice} or {@link Material} value.
	 * This method is never null.
	 * 
	 * @param result {@link ItemStack} The result of the recipe.
	 * @param shape {@link String}[] An array of strings, should be 3x3.
	 * @param recipe {@link Map} <Character, Object> A Character, RecipeChoice map. Can also use Materials.
	 * @return {@link CraftingRecipe} The created recipe.
	 */
	
	@Nonnull
	public static CraftingRecipe createShapedRecipe(String recipe_id, @Nonnull ItemStack result, @Nonnull String[] shape, @Nonnull Map<Character, Object> recipe) {
		ShapedRecipe cRecipe = new ShapedRecipe(PirateFinds.createKey(recipe_id), result);
		cRecipe.shape(shape);
		for(Entry<Character, Object> key : recipe.entrySet()) {
			Object value = key.getValue();	
			if(value instanceof Material) {
				Material material = (Material) value;
				cRecipe.setIngredient(key.getKey(), material);
			} else if(value instanceof RecipeChoice) {
				RecipeChoice choice = (RecipeChoice) value;
				cRecipe.setIngredient(key.getKey(), choice);
			}

		}
		return cRecipe;
	}
	
	/**
	 * 
	 * This method takes a {@link ItemStack} and returns a {@link RecipeChoice} to be 
	 * used in {@link Crafting#createShapedRecipe(ItemStack, String[], Map)}.
	 * 
	 * @param item {@link ItemStack} The item to create a choice from.
	 * @return {@link RecipeChoice} The created choice.
	 */ 
	@Nonnull
	public static RecipeChoice createExactChoice(@Nonnull ItemStack item) {
		return new RecipeChoice.ExactChoice(item);
	}
	
	/**
	 * 
	 * This method takes a {@link List} of {@link Material}s and returns a {@link RecipeChoice} to be 
	 * used in {@link Crafting#createShapedRecipe(ItemStack, String[], Map)}.
	 * 
	 * @param choices {@link List} The list of materials.
	 * @return {@link RecipeChoice} The created choice.
	 */
	
	@Nonnull
	public static RecipeChoice createMaterialChoice(@Nonnull List<Material> choices) {
		return new RecipeChoice.MaterialChoice(choices);
	}
	
	public static void setupRecipies() {
		// Infinite water bucket crafting recipe
		
		Map<Character, Object> water_bucket_key = new HashMap<Character, Object>();
		water_bucket_key.put('a', Material.IRON_INGOT);
		water_bucket_key.put('b', 
				createExactChoice(PFItemClass.getItem("DIAMOND_SINGULARITY").getPFItem())
		);
		water_bucket_key.put('c', Material.PRISMARINE_CRYSTALS);
		
		addShaped(
				createShapedRecipe(
					"water_bucket",
					PFItemClass.getItem("infinite_bucket").getPFItem(),
					new String[] {
							"aca",
							"aba",
							"cac"
					},
					water_bucket_key
				)
		);
		
		// end
		
		Map<Character, Object> diamond_singularity_key = new HashMap<Character, Object>();
		diamond_singularity_key.put('a', Material.DIAMOND_BLOCK);
		
		addShaped(
				createShapedRecipe(
					"diamond_singularity",
					PFItemClass.getItem("DIAMOND_SINGULARITY").getPFItem(),
					new String[] {
							"aaa",
							"aaa",
							"aaa"
					},
					diamond_singularity_key
				)
		);
		
		
	}
	
}
