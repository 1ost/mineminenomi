package xyz.pixelatedw.MineMineNoMi3.helpers;

import java.util.Arrays;
import java.util.HashMap;

import xyz.pixelatedw.MineMineNoMi3.api.abilities.AbilityAttribute;
import xyz.pixelatedw.MineMineNoMi3.data.ExtendedEntityData;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.RenderZoanMorph;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.models.ModelBisonPower;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.models.ModelBisonSpeed;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.models.ModelPhoenixFull;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.models.ModelPhoenixHybrid;
import xyz.pixelatedw.MineMineNoMi3.entities.zoan.models.ModelVenomDemon;

public class MorphsHelper
{
	
	private static HashMap<String, Object[][]> morphsMap = new HashMap<String, Object[][]>();
	
	public static HashMap<String, Object[][]> getMorphsMap()
	{
		return morphsMap;
	}

	static
	{
		morphsMap.put("ushiushibison", new Object[][] 
				{
						{ "power", new RenderZoanMorph(new ModelBisonPower(), "bisonpower", 1.4, new float[] { 0, 0.8f, 0 }) },
						{ "speed", new RenderZoanMorph(new ModelBisonSpeed(), "bisonspeed", 1.4, new float[] { 0, 0.8f, 0 }) }					
				});
		morphsMap.put("toritoriphoenix", new Object[][]
				{
						{ "full", new RenderZoanMorph(new ModelPhoenixFull(), "phoenixfull", 1.3, new float[] { 0, 0.3f, 0 }) },
						{ "hybrid", new RenderZoanMorph(new ModelPhoenixHybrid(), "phoenixhybrid", 1, new float[] { 0, 0.3f, 0 }) }		
				});
		morphsMap.put("dokudoku", new Object[][]
				{
						{ "venomDemon", new RenderZoanMorph(new ModelVenomDemon(), "venomdemon") },
				});
	}
	
}
