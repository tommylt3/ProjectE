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

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.LevelAccessor;

public interface InterdictionTorchEntityBlock extends PEEntityBlock<InterdictionTorchBlockEntity> {

	@Nullable
	@Override
	default BlockEntityTypeRegistryObject<InterdictionTorchBlockEntity> getType() {
		return PEBlockEntityTypes.INTERDICTION_TORCH;
	}

	class InterdictionTorch extends TorchBlock implements InterdictionTorchEntityBlock {

		public InterdictionTorch(Properties props) {
			super(props, ParticleTypes.SOUL_FIRE_FLAME);
		}
        
        public void updateShape(@NotNull BlockState pState, @NotNull Direction pFacing, @NotNull BlockState pFacingState, @NotNull LevelAccessor pLevel, @NotNull BlockPos pCurrentPos, @NotNull BlockPos pFacingPos) {
            super.updateShape(pState,pFacing,pFacingState,pLevel,pCurrentPos,pFacingPos);
        }

        
		@Override
		@Deprecated
		public boolean triggerEvent(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int id, int param) {
			super.triggerEvent(state, level, pos, id, param);
			return triggerBlockEntityEvent(state, level, pos, id, param);
		}
	}

	class InterdictionTorchWall extends WallTorchBlock implements InterdictionTorchEntityBlock {

		public InterdictionTorchWall(Properties props) {
			super(props, ParticleTypes.SOUL_FIRE_FLAME);
		}

		@Override
		@Deprecated
		public boolean triggerEvent(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, int id, int param) {
			super.triggerEvent(state, level, pos, id, param);
			return triggerBlockEntityEvent(state, level, pos, id, param);
		}
	}

}