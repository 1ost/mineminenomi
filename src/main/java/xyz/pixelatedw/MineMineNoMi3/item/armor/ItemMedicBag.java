package xyz.pixelatedw.MineMineNoMi3.item.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.models.armors.ModelMedicBag;

public class ItemMedicBag extends ItemArmor {

	public ItemMedicBag(EntityEquipmentSlot equipmentSlotIn) {
		super(ArmorMaterial.CHAIN, 1, equipmentSlotIn);
		this.setMaxDamage(1000);

	}
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemstack, int armorSlot)
	{
		ModelMedicBag model = new ModelMedicBag();

		return model;
	}

	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String layer)
	{
		return String.format("%s:textures/armor/medic_bag.png", ID.PROJECT_ID);
	}
	
}
