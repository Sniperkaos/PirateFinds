package me.cworldstar.piratefinds.impl.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringEditor {

	private List<String> base;
	
	public StringEditor(String base) {
		this.base = Arrays.asList(new String[] {
				base
		});
	}
	
	public StringEditor(List<String> lore) {
		this.base = lore;
	}

	private String local_capitalize(String s) {
				
		List<String> words = Arrays.asList(s.split(" "));
		words.replaceAll(word->word.substring(0, 1).toUpperCase().concat(word.substring(1,word.length())));
		
		return words.stream().collect(Collectors.joining(" "));
	}
	
	public StringEditor capitalize() {
		
		this.base.replaceAll(line->local_capitalize(line));
		
		return this;
	}
	
	public StringEditor replace(String pattern, String newValue) {
		base.replaceAll(line->line.replace(pattern, newValue));
		return this;
	}
	
	public List<String> finish() {
		return this.base;
	}
	
	public String finishSingle() {
		return this.base.get(0);
	}
	
}
