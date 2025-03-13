package me.cworldstar.piratefinds.impl.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VectorUtils {
	public static Vector toObjectSpace(Location object, Location abs) {
        double x = abs.getX() - object.getX();
        double y = abs.getY() - object.getY();
        double z = abs.getZ() - object.getZ();
        return new Vector(x, y, z);
	}
}
