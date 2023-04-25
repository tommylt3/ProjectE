package moze_intel.projecte.gameObjs.blocks;

import moze_intel.projecte.gameObjs.block_entities.CondenserBlockEntity;
import moze_intel.projecte.gameObjs.registration.impl.BlockEntityTypeRegistryObject;
import moze_intel.projecte.gameObjs.registries.PEBlockEntityTypes;
import org.jetbrains.annotations.Nullable;

/**
 * The Condenser class represents a specific type of block entity called a Condenser in the game.
 * This class extends the AlchemicalChest class and inherits its properties and behaviors.
 */
public class Condenser extends AlchemicalChest {

    /**
     * Constructs a new Condenser object with the specified properties.
     *
     * @param props The properties to apply to the Condenser.
     */
	public Condenser(Properties props) {
		super(props);
	}

    /**
     * Returns the BlockEntityTypeRegistryObject for the CondenserBlockEntity class.
     *
     * @return The BlockEntityTypeRegistryObject for the CondenserBlockEntity class.
     * @Nullable This method can return null.
     */
	@Nullable
	@Override
	public BlockEntityTypeRegistryObject<? extends CondenserBlockEntity> getType() {
		return PEBlockEntityTypes.CONDENSER;
	}
}