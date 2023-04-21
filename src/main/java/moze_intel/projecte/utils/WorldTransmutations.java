package moze_intel.projecte.utils;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import moze_intel.projecte.PECore;
import moze_intel.projecte.api.imc.IMCMethods;
import moze_intel.projecte.api.imc.WorldTransmutationEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.fml.InterModComms;
import org.jetbrains.annotations.Nullable;

public final class WorldTransmutations {

	private static List<WorldTransmutationEntry> DEFAULT_ENTRIES = Collections.emptyList();
	private static List<WorldTransmutationEntry> ENTRIES = Collections.emptyList();

	public static void init() {
		registerBackAndForthAllStates(Blocks.WATER, Blocks.LAVA);
		registerConsecutivePairsAllStates(Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.SPRUCE_LOG, Blocks.JUNGLE_LOG, Blocks.ACACIA_LOG, Blocks.DARK_OAK_LOG);
		registerConsecutivePairsAllStates(Blocks.STRIPPED_OAK_LOG, Blocks.STRIPPED_BIRCH_LOG, Blocks.STRIPPED_SPRUCE_LOG, Blocks.STRIPPED_JUNGLE_LOG,
				Blocks.STRIPPED_ACACIA_LOG, Blocks.STRIPPED_DARK_OAK_LOG);
		registerConsecutivePairsAllStates(Blocks.OAK_WOOD, Blocks.BIRCH_WOOD, Blocks.SPRUCE_WOOD, Blocks.JUNGLE_WOOD, Blocks.ACACIA_WOOD, Blocks.DARK_OAK_WOOD);
		registerConsecutivePairsAllStates(Blocks.STRIPPED_OAK_WOOD, Blocks.STRIPPED_BIRCH_WOOD, Blocks.STRIPPED_SPRUCE_WOOD, Blocks.STRIPPED_JUNGLE_WOOD,
				Blocks.STRIPPED_ACACIA_WOOD, Blocks.STRIPPED_DARK_OAK_WOOD);
		registerConsecutivePairsAllStates(Blocks.OAK_LEAVES, Blocks.BIRCH_LEAVES, Blocks.SPRUCE_LEAVES, Blocks.JUNGLE_LEAVES, Blocks.ACACIA_LEAVES, Blocks.DARK_OAK_LEAVES);
		registerConsecutivePairs(Blocks.OAK_SAPLING, Blocks.BIRCH_SAPLING, Blocks.SPRUCE_SAPLING, Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING, Blocks.DARK_OAK_SAPLING);
		registerConsecutivePairs(Blocks.OAK_PLANKS, Blocks.BIRCH_PLANKS, Blocks.SPRUCE_PLANKS, Blocks.JUNGLE_PLANKS, Blocks.ACACIA_PLANKS, Blocks.DARK_OAK_PLANKS);
		registerConsecutivePairsAllStates(Blocks.OAK_SLAB, Blocks.BIRCH_SLAB, Blocks.SPRUCE_SLAB, Blocks.JUNGLE_SLAB, Blocks.ACACIA_SLAB, Blocks.DARK_OAK_SLAB);
		registerConsecutivePairsAllStates(Blocks.OAK_STAIRS, Blocks.BIRCH_STAIRS, Blocks.SPRUCE_STAIRS, Blocks.JUNGLE_STAIRS, Blocks.ACACIA_STAIRS, Blocks.DARK_OAK_STAIRS);
		registerConsecutivePairsAllStates(Blocks.OAK_FENCE, Blocks.BIRCH_FENCE, Blocks.SPRUCE_FENCE, Blocks.JUNGLE_FENCE, Blocks.ACACIA_FENCE, Blocks.DARK_OAK_FENCE);
		registerConsecutivePairs(Blocks.OAK_PRESSURE_PLATE, Blocks.BIRCH_PRESSURE_PLATE, Blocks.SPRUCE_PRESSURE_PLATE, Blocks.JUNGLE_PRESSURE_PLATE,
				Blocks.ACACIA_PRESSURE_PLATE, Blocks.DARK_OAK_PRESSURE_PLATE);
		registerConsecutivePairs(Blocks.WHITE_CONCRETE, Blocks.ORANGE_CONCRETE, Blocks.MAGENTA_CONCRETE, Blocks.LIGHT_BLUE_CONCRETE, Blocks.YELLOW_CONCRETE,
				Blocks.LIME_CONCRETE, Blocks.PINK_CONCRETE, Blocks.GRAY_CONCRETE, Blocks.LIGHT_GRAY_CONCRETE, Blocks.CYAN_CONCRETE, Blocks.PURPLE_CONCRETE,
				Blocks.BLUE_CONCRETE, Blocks.BROWN_CONCRETE, Blocks.GREEN_CONCRETE, Blocks.RED_CONCRETE, Blocks.BLACK_CONCRETE);
		registerConsecutivePairs(Blocks.WHITE_CONCRETE_POWDER, Blocks.ORANGE_CONCRETE_POWDER, Blocks.MAGENTA_CONCRETE_POWDER, Blocks.LIGHT_BLUE_CONCRETE_POWDER,
				Blocks.YELLOW_CONCRETE_POWDER, Blocks.LIME_CONCRETE_POWDER, Blocks.PINK_CONCRETE_POWDER, Blocks.GRAY_CONCRETE_POWDER, Blocks.LIGHT_GRAY_CONCRETE_POWDER,
				Blocks.CYAN_CONCRETE_POWDER, Blocks.PURPLE_CONCRETE_POWDER, Blocks.BLUE_CONCRETE_POWDER, Blocks.BROWN_CONCRETE_POWDER, Blocks.GREEN_CONCRETE_POWDER,
				Blocks.RED_CONCRETE_POWDER, Blocks.BLACK_CONCRETE_POWDER);
		registerConsecutivePairs(Blocks.WHITE_CARPET, Blocks.ORANGE_CARPET, Blocks.MAGENTA_CARPET, Blocks.LIGHT_BLUE_CARPET, Blocks.YELLOW_CARPET, Blocks.LIME_CARPET,
				Blocks.PINK_CARPET, Blocks.GRAY_CARPET, Blocks.LIGHT_GRAY_CARPET, Blocks.CYAN_CARPET, Blocks.PURPLE_CARPET, Blocks.BLUE_CARPET, Blocks.BROWN_CARPET,
				Blocks.GREEN_CARPET, Blocks.RED_CARPET, Blocks.BLACK_CARPET);
		registerConsecutivePairs(Blocks.WHITE_WOOL, Blocks.ORANGE_WOOL, Blocks.MAGENTA_WOOL, Blocks.LIGHT_BLUE_WOOL, Blocks.YELLOW_WOOL, Blocks.LIME_WOOL,
				Blocks.PINK_WOOL, Blocks.GRAY_WOOL, Blocks.LIGHT_GRAY_WOOL, Blocks.CYAN_WOOL, Blocks.PURPLE_WOOL, Blocks.BLUE_WOOL, Blocks.BROWN_WOOL, Blocks.GREEN_WOOL,
				Blocks.RED_WOOL, Blocks.BLACK_WOOL);
		registerConsecutivePairs(Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA, Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA,
				Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA, Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA,
				Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA, Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA);
		registerConsecutivePairs(Blocks.WHITE_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS,
				Blocks.YELLOW_STAINED_GLASS, Blocks.LIME_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS,
				Blocks.CYAN_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS,
				Blocks.RED_STAINED_GLASS, Blocks.BLACK_STAINED_GLASS);
		registerConsecutivePairsAllStates(Blocks.WHITE_STAINED_GLASS_PANE, Blocks.ORANGE_STAINED_GLASS_PANE, Blocks.MAGENTA_STAINED_GLASS_PANE,
				Blocks.LIGHT_BLUE_STAINED_GLASS_PANE, Blocks.YELLOW_STAINED_GLASS_PANE, Blocks.LIME_STAINED_GLASS_PANE, Blocks.PINK_STAINED_GLASS_PANE,
				Blocks.GRAY_STAINED_GLASS_PANE, Blocks.LIGHT_GRAY_STAINED_GLASS_PANE, Blocks.CYAN_STAINED_GLASS_PANE, Blocks.PURPLE_STAINED_GLASS_PANE,
				Blocks.BLUE_STAINED_GLASS_PANE, Blocks.BROWN_STAINED_GLASS_PANE, Blocks.GREEN_STAINED_GLASS_PANE, Blocks.RED_STAINED_GLASS_PANE,
				Blocks.BLACK_STAINED_GLASS_PANE);
		registerBackAndForth(Blocks.SOUL_SAND, Blocks.SOUL_SOIL);
		registerDefault(Blocks.NETHERRACK, Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM);
		registerDefault(Blocks.CRIMSON_NYLIUM, Blocks.WARPED_NYLIUM, Blocks.NETHERRACK);
		registerDefault(Blocks.WARPED_NYLIUM, Blocks.CRIMSON_NYLIUM, Blocks.NETHERRACK);
		registerBackAndForthAllStates(Blocks.CRIMSON_STEM, Blocks.WARPED_STEM);
		registerBackAndForthAllStates(Blocks.STRIPPED_CRIMSON_STEM, Blocks.STRIPPED_WARPED_STEM);
		registerBackAndForth(Blocks.CRIMSON_HYPHAE, Blocks.WARPED_HYPHAE);
		registerBackAndForth(Blocks.STRIPPED_CRIMSON_HYPHAE, Blocks.STRIPPED_WARPED_HYPHAE);
		registerBackAndForth(Blocks.NETHER_WART_BLOCK, Blocks.WARPED_WART_BLOCK);
		registerBackAndForth(Blocks.CRIMSON_FUNGUS, Blocks.WARPED_FUNGUS);
		registerBackAndForth(Blocks.CRIMSON_ROOTS, Blocks.WARPED_ROOTS);
		registerBackAndForth(Blocks.CRIMSON_PLANKS, Blocks.WARPED_PLANKS);
		registerBackAndForthAllStates(Blocks.CRIMSON_SLAB, Blocks.WARPED_SLAB);
		registerBackAndForthAllStates(Blocks.CRIMSON_STAIRS, Blocks.WARPED_STAIRS);
		registerBackAndForthAllStates(Blocks.CRIMSON_FENCE, Blocks.WARPED_FENCE);
		registerBackAndForthAllStates(Blocks.CRIMSON_PRESSURE_PLATE, Blocks.WARPED_PRESSURE_PLATE);

      // TODO Delete This Later
      registerBackAndForthAllStates(Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK);
      registerBackAndForthAllStates(Blocks.RED_MUSHROOM_BLOCK, Blocks.BROWN_MUSHROOM_BLOCK);
      registerBackAndForthAllStates(Blocks.SPONGE, Blocks.WET_SPONGE);
      registerConsecutivePairsAllStates(Blocks.GLOWSTONE, Blocks.MAGMA_BLOCK, Blocks.SHROOMLIGHT);
      registerBackAndForthAllStates(Blocks.HONEY_BLOCK, Blocks.SLIME_BLOCK);
      registerDefault(Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN, Blocks.CARVED_PUMPKIN);
      registerDefault(Blocks.CARVED_PUMPKIN, Blocks.PUMPKIN, Blocks.CARVED_PUMPKIN);
      
      // Light Family
      registerBackAndForthAllStates(Blocks.LANTERN, Blocks.SOUL_LANTERN);
      registerBackAndForthAllStates(Blocks.SOUL_LANTERN, Blocks.LANTERN);
      registerBackAndForthAllStates(Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE);
      registerBackAndForthAllStates(Blocks.SOUL_CAMPFIRE, Blocks.CAMPFIRE);
      registerBackAndForthAllStates(Blocks.TORCH, Blocks.SOUL_TORCH);
      registerBackAndForthAllStates(Blocks.SOUL_TORCH, Blocks.TORCH);
      registerBackAndForthAllStates(Blocks.FIRE, Blocks.SOUL_FIRE);
      registerBackAndForthAllStates(Blocks.SOUL_FIRE, Blocks.FIRE);
      registerConsecutivePairsAllStates(Blocks.ICE,Blocks.PACKED_ICE, Blocks.BLUE_ICE);

      // Ores Family
      registerBackAndForthAllStates(Blocks.RAW_IRON_BLOCK, Blocks.IRON_BLOCK);
      registerBackAndForthAllStates(Blocks.IRON_BLOCK, Blocks.RAW_IRON_BLOCK);
      registerBackAndForthAllStates(Blocks.RAW_GOLD_BLOCK, Blocks.GOLD_BLOCK);
      registerBackAndForthAllStates(Blocks.GOLD_BLOCK, Blocks.RAW_GOLD_BLOCK);
      registerBackAndForthAllStates(Blocks.CRYING_OBSIDIAN, Blocks.OBSIDIAN);
      registerBackAndForthAllStates(Blocks.OBSIDIAN, Blocks.CRYING_OBSIDIAN);


      // Copper Family
      registerDefault(Blocks.RAW_COPPER_BLOCK, Blocks.COPPER_BLOCK, Blocks.COPPER_BLOCK);
      registerConsecutivePairsAllStates(Blocks.COPPER_BLOCK, Blocks.EXPOSED_COPPER, Blocks.WEATHERED_COPPER, Blocks.OXIDIZED_COPPER);
      registerConsecutivePairsAllStates(Blocks.WAXED_COPPER_BLOCK, Blocks.WAXED_EXPOSED_COPPER, Blocks.WAXED_WEATHERED_COPPER, Blocks.WAXED_OXIDIZED_COPPER);
      registerConsecutivePairsAllStates(Blocks.CUT_COPPER, Blocks.EXPOSED_CUT_COPPER, Blocks.WEATHERED_CUT_COPPER, Blocks.OXIDIZED_CUT_COPPER);
      registerConsecutivePairsAllStates(Blocks.WAXED_CUT_COPPER, Blocks.WAXED_EXPOSED_CUT_COPPER, Blocks.WAXED_WEATHERED_CUT_COPPER, Blocks.WAXED_OXIDIZED_CUT_COPPER);
      registerConsecutivePairsAllStates(Blocks.CUT_COPPER_SLAB, Blocks.EXPOSED_CUT_COPPER_SLAB, Blocks.WEATHERED_CUT_COPPER_SLAB, Blocks.OXIDIZED_CUT_COPPER_SLAB);
      registerConsecutivePairsAllStates(Blocks.WAXED_CUT_COPPER_SLAB, Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB, Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB, Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB);
      registerConsecutivePairsAllStates(Blocks.CUT_COPPER_STAIRS, Blocks.EXPOSED_CUT_COPPER_STAIRS, Blocks.WEATHERED_CUT_COPPER_STAIRS, Blocks.OXIDIZED_CUT_COPPER_STAIRS);
      registerConsecutivePairsAllStates(Blocks.WAXED_CUT_COPPER_STAIRS, Blocks.WAXED_EXPOSED_CUT_COPPER_STAIRS, Blocks.WAXED_WEATHERED_CUT_COPPER_STAIRS, Blocks.WAXED_OXIDIZED_CUT_COPPER_STAIRS);

     
      // Stone Families
      registerBackAndForthAllStates(Blocks.STONE, Blocks.SMOOTH_STONE);
      registerBackAndForthAllStates(Blocks.SMOOTH_STONE, Blocks.STONE);
      registerBackAndForthAllStates(Blocks.SMOOTH_STONE_SLAB, Blocks.STONE_SLAB);
      registerBackAndForthAllStates(Blocks.STONE_SLAB, Blocks.SMOOTH_STONE_SLAB);
      registerBackAndForthAllStates(Blocks.COBBLESTONE, Blocks.MOSSY_COBBLESTONE);
      registerBackAndForthAllStates(Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE);
      registerBackAndForthAllStates(Blocks.COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_SLAB);
      registerBackAndForthAllStates(Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.COBBLESTONE_SLAB);
      registerBackAndForthAllStates(Blocks.COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_STAIRS);
      registerBackAndForthAllStates(Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.COBBLESTONE_STAIRS);
      registerBackAndForthAllStates(Blocks.COBBLESTONE_WALL, Blocks.MOSSY_COBBLESTONE_WALL);
      registerBackAndForthAllStates(Blocks.MOSSY_COBBLESTONE_WALL, Blocks.COBBLESTONE_WALL);
      registerBackAndForthAllStates(Blocks.STONE_BRICKS, Blocks.MOSSY_STONE_BRICKS);
      registerBackAndForthAllStates(Blocks.MOSSY_STONE_BRICKS, Blocks.STONE_BRICKS);
      registerBackAndForthAllStates(Blocks.STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_SLAB);
      registerBackAndForthAllStates(Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.STONE_BRICK_SLAB);
      registerBackAndForthAllStates(Blocks.STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_STAIRS);
      registerBackAndForthAllStates(Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.STONE_BRICK_STAIRS);
      registerBackAndForthAllStates(Blocks.STONE_BRICK_WALL, Blocks.MOSSY_STONE_BRICK_WALL);
      registerBackAndForthAllStates(Blocks.MOSSY_STONE_BRICK_WALL, Blocks.STONE_BRICK_WALL);


      // Wish They Were Stone Families
      registerConsecutivePairsAllStates(Blocks.DIORITE, Blocks.ANDESITE, Blocks.GRANITE, Blocks.GRAVEL);
      registerConsecutivePairsAllStates(Blocks.DIORITE_SLAB, Blocks.ANDESITE_SLAB, Blocks.GRANITE_SLAB);
      registerConsecutivePairsAllStates(Blocks.DIORITE_STAIRS, Blocks.ANDESITE_STAIRS, Blocks.GRANITE_STAIRS);
      registerConsecutivePairsAllStates(Blocks.DIORITE_WALL, Blocks.ANDESITE_WALL, Blocks.GRANITE_WALL);
      registerConsecutivePairsAllStates(Blocks.POLISHED_DIORITE, Blocks.POLISHED_ANDESITE, Blocks.POLISHED_GRANITE);
      registerConsecutivePairsAllStates(Blocks.POLISHED_DIORITE_SLAB, Blocks.POLISHED_ANDESITE_SLAB, Blocks.POLISHED_GRANITE_SLAB);
      registerConsecutivePairsAllStates(Blocks.POLISHED_DIORITE_STAIRS, Blocks.POLISHED_ANDESITE_STAIRS, Blocks.POLISHED_GRANITE_STAIRS);


      // Sand + Sandstone
      registerBackAndForthAllStates(Blocks.SAND, Blocks.RED_SAND);
      registerBackAndForthAllStates(Blocks.RED_SAND, Blocks.SAND);
      registerBackAndForthAllStates(Blocks.SANDSTONE, Blocks.RED_SANDSTONE);
      registerBackAndForthAllStates(Blocks.RED_SANDSTONE, Blocks.SANDSTONE);
      registerBackAndForthAllStates(Blocks.CHISELED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE);
      registerBackAndForthAllStates(Blocks.CHISELED_RED_SANDSTONE, Blocks.CHISELED_SANDSTONE);
      registerBackAndForthAllStates(Blocks.SMOOTH_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
      registerBackAndForthAllStates(Blocks.SMOOTH_RED_SANDSTONE, Blocks.SMOOTH_SANDSTONE);
      registerBackAndForthAllStates(Blocks.SANDSTONE_SLAB, Blocks.RED_SANDSTONE_SLAB);
      registerBackAndForthAllStates(Blocks.RED_SANDSTONE_SLAB, Blocks.SANDSTONE_SLAB);
      registerBackAndForthAllStates(Blocks.SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_STAIRS);
      registerBackAndForthAllStates(Blocks.RED_SANDSTONE_STAIRS, Blocks.SANDSTONE_STAIRS);
      registerBackAndForthAllStates(Blocks.SANDSTONE_WALL, Blocks.RED_SANDSTONE_WALL);
      registerBackAndForthAllStates(Blocks.RED_SANDSTONE_WALL, Blocks.SANDSTONE_WALL);
      registerBackAndForthAllStates(Blocks.SMOOTH_SANDSTONE_STAIRS, Blocks.SMOOTH_RED_SANDSTONE_STAIRS);
      registerBackAndForthAllStates(Blocks.SMOOTH_RED_SANDSTONE_STAIRS, Blocks.SMOOTH_SANDSTONE_STAIRS);
      registerBackAndForthAllStates(Blocks.SMOOTH_SANDSTONE_SLAB, Blocks.SMOOTH_RED_SANDSTONE_SLAB);
      registerBackAndForthAllStates(Blocks.SMOOTH_RED_SANDSTONE_SLAB, Blocks.SMOOTH_SANDSTONE_SLAB);
      registerBackAndForthAllStates(Blocks.CUT_SANDSTONE, Blocks.CUT_RED_SANDSTONE);
      registerBackAndForthAllStates(Blocks.CUT_RED_SANDSTONE, Blocks.CUT_SANDSTONE);
      registerBackAndForthAllStates(Blocks.CUT_SANDSTONE_SLAB, Blocks.CUT_RED_SANDSTONE_SLAB);
      registerBackAndForthAllStates(Blocks.CUT_RED_SANDSTONE_SLAB, Blocks.CUT_SANDSTONE_SLAB);
      
      // Glazed Terracotta
      registerConsecutivePairsAllStates(Blocks.WHITE_GLAZED_TERRACOTTA, Blocks.ORANGE_GLAZED_TERRACOTTA, Blocks.MAGENTA_GLAZED_TERRACOTTA, Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA, Blocks.YELLOW_GLAZED_TERRACOTTA,
				Blocks.LIME_GLAZED_TERRACOTTA, Blocks.PINK_GLAZED_TERRACOTTA, Blocks.GRAY_GLAZED_TERRACOTTA, Blocks.LIGHT_GRAY_GLAZED_TERRACOTTA, Blocks.CYAN_GLAZED_TERRACOTTA,
				Blocks.PURPLE_GLAZED_TERRACOTTA, Blocks.BLUE_GLAZED_TERRACOTTA, Blocks.BROWN_GLAZED_TERRACOTTA, Blocks.GREEN_GLAZED_TERRACOTTA, Blocks.RED_GLAZED_TERRACOTTA, Blocks.BLACK_GLAZED_TERRACOTTA);

      // Nature
      registerConsecutivePairs(Blocks.ALLIUM, Blocks.AZURE_BLUET,  Blocks.BLUE_ORCHID,  Blocks.CORNFLOWER, Blocks. DANDELION,  Blocks.LILAC, Blocks.LILY_OF_THE_VALLEY, Blocks.ORANGE_TULIP,  Blocks.OXEYE_DAISY, Blocks.PEONY, Blocks.PINK_TULIP,  Blocks.POPPY,  Blocks.RED_TULIP,  Blocks.ROSE_BUSH,  Blocks.SUNFLOWER,  Blocks.WHITE_TULIP);
      registerBackAndForthAllStates(Blocks.LARGE_FERN, Blocks.FERN);
      registerBackAndForthAllStates(Blocks.FERN, Blocks.LARGE_FERN);
      registerBackAndForthAllStates(Blocks.TALL_GRASS, Blocks.GRASS);
      registerBackAndForthAllStates(Blocks.GRASS, Blocks.TALL_GRASS);
      registerConsecutivePairsAllStates(Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.ROOTED_DIRT, Blocks.DIRT_PATH);
      registerConsecutivePairsAllStates(Blocks.GRASS_BLOCK, Blocks.MYCELIUM, Blocks.PODZOL);

      // Dyed Things
      registerConsecutivePairsAllStates(Blocks.CANDLE, Blocks.RED_CANDLE, Blocks.BLUE_CANDLE, Blocks.PURPLE_CANDLE, Blocks.BLACK_CANDLE, Blocks.BROWN_CANDLE, Blocks.CYAN_CANDLE, Blocks.GREEN_CANDLE, Blocks.GRAY_CANDLE, Blocks.LIGHT_BLUE_CANDLE, Blocks.LIGHT_GRAY_CANDLE, Blocks.LIME_CANDLE, Blocks.MAGENTA_CANDLE, Blocks.ORANGE_CANDLE, Blocks.PINK_CANDLE,Blocks.WHITE_CANDLE, Blocks.YELLOW_CANDLE);
      registerConsecutivePairsAllStates(Blocks.RED_BED, Blocks.BLUE_BED, Blocks.PURPLE_BED, Blocks.BLACK_BED, Blocks.BROWN_BED, Blocks.CYAN_BED, Blocks.GREEN_BED, Blocks.GRAY_BED, Blocks.LIGHT_BLUE_BED, Blocks.LIGHT_GRAY_BED, Blocks.LIME_BED, Blocks.MAGENTA_BED, Blocks.ORANGE_BED, Blocks.PINK_BED,Blocks.WHITE_BED, Blocks.YELLOW_BED);
      registerConsecutivePairsAllStates(Blocks.RED_CANDLE_CAKE, Blocks.BLUE_CANDLE_CAKE, Blocks.PURPLE_CANDLE_CAKE, Blocks.BLACK_CANDLE_CAKE, Blocks.BROWN_CANDLE_CAKE, Blocks.CYAN_CANDLE_CAKE, Blocks.GREEN_CANDLE_CAKE, Blocks.GRAY_CANDLE_CAKE, Blocks.LIGHT_BLUE_CANDLE_CAKE, Blocks.LIGHT_GRAY_CANDLE_CAKE, Blocks.LIME_CANDLE_CAKE, Blocks.MAGENTA_CANDLE_CAKE, Blocks.ORANGE_CANDLE_CAKE, Blocks.PINK_CANDLE_CAKE,Blocks.WHITE_CANDLE_CAKE, Blocks.YELLOW_CANDLE_CAKE);
      registerConsecutivePairsAllStates(Blocks.SHULKER_BOX, Blocks.RED_SHULKER_BOX, Blocks.BLUE_SHULKER_BOX, Blocks.PURPLE_SHULKER_BOX, Blocks.BLACK_SHULKER_BOX, Blocks.BROWN_SHULKER_BOX, Blocks.CYAN_SHULKER_BOX, Blocks.GREEN_SHULKER_BOX, Blocks.GRAY_SHULKER_BOX, Blocks.LIGHT_BLUE_SHULKER_BOX, Blocks.LIGHT_GRAY_SHULKER_BOX, Blocks.LIME_SHULKER_BOX, Blocks.MAGENTA_SHULKER_BOX, Blocks.ORANGE_SHULKER_BOX, Blocks.PINK_SHULKER_BOX,Blocks.WHITE_SHULKER_BOX, Blocks.YELLOW_SHULKER_BOX);
	}

   public void commandsSetTransmutation(Blocks... blocks){
      ArrayList<Blocks> listOfBlocks = new ArrayList<>(Arrays.asList(blocks));
      // registerConsecutivePairsAllStates(listOfBlocks.toArray(new Blocks[0]));
   }

	@Nullable
	public static BlockState getWorldTransmutation(BlockState current, boolean isSneaking) {
		for (WorldTransmutationEntry e : ENTRIES) {
			if (e.origin() == current) {
				return isSneaking ? e.altResult() : e.result();
			}
		}
		return null;
	}

	public static List<WorldTransmutationEntry> getWorldTransmutations() {
		return ENTRIES;
	}

	public static void setWorldTransmutation(List<WorldTransmutationEntry> entries) {
		DEFAULT_ENTRIES = ImmutableList.copyOf(entries);
		resetWorldTransmutations();
	}

	public static void resetWorldTransmutations() {
		//Make it so that ENTRIES are mutable so we can modify it with CraftTweaker
		ENTRIES = new ArrayList<>(DEFAULT_ENTRIES);
	}

	public static void register(BlockState from, BlockState result, @Nullable BlockState altResult) {
		ENTRIES.add(new WorldTransmutationEntry(from, result, altResult));
	}

	private static void registerIMC(BlockState from, BlockState result, @Nullable BlockState altResult) {
		InterModComms.sendTo(PECore.MODID, IMCMethods.REGISTER_WORLD_TRANSMUTATION, () -> new WorldTransmutationEntry(from, result, altResult));
	}

	private static void registerDefault(Block from, Block result, @Nullable Block altResult) {
		registerIMC(from.defaultBlockState(), result.defaultBlockState(), altResult == null ? null : altResult.defaultBlockState());
	}

	private static void registerAllStates(Block from, Block result, @Nullable Block altResult) {
		StateDefinition<Block, BlockState> stateContainer = from.getStateDefinition();
		ImmutableList<BlockState> validStates = stateContainer.getPossibleStates();
		for (BlockState validState : validStates) {
			try {
				BlockState resultState = copyProperties(validState, result.defaultBlockState());
				BlockState altResultState = altResult == null ? null : copyProperties(validState, altResult.defaultBlockState());
				registerIMC(validState, resultState, altResultState);
			} catch (IllegalArgumentException e) {
				//Something went wrong skip adding a conversion for this but log that we failed
				// This should never happen unless some mod is doing really weird things to the
				// BlockStates like try to add more BlockStates for a block (this will fail in
				// a lot of other ways, but just in case don't hard crash the game let them do
				// so instead). The other case this may fail is if something changed between
				// MC versions and we need to fix some conversion that no longer necessarily
				// makes sense
				PECore.LOGGER.error("Something went wrong registering conversions for " + from.getRegistryName(), e);
			}
		}
	}

	private static BlockState copyProperties(BlockState source, BlockState target) {
		ImmutableMap<Property<?>, Comparable<?>> values = source.getValues();
		for (Entry<Property<?>, Comparable<?>> entry : values.entrySet()) {
			target = applyProperty(target, entry.getKey(), entry.getValue());
		}
		return target;
	}

	private static <T extends Comparable<T>, V extends T> BlockState applyProperty(BlockState target, Property<T> property, Comparable<?> value) {
		return target.setValue(property, (V) value);
	}

	private static void registerBackAndForth(Block first, Block second) {
		registerDefault(first, second, null);
		registerDefault(second, first, null);
	}

	private static void registerBackAndForthAllStates(Block first, Block second) {
		registerAllStates(first, second, null);
		registerAllStates(second, first, null);
	}

	private static void registerConsecutivePairs(RegisterBlock registerMethod, Block... blocks) {
		for (int i = 0; i < blocks.length; i++) {
			Block prev = i == 0 ? blocks[blocks.length - 1] : blocks[i - 1];
			Block cur = blocks[i];
			Block next = i == blocks.length - 1 ? blocks[0] : blocks[i + 1];
			registerMethod.register(cur, next, prev);
		}
	}

	private static void registerConsecutivePairs(Block... blocks) {
		registerConsecutivePairs(WorldTransmutations::registerDefault, blocks);
	}

	private static void registerConsecutivePairsAllStates(Block... blocks) {
		registerConsecutivePairs(WorldTransmutations::registerAllStates, blocks);
	}

	@FunctionalInterface
	private interface RegisterBlock {

		void register(Block from, Block result, @Nullable Block altResult);
	}
}