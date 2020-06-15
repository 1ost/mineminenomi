package xyz.pixelatedw.mineminenomi.world.features.structures.camp.marine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ChestTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.feature.structure.StructurePiece;
import net.minecraft.world.gen.feature.structure.TemplateStructurePiece;
import net.minecraft.world.gen.feature.template.BlockIgnoreStructureProcessor;
import net.minecraft.world.gen.feature.template.PlacementSettings;
import net.minecraft.world.gen.feature.template.Template;
import net.minecraft.world.gen.feature.template.TemplateManager;
import xyz.pixelatedw.mineminenomi.blocks.tileentities.CustomSpawnerTileEntity;
import xyz.pixelatedw.mineminenomi.init.ModBlocks;
import xyz.pixelatedw.mineminenomi.init.ModEntities;
import xyz.pixelatedw.mineminenomi.init.ModFeatures;
import xyz.pixelatedw.mineminenomi.init.ModLootTables;
import xyz.pixelatedw.wypi.APIConfig;
import xyz.pixelatedw.wypi.WyHelper;
import xyz.pixelatedw.wypi.debug.WyDebug;

public class MarineCampPieces
{
	private static final ResourceLocation SMALL_TENT = new ResourceLocation(APIConfig.PROJECT_ID, "camp/marine/small_tent");
	private static final ResourceLocation LERGE_TENT = new ResourceLocation(APIConfig.PROJECT_ID, "camp/marine/large_tent");
	private static final ResourceLocation FIRE_PLACE = new ResourceLocation(APIConfig.PROJECT_ID, "camp/fire_place");
	private static final Map<ResourceLocation, BlockPos> CENTER_OFFSET = ImmutableMap.of(SMALL_TENT, new BlockPos(3, 0, 3), LERGE_TENT, new BlockPos(0, 0, 0), FIRE_PLACE, new BlockPos(0, 0, 0));

	//private static final Map<ResourceLocation, BlockState> DEBUG_BLOCKS = ImmutableMap.of(SMALL_TENT, Blocks.EMERALD_BLOCK.getDefaultState(), LERGE_TENT, Blocks.DIAMOND_BLOCK.getDefaultState(), FIRE_PLACE, Blocks.COAL_BLOCK.getDefaultState());
	
	private static final List<EntityType> GRUNT_TYPES = Lists.newArrayList(ModEntities.MARINE_WITH_SWORD, ModEntities.MARINE_WITH_GUN);
	
	private static EntityType chooseGruntType()
	{
		return GRUNT_TYPES.get((int) WyHelper.randomWithRange(0, GRUNT_TYPES.size() - 1));
	}
	
	// Setting up the structure with a camp fire (center piece), a minimum of 4 small tents with 2 additional ones and 1 large tent, with 1 additional one.
	// Additional tents are based on a random value
	public static void addComponents(TemplateManager templateManager, BlockPos pos, Random rand, List<StructurePiece> components)
	{
		List<BlockPos> list = new ArrayList<BlockPos>();
		boolean hasExtraTents = false;
		
		if(WyHelper.randomDouble() < 0.2)
			hasExtraTents = true;

		components.add(new Piece(templateManager, FIRE_PLACE, pos, Rotation.CLOCKWISE_90));

		components.add(new Piece(templateManager, LERGE_TENT, pos.add(13, -1, 0), Rotation.CLOCKWISE_90));
		if(hasExtraTents)
		{
			components.add(new Piece(templateManager, LERGE_TENT, pos.add(-19, -1, 6), Rotation.COUNTERCLOCKWISE_90));
			list.add(pos.add(-19, -1, 6));
		}
			
		list.add(pos.add(13, -1, 0));
		
		for(int i = 0; i < (hasExtraTents ? 6 : 4); i++)
		{
			BlockPos piecePos = trySpawnTent(pos, 0, list);
			if(piecePos == null)
				continue;
			components.add(new Piece(templateManager, SMALL_TENT, piecePos, Rotation.randomRotation(rand)));
			list.add(piecePos);
		}
	}
	
	private static BlockPos trySpawnTent(BlockPos pos, int attempts, List<BlockPos> list)
	{
		if(attempts > 20)
			return null;
		for(BlockPos tentPos : list)
		{
			if(Math.sqrt(tentPos.distanceSq(pos)) < 9)
				return trySpawnTent(pos, ++attempts, list);
		}
		BlockPos random = new BlockPos(WyHelper.randomWithRange(-40, 40), 0, WyHelper.randomWithRange(-40, 40));
		BlockPos piecePos = pos.add(random.getX(), 0, random.getZ());
		if(Math.sqrt(pos.distanceSq(piecePos)) < 15)
			return trySpawnTent(pos, ++attempts, list);
		
		return piecePos;
	}
	
