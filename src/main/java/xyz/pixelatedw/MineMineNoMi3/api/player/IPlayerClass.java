package xyz.pixelatedw.MineMineNoMi3.api.player;

import net.minecraft.nbt.NBTTagCompound;

import java.util.Map;

public interface IPlayerClass {


    NBTTagCompound saveNBTData(NBTTagCompound tag);

    void loadNBTData(NBTTagCompound tag);


    //int[] getPlayerData(boolean withClass);

    //void setPlayerData(int[] data);
}
