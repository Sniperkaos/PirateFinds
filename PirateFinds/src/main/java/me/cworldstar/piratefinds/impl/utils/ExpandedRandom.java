package me.cworldstar.piratefinds.impl.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.bukkit.OfflinePlayer;

public class ExpandedRandom<T> {
	
	private Random random;
	private int max_weight = 100;
	private HashMap<T, Integer> values = new HashMap<T, Integer>();
	
	
	public ExpandedRandom() {
		this.random = new Random();
	}
	
	public ExpandedRandom(OfflinePlayer p) {
		this.random = new Random();
		this.random.setSeed(p.getUniqueId().getMostSignificantBits() * System.currentTimeMillis());
	}
	
	// add value at 1 chance
	public void add(T value, int chance) {
		values.put(value, chance);
	}
	
	public void addAll(List<T> in_values, int chance_for_each) {
		in_values.forEach((T value) -> {
			values.put(value, chance_for_each);
		});
	}
	
	// 1 > 100?
	public boolean tryEvaluate(T object, int chance) {
		if(chance == 100) return true;
		return (this.random.nextInt(chance, max_weight) >= max_weight);
	}
	
	public T resolve() {
		
		values = values.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1,e2)-> e1, LinkedHashMap::new));
		
		for(Entry<T, Integer> sets : values.entrySet()) {
			boolean accepted = tryEvaluate(sets.getKey(), sets.getValue());
			if(accepted) {
				return sets.getKey();
			}
		}
		
		return null;
	}

	public void setMaxChance(int max_chance) {
		this.max_weight = max_chance;
	}


	
}