	public static class Piece extends TemplateStructurePiece
	{
		private ResourceLocation resourceLocation;
		private Rotation rotation;
		private Random rand;
		
		public Piece(TemplateManager template, CompoundNBT nbt)
		{
			super(ModFeatures.Pieces.MARINE_CAMP_BODY, nbt);
			this.resourceLocation = new ResourceLocation(nbt.getString("Template"));
			this.rotation = Rotation.valueOf(nbt.getString("Rot"));
			this.build(template);
		}

		public Piece(TemplateManager template, ResourceLocation res, BlockPos pos, Rotation rot)
		{
			super(ModFeatures.Pieces.MARINE_CAMP_BODY, 0);
			this.rotation = rot;
			this.resourceLocation = res;
			this.templatePosition = pos;
			this.build(template);
		}

		@Override
		protected void readAdditional(CompoundNBT nbt)
		{
			super.readAdditional(nbt);
			nbt.putString("Template", this.resourceLocation.toString());
			nbt.putString("Rot", this.rotation.name());
		}
		
		private void build(TemplateManager templateManager)
		{
			Template template = templateManager.getTemplateDefaulted(this.resourceLocation);
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(MarineCampPieces.CENTER_OFFSET.get(this.resourceLocation)).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
			this.setup(template, this.templatePosition, placementsettings);
			
			WyDebug.debug("Marine Camp spawned at: /tp " + this.templatePosition.getX() + " ~ " + this.templatePosition.getZ());
		}

		@Override
		protected void handleDataMarker(String function, BlockPos pos, IWorld world, Random rand, MutableBoundingBox sbb)
		{
			// Chests
			if (function.equals("small_tent_chest"))
			{
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				TileEntity tileentity = world.getTileEntity(pos.down());
				if (tileentity instanceof ChestTileEntity)
				{
					((ChestTileEntity) tileentity).setLootTable(ModLootTables.CAMP_SMALL_TENT, rand.nextLong());
				}
			}
			if (function.equals("large_tent_chest"))
			{
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				TileEntity tileentity = world.getTileEntity(pos.down());
				if (tileentity instanceof ChestTileEntity)
				{
					((ChestTileEntity) tileentity).setLootTable(ModLootTables.CAMP_LARGE_TENT, rand.nextLong());
				}
			}

			// Spawners
			if (function.equals("captain_spawn"))
			{
				world.setBlockState(pos, ModBlocks.CUSTOM_SPAWNER.getDefaultState(), 3);
				TileEntity spawner = world.getTileEntity(pos);
				if (spawner instanceof CustomSpawnerTileEntity)
				{
					((CustomSpawnerTileEntity) spawner).setSpawnerLimit(1);
					((CustomSpawnerTileEntity) spawner).setSpawnerMob(ModEntities.MARINE_CAPTAIN);
				}
			}
			if (function.equals("grunt_spawn"))
			{
				world.setBlockState(pos, ModBlocks.CUSTOM_SPAWNER.getDefaultState(), 3);
				TileEntity spawner = world.getTileEntity(pos);
				if (spawner instanceof CustomSpawnerTileEntity)
				{
					((CustomSpawnerTileEntity) spawner).setSpawnerLimit(2);
					((CustomSpawnerTileEntity) spawner).setSpawnerMob(MarineCampPieces.chooseGruntType());
				}
			}
		}

		@Override
		public boolean addComponentParts(IWorld world, Random random, MutableBoundingBox bb, ChunkPos chunkPos)
		{
			PlacementSettings placementsettings = (new PlacementSettings()).setRotation(this.rotation).setMirror(Mirror.NONE).setCenterOffset(MarineCampPieces.CENTER_OFFSET.get(this.resourceLocation)).addProcessor(BlockIgnoreStructureProcessor.AIR_AND_STRUCTURE_BLOCK);
			BlockPos offset = new BlockPos(0, 0, 0);
			this.templatePosition.add(Template.transformedBlockPos(placementsettings, new BlockPos(offset.getX(), offset.getY(), offset.getZ())));
			
			BlockPos blockpos2 = this.templatePosition;
			int i = world.getHeight(Heightmap.Type.WORLD_SURFACE_WG, this.templatePosition.getX(), this.templatePosition.getZ());
			this.templatePosition = this.templatePosition.add(0, i - 90 - 1, 0);

			boolean flag = super.addComponentParts(world, random, bb, chunkPos);

			//BlockPos debugOffset = this.templatePosition.add(MarineCampPieces.CENTER_OFFSET.get(this.resourceLocation));
			//world.setBlockState(debugOffset, DEBUG_BLOCKS.get(this.resourceLocation), 3);
			
			this.templatePosition = blockpos2;
			return flag;		
		}
	}
}
