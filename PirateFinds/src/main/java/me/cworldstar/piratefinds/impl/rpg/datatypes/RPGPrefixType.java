package me.cworldstar.piratefinds.impl.rpg.datatypes;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import me.cworldstar.piratefinds.impl.rpg.RPGPrefix;

public class RPGPrefixType implements PersistentDataType<PersistentDataContainer, RPGPrefix> {

	@Override
	public Class<PersistentDataContainer> getPrimitiveType() {
		return PersistentDataContainer.class;
	}

	@Override
	public Class<RPGPrefix> getComplexType() {
		return RPGPrefix.class;
	}

	@Override
	public PersistentDataContainer toPrimitive(RPGPrefix complex, PersistentDataAdapterContext context) {
		return complex.toPrimitive(context);
	}

	@Override
	public RPGPrefix fromPrimitive(PersistentDataContainer primitive, PersistentDataAdapterContext context) {
		return null;
	}

}
