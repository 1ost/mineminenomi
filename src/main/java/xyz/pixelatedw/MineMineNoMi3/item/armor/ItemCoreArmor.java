package xyz.pixelatedw.MineMineNoMi3.item.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;

public class ItemCoreArmor extends ItemArmor
{
	private String name;
	private ArmorMaterial mat;

	public ItemCoreArmor(String name, ArmorMaterial material, EntityEquipmentSlot equipmentSlotIn)
	{
		super(material, 1, equipmentSlotIn);
		this.setMaxStackSize(1);
		this.mat = material;
		this.name = name;
	}

	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemstack, int armorSlot)
	{
		ModelBiped armorModel = new ModelBiped(0.05F);

		if (armorModel != null)
		{
			armorModel.bipedHead.showModel = armorSlot == 0;
			armorModel.bipedHeadwear.showModel = false;
			armorModel.bipedBody.showModel = armorSlot == 1 || armorSlot == 2;
			armorModel.bipedRightArm.showModel = armorSlot == 1;
			armorModel.bipedLeftArm.showModel = armorSlot == 1;
			armorModel.bipedRightLeg.showModel = armorSlot == 2 || armorSlot == 3;
			armorModel.bipedLeftLeg.showModel = armorSlot == 2 || armorSlot == 3;

			armorModel.isSneak = entityLiving.isSneaking();
			armorModel.isRiding = entityLiving.isRiding();
			armorModel.isChild = entityLiving.isChild();

			//armorModel.getHeldItem = 0;
			//armorModel.aimedBow = false;

			EntityPlayer player = (EntityPlayer) entityLiving;

			ItemStack held_item = player.inventory.armorItemInSlot(0);

			if (held_item != null)
			{
				//armorModel.heldItemRight = 1;

				if (player.getItemInUseCount() > 0)
				{

					EnumAction enumaction = held_item.getItemUseAction();

					if (enumaction == EnumAction.BOW)
					{
						//armorModel.aimedBow = true;
					} else if (enumaction == EnumAction.BLOCK)
					{
						//armorModel.heldItemRight = 3;
					}

				}

			}
		}

		return armorModel;
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
	{
		return String.format("%s:textures/armor/%s_%d.png", ID.PROJECT_ID, name, slot == 2 ? 2 : 1);
	}
	
	public String getName()
	{
		return WyHelper.getFancyName(this.name);
	}
}
