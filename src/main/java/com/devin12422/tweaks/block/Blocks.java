package com.devin12422.tweaks.block;

import com.devin12422.tweaks.block.crops.CornBlock;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks {
//  
//	public static final Item WOOD_HAMMER = register("wood_hammer", "weapon/onehanded/hammer/",
//			new HammerItem(ToolMaterials.WOOD, 1, -2.4F, new Item.Settings().group(ItemGroup.COMBAT)));

	public static final Block CUSTOM_CROP_BLOCK = register("corn", "crops/",
			new CornBlock(AbstractBlock.Settings.of(Material.REPLACEABLE_PLANT).noCollision().ticksRandomly()
					.breakInstantly().sounds(BlockSoundGroup.CROP)));

	public static Block register(String name, String type, Block item) {

		return Registry.register(Registry.BLOCK, new Identifier("tweaks", type + name), item);
	}

	public static void init() {

//		for (Item item : projectileSpells) {
//			GameRegistry.registerItem(item, item.getUnlocalizedName());
//		}
//		projectileSpells.forEach(new Consumer<SpellProjectile>() {
//
//			@Override
//			public void accept(SpellProjectile t) {
//				register(t.getSpellType() + "_ball_spell", "item/handheld/", (Item) t);
//			}
//
//		});
	}

}
