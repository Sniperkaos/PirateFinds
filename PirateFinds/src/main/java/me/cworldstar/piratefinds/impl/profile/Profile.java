package me.cworldstar.piratefinds.impl.profile;

import java.util.HashMap;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Profile {

	public static final Profile NULL_PROFILE;
	static {
		
		JsonObject object = new JsonObject();
		
		object.add("max_health", new JsonPrimitive(20L));
		object.add("health", new JsonPrimitive(20L));
		
		NULL_PROFILE = fromJSON(object);
	}
	protected HashMap<String, Long> stats = new HashMap<String, Long>();
	
	public static Profile fromJSON(JsonElement profileJson) {
		
		Profile to_return = new Profile();
		
		JsonObject obj = profileJson.getAsJsonObject();
		obj.asMap().forEach((String id, JsonElement element) -> {
			to_return.setStat(id, element.getAsLong());
		});
		
		return to_return;

	}
	
	public String serialize() {
		
		JsonObject serialized = new JsonObject();;
		
		this.stats.forEach((String id, Long value) -> {
			serialized.add(id, new JsonPrimitive(value));
		});
		
		return serialized.toString();
	}
	
	public Profile() {
		
	}
	
	public long getStat(String stat) {
		return this.stats.get(stat);
	}
	
	public void setStat(String stat, long entry) {
		this.stats.put(stat, entry);
	}

}
