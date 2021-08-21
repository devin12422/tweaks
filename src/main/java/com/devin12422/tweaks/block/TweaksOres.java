package com.devin12422.tweaks.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;

public class TweaksOres {

	public static final OreBlock ADAMANTITE_ORE = new OreBlock(
			FabricBlockSettings.of(Material.STONE).strength(5.0f, 4.0f).breakByTool(FabricToolTags.PICKAXES, 3)
					.requiresTool().sounds(BlockSoundGroup.GILDED_BLACKSTONE));
	public static final OreBlock CALCITE_KYBER_ORE = new OreBlock(
			FabricBlockSettings.of(Material.STONE).strength(3.0f, 4.0f).breakByTool(FabricToolTags.PICKAXES, 2)
					.requiresTool().sounds(BlockSoundGroup.CALCITE));
	public static final OreBlock DEEPSLATE_ADAMANTITE_ORE = new OreBlock(
			FabricBlockSettings.of(Material.STONE).strength(6.0f, 6.0f).breakByTool(FabricToolTags.PICKAXES, 3)
					.requiresTool().sounds(BlockSoundGroup.DEEPSLATE));
	public static final OreBlock MANGANESE_ORE = new OreBlock(FabricBlockSettings.of(Material.STONE)
			.strength(3.0f, 4.0f).breakByTool(FabricToolTags.PICKAXES, 2).requiresTool());

}
