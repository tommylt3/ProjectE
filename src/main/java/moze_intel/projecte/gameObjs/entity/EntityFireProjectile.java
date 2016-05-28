package moze_intel.projecte.gameObjs.entity;

import moze_intel.projecte.utils.PlayerHelper;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntityFireProjectile extends PEProjectile
{
	public EntityFireProjectile(World world)
	{
		super(world);
	}

	public EntityFireProjectile(World world, EntityPlayer entity)
	{
		super(world, entity);
	}

	public EntityFireProjectile(World world, double x, double y, double z)
	{
		super(world, x, y, z);
	}

	@Override
	protected void apply(RayTraceResult mop)
	{
		if(!worldObj.isRemote && mop.typeOfHit == RayTraceResult.Type.BLOCK)
		{
			BlockPos pos = mop.getBlockPos();
			Block block = worldObj.getBlockState(pos).getBlock();
			
			if(block == Blocks.OBSIDIAN)
			{
				worldObj.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
			}
			else if(block == Blocks.SAND)
			{
				for (BlockPos currentPos : WorldHelper.getPositionsFromCorners(pos.add(-2, -2, -2), mop.getBlockPos().add(2, 2, 2)))
				{
					if(worldObj.getBlockState(currentPos).getBlock() == Blocks.SAND)
					{
						PlayerHelper.checkedPlaceBlock(((EntityPlayerMP) getThrower()), pos, Blocks.GLASS.getDefaultState());
					}
				}
			}
			else
			{
				for (BlockPos currentPos : WorldHelper.getPositionsFromCorners(pos.add(-1, -1, -1), mop.getBlockPos().add(1, 1, 1)))
				{
					if(worldObj.isAirBlock(currentPos))
					{
						PlayerHelper.checkedPlaceBlock(((EntityPlayerMP) getThrower()), currentPos, Blocks.FIRE.getDefaultState());
					}
				}
			}
		}
	}
}
