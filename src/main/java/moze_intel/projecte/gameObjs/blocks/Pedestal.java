package moze_intel.projecte.gameObjs.blocks;

import java.util.List;
import moze_intel.projecte.api.capabilities.PECapabilities;
import moze_intel.projecte.gameObjs.EnumMatterType;
import moze_intel.projecte.gameObjs.block_entities.DMPedestalBlockEntity;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import moze_intel.projecte.utils.WorldHelper;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pedestal extends Block implements SimpleWaterloggedBlock, PEEntityBlock<DMPedestalBlockEntity>, IMatterBlock {

    /**
     * Defines the voxel shape of the block model
     */
	private static final VoxelShape SHAPE = Shapes.or(
			Block.box(3, 0, 3, 13, 2, 13),
			Shapes.or(
					Block.box(6, 2, 6, 10, 9, 10),
					Block.box(5, 9, 5, 11, 10, 11)
			)
	);

    /** 
     * Basic consructor for the Pedestal Class
     * 
     * @param props the initial properties of the block
     */
	public Pedestal(Properties props) {
		super(props);
		this.registerDefaultState(getStateDefinition().any().setValue(BlockStateProperties.WATERLOGGED, false));
	}

    /**
     * Overrides the inherited createBlockStateDefinition from the Block Class
     * @param props the properties of the StateDefinition.Builder from the Block class
     */
	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> props) {
		super.createBlockStateDefinition(props);
		props.add(BlockStateProperties.WATERLOGGED);
	}

    /**
     * Override from the inherited Block class for pathing the block
     * It is marked as deprecated and should not be used directly.
     * @param state the state of the block
     * @param level the level of the block on the z axis
     * @param pos   the x and y values of the block
     * @param type  the type of the block
     * @return returns false automatically because its not a pathable block
     */
	@Override
	@Deprecated
	public boolean isPathfindable(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull PathComputationType type) {
		return false;
	}

    /**
     * Override from inherited block class to use the custom shape of this block
     * It is marked as deprecated and should not be used directly.
     * @param state the state of the block
     * @param level the z level of the block
     * @param pos   the x and y of the block
     * @param ctx   the collision context of the block 
     * @return returns the predefined SHAPE global variable 
     */
	@NotNull
	@Override
	@Deprecated
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
		return SHAPE;
	}

	/**
     * Private method aimed at letting the block know if there was a dropped item
     * @param level the blocks y position in the world
     * @param pos   the blocks postion based on the x,-,z coordinate
	 * @return True if there was an item and it got dropped, false otherwise.
	 */
	private boolean dropItem(Level level, BlockPos pos) {
		DMPedestalBlockEntity pedestal = WorldHelper.getBlockEntity(DMPedestalBlockEntity.class, level, pos);
		if (pedestal != null) {
			ItemStack stack = pedestal.getInventory().getStackInSlot(0);
			if (!stack.isEmpty()) {
				pedestal.getInventory().setStackInSlot(0, ItemStack.EMPTY);
				level.addFreshEntity(new ItemEntity(level, pos.getX(), pos.getY() + 0.8, pos.getZ(), stack));
				return true;
			}
		}
		return false;
	}

    /**
	 * This method overrides the getAnalogOutputSignal method from the Block class to provide the strength of the
	 * redstone signal produced by this block.
	 * It is marked as deprecated and should not be used directly.
	 *
	 * @param state    the state of the block
	 * @param level    the level in which the block resides
	 * @param pos      the position of the block
     * @param newState the new removed state of the block
     * @param isMoving the bool state of movement of the block
	 * @return the strength of the redstone signal produced by this block
	 */
	@Override
	@Deprecated
	public void onRemove(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			dropItem(level, pos);
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}

    /**
	 * This method helps the pedestal communicate with the game properly regarding attacks made to the block
	 * It is marked as deprecated and should not be used directly.
	 *
	 * @param state  the state of the block
	 * @param level  the level in which the block resides
	 * @param pos    the position of the block
     * @param player the player interacting with the block
	 */
	@Override
	@Deprecated
	public void attack(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player) {
		if (!level.isClientSide) {
			dropItem(level, pos);
		}
	}

    /**
	 * Used to help decide if the block is fully destroyed by the player and what drop the
     * game should generate
	 *
	 * @param state       the state of the block
	 * @param level       the level in which the block resides
	 * @param pos         the position of the block
     * @param player      the player interacting with the block
     * @param willHarvest the state of the harvest condtion in creative
     * @param fluid       the fluid stored in the block
     * @return Boolean if the pedestal is dropped from the player destroying it (false if in creative)
	 */
	@Override
	public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
		if (player.isCreative() && dropItem(level, pos)) {
			//If the player is creative, try to drop the item, and if we succeeded return false to cancel removing the pedestal
			// Note: we notify the block of an update to make sure that it re-appears visually on the client instead of having there
			// be a desync
			level.sendBlockUpdated(pos, state, state, Block.UPDATE_IMMEDIATE);
			return false;
		}
		return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
	}

    /**
     * Called when a player interacts with this block.
     * Opens the GUI for the Pedesal's block entity if the player is a server player.
     * Deprecated so do not use
     * @param state  the block state.
     * @param level  the world level.
     * @param pos    the block position.
     * @param player the player interacting with the block.
     * @param hand   the player's interaction hand.
     * @param rtr    the block hit result.
     * @return {@link InteractionResult#SUCCESS} if this is the client side, {@link InteractionResult#CONSUME} otherwise.
     */
	@NotNull
	@Override
	@Deprecated
	public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand,
			@NotNull BlockHitResult rtr) {
		if (!level.isClientSide) {
			DMPedestalBlockEntity pedestal = WorldHelper.getBlockEntity(DMPedestalBlockEntity.class, level, pos, true);
			if (pedestal == null) {
				return InteractionResult.FAIL;
			}
			ItemStack item = pedestal.getInventory().getStackInSlot(0);
			ItemStack stack = player.getItemInHand(hand);
			if (stack.isEmpty() && !item.isEmpty()) {
				item.getCapability(PECapabilities.PEDESTAL_ITEM_CAPABILITY).ifPresent(pedestalItem -> {
					pedestal.setActive(!pedestal.getActive());
					level.sendBlockUpdated(pos, state, state, Block.UPDATE_IMMEDIATE);
				});
			} else if (!stack.isEmpty() && item.isEmpty()) {
				pedestal.getInventory().setStackInSlot(0, stack.split(1));
				if (stack.getCount() <= 0) {
					player.setItemInHand(hand, ItemStack.EMPTY);
				}
			}
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	// [VanillaCopy] Adapted from NoteBlock
	@Override
	@Deprecated
	public void neighborChanged(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Block neighbor, @NotNull BlockPos neighborPos, boolean isMoving) {
		boolean hasSignal = level.hasNeighborSignal(pos);
		DMPedestalBlockEntity ped = WorldHelper.getBlockEntity(DMPedestalBlockEntity.class, level, pos);
		if (ped != null && ped.previousRedstoneState != hasSignal) {
			if (hasSignal) {
				ItemStack stack = ped.getInventory().getStackInSlot(0);
				if (!stack.isEmpty() && stack.getCapability(PECapabilities.PEDESTAL_ITEM_CAPABILITY).isPresent()) {
					ped.setActive(!ped.getActive());
					level.sendBlockUpdated(pos, state, state, Block.UPDATE_ALL_IMMEDIATE);
				}
			}
			ped.previousRedstoneState = hasSignal;
			ped.markDirty(false);
		}
	}

    /**
	 * This method overrides the hasAnalogOutputSignal method from the Block class to indicate that this block
	 * produces a redstone signal.
	 * It is marked as deprecated and should not be used directly.
	 *
	 * @param state the state of the block
	 * @return true to indicate that this block has an analog output signal
	 */
	@Override
	@Deprecated
	public boolean hasAnalogOutputSignal(@NotNull BlockState state) {
		return true;
	}

    /**
	 * This method overrides the getAnalogOutputSignal method from the Block class to provide the strength of the
	 * redstone signal produced by this block.
	 * It is marked as deprecated and should not be used directly.
	 *
	 * @param state the state of the block
	 * @param level the level in which the block resides
	 * @param pos   the position of the block
	 * @return the strength of the redstone signal produced by this block
	 */
	@Override
	@Deprecated
	public int getAnalogOutputSignal(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
		DMPedestalBlockEntity pedestal = WorldHelper.getBlockEntity(DMPedestalBlockEntity.class, level, pos);
		if (pedestal != null) {
			ItemStack stack = pedestal.getInventory().getStackInSlot(0);
			if (!stack.isEmpty()) {
				if (stack.getCapability(PECapabilities.PEDESTAL_ITEM_CAPABILITY).isPresent()) {
					return pedestal.getActive() ? 15 : 10;
				}
				return 5;
			}
		}
		return 0;
	}

    /**
     * Returns the BlockEntityTypeRegistryObject for the class.
     *
     * @return The BlockEntityTypeRegistryObject for the class.
     * @Nullable This method can return null.
     */
	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<DMPedestalBlockEntity> getType() {
		return PEBlockEntityTypes.DARK_MATTER_PEDESTAL;
	}

    /**
         * Triggers an event associated with the Pedestal.
         * This method is marked as deprecated and shouldn't be used
         *
         * @param state The block state associated with the Pedestal.
         * @param level The level in which the Pedestal exists.
         * @param pos   The position of the Pedestal.
         * @param id    The ID of the event to trigger.
         * @param param The parameter associated with the event.
         * @return true if the block entity event was successfully triggered; false otherwise.
         */
	@Override
	@Deprecated
	public boolean triggerEvent(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int id, int param) {
		super.triggerEvent(state, level, pos, id, param);
		return triggerBlockEntityEvent(state, level, pos, id, param);
	}

	@Override
	public void appendHoverText(@NotNull ItemStack stack, @Nullable BlockGetter level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flags) {
		super.appendHoverText(stack, level, tooltip, flags);
		tooltip.add(PELang.PEDESTAL_TOOLTIP1.translate());
		tooltip.add(PELang.PEDESTAL_TOOLTIP2.translate());
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state == null ? null : state.setValue(BlockStateProperties.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

    /**
     * Overrides the getFluidState from the super Block class that gets the state of the fluid in the block
     * It is marked as deprecated and should not be used directly.
     * @param state  the state of the block that triggered the event
     * @return Fluidstate of the block
     */
	@NotNull
	@Override
	@Deprecated
	public FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
	}

    /**
     * Overrides the inherited UpdateShape method from the class Block and uses it to change the state of the blocks shape
     * It is marked as deprecated and should not be used directly.
     * 
     * @param state       the state of the block
     * @param facing      the direction the block is facing
     * @param facingState the state of the side of the block in the direction it's facing
     * @param level       the world level of the block on the z axis
     * @param currentPos  the current x and y position of the block
     * @param facingPos   the position the block is facing
     * @return returns the updateShape class from the super with tweaked parameters
     */
	@NotNull
	@Override
	@Deprecated
	public BlockState updateShape(@NotNull BlockState state, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level,
			@NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
		if (state.getValue(BlockStateProperties.WATERLOGGED)) {
			level.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(level));
		}
		return super.updateShape(state, facing, facingState, level, currentPos, facingPos);
	}

    /**
    * Gets the matter type of this block.
    * @return The matter type of the block.
    */
	@Override
	public EnumMatterType getMatterType() {
		return EnumMatterType.DARK_MATTER;
	}
}