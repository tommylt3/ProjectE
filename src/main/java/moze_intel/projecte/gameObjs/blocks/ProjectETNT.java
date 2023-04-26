package moze_intel.projecte.gameObjs.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.TntBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**

This class represents a custom TNT block that can be used in Minecraft. It extends the base TNT block and provides

additional functionality such as creating TNT entities and changing its behavior when caught on fire or exploded.
*/
public class ProjectETNT extends TntBlock {

    /**
    *The object responsible for creating TNT entities.
    */
	private final TNTEntityCreator tntEntityCreator;
    
    /**
    * Constructs a new ProjectETNT block with the given properties and TNT entity creator.
    * @param properties       The properties of the block.
    * @param tntEntityCreator The object responsible for creating TNT entities.
    */
	public ProjectETNT(Properties properties, TNTEntityCreator tntEntityCreator) {
		super(properties);
		this.tntEntityCreator = tntEntityCreator;
	}

    /**
    * Returns the flammability of the block, which is always set to 100.
    * @param state The state of the block.
    * @param level The level in which the block is present.
    * @param pos   The position of the block.
    * @param face  The direction in which the block is facing.
    * @return The flammability of the block, which is 100.
    */
	@Override
	public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction face) {
		return 100;
	}

    /**
    * Called when the block is caught on fire. Creates a TNT entity and adds it to the level, and triggers the
    * PRIME_FUSE game event.
    * @param state   The state of the block.
    * @param level   The level in which the block is present.
    * @param pos     The position of the block.
    * @param side    The side of the block that was caught on fire.
    * @param igniter The entity that caused the block to catch on fire, if any.
    */
	@Override
	public void onCaughtFire(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @Nullable Direction side, @Nullable LivingEntity igniter) {
		if (!level.isClientSide) {
			createAndAddEntity(level, pos, igniter);
			level.gameEvent(igniter, GameEvent.PRIME_FUSE, pos);
		}
	}

    /**
    * Creates a TNT entity and adds it to the level at the given position. This method is used to create and add
    * TNT entities for both the onCaughtFire and wasExploded methods.
    * @param level   The level in which to add the entity.
    * @param pos     The position at which to add the entity.
    * @param igniter The entity that caused the block to catch on fire or explode, if any.
    */
	public void createAndAddEntity(@NotNull Level level, @NotNull BlockPos pos, @Nullable LivingEntity igniter) {
		PrimedTnt tnt = tntEntityCreator.create(level, pos.getX() + 0.5F, pos.getY(), pos.getZ() + 0.5F, igniter);
		level.addFreshEntity(tnt);
		level.playSound(null, tnt.getX(), tnt.getY(), tnt.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
	}

    /**
    * Returns a DispenseItemBehavior object that specifies how the block should behave when dispensed from a dispenser.
    * @return A DispenseItemBehavior object that specifies the behavior of the block when dispensed.
    */
	public DispenseItemBehavior createDispenseItemBehavior() {
		//Based off vanilla's TNT behavior
		return new DefaultDispenseItemBehavior() {
			@NotNull
			@Override
			protected ItemStack execute(@NotNull BlockSource source, @NotNull ItemStack stack) {
				BlockPos blockpos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
				createAndAddEntity(source.getLevel(), blockpos, null);
				source.getLevel().gameEvent(null, GameEvent.ENTITY_PLACE, blockpos);
				stack.shrink(1);
				return stack;
			}
		};
	}

    /**
    * This method is called when the TNT block is exploded.
    * It creates a new instance of the PrimedTnt class using the tntEntityCreator instance.
    * It then sets the fuse time of the TNT based on a random number between a quarter and an eighth of the original fuse time.
    * Finally, it adds the new PrimedTnt entity to the level.
    * @param level     The level in which the TNT block exists.
    * @param pos       The position of the TNT block.
    * @param explosion The Explosion instance that caused the TNT to explode.
    */
	@Override
	public void wasExploded(Level level, @NotNull BlockPos pos, @NotNull Explosion explosion) {
		if (!level.isClientSide) {
			PrimedTnt tnt = tntEntityCreator.create(level, (float) pos.getX() + 0.5F, pos.getY(), (float) pos.getZ() + 0.5F, explosion.getSourceMob());
			int fuse = tnt.getFuse();
			tnt.setFuse((short) (level.random.nextInt(fuse / 4) + fuse / 8));
			level.addFreshEntity(tnt);
		}
	}

    /**
    * A functional interface used for creating instances of the PrimedTnt class.
    */
	@FunctionalInterface
	public interface TNTEntityCreator {

        /**
        * Creates a new instance of the PrimedTnt class.
        * @param level   The level in which to create the PrimedTnt instance.
        * @param posX    The x-coordinate of the PrimedTnt entity.
        * @param posY    The y-coordinate of the PrimedTnt entity.
        * @param posZ    The z-coordinate of the PrimedTnt entity.
        * @param igniter The LivingEntity instance that ignited the TNT.
        * @return The new PrimedTnt instance.
        */
		PrimedTnt create(Level level, double posX, double posY, double posZ, @Nullable LivingEntity igniter);
	}
}