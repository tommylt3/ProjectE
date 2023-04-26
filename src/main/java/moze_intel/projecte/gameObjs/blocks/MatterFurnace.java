package moze_intel.projecte.gameObjs.blocks;

import moze_intel.projecte.gameObjs.EnumMatterType;
import moze_intel.projecte.gameObjs.block_entities.DMFurnaceBlockEntity;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AbstractFurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**

    A MatterFurnace is a special furnace block that smelts items using fuel and emits redstone signals based on its contents.
    Implements the IMatterBlock interface to provide the matter type of the block.
    Implements the PEEntityBlock interface to define the block entity type for this block.
*/
public class MatterFurnace extends AbstractFurnaceBlock implements IMatterBlock, PEEntityBlock<DMFurnaceBlockEntity> {

    /**
    The matter type of this block.
    */
	private final EnumMatterType matterType;

    
    /**
    * Constructs a new MatterFurnace with the specified properties and matter type.
    * @param props The properties of the block.
    * @param type  The matter type of the block.
    */
	public MatterFurnace(Properties props, EnumMatterType type) {
		super(props);
		this.matterType = type;
	}

    /**
    * Gets the block entity type of this block.
    * @return The block entity type of this block.
    */
	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<? extends DMFurnaceBlockEntity> getType() {
		return matterType == EnumMatterType.RED_MATTER ? PEBlockEntityTypes.RED_MATTER_FURNACE : PEBlockEntityTypes.DARK_MATTER_FURNACE;
	}

    /**
    * Opens the container for this furnace block.
    * @param level  The level in which the furnace block is present.
    * @param pos    The position of the furnace block.
    * @param player The player that is interacting with the furnace block.
    */
	@Override
	protected void openContainer(Level level, @NotNull BlockPos pos, @NotNull Player player) {
		if (!level.isClientSide) {
			DMFurnaceBlockEntity furnace = WorldHelper.getBlockEntity(DMFurnaceBlockEntity.class, level, pos, true);
			if (furnace != null) {
				NetworkHooks.openGui((ServerPlayer) player, furnace, pos);
			}
		}
	}
    
    /**
    * Called before a block is removed from the world.
    * Drops the contents of the furnace when it is destroyed.
    * @param state The current state of the block.
    * @param level The level in which the block is present.
    * @param pos The position of the block.
    * @param newState The new state of the block.
    * @param isMoving Whether the block is being moved or not.
    */
	@Override
	@Deprecated
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			BlockEntity furnace = WorldHelper.getBlockEntity(level, pos);
			if (furnace != null) {
				furnace.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(inv -> WorldHelper.dropInventory(inv, level, pos));
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

    /**
    * Gets the redstone signal strength emitted by this block.
    * @param state The current state of the block.
    * @param level The level in which the block is present.
    * @param pos   The position of the block.
    * @return The redstone signal strength emitted by this block.
    */
	@Override
	public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		BlockEntity blockEntity = WorldHelper.getBlockEntity(level, pos);
		if (blockEntity != null) {
			return blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(ItemHandlerHelper::calcRedstoneFromInventory).orElse(0);
		}
		return 0;
	}

    /**
    * Gets the matter type of this block.
    * @return The matter type of the block.
    */
	@Override
	public EnumMatterType getMatterType() {
		return matterType;
	}
}