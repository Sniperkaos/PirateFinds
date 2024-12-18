package me.cworldstar.piratefinds.impl.utils;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Particle.DustOptions;

public class ParticleUtils {
	public static void summonCircle(Location location, int size, Particle particle) {
	    for (int d = 0; d <= 90; d += 1) {
	        Location particleLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
	        particleLoc.setX(location.getX() + Math.cos(d) * size);
	        particleLoc.setZ(location.getZ() + Math.sin(d) * size);
	        location.getWorld().spawnParticle(particle, particleLoc, 1);
	    }
	}
	
	public static void summonCircle(Location location, int size, Particle particle, Location offset) {
	    for (int d = 0; d <= 90; d += 1) {
	        Location particleLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
	        particleLoc.setX(location.getX() + Math.cos(d) * size);
	        particleLoc.setZ(location.getZ() + Math.sin(d) * size);
	        location.getWorld().spawnParticle(particle, particleLoc, 1, offset.getX(), offset.getY(), offset.getZ());
	    }
	}
	
	public static void summonCircle(Location location, int size, Particle particle, Location offset, double force) {
	    for (int d = 0; d <= 90; d += 1) {
	        Location particleLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
	        particleLoc.setX(location.getX() + Math.cos(d) * size);
	        particleLoc.setZ(location.getZ() + Math.sin(d) * size);
	        location.getWorld().spawnParticle(particle, particleLoc, 1, offset.getX(), offset.getY(), offset.getZ(), force);
	    }
	}
	
	public static void summonCircle(Location location, int size, DustOptions options) {
	    for (int d = 0; d <= 90; d += 1) {
	        Location particleLoc = new Location(location.getWorld(), location.getX(), location.getY(), location.getZ());
	        particleLoc.setX(location.getX() + Math.cos(d) * size);
	        particleLoc.setZ(location.getZ() + Math.sin(d) * size);
	        location.getWorld().spawnParticle(Particle.DUST, particleLoc, 1, options);
	    }
	}
}
