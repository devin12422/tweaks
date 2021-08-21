package com.devin12422.tweaks.block.crops;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.TallPlantBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

public class TallCropBlock extends TallPlantBlock implements Fertilizable {
		   public TallCropBlock(AbstractBlock.Settings settings) {
		      super(settings);
		   }

		   public boolean canReplace(BlockState state, ItemPlacementContext context) {
		      return false;
		   }

		   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
		      return true;
		   }

		   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
		      return true;
		   }

		   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
		      dropStack(world, pos, new ItemStack(this));
		   }

//
//	   public static final int MAX_AGE = 7;
//	   public static final IntProperty AGE;
//
//	   protected TallCropBlock(AbstractBlock.Settings settings) {
//	      super(settings);
//	      this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(this.getAgeProperty(), 0));
//	   }
//
//	   protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
//	      return floor.isOf(Blocks.FARMLAND);
//	   }
//
//	   public IntProperty getAgeProperty() {
//	      return AGE;
//	   }
//
//	   public int getMaxAge() {
//	      return 7;
//	   }
//
//	   protected int getAge(BlockState state) {
//	      return (Integer)state.get(this.getAgeProperty());
//	   }
//
//	   public BlockState withAge(int age) {
//	      return (BlockState)this.getDefaultState().with(this.getAgeProperty(), age);
//	   }
//
//	   public boolean isMature(BlockState state) {
//	      return (Integer)state.get(this.getAgeProperty()) >= this.getMaxAge();
//	   }
//
//	   public boolean hasRandomTicks(BlockState state) {
//	      return !this.isMature(state);
//	   }
//
//	   public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
//	      if (world.getBaseLightLevel(pos, 0) >= 9) {
//	         int i = this.getAge(state);
//	         if (i < this.getMaxAge()) {
//	            float f = getAvailableMoisture(this, world, pos);
//	            if (random.nextInt((int)(25.0F / f) + 1) == 0) {
//	               world.setBlockState(pos, this.withAge(i + 1), Block.NOTIFY_LISTENERS);
//	            }
//	         }
//	      }
//
//	   }
//	   public boolean canReplace(BlockState state, ItemPlacementContext context) {
//		      return false;
//		   }
//	   public void applyGrowth(World world, BlockPos pos, BlockState state) {
//	      int i = this.getAge(state) + this.getGrowthAmount(world);
//	      int j = this.getMaxAge();
//	      if (i > j) {
//	         i = j;
//	      }
//
//	      world.setBlockState(pos, this.withAge(i), Block.NOTIFY_LISTENERS);
//	   }
//
//	   protected int getGrowthAmount(World world) {
//	      return MathHelper.nextInt(world.random, 2, 5);
//	   }
//
//	   protected static float getAvailableMoisture(Block block, BlockView world, BlockPos pos) {
//	      float f = 1.0F;
//	      BlockPos blockPos = pos.down();
//
//	      for(int i = -1; i <= 1; ++i) {
//	         for(int j = -1; j <= 1; ++j) {
//	            float g = 0.0F;
//	            BlockState blockState = world.getBlockState(blockPos.add(i, 0, j));
//	            if (blockState.isOf(Blocks.FARMLAND)) {
//	               g = 1.0F;
//	               if ((Integer)blockState.get(FarmlandBlock.MOISTURE) > 0) {
//	                  g = 3.0F;
//	               }
//	            }
//
//	            if (i != 0 || j != 0) {
//	               g /= 4.0F;
//	            }
//
//	            f += g;
//	         }
//	      }
//
//	      BlockPos blockPos2 = pos.north();
//	      BlockPos blockPos3 = pos.south();
//	      BlockPos blockPos4 = pos.west();
//	      BlockPos blockPos5 = pos.east();
//	      boolean bl = world.getBlockState(blockPos4).isOf(block) || world.getBlockState(blockPos5).isOf(block);
//	      boolean bl2 = world.getBlockState(blockPos2).isOf(block) || world.getBlockState(blockPos3).isOf(block);
//	      if (bl && bl2) {
//	         f /= 2.0F;
//	      } else {
//	         boolean bl3 = world.getBlockState(blockPos4.north()).isOf(block) || world.getBlockState(blockPos5.north()).isOf(block) || world.getBlockState(blockPos5.south()).isOf(block) || world.getBlockState(blockPos4.south()).isOf(block);
//	         if (bl3) {
//	            f /= 2.0F;
//	         }
//	      }
//
//	      return f;
//	   }
//
//	   public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
//	      return (world.getBaseLightLevel(pos, 0) >= 8 || world.isSkyVisible(pos)) && super.canPlaceAt(state, world, pos);
//	   }
//
//	   public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
//	      if (entity instanceof RavagerEntity && world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {
//	         world.breakBlock(pos, true, entity);
//	      }
//
//	      super.onEntityCollision(state, world, pos, entity);
//	   }
//
//	   protected ItemConvertible getSeedsItem() {
//	      return null;
//	   }
//
//	   public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
//	      return new ItemStack(this.getSeedsItem());
//	   }
//
//	   public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
//	      return !this.isMature(state);
//	   }
//
//	   public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
//	      return true;
//	   }
//
//	   public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
//	      this.applyGrowth(world, pos, state);
//	   }
//
//	   protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
//	      builder.add(AGE);
//	   }
//
//	   static {
//	      AGE = Properties.AGE_7;
//	   }

	
}
