package me.cworldstar.piratefinds.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.CraftingRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

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
	
	private static List<CraftingRecipe> PF_RECIPIES = new ArrayList<CraftingRecipe>();
	
	public static void add(@Nonnull CraftingRecipe recipe) {
		if(Bukkit.getServer().getRecipe(recipe.getKey()) != null) {
			PirateFinds.log("Crafting recipe " + recipe.getKey().toString() + " already exists.");
			return;
		}
		Bukkit.getServer().addRecipe(recipe);
		PF_RECIPIES.add(recipe);
	}
	
	
	/**
	 * 
	 * @param outcome {@link ItemStack} The ItemStack outcome of the expected recipe.
	 * @return {@link CraftingRecipe} The recipe that matches the outcome. Can be null.
	 */
	@Nullable
	public static CraftingRecipe getRecipe(ItemStack outcome) {
		for(CraftingRecipe recipe :  PF_RECIPIES) {
			if(recipe.getResult().isSimilar(outcome)) {
				return recipe;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @return {@link List}<CraftingRecipe> A list of all the crafting recipes registered by this plugin.
	 */
	@Nonnull
	public static List<CraftingRecipe> allRecipies() {
		return PF_RECIPIES;
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
	
	@Nonnull
	public static CraftingRecipe createShapelessRecipe(String recipe_id, @Nonnull ItemStack result, @Nonnull Object[] shape) {
		ShapelessRecipe cRecipe = new ShapelessRecipe(PirateFinds.createKey(recipe_id), result);
		for(Object o : shape) {
			// I have to do this ugly shit for my compiler
			if(o instanceof Material) {
				cRecipe.addIngredient((Material) o);
			} else if(o instanceof RecipeChoice) {
				cRecipe.addIngredient((RecipeChoice) o);
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
	
	/**
	 * Static class for registering all recipes. 
	 */
	public static void setupRecipies() {

		//-----------------------
		// Infinite Water Bucket recipe
		
		Map<Character, Object> water_bucket_key = new HashMap<Character, Object>();
		water_bucket_key.put('a', Material.IRON_INGOT);
		water_bucket_key.put('b', 
				createExactChoice(PFItemClass.getItem("DIAMOND_SINGULARITY").getPFItem())
		);
		water_bucket_key.put('c', Material.PRISMARINE_CRYSTALS);
		
		add(
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
		
		//-----------------------
		// Ground Pounder recipe
		
		Map<Character, Object> ground_pounder_key = new HashMap<Character, Object>();
		ground_pounder_key.put('a', 
				createExactChoice(PFItemClass.getItem("IRON_SINGULARITY_BLOCK").getPFItem())		
		);
		ground_pounder_key.put('b', 
				Material.MACE
		);
		ground_pounder_key.put('c',
				createExactChoice(PFItemClass.getItem("WARDEN_EYE").getPFItem())
		);
		ground_pounder_key.put('d',
				createExactChoice(PFItemClass.getItem("GOLD_SINGULARITY").getPFItem())
		);
		
		add(
				createShapedRecipe(
					"hammer",
					PFItemClass.getItem("Hammer").getPFItem(),
					new String[] {
							"aaa",
							"cda",
							"b  "
					},
					ground_pounder_key
				)
		);
		
		
		//-----------------------
		// Diamond Singularity recipe
		
		Map<Character, Object> diamond_singularity_key = new HashMap<Character, Object>();
		diamond_singularity_key.put('a', Material.DIAMOND_BLOCK);
		
		add(
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
		//-----------------------
		// Emerald Singularity recipe
		
		Map<Character, Object> emerald_singularity_key = new HashMap<Character, Object>();
		emerald_singularity_key.put('a', Material.EMERALD_BLOCK);
		
		add(
				createShapedRecipe(
					"emerald_singularity",
					PFItemClass.getItem("EMERALD_SINGULARITY").getPFItem(),
					new String[] {
							"aaa",
							"aaa",
							"aaa"
					},
					emerald_singularity_key
				)
		);
		//-----------------------
		// Iron Singularity recipe

		Map<Character, Object> iron_singularity_key = new HashMap<Character, Object>();
		iron_singularity_key.put('a', Material.IRON_BLOCK);
		
		add(
				createShapedRecipe(
					"iron_singularity",
					PFItemClass.getItem("IRON_SINGULARITY").getPFItem(),
					new String[] {
							"aaa",
							"aaa",
							"aaa"
					},
					iron_singularity_key
				)
		);
		//-----------------------
		// Gold Singularity recipe
		
		Map<Character, Object> golden_singularity_key = new HashMap<Character, Object>();
		golden_singularity_key.put('a', Material.GOLD_BLOCK);
		
		add(
				createShapedRecipe(
					"golden_singularity",
					PFItemClass.getItem("GOLD_SINGULARITY").getPFItem(),
					new String[] {
							"aaa",
							"aaa",
							"aaa"
					},
					golden_singularity_key
				)
		);
		
		//-----------------------
		
	}
	
}
