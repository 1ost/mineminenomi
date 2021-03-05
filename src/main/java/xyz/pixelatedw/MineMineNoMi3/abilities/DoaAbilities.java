package xyz.pixelatedw.MineMineNoMi3.abilities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.Ability;
import xyz.pixelatedw.MineMineNoMi3.api.math.WyMathHelper;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.lists.ListAttributes;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketSync;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketSyncInfo;

import java.util.Timer;
import java.util.TimerTask;

public class DoaAbilities {

    public static Ability[] abilitiesArray = new Ability[]{new AirDoor(), new Door()};

    public static class AirDoor extends Ability {
        
        public AirDoor() {
            super(ListAttributes.AIR_DOOR);
        }

        public void passive(EntityPlayer player) {
            if (!this.isOnCooldown()) {
                ExtendedEntityData propz = ExtendedEntityData.get(player);
            propz.setInAirWorld(true);
            WyNetworkHelper.sendTo(new PacketSync(propz), (EntityPlayerMP) player);
            WyNetworkHelper.sendToAll(new PacketSyncInfo(player.getEntityId(), propz));
            super.passive(player);
        }
        }

        public void duringPassive(EntityPlayer player, int timer) {
            if (timer > 45 * 8) {
                this.setPassiveActive(false);
                this.setCooldownActive(true);
                this.endPassive(player);
            }
        }

        public void endPassive(EntityPlayer player) {
            this.startCooldown();
            this.startExtUpdate(player);
            ExtendedEntityData propz = ExtendedEntityData.get(player);
            propz.setInAirWorld(false);
            WyNetworkHelper.sendTo(new PacketSync(propz), (EntityPlayerMP) player);
            WyNetworkHelper.sendToAll(new PacketSyncInfo(player.getEntityId(), propz));
        }

    }

    public static class Door extends Ability {
        public Door() {
            super(ListAttributes.DOOR);
        }

        public static boolean isBlock(int[] coords, EntityPlayer player) {
            if (player.getEntityWorld().getBlockState(new BlockPos(coords[0], coords[1], coords[2])) == Blocks.AIR && player.getEntityWorld().getBlockState(new BlockPos(coords[0], (coords[1] + 1), coords[2])) == Blocks.AIR) {
                return true;
            }
            return false;
        }

        public void use(EntityPlayer player) {
            if (!this.isOnCooldown()) {

            RayTraceResult MOP = WyHelper.rayTraceBlocks(player);

            if (MOP != null && (MOP.getBlockPos().getY() >= (player.posY + 1))) {
                int checkX = MOP.getBlockPos().getX() - (int) player.posX;
                int checkZ = MOP.getBlockPos().getZ() - (int) player.posZ;

                if ((checkX > -3 && checkX < 3) && (checkZ > -3 && checkZ < 3)) {

                    int[] coords = new int[]{MOP.getBlockPos().getX(), (int) player.posY, MOP.getBlockPos().getZ()};
                    int timer = 0;
                    while (!isBlock(coords, player)) {
                        coords = WyMathHelper.moveAway(player, coords);
                        timer += 1;
                        if (timer >= 100) {
                            break;
                        }

                    }
                    WyMathHelper.moveAway(player, coords);

                    if (timer < 100) {
                        player.setPositionAndUpdate(coords[0], coords[1], coords[2]);
                        super.use(player);
                    }

                }
                }
            }
        }
    }

}
