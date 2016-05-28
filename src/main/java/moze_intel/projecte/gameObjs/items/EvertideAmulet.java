package moze_intel.projecte.gameObjs.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import com.google.common.collect.Lists;
import moze_intel.projecte.api.PESounds;
import moze_intel.projecte.api.item.IPedestalItem;
import moze_intel.projecte.api.item.IProjectileShooter;
import moze_intel.projecte.config.ProjectEConfig;
import moze_intel.projecte.gameObjs.entity.EntityWaterProjectile;
import moze_intel.projecte.gameObjs.tiles.DMPedestalTile;
import moze_intel.projecte.utils.ClientKeyHelper;
import moze_intel.projecte.utils.Constants;
import moze_intel.projecte.utils.FluidHelper;
import moze_intel.projecte.utils.MathUtils;
import moze_intel.projecte.utils.PEKeybind;
import moze_intel.projecte.utils.PlayerHelper;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

@Optional.Interface(iface = "baubles.api.IBauble", modid = "Baubles")
public class EvertideAmulet extends ItemPE implements IProjectileShooter, IBauble, IPedestalItem, IFluidContainerItem
{
	public EvertideAmulet()
	{
		this.setUnlocalizedName("evertide_amulet");
		this.setMaxStackSize(1);
		this.setNoRepair();
		this.setContainerItem(this);
	}

