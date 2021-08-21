package com.devin12422.tweaks.block.crops;

import com.devin12422.tweaks.item.Items;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

public class CornBlock extends TallCropBlock {

 
    public CornBlock(AbstractBlock.Settings settings) {
        super(settings);
    }
 
    public ItemConvertible getSeedsItem() {
        return Items.CUSTOM_SEEDS;
    }
 

}
