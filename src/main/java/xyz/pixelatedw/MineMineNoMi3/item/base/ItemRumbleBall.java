package xyz.pixelatedw.MineMineNoMi3.item.base;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;

public class ItemRumbleBall extends Item
{
	private boolean alwaysEdible;

	public ItemRumbleBall()
	{

	}

    @Override
	public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.EAT;
    }
	
    @Override
	public int getMaxItemUseDuration(ItemStack p_77626_1_)
    {
        return 32;
    }

	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);

		if (playerIn.canEat(this.alwaysEdible))
		{
			playerIn.setActiveHand(handIn);
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
		}
		else
		{
			return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
		}
	}

	public ItemStack onFoodEaten(ItemStack itemStack, World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			ExtendedEntityData props = ExtendedEntityData.get(player);

			if(WyHelper.isNullOrEmpty(props.getUsedFruit()))
				return itemStack;



			if(!player.capabilities.isCreativeMode)
				itemStack.setCount(itemStack.getCount() -1);
		}

		return itemStack;
	}
}
