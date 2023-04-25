package moze_intel.projecte.gameObjs.blocks;

import moze_intel.projecte.gameObjs.container.TransmutationContainer;
import moze_intel.projecte.utils.text.PELang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DirectionalBlock;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TransmutationStone extends DirectionalBlock implements SimpleWaterloggedBlock {

    /**
     * Defines the voxel shape for the block in all possible positions
     */
	private static final VoxelShape UP_SHAPE = Block.box(0, 0, 0, 16, 4, 16);
	private static final VoxelShape DOWN_SHAPE = Block.box(0, 12, 0, 16, 16, 16);
	private static final VoxelShape NORTH_SHAPE = Block.box(0, 0, 12, 16, 16, 16);
	private static final VoxelShape SOUTH_SHAPE = Block.box(0, 0, 0, 16, 16, 4);
	private static final VoxelShape WEST_SHAPE = Block.box(12, 0, 0, 16, 16, 16);
	private static final VoxelShape EAST_SHAPE = Block.box(0, 0, 0, 4, 16, 16);

	public TransmutationStone(Properties props) {
		super(props);
		this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.UP).setValue(BlockStateProperties.WATERLOGGED, false));
	}

    /**
     * Overrides the inherited createBlockStateDefinition from the Block Class
     * @param props the properties of the StateDefinition.Builder from the Block class
     */
	@Override
	protected void createBlockStateDefinition(@NotNull StateDefinition.Builder<Block, BlockState> props) {
		super.createBlockStateDefinition(props);
		props.add(FACING, BlockStateProperties.WATERLOGGED);
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
     * @return returns the predefined SHAPE global variable in the direction it's facing
     */
	@NotNull
	@Override
	@Deprecated
	public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext ctx) {
		Direction facing = state.getValue(FACING);
		return switch (facing) {
			case DOWN -> DOWN_SHAPE;
			case NORTH -> NORTH_SHAPE;
			case SOUTH -> SOUTH_SHAPE;
			case WEST -> WEST_SHAPE;
			case EAST -> EAST_SHAPE;
			case UP -> UP_SHAPE;
		};
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
			NetworkHooks.openGui((ServerPlayer) player, new ContainerProvider(), b -> b.writeBoolean(false));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(@NotNull BlockPlaceContext context) {
		BlockState state = super.getStateForPlacement(context);
		return state == null ? null : state.setValue(FACING, context.getClickedFace()).setValue(BlockStateProperties.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
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
     * Rotates the block model and state when called
     * @param state the state of the block
     * @param rot   the rotation state of the block when called
     * @return the state of the block
     */
	@NotNull
	@Override
	@Deprecated
	public BlockState rotate(BlockState state, Rotation rot) {
		return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
	}

    /** 
     * Sets the block to a mirrored state when changed
     * @param state    the state of the block
     * @param mirrorIn the mirror state of the block when called
     * @return the state of the block
     */
	@NotNull
	@Override
	@Deprecated
	public BlockState mirror(BlockState state, Mirror mirrorIn) {
		return state.rotate(mirrorIn.getRotation(state.getValue(FACING)));
	}

	private static class ContainerProvider implements MenuProvider {

		@Override
		public AbstractContainerMenu createMenu(int windowId, @NotNull Inventory playerInventory, @NotNull Player player) {
			return new TransmutationContainer(windowId, playerInventory);
		}

		@NotNull
		@Override
		public Component getDisplayName() {
			return PELang.TRANSMUTATION_TRANSMUTE.translate();
		}
	}
}