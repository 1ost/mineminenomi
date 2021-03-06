package xyz.pixelatedw.MineMineNoMi3;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;

import javax.annotation.Nullable;

public class MainEnchantment extends Enchantment
{
    private final EntityEquipmentSlot[] applicableEquipmentTypes;
    private final Rarity rarity;
    /** The EnumEnchantmentType given to this Enchantment. */
    @Nullable
    public EnumEnchantmentType type;
    /** Used in localisation and stats. */
    protected String name;

    protected MainEnchantment(Enchantment.Rarity rarityIn, EnumEnchantmentType typeIn, EntityEquipmentSlot[] slots)
    {
        super(rarityIn, typeIn, slots);
        this.rarity = rarityIn;
        this.type = typeIn;
        this.applicableEquipmentTypes = slots;
    }
    
    public int getMaxLevel()
    {
        return 1;
    }
}