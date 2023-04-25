package moze_intel.projecte.gameObjs.blocks;

import moze_intel.projecte.gameObjs.EnumRelayTier;
import moze_intel.projecte.gameObjs.block_entities.RelayMK1BlockEntity;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import moze_intel.projecte.utils.MathUtils;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Relay extends BlockDirection implements PEEntityBlock<RelayMK1BlockEntity> {

    /**
     * Defines the tier of the block for use in other methods
     */
	private final EnumRelayTier tier;

    /**
    * Constructs a new Relay with the specified properties and matter type.
    * @param tier  The tier of the block.
    * @param props The properties of the block.
    */
	public Relay(EnumRelayTier tier, Properties props) {
		super(props);
		this.tier = tier;
	}

    /**
     * A getter function for the stored tier of the block
     * @return the tier of the Relay
     */
	public EnumRelayTier getTier() {
		return tier;
	}

    /**
     * Called when a player interacts with this block.
     * Opens the GUI for the Relay's block entity if the player is a server player.
     * Deprecated so do not use
     * @param state the block state.
     * @param level the world level.
     * @param pos the block position.
     * @param player the player interacting with the block.
     * @param hand the player's interaction hand.
     * @param rtr the block hit result.
     * @return {@link InteractionResult#SUCCESS} if this is the client side, {@link InteractionResult#CONSUME} otherwise.
     */
	@NotNull
	@Override
	@Deprecated
	public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand,
			@NotNull BlockHitResult rtr) {
		if (level.isClientSide) {
			return InteractionResult.SUCCESS;
		}
		RelayMK1BlockEntity relay = WorldHelper.getBlockEntity(RelayMK1BlockEntity.class, level, pos, true);
		if (relay != null) {
			NetworkHooks.openGui((ServerPlayer) player, relay, pos);
		}
		return InteractionResult.CONSUME;
	}

    /**
     * Returns the BlockEntityTypeRegistryObject for the Relay class.
     *
     * @return The BlockEntityTypeRegistryObject for the Relay class.
     * @Nullable This method can return null.
     */
	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<? extends RelayMK1BlockEntity> getType() {
		return switch (tier) {
			case MK1 -> PEBlockEntityTypes.RELAY;
			case MK2 -> PEBlockEntityTypes.RELAY_MK2;
			case MK3 -> PEBlockEntityTypes.RELAY_MK3;
		};
	}

    /**
         * Triggers an event associated with the Relay Block.
         *
         * @param state The block state associated with the Relay.
         * @param level The level in which the Relay exists.
         * @param pos   The position
         * @param id    The ID of the event to trigger.
         * @param param The parameter associated with the event.
         * 
         * @return true if the block entity event was successfully triggered; false otherwise.
         * @deprecated This method is deprecated and should not be used.
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
		RelayMK1BlockEntity relay = WorldHelper.getBlockEntity(RelayMK1BlockEntity.class, level, pos, true);
		if (relay == null) {
			return 0;
		}
		return MathUtils.scaleToRedstone(relay.getStoredEmc(), relay.getMaximumEmc());
	}
}