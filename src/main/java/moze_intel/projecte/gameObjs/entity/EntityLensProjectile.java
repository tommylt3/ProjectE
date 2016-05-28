package moze_intel.projecte.gameObjs.entity;

import moze_intel.projecte.network.PacketHandler;
import moze_intel.projecte.network.packets.ParticlePKT;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.WorldHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class EntityLensProjectile extends PEProjectile
{
	private byte charge;
	
	public EntityLensProjectile(World world) 
	{
		super(world);
	}

	public EntityLensProjectile(World world, EntityPlayer entity, byte charge)
	{
		super(world, entity);
		this.charge = charge;
	}

	public EntityLensProjectile(World world, double x, double y, double z, byte charge) 
	{
		super(world, x, y, z);
		this.charge = charge;
	}
	
	@Override
	public void onUpdate()
	{
		super.onUpdate();
		
		if (this.worldObj.isRemote)
		{
			return;
		}

		if (ticksExisted > 400 || !this.worldObj.isBlockLoaded(new BlockPos(this)))
		{
			this.setDead();
			return;
		}

		if (this.isInWater())
		{
			this.playSound(SoundEvents.ENTITY_GENERIC_BURN, 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
			PacketHandler.sendToAllAround(new ParticlePKT(EnumParticleTypes.SMOKE_LARGE, posX, posY, posZ), new NetworkRegistry.TargetPoint(worldObj.provider.getDimension(), posX, posY, posZ, 32));
			this.setDead();
		}
	}

	@Override
	protected void apply(RayTraceResult mop)
	{
		if (this.worldObj.isRemote) return;
		WorldHelper.createNovaExplosion(worldObj, getThrower(), posX, posY, posZ, Constants.EXPLOSIVE_LENS_RADIUS[charge]);
	}
	
	public void writeEntityToNBT(NBTTagCompound nbt)
	{
		super.writeEntityToNBT(nbt);
		nbt.setByte("Charge", charge);
	}
	
	public void readEntityFromNBT(NBTTagCompound nbt)
	{
		super.readEntityFromNBT(nbt);
		charge = nbt.getByte("Charge");
	}
}
