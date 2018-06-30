package xyz.pixelatedw.MineMineNoMi3.gui;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import xyz.pixelatedw.MineMineNoMi3.ID;
import xyz.pixelatedw.MineMineNoMi3.api.WyHelper;
import xyz.pixelatedw.MineMineNoMi3.api.WyRenderHelper;
import xyz.pixelatedw.MineMineNoMi3.api.abilities.extra.AbilityManager;
import xyz.pixelatedw.MineMineNoMi3.api.network.WyNetworkHelper;
import xyz.pixelatedw.MineMineNoMi3.gui.extra.GUIAbilitiesList;
import xyz.pixelatedw.MineMineNoMi3.gui.extra.GUIButtonNoTexture;
import xyz.pixelatedw.MineMineNoMi3.ieep.ExtendedEntityStats;
import xyz.pixelatedw.MineMineNoMi3.packets.PacketSync;

@SideOnly(Side.CLIENT)
public class GUISelectHotbarAbilities extends GuiScreen
{
	protected EntityPlayer player;
	protected ExtendedEntityStats props;
	
	private GUIAbilitiesList devilFruitsAbilitiesList, racialAbilitiesList, hakiAbilitiesList;
	private RenderItem renderItem;
	public int slotSelected = -1;
	private int menuSelected = 0;
	public int relativePosX, relativePosY;
	public int abilitySelected = -1;

	public GUISelectHotbarAbilities(EntityPlayer player)
	{
		this.player = player;
		this.props = ExtendedEntityStats.get(player);	
	}
	
	public void drawScreen(int x, int y, float f)
	{
		drawDefaultBackground();

		Minecraft.getMinecraft().getTextureManager().bindTexture(ID.TEXTURE_BLANK);
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
		
		int posX = sr.getScaledWidth();
		int posY = sr.getScaledHeight();
		drawTexturedModalRect((posX - 250) / 2, (posY - 230) / 2, 0, 0, 256, 256);
		drawTexturedModalRect((posX - 250) / 2, posY - 60, 0, 0, 256, 256);
		
		this.mc.getTextureManager().bindTexture(ID.TEXTURE_COMBATMODE);	
	
        GL11.glEnable(GL11.GL_BLEND);		
        
		for(int i = 0; i < 8; i++)
		{
            if(this.slotSelected == i)
            	this.drawTexturedModalRect((posX - 200 + (i * 50)) / 2, posY - 33, 48, 0, 23, 23);
            else
            	this.drawTexturedModalRect((posX - 200 + (i * 50)) / 2, posY - 33, 0, 0, 23, 23);
		} 		
		
		for(int i = 0; i < 8; i++)
		{
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            if(props.getAbilityFromSlot(i) != null)
            	WyRenderHelper.drawAbilityIcon(WyHelper.getFancyName(props.getAbilityFromSlot(i).getAttribute().getAttributeName()), (posX - 192 + (i * 50)) / 2, posY - 29, 16, 16);
        }
		
		this.mc.getTextureManager().bindTexture(ID.TEXTURE_COMBATMODE);		
		if(props.getUsedFruit() != null && !props.getUsedFruit().toLowerCase().equals("n/a"))
		{
			this.drawTexturedModalRect((posX - 280) / 2, (posY - 200) / 2, 0, 23, 27, 26);
			if(props.hasYamiPower())
				WyRenderHelper.drawAbilityIcon("yamiyaminomi", (posX - 268) / 2, (posY - 187) / 2, 16, 16);
			else
			{
				String model = "";
				String fullModel = "";
				if(props.getUsedFruit().equals("ushiushibison"))
				{
					model = "bison";
					fullModel = "model" + model;
				}
				
				WyRenderHelper.drawAbilityIcon(props.getUsedFruit().replace(model, "") + "nomi" + fullModel, (posX - 268) / 2, (posY - 187) / 2, 16, 16);
			}
			this.mc.getTextureManager().bindTexture(ID.TEXTURE_COMBATMODE);	
		}
		if(props.getRacialAbilities()[0] != null && !props.getRacialAbilities()[0].equals("n/a"))
		{
			this.drawTexturedModalRect((posX - 280) / 2, (posY - 140) / 2, 0, 23, 27, 26);
			WyRenderHelper.drawAbilityIcon(props.getRacialAbilities()[0], (posX - 268) / 2, (posY - 127) / 2, 16, 16);	
			this.mc.getTextureManager().bindTexture(ID.TEXTURE_COMBATMODE);	
		}
		if(props.getHakiAbilities()[0] != null && !props.getHakiAbilities()[0].equals("n/a"))
		{
			this.drawTexturedModalRect((posX - 280) / 2, (posY - 80) / 2, 0, 23, 27, 26);
			WyRenderHelper.drawAbilityIcon(props.getHakiAbilities()[0], (posX - 268) / 2, (posY - 67) / 2, 16, 16);	
			this.mc.getTextureManager().bindTexture(ID.TEXTURE_COMBATMODE);	
		}
		
		GL11.glDisable(GL11.GL_BLEND);				
		
		int scale = sr.getScaleFactor();

		WyRenderHelper.startGlScissor((posX - 220) / 2, (posY - 200) / 2, 215, 130);
		
		if(this.menuSelected == 0)
			this.devilFruitsAbilitiesList.drawScreen(x, y, f);
		else if(this.menuSelected == 1)
			this.racialAbilitiesList.drawScreen(x, y, f);
		else if(this.menuSelected == 2)
			this.hakiAbilitiesList.drawScreen(x, y, f);
		
		WyRenderHelper.endGlScissor();
		
		super.drawScreen(x, y, f);
	}
	
