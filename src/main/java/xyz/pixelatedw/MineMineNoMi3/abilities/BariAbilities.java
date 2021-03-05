package xyz.pixelatedw.MineMineNoMi3.abilities;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.Values;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.math.ISphere;
import xyz.pixelatedw.MineMineNoMi3.api.math.Sphere;
import xyz.pixelatedw.MineMineNoMi3.blocks.BlockBarrier;
import xyz.pixelatedw.MineMineNoMi3.entities.abilityprojectiles.BariProjectiles;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.lists.ListMisc;

public class BariAbilities 
{
	static
	{
		Values.abilityWebAppExtraParams.put("barrier", new String[] {"desc", "The user creates an impenetrable wall that shields them from attacks."});
		Values.abilityWebAppExtraParams.put("barrierball", new String[] {"desc", "The user creates a spherical barrier around them, shielding them from attacks."});
		Values.abilityWebAppExtraParams.put("barriercrash", new String[] {"desc", "Launches a barrier towards the opponent, smashing it against them."});
		Values.abilityWebAppExtraParams.put("baribarinopistol", new String[] {"desc", "The user shapes a barrier aroud their fist, which greatly increases the power of a punch."});
		Values.abilityWebAppExtraParams.put("barrierbilitystairs", new String[] {"desc", "By shaping the Barrier, the user can create stairs."});
	}
	
	public static Ability[] abilitiesArray = new Ability[] {new Barrier(), new BarrierBall(), new BarrierCrash(), new BariBariNoPistol(), new BarrierbilityStairs()};
	
	public static class BarrierbilityStairs extends Ability
	{
		private List<int[]> blockList = new ArrayList<int[]>();
		
		public BarrierbilityStairs() 
		{
			super(ListAttributes.BARRIERBILITY_STAIRS); 
		}	
		
		public void fillBlocksList(List<int[]> entityList)
		{
			this.blockList.addAll(entityList);
		}
		
		public void passive(EntityPlayer player)
		{
			if(!this.isOnCooldown)
			{
				if(this.blockList.isEmpty())
					player.world.spawnEntity(new BariProjectiles.BarrierbilityStairs(player.world, player, attr));
				super.passive(player);
			}
		} 
		
		public void endPassive(EntityPlayer player)
		{
			for(int[] blockPos : this.blockList)
			{
				if(player.world.getBlockState(new BlockPos(blockPos[0], blockPos[1], blockPos[2])) == ListMisc.Barrier)
					player.world.setBlockState(new BlockPos(blockPos[0], blockPos[1], blockPos[2]), (IBlockState) Blocks.AIR);
			}
            this.blockList = new ArrayList<int[]>();
		}
	}
	
	public static class BariBariNoPistol extends Ability
	{
		public BariBariNoPistol() 
		{
			super(ListAttributes.BARI_BARI_NO_PISTOL); 
		}	
		
		public void hitEntity(EntityPlayer player, EntityLivingBase target) 
		{
			if(!this.isOnCooldown)
			{				
				super.hitEntity(player, target);
				
				target.attackEntityFrom(DamageSource.causePlayerDamage(player), 10);
			}		
		}
	}
	
	public static class BarrierCrash extends Ability
	{
		public BarrierCrash() 
		{
			super(ListAttributes.BARRIER_CRASH); 
		}
		
		public void use(EntityPlayer player)
		{
			this.projectile = new BariProjectiles.BarrierCrash(player.world, player, attr);
			super.use(player);
		} 
	}
	
	public static class BarrierBall extends Ability
	{
		private List<int[]> blockList = new ArrayList<int[]>();
		
		public BarrierBall() 
		{
			super(ListAttributes.BARRIER_BALL); 
		}
		
		public void passive(EntityPlayer player)
		{
			if(!this.isOnCooldown)
			{
				if(this.blockList.isEmpty())
				{
					RayTraceResult mop = WyHelper.rayTraceBlocks(player);
	
					World world = player.world;
					if(player.isSneaking())
					{		
						blockList.addAll(WyHelper.createEmptySphere(world, (int)player.posX, (int)player.posY, (int)player.posZ, 5, ListMisc.Barrier, "air", "foliage", "nogrief"));
					}
					else
					{
						if(mop != null && world.getBlockState(new BlockPos(mop.getBlockPos().getX(), mop.getBlockPos().getY(), mop.getBlockPos().getZ())) != Blocks.AIR)
						{
							blockList.addAll(WyHelper.createEmptySphere(world, mop.getBlockPos().getX(), mop.getBlockPos().getY(), mop.getBlockPos().getZ(), 5, ListMisc.Barrier, "air", "foliage", "nogrief"));
						}
					}
				}
				
				super.passive(player);
			}
		} 
		
		public void endPassive(EntityPlayer player) 
		{
			for(int[] blockPos : this.blockList)
			{
				if(player.world.getBlockState(new BlockPos(blockPos[0], blockPos[1], blockPos[2])) == ListMisc.Barrier)
					player.world.setBlockState(new BlockPos(blockPos[0], blockPos[1], blockPos[2]), (IBlockState) Blocks.AIR);
			}
            this.blockList = new ArrayList<int[]>();
            this.startCooldown();
            this.startExtUpdate(player);
		}
	}
	
	public static class Barrier extends Ability
	{
		private List<int[]> blockList = new ArrayList<int[]>();
		
		public Barrier() 
		{
			super(ListAttributes.BARRIER); 
		}
		
		public void passive(EntityPlayer player) 
		{
			if(!this.isOnCooldown)
			{
				if(this.blockList.isEmpty())
				{
					if(WyHelper.get4Directions(player) == WyHelper.Direction.NORTH)
						this.blockList = WyHelper.createFilledCube(player.world, player.posX, player.posY, player.posZ - 3, new int[] {3, 4, 1}, ListMisc.Barrier, "air", "foliage", "nogrief");
					if(WyHelper.get4Directions(player) == WyHelper.Direction.SOUTH)
						this.blockList = WyHelper.createFilledCube(player.world, player.posX, player.posY, player.posZ + 3, new int[] {3, 4, 1}, ListMisc.Barrier, "air", "foliage", "nogrief");
					if(WyHelper.get4Directions(player) == WyHelper.Direction.EAST)
						this.blockList = WyHelper.createFilledCube(player.world, player.posX + 3, player.posY, player.posZ, new int[] {1, 4, 3}, ListMisc.Barrier, "air", "foliage", "nogrief");
					if(WyHelper.get4Directions(player) == WyHelper.Direction.WEST)
						this.blockList = WyHelper.createFilledCube(player.world, player.posX - 3, player.posY, player.posZ, new int[] {1, 4, 3}, ListMisc.Barrier, "air", "foliage", "nogrief");
				}
				super.passive(player);
			}
		}
		
		public void endPassive(EntityPlayer player) 
		{
			for(int[] blockPos : this.blockList)
			{
				if(player.world.getBlockState(new BlockPos(blockPos[0], blockPos[1], blockPos[2])) == ListMisc.Barrier)
					player.world.setBlockState(new BlockPos(blockPos[0], blockPos[1], blockPos[2]), (IBlockState) Blocks.AIR);
			}
            this.blockList = new ArrayList<int[]>();
            this.startCooldown();
            this.startExtUpdate(player);
		}
	}
}
