package moze_intel.projecte.gameObjs.blocks;

import java.util.Random;
import moze_intel.projecte.gameObjs.block_entities.EmcChestBlockEntity;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AlchemicalChest extends BlockDirection implements SimpleWaterloggedBlock, PEEntityBlock<EmcChestBlockEntity> {

	private static final VoxelShape SHAPE = Block.box(1.0D, 0.0D, 1.0D, 15.0D, 14.0D, 15.0D);

    /**
     * Constructs a basic Alchemical Chest with the specified properties
     * @param props the block properties for this block
     */
	public AlchemicalChest(Properties props) {
		super(props);
		this.registerDefaultState(getStateDefinition().any().setValue(FACING, Direction.NORTH).setValue(BlockStateProperties.WATERLOGGED, false));
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
     * The renders the shape of the given custom block
     * It is marked as deprecated and should not be used directly.
     * @param state the state of the block that triggered the event
     * @return returns the shape to be rendered 
     */
	@NotNull
	@Override
	@Deprecated
	public RenderShape getRenderShape(@NotNull BlockState state) {
		return RenderShape.ENTITYBLOCK_ANIMATED;
	}

    /**
     * Override from inherited Block class 
     * It is marked as deprecated and should not be used directly.
     * 
     * @param state  the state of the block that triggered the event
     * @param level  the world level of the block
     * @param pos    the x and y position of the block
     * @param player the player that triggered the event
     * @param hand   the had that was used to trigger the event 
     * @param rtr    result of the player hitting the block
     * @return returns the interaction result after it's consumption
     */
	@NotNull
	@Override
	@Deprecated
	public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand,
			@NotNull BlockHitResult rtr) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		EmcChestBlockEntity chest = WorldHelper.getBlockEntity(EmcChestBlockEntity.class, level, pos, true);
		if (chest != null) {
			NetworkHooks.openGui((ServerPlayer) player, chest, pos);
			player.awardStat(Stats.OPEN_CHEST);
			PiglinAi.angerNearbyPiglins(player, true);
		}
		return InteractionResult.CONSUME;
	}

    
    
    /**
     * Overrides the getType function from the inherited Block Class
     * @return returns the entity type from PEBlockEntityTypes
     */
	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<? extends EmcChestBlockEntity> getType() {
		return PEBlockEntityTypes.ALCHEMICAL_CHEST;
	}

    /**
     *  This method overrides the triggerEvent method from the Block class to handle events triggered on this block.
     * It is marked as deprecated and should not be used directly.
     * 
     * @param state the state of the block that triggered the event
	 * @param level the level in which the block resides
	 * @param pos   the position of the block that triggered the event
	 * @param id    the ID of the event that was triggered
	 * @param param the parameter of the event that was triggered
	 * @return true if the event was handled successfully, false otherwise
     */
	@Override
	@Deprecated
	public boolean triggerEvent(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int id, int param) {
		super.triggerEvent(state, level, pos, id, param);
		return triggerBlockEntityEvent(state, level, pos, id, param);
	}

    /**
     * 
     * @param state  the state of the block
	 * @param level  the level in which the block resides
	 * @param pos    the position of the block
     * @param random random variable from java Random
     */
	@Override
	@Deprecated
	public void tick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random) {
		EmcChestBlockEntity chest = WorldHelper.getBlockEntity(EmcChestBlockEntity.class, level, pos);
		if (chest != null) {
			chest.recheckOpen();
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
		BlockEntity blockEntity = WorldHelper.getBlockEntity(level, pos);
		if (blockEntity != null) {
			return blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).map(ItemHandlerHelper::calcRedstoneFromInventory).orElse(0);
		}
		return 0;
	}

    /**
     * This method overrides the getStateForPlacement from the inherited Block class that gets the state of the block
     * when placed
     * 
     * @param context the given context of the placed block
     * @return returns the getStateForPlacement from the super Block class with the given parameters
     */
	@NotNull
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(BlockStateProperties.WATERLOGGED, context.getLevel().getFluidState(context.getClickedPos()).getType() == Fluids.WATER);
	}

    /**
     * Overrides the getFluidState from the super Block class that gets the state of the fluid in the block
     * It is marked as deprecated and should not be used directly.
     * @param state  the state of the block that triggered the event
     * @return
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
}