package moze_intel.projecte.gameObjs.blocks;

import moze_intel.projecte.gameObjs.block_entities.InterdictionTorchBlockEntity;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TorchBlock;
import net.minecraft.world.level.block.WallTorchBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The InterdictionTorchEntityBlock interface represents a type of block that is associated with an entity.
 * This interface extends the PEEntityBlock interface and specifies the BlockEntityTypeRegistryObject for the InterdictionTorchBlockEntity class.
 */
public interface InterdictionTorchEntityBlock extends PEEntityBlock<InterdictionTorchBlockEntity> {

    /**
     * The InterdictionTorchEntityBlock interface represents a type of block that is associated with an entity.
     * This interface extends the PEEntityBlock interface and specifies the BlockEntityTypeRegistryObject for the InterdictionTorchBlockEntity class.
     */
	@Nullable
	@Override
	default BlockEntityTypeRegistryObject<InterdictionTorchBlockEntity> getType() {
		return PEBlockEntityTypes.INTERDICTION_TORCH;
	}

    /**
     * The InterdictionTorch class represents a type of torch block that is associated with an entity.
     * This class extends the TorchBlock class and implements the InterdictionTorchEntityBlock interface.
     */
	class InterdictionTorch extends TorchBlock implements InterdictionTorchEntityBlock {

        /**
         * Constructs a new InterdictionTorch object with the specified properties and particle type.
         *
         * @param props        The properties to apply to the InterdictionTorch.
         * @param particleType The type of particle to display when the torch is lit.
         */
		public InterdictionTorch(Properties props) {
			super(props, ParticleTypes.SOUL_FIRE_FLAME);
		}

        /**
         * Triggers an event associated with the InterdictionTorchBlockEntity.
         *
         * @param state The block state associated with the InterdictionTorch.
         * @param level The level in which the InterdictionTorch exists.
         * @param pos   The position of the InterdictionTorch.
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
	}

    /**
     * The InterdictionTorchWall class represents a type of wall torch block that is associated with an entity.
     * This class extends the WallTorchBlock class and implements the InterdictionTorchEntityBlock interface.
     */
	class InterdictionTorchWall extends WallTorchBlock implements InterdictionTorchEntityBlock {

        /**
         * Constructs a new InterdictionTorchWall object with the specified properties and particle type.
         *
         * @param props        The properties to apply to the InterdictionTorchWall.
         * @param particleType The type of particle to display when the wall torch is lit.
         */
		public InterdictionTorchWall(Properties props) {
			super(props, ParticleTypes.SOUL_FIRE_FLAME);
		}
        
        /**
         * Triggers an event associated with the InterdictionTorchBlockEntity.
         *
         * @param state The block state associated with the InterdictionTorchWall.
         * @param level The level in which the InterdictionTorchWall exists.
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
	}
}