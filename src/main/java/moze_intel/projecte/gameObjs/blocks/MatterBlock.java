package moze_intel.projecte.gameObjs.blocks;

import moze_intel.projecte.gameObjs.EnumMatterType;
import net.minecraft.world.level.block.Block;

/**
* A block that represents matter in the game world.
*
* Implements the IMatterBlock interface to provide the matter type of the block.
*/
public class MatterBlock extends Block implements IMatterBlock {

    /**
    *
    * The matter type of this block.
    */
	public final EnumMatterType matterType;

    /**
    * Constructs a new MatterBlock with the specified properties and matter type.
    * @param props The properties of the block.
    * @param type  The matter type of the block.
    */
	public MatterBlock(Properties props, EnumMatterType type) {
		super(props);
		this.matterType = type;
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