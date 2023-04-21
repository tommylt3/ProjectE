package moze_intel.projecte.gameObjs.blocks;

import java.util.Optional;
import moze_intel.projecte.api.capabilities.PECapabilities;
import moze_intel.projecte.api.capabilities.item.IItemEmcHolder;
import moze_intel.projecte.gameObjs.EnumCollectorTier;
import moze_intel.projecte.gameObjs.block_entities.CollectorMK1BlockEntity;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import moze_intel.projecte.utils.MathUtils;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a collector block that can generate EMC by pulling in items or fluids from its input face.
 * The collector's tier determines its maximum EMC storage capacity and transfer rate.
 * This block also has an inventory slot for upgrading items that increase its efficiency.
 * Implements the {@link PEEntityBlock} interface with a {@link CollectorMK1BlockEntity} block entity.
 */
public class Collector extends BlockDirection implements PEEntityBlock<CollectorMK1BlockEntity> {

    /**
     * The tier of this collector, which determines its maximum EMC storage capacity and transfer rate.
     */
    private final EnumCollectorTier tier;

    /**
     * Constructs a new collector block with the specified tier and block properties.
     * @param tier the collector's tier, which determines its maximum EMC storage capacity and transfer rate.
     * @param props the block properties for this block.
     */
    public Collector(EnumCollectorTier tier, Properties props) {
        super(props);
        this.tier = tier;
    }

    /**
     * Returns the tier of this collector, which determines its maximum EMC storage capacity and transfer rate.
     * @return the collector's tier.
     */
    public EnumCollectorTier getTier() {
        return tier;
    }

    /**
     * Called when a player interacts with this block.
     * Opens the GUI for the collector's block entity if the player is a server player.
     * @param state the block state.
     * @param level the world level.
     * @param pos the block position.
     * @param player the player interacting with the block.
     * @param hand the player's interaction hand.
     * @param hit the block hit result.
     * @return {@link InteractionResult#SUCCESS} if this is the client side, {@link InteractionResult#CONSUME} otherwise.
     */
    @NotNull
    @Override
    @Deprecated
    public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand,
                                 @NotNull BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }
        CollectorMK1BlockEntity collector = WorldHelper.getBlockEntity(CollectorMK1BlockEntity.class, level, pos, true);
        if (collector != null) {
            NetworkHooks.openGui((ServerPlayer) player, collector, pos);
        }
        return InteractionResult.CONSUME;
    }

    /**
     * Gets the menu provider for the GUI of the collector's block entity.
     * @param state the block state.
     * @param level the world level.
     * @param pos the block position.
     * @return the block entity cast as a {@link MenuProvider}.
     */
    @Nullable
    @Override
    @Deprecated
    public MenuProvider getMenuProvider(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos) {
        return WorldHelper.getBlockEntity(CollectorMK1BlockEntity.class, level, pos, true);
    }

    /**
     * Gets the block entity type for the collector's block entity based on its tier.
     * @return the {@link BlockEntityTypeRegistryObject} for the collector's block entity based on its tier.
     */
    @Nullable
    @Override
    public BlockEntityTypeRegistryObject<? extends CollectorMK1BlockEntity> getType() {
        return switch (tier) {
            case MK1 -> PEBlockEntityTypes.COLLECTOR;
            case MK2 -> PEBlockEntityTypes.COLLECTOR_MK2;
            case MK3 -> PEBlockEntityTypes.COLLECTOR_MK3;
        };
    }

    /**
	 * This method overrides the triggerEvent method from the Block class to handle events triggered on this block.
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
		CollectorMK1BlockEntity collector = WorldHelper.getBlockEntity(CollectorMK1BlockEntity.class, level, pos, true);
		if (collector == null) {
			//If something went wrong fallback to default implementation
			return super.getAnalogOutputSignal(state, level, pos);
		}
		Optional<IItemHandler> cap = collector.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.UP).resolve();
		if (cap.isEmpty()) {
			//If something went wrong fallback to default implementation
			return super.getAnalogOutputSignal(state, level, pos);
		}
		ItemStack charging = cap.get().getStackInSlot(CollectorMK1BlockEntity.UPGRADING_SLOT);
		if (!charging.isEmpty()) {
			Optional<IItemEmcHolder> holderCapability = charging.getCapability(PECapabilities.EMC_HOLDER_ITEM_CAPABILITY).resolve();
			if (holderCapability.isPresent()) {
				IItemEmcHolder emcHolder = holderCapability.get();
				return MathUtils.scaleToRedstone(emcHolder.getStoredEmc(charging), emcHolder.getMaximumEmc(charging));
			}
			return MathUtils.scaleToRedstone(collector.getStoredEmc(), collector.getEmcToNextGoal());
		}
		return MathUtils.scaleToRedstone(collector.getStoredEmc(), collector.getMaximumEmc());
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
	public void onRemove(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState newState, boolean isMoving) {
		if (state.getBlock() != newState.getBlock()) {
			CollectorMK1BlockEntity ent = WorldHelper.getBlockEntity(CollectorMK1BlockEntity.class, level, pos);
			if (ent != null) {
				//Clear the ghost slot so calling super doesn't drop the item in it
				ent.clearLocked();
			}
			super.onRemove(state, level, pos, newState, isMoving);
		}
	}
}
