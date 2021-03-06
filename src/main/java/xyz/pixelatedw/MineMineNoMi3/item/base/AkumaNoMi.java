package xyz.pixelatedw.MineMineNoMi3.item.base;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import xyz.pixelatedw.MineMineNoMi3.EnumFruitType;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.MainConfig;
import xyz.pixelatedw.MineMineNoMi3.abilities.FishKarateAbilities;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.WyRegistry;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityProperties;
import xyz.pixelatedw.MineMineNoMi3.api.network.PacketAbilitySync;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.api.telemetry.WyTelemetry;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedWorldData;
import xyz.pixelatedw.MineMineNoMi3.helpers.DevilFruitsHelper;
import xyz.pixelatedw.MineMineNoMi3.lists.ListCreativeTabs;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketSyncInfo;

import javax.annotation.Nullable;
import java.util.List;

public class AkumaNoMi extends ItemFood {

    private boolean alwaysEdible;
    public EnumFruitType type;
    public Ability[] abilities;
    public AkumaNoMi(int amount, Ability... abilitiesArray) {
        super(0, false);
        this.maxStackSize = 1;
        this.type = type;
        this.abilities = abilitiesArray;
        this.setCreativeTab(ListCreativeTabs.tabDevilFruits);
        this.setAlwaysEdible();
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
    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        ExtendedWorldData worldProps = ExtendedWorldData.get(entityItem.world);

        if(entityItem.isBurning())
            worldProps.removeDevilFruitFromWorld(this);

        return false;
    }

    @Override
    public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
        ExtendedEntityData props = ExtendedEntityData.get(player);
        AbilityProperties abilityProps = AbilityProperties.get(player);
        ExtendedWorldData worldProps = ExtendedWorldData.get(world);

        String eatenFruit = this.getUnlocalizedName().substring(5).replace("nomi", "").replace(":", "").replace(",", "").replace("model", "");

        boolean flag1 = !props.getUsedFruit().equalsIgnoreCase("n/a") && !props.hasYamiPower() && !eatenFruit.equalsIgnoreCase("yamiyami");
        boolean flag2 = props.hasYamiPower() && !eatenFruit.equalsIgnoreCase(props.getUsedFruit()) && !props.getUsedFruit().equalsIgnoreCase("yamidummy");
        boolean flag3 = !MainConfig.enableYamiSpecialPower && !props.getUsedFruit().equalsIgnoreCase("n/a") && (eatenFruit.equalsIgnoreCase("yamiyami") || !eatenFruit.equalsIgnoreCase(props.getUsedFruit()));

        if (flag1 || flag2 || flag3)
        {
            player.attackEntityFrom(DamageSource.WITHER, Float.POSITIVE_INFINITY);
            return;
        }

        if (!eatenFruit.equalsIgnoreCase("yamiyami"))
            props.setUsedFruit(eatenFruit);

        if (this.type == EnumFruitType.LOGIA)
            props.setIsLogia(true);

        if (eatenFruit.equalsIgnoreCase("yamiyami"))
        {
            props.setIsLogia(false);

            props.setYamiPower(true);

            if (props.getUsedFruit().equalsIgnoreCase("n/a"))
                props.setUsedFruit("yamidummy");
        }

        if (eatenFruit.equalsIgnoreCase("hitohito") && !player.world.isRemote)
        {
            WyHelper.sendMsgToPlayer(player, "You've gained some enlightenment");
            if (props.isFishman())
            {
                props.setRace(ID.RACE_HUMAN);

                for (int i = 0; i < 8; i++)
                {
                    Ability abl = abilityProps.getAbilityFromSlot(i);
                    for (Ability fshAbl : FishKarateAbilities.abilitiesArray)
                    {
                        if (abl != null && abl.getAttribute().getAttributeName().equalsIgnoreCase(fshAbl.getAttribute().getAttributeName()))
                            abilityProps.setAbilityInSlot(i, null);
                    }
                }

                DevilFruitsHelper.validateStyleMoves(player);
                DevilFruitsHelper.validateRacialMoves(player);
                WyNetworkHelper.sendTo(new PacketAbilitySync(abilityProps), (EntityPlayerMP) player);
            }
        }

        if(MainConfig.enableOneFruitPerWorld && !worldProps.isDevilFruitInWorld(eatenFruit))
            worldProps.addDevilFruitInWorld(eatenFruit);

        if(!props.getUsedFruit().equalsIgnoreCase("yomiyomi"))
            for(Ability a : abilities)
                if(!DevilFruitsHelper.verifyIfAbilityIsBanned(a) && !abilityProps.hasDevilFruitAbility(a))
                    abilityProps.addDevilFruitAbility(a);

        if(!world.isRemote)
            WyNetworkHelper.sendToAll(new PacketSyncInfo(player.getName(), props));
        if(!world.isRemote && !player.capabilities.isCreativeMode)
            WyTelemetry.addDevilFruitStat(props.getUsedFruit(), (String) WyRegistry.getItemsMap().get(this), 1);

    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        for (int i = 0; i < this.abilities.length; i++)
            if (!DevilFruitsHelper.verifyIfAbilityIsBanned(this.abilities[i]) && this.abilities[i] != null)
                tooltip.add(I18n.format("ability." + WyHelper.getFancyName(this.abilities[i].getAttribute().getAttributeName()) + ".name"));

        tooltip.add("");
        tooltip.add(type.getColor() + type.getName());
    }

    public EnumFruitType getType()
    {
        return type;
    }

}