	@Nonnull
	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing sideHit, float f1, float f2, float f3)
	{
		if (!world.isRemote && PlayerHelper.hasEditPermission(((EntityPlayerMP) player), pos))
		{
			TileEntity tile = world.getTileEntity(pos);

			if (tile instanceof IFluidHandler)
			{
				IFluidHandler tank = (IFluidHandler) tile;

				if (FluidHelper.canFillTank(tank, FluidRegistry.WATER, sideHit))
				{
					FluidHelper.fillTank(tank, FluidRegistry.WATER, sideHit, 1000);
				}
			}

			IBlockState state = world.getBlockState(pos);
			if (state.getBlock() == Blocks.CAULDRON)
			{
				int waterLevel = state.getValue(BlockCauldron.LEVEL);
				if (waterLevel < 3)
				{
					((BlockCauldron) state.getBlock()).setWaterLevel(world, pos, state, waterLevel + 1);
				}
			}
		}

		return EnumActionResult.SUCCESS;
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull ItemStack stack, World world, EntityPlayer player, EnumHand hand)
	{
		if (!world.isRemote)
		{
			RayTraceResult mop = this.rayTrace(world, player, false);
			if (mop != null && mop.typeOfHit == RayTraceResult.Type.BLOCK)
			{
				BlockPos blockPosHit = mop.getBlockPos();

				if (!(world.getTileEntity(blockPosHit) instanceof IFluidHandler))
				{
					if (world.isAirBlock(blockPosHit.offset(mop.sideHit)))
					{
						world.playSound(null, player.posX, player.posY, player.posZ, PESounds.WATER, SoundCategory.PLAYERS, 1.0F, 1.0F);
						placeWater(world, player, blockPosHit.offset(mop.sideHit));
						PlayerHelper.swingItem(player);
					}
				}
			}
		}

		return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}

	private void placeWater(World world, EntityPlayer player, BlockPos pos)
	{
		Material material = world.getBlockState(pos).getMaterial();

		if (world.provider.doesWaterVaporize())
		{
			world.playSound(null, pos, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.PLAYERS, 0.5F, 2.6F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.8F);

			for (int l = 0; l < 8; ++l)
			{
				world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0.0D, 0.0D, 0.0D);
			}
		}
		else
		{
			if (!world.isRemote && !material.isSolid() && !material.isLiquid())
			{
				world.destroyBlock(pos, true);
			}
			world.setBlockState(pos, Blocks.FLOWING_WATER.getDefaultState());
			PlayerHelper.checkedPlaceBlock(((EntityPlayerMP) player), pos, Blocks.FLOWING_WATER.getDefaultState());
		}

	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int invSlot, boolean par5)
	{
		if (invSlot > 8 || !(entity instanceof EntityPlayer)) 
		{
			return;
		}
		
		EntityPlayer player = (EntityPlayer) entity;

		int x = (int) Math.floor(player.posX);
		int y = (int) (player.posY - player.getYOffset());
		int z = (int) Math.floor(player.posZ);
		BlockPos pos = new BlockPos(x, y, z);

		if ((world.getBlockState(pos.down()).getBlock() == Blocks.WATER || world.getBlockState(pos.down()).getBlock() == Blocks.FLOWING_WATER) && world.isAirBlock(pos))
		{
			if (!player.isSneaking())
			{
				player.motionY = 0.0D;
				player.fallDistance = 0.0F;
				player.onGround = true;
			}
				
			if (!world.isRemote && player.capabilities.getWalkSpeed() < 0.25F)
			{
				PlayerHelper.setPlayerWalkSpeed(player, 0.25F);
			}
		}
		else if (!world.isRemote)
		{
			if (player.isInWater())
			{
				player.setAir(300);
			}
				
			if (player.capabilities.getWalkSpeed() != Constants.PLAYER_WALK_SPEED)
			{
				PlayerHelper.setPlayerWalkSpeed(player, Constants.PLAYER_WALK_SPEED);
			}
		}
	}
	
	@Override
	public boolean shootProjectile(EntityPlayer player, ItemStack stack, EnumHand hand)
	{
		World world = player.worldObj;

		if (!world.provider.doesWaterVaporize())
		{
			world.playSound(null, player.posX, player.posY, player.posZ, PESounds.WATER, SoundCategory.PLAYERS, 1.0F, 1.0F);
			EntityWaterProjectile ent = new EntityWaterProjectile(world, player);
			ent.setHeadingFromThrower(player, player.rotationPitch, player.rotationYaw, 0, 1.5F, 1);
			world.spawnEntityInWorld(ent);
			return true;
		}

		return false;
	}

	/** Start IFluidContainerItem **/
	@Override
	public FluidStack getFluid(ItemStack container)
	{
		return new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
	}

	@Override
	public int getCapacity(ItemStack container)
	{
		return FluidContainerRegistry.BUCKET_VOLUME;
	}

	@Override
	public int fill(ItemStack container, FluidStack resource, boolean doFill)
	{
		return 0;
	}

	@Override
	public FluidStack drain(ItemStack container, int maxDrain, boolean doDrain)
	{
		return new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
	}

	/** End IFluidContainerItem **/

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List<String> list, boolean par4)
	{
		list.add(I18n.format("pe.evertide.tooltip1", ClientKeyHelper.getKeyName(PEKeybind.FIRE_PROJECTILE)));

		list.add(I18n.format("pe.evertide.tooltip2"));
		list.add(I18n.format("pe.evertide.tooltip3"));
		list.add(I18n.format("pe.evertide.tooltip4"));
	}
	
	@Override
	@Optional.Method(modid = "Baubles")
	public baubles.api.BaubleType getBaubleType(ItemStack itemstack)
	{
		return BaubleType.AMULET;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onWornTick(ItemStack stack, EntityLivingBase player) 
	{
		this.onUpdate(stack, player.worldObj, player, 0, false);
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onEquipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	@Optional.Method(modid = "Baubles")
	public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {}

	@Override
	@Optional.Method(modid = "Baubles")
	public boolean canEquip(ItemStack itemstack, EntityLivingBase player)
	{
		return true;
	}

	@Override
	@Optional.Method(modid = "Baubles")
	public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) 
	{
		return true;
	}

	@Override
	public void updateInPedestal(World world, BlockPos pos)
	{
		if (!world.isRemote && ProjectEConfig.evertidePedCooldown != -1)
		{
			DMPedestalTile tile = ((DMPedestalTile) world.getTileEntity(pos));

			if (tile.getActivityCooldown() == 0)
			{
				int i = (300 + world.rand.nextInt(600)) * 20;
				world.getWorldInfo().setRainTime(i);
				world.getWorldInfo().setThunderTime(i);
				world.getWorldInfo().setRaining(true);

				tile.setActivityCooldown(ProjectEConfig.evertidePedCooldown);
			}
			else
			{
				tile.decrementActivityCooldown();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public List<String> getPedestalDescription()
	{
		List<String> list = Lists.newArrayList();
		if (ProjectEConfig.evertidePedCooldown != -1)
		{
			list.add(TextFormatting.BLUE + I18n.format("pe.evertide.pedestal1"));
			list.add(TextFormatting.BLUE +
					I18n.format("pe.evertide.pedestal2", MathUtils.tickToSecFormatted(ProjectEConfig.evertidePedCooldown)));
		}
		return list;
	}
}
