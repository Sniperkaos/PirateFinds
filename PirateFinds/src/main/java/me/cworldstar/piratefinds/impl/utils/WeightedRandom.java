package me.cworldstar.piratefinds.impl.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.bukkit.OfflinePlayer;

public class WeightedRandom<T> {
	
	private HashMap<T, Integer> weights_and_values = new HashMap<T, Integer>();
	private Random random;
	private int max_weight;
	
	public WeightedRandom() {
		this.random = new Random();
	}
	
	public WeightedRandom(OfflinePlayer p) {
		this.random = new Random();
		this.random.setSeed(p.getUniqueId().getMostSignificantBits() * System.currentTimeMillis());
	}
	
	public void add(T value, int weight) {
		this.weights_and_values.putIfAbsent(value, weight);
	}
	
	
	
	public T resolve() {
		
		max_weight = 0;
		
		ArrayList<T> values = new ArrayList<T>();
		this.weights_and_values.forEach((T value, Integer weight) -> {
			max_weight += weight;
			for(int i=0; i<weight; i++) {
				values.add(value);
			}
		});
		
		
		return values.get(this.random.nextInt(max_weight));
	}
	
}
