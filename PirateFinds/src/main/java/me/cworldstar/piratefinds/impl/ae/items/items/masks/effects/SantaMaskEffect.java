package me.cworldstar.piratefinds.impl.ae.items.items.masks.effects;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.cworldstar.piratefinds.impl.utils.ParticleUtils;

public class SantaMaskEffect extends AbstractMaskEffect {
	public SantaMaskEffect() {
	
		this.addHandler((Player player, ItemStack item) -> {
			player.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 60, 1));
			player.addPotionEffect(new PotionEffect(PotionEffectType.HEALTH_BOOST, 60, 4));
			Random random = new Random();
			ParticleUtils.summonCircle(player.getEyeLocation(), 1, Particle.SNOWFLAKE, new Location(player.getWorld(),0,random.nextDouble(-1, 1),0), 0);
		});
		
		MaskEffects.registerMaskEffect("SANTA_MASK_EFFECT", this);
	}
}
