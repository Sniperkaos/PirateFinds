package me.cworldstar.piratefinds.impl.food;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.components.FoodComponent;
import org.bukkit.potion.PotionEffect;

public class ChristmasCookie implements FoodComponent {

	@Override
	public Map<String, Object> serialize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNutrition() {
		// TODO Auto-generated method stub
		return 10;
	}

	@Override
	public void setNutrition(int nutrition) {
        throw new IllegalStateException("You cannot set the nutrition of this food.");
	}

	@Override
	public float getSaturation() {
		return 10;
	}

	@Override
	public void setSaturation(float saturation) {
        throw new IllegalStateException("You cannot set the saturation of this food.");
	}

	@Override
	public boolean canAlwaysEat() {
		return true;
	}

	@Override
	public void setCanAlwaysEat(boolean canAlwaysEat) {
        throw new IllegalStateException("You cannot change this food.");
	}

	@Override
	public float getEatSeconds() {
		return 4;
	}

	@Override
	public void setEatSeconds(float eatSeconds) {
        throw new IllegalStateException("You cannot change this food.");
	}

	@Override
	public ItemStack getUsingConvertsTo() {
		return null;
	}

	@Override
	public void setUsingConvertsTo(ItemStack item) {
        throw new IllegalStateException("You cannot change this food.");
	}

	@Override
	public List<FoodEffect> getEffects() {
		return Arrays.asList(new FoodEffect[0]);
	}

	@Override
	public void setEffects(List<FoodEffect> effects) {
        throw new IllegalStateException("You cannot change this food.");
	}

	@Override
	public FoodEffect addEffect(PotionEffect effect, float probability) {
        throw new IllegalStateException("You cannot change this food.");
	}

}