	public void initGui()
	{
		ScaledResolution sr = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

		int posX = sr.getScaledWidth();
		int posY = sr.getScaledHeight();
		relativePosX = posX;
		relativePosY = posY;
		
		//if(MainConfig.devilFruitAbilitiesSystem == 0 || MainConfig.devilFruitAbilitiesSystem == 1)
		//	this.buttonList.add(new GuiButton(-1, (posX + 250) / 2, posY - 220, 80, 20, "Edit Abilities"));
		
		if(props.getUsedFruit() != null && !props.getUsedFruit().toLowerCase().equals("n/a"))
			this.buttonList.add(new GUIButtonNoTexture(10, (posX - 280) / 2, (posY - 200) / 2, 27, 25, ""));
		if(props.getRacialAbilities()[0] != null && !props.getRacialAbilities()[0].equals("n/a"))
			this.buttonList.add(new GUIButtonNoTexture(11, (posX - 280) / 2, (posY - 140) / 2, 27, 25, ""));
		if(props.getHakiAbilities()[0] != null && !props.getHakiAbilities()[0].equals("n/a"))
			this.buttonList.add(new GUIButtonNoTexture(12, (posX - 280) / 2, (posY - 80) / 2, 27, 25, ""));
		
		for(int i = 0; i < 8; i++)
		{
            GL11.glEnable(GL11.GL_BLEND);
			this.buttonList.add(new GUIButtonNoTexture(i, (posX - 196 + (i * 50)) / 2, posY - 31, 21, 21, ""));
		}	
		
        this.devilFruitsAbilitiesList = new GUIAbilitiesList(this, props, props.getDevilFruitAbilities());
        this.devilFruitsAbilitiesList.registerScrollButtons(this.buttonList, 998, 999);
   
        this.racialAbilitiesList = new GUIAbilitiesList(this, props, props.getRacialAbilities());
        this.racialAbilitiesList.registerScrollButtons(this.buttonList, 998, 999);
        
        this.hakiAbilitiesList = new GUIAbilitiesList(this, props, props.getHakiAbilities());
        this.hakiAbilitiesList.registerScrollButtons(this.buttonList, 998, 999);
   
		updateScreen();
	}
	
	public void updateScreen()
	{

	}
	
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
    	if(mouseButton == 1 && this.slotSelected > -1 && props.getAbilityFromSlot(this.slotSelected) != null)
    	{	
    		props.setAbilityInSlot(this.slotSelected, null);
    	}
    	super.mouseClicked(mouseX, mouseY, mouseButton);
    }
	
	public void actionPerformed(GuiButton button)
	{
		if(button.id >= 10 && button.id <= 20)
		{
			this.menuSelected = button.id % 10;
			this.updateScreen();
		}
		if(button.id >= 0 && button.id <= 7)
		{
			if(this.slotSelected != button.id)
				slotSelected = button.id;
			else
				slotSelected = -1;
		}
		/*		
 		if(button.id >= 100)
		{
			if(this.slotSelected != -1)
			{		
				boolean flag = true;
				for(int i = 0; i < props.getAbilitiesInHotbar(); i++)
				{
					if( i != this.slotSelected && props.getAbilityFromSlot(i) != null && props.getAbilityFromSlot(i) == AbilityManager.instance().getAbilityByName(WyHelper.getFancyName(button.displayString)) )
					{
						flag = false;
					}
				}
				if(flag)
				{
					props.setAbilityInSlot(this.slotSelected, AbilityManager.instance().getAbilityByName(WyHelper.getFancyName(button.displayString)));
					button.enabled = false;
				}
			}
			else
			{
				
			}
		}
		*/
	}
	
	public void onGuiClosed()
	{
		WyNetworkHelper.sendToServer(new PacketSync(props));
	}
	
	public boolean doesGuiPauseGame()
	{
		return false;
	}

    private void drawItemStack(ItemStack itemStack, int x, int y, String string)
    {
        GL11.glTranslatef(0.0F, 0.0F, 32.0F);
        this.zLevel = 200.0F;
        itemRender.zLevel = 200.0F;
        FontRenderer font = null;
        if (itemStack != null) font = itemStack.getItem().getFontRenderer(itemStack);
        if (font == null) font = fontRendererObj;
        itemRender.renderItemAndEffectIntoGUI(font, this.mc.getTextureManager(), itemStack, x, y);
        itemRender.renderItemOverlayIntoGUI(font, this.mc.getTextureManager(), itemStack, x, y - 0, string);
        this.zLevel = 0.0F;
        itemRender.zLevel = 0.0F;
    }

}
