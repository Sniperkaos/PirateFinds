package me.cworldstar.piratefinds.impl.ae.items.items.masks.effects;

import java.util.HashMap;

import javax.annotation.Nullable;

public class MaskEffects {

	private static HashMap<String, AbstractMaskEffect> effects = new HashMap<String, AbstractMaskEffect>();
	
	public static void registerMaskEffect(String key, AbstractMaskEffect effect) {
		MaskEffects.effects.put(key, effect);
	}

	@Nullable
	public static AbstractMaskEffect getMaskEffect(String maskEffect) {
		return effects.get(maskEffect);
	}
	
	public static final void registerMaskEffects() {
		new SantaMaskEffect();
	}

}
