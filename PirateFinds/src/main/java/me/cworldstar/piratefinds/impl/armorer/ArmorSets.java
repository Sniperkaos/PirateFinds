package me.cworldstar.piratefinds.impl.armorer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.OfflinePlayer;
import org.checkerframework.checker.nullness.qual.NonNull;

import me.cworldstar.piratefinds.impl.utils.WeightedRandom;
import net.advancedplugins.ae.features.sets.SetsAPI;
import net.advancedplugins.ae.features.sets.SetsManager;

public class ArmorSets {
	
	public static HashMap<OfflinePlayer, String> lastSet = new HashMap<OfflinePlayer, String>();
	public static HashMap<OfflinePlayer, String> lastPiece = new HashMap<OfflinePlayer, String>();
	public static HashMap<OfflinePlayer, String> Guaranteed = new HashMap<OfflinePlayer, String>();

	
	public static void updateLastSet(OfflinePlayer player, String set) {
		ArmorSets.lastSet.put(player, set);
	}
	
	public static void updateLastPiece(OfflinePlayer player, String set) {
		ArmorSets.lastPiece.put(player ,set);
	}
	
	public static String getLastPiece(OfflinePlayer player) {
		return ArmorSets.lastPiece.get(player);
	}
	
	public static String getLastSet(OfflinePlayer player) {
		return ArmorSets.lastSet.get(player);
	}
	
	
	private static enum Weight {
		VERY_HIGH(200),
		HIGH(100),
		MEDIUM(70),
		LOW(35),
		VERY_LOW(10),
		NEARLY_IMPOSSIBLE(1);
		
		private final int weight;
		
		private Weight(int weight) {
			this.weight = weight;
		}
		
		public int getWeight() {
			return this.weight;
		}
		
	}
	
	public static enum Sets {
			DIMENSIONAL_TRAVELLER("DimensionalTraveller", 1, "Dimensional Traveller Set", Weight.HIGH),
			KOTH("Koth", 2, "KoTH Set"),
			PHANTOM("Phantom", 3, "Phantom Set"),
			SUPREME("Supreme", 4, "Supreme Set"),
			YETI("Yeti", 5, "Yeti Set"),
			YIJKI("Yijki", 6, "Yijki Set"),
			TURTLE("Turtle", 7, "Turtle Set", Weight.VERY_HIGH, new Pieces[] {
					Pieces.HELMET,
					Pieces.CHESTPLATE,
					Pieces.LEGGINGS,
					Pieces.BOOTS
					
			});
		
			private String setString;
			private String name;
			private Weight weight;
			private int list_place;
			private Pieces[] pieces = new Pieces[] {
					Pieces.HELMET,
					Pieces.CHESTPLATE,
					Pieces.LEGGINGS,
					Pieces.BOOTS
			};
			
			private Sets(String string, int integer, String localized_name, Weight weight) {
				this.setString = string;
				this.list_place = integer;
				this.name = localized_name;
				this.weight = weight;
				
			}
			
			private Sets(String string, int integer, String localized_name, Weight weight, Pieces[] pieces) {
				this.setString = string;
				this.list_place = integer;
				this.name = localized_name;
				this.weight = weight;
				this.pieces = pieces;
			}
			
			private Sets(String string, int integer, String localized_name) {
				this.setString = string;
				this.list_place = integer;
				this.name = localized_name;
				this.weight = Weight.MEDIUM;
			}
			
			public Pieces[] getPieces() {
				return this.pieces;
			}
			
			public int getWeight() {
				return this.weight.getWeight();
			}
			
			public String getSetString() {
				return this.setString;
			}
			
			public String getSetDisplayName() {
				return this.name;
			}
			
			public static int getListSize() {
				return Pieces.values().length;
			}
			
			public int getListPlace() {
				return this.list_place;
			}
			
			@NonNull
			public static String getSetFromInteger(int integer) {
				for (Sets p_enum : Sets.values()) {
					if(p_enum.getListPlace() == integer) {
						return p_enum.getSetString();
					}
				}
				return "NONE";
			}

			public static Sets getSetFromName(String armor_set_id) {
				for (Sets p_enum : Sets.values()) {
					if(p_enum.getSetString() == armor_set_id) {
						return p_enum;
					}
				}
				
				return Sets.TURTLE;
			}
	}

	
	public static enum Pieces {
		
		HELMET("HELMET", 0),
		CHESTPLATE("CHESTPLATE", 1),
		LEGGINGS("LEGGINGS", 2),
		BOOTS("BOOTS", 3);
		
		
		private String pieceString;
		private int list_place;
		
		private Pieces(String string, int integer) {
			this.pieceString = string;
			this.list_place = integer;
			
		}
		
		public String getPieceString() {
			return this.pieceString;
		}
		
		public int getListPlace() {
			return this.list_place;
		}
		
		
		public static int getListSize() {
			return Pieces.values().length;
		}
		
		@NonNull
		public static Pieces getPieceFromInteger(int integer) {
			for (Pieces p_enum : Pieces.values()) {
				if(p_enum.getListPlace() == integer) {
					return p_enum;
				}
			}
			return HELMET;
		}
	}
	
	public ArmorSets() {
		throw new IllegalStateException("Static class");
	}
	
	public static String randomSet(OfflinePlayer player) {
		
		WeightedRandom<String> random = new WeightedRandom<String>(player);
		for(Sets set : Sets.values()) {
			random.add(set.setString, set.getWeight());
		}
		
		String guaranteed = Guaranteed.get(player);
		if(guaranteed != null) {
			Guaranteed.remove(player);
			return guaranteed;
		}
		
		int set = Sets.getSetFromName(random.resolve()).getListPlace();
		if(set == 0) {
			set = 1;
		}
		
		String return_set = Sets.getSetFromInteger(set);
		if(return_set == "NONE") {
			return "VOTE";
		}
		// number
		
		return return_set;
		
	}
	
	public static String randomPiece(OfflinePlayer player) {
		Random random = new Random();
		random.setSeed(player.getUniqueId().getMostSignificantBits() * System.currentTimeMillis());
		int piece = random.nextInt(Pieces.getListSize());
		Pieces return_piece = Pieces.getPieceFromInteger(piece);
		if(Arrays.asList(Sets.getSetFromName(lastSet.get(player)).getPieces()).contains(return_piece)) {
			return return_piece.getPieceString();
		} else {
			return randomPiece(player);
		}
		// number
	}

	public static void guaranteeNextForge(OfflinePlayer player, String string) {
		Guaranteed.put(player, string);	
	}

	public static String randomPiece() {
		Random random = new Random();
		random.setSeed(UUID.randomUUID().getMostSignificantBits() * System.currentTimeMillis());
		
		int piece = random.nextInt(Pieces.getListSize());
		
		if(piece == 0) {
			piece = 1;
		}
		
		Pieces return_piece = Pieces.getPieceFromInteger(piece);
		
		return return_piece.getPieceString();
	}
	
}
