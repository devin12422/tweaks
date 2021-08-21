package com.devin12422.tweaks.item.magic;

import com.devin12422.tweaks.util.ClientStorage;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BaseSpellItem<T extends SpellType> extends Item implements Vanishable {
//	<Spell extends SpellClass> 
	protected int cost;
	private T t;

	public BaseSpellItem(Item.Settings settings, int cost, SpellType spell) {
		super(settings);
		this.cost = cost;
		set((T) spell);
	}

	public void set(T t) {
		this.t = t;
	}

	public T get() {
		return t;
	}

	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return false;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

		ItemStack itemStack = user.getStackInHand(hand);
//		if (user.getStackInHand(Hand.OFF_HAND).getItem() instanceof ShieldItem) {
//			user.setCurrentHand(Hand.OFF_HAND);
//			return TypedActionResult.pass(user.getStackInHand(hand));
//
//		}
		user.setCurrentHand(hand);
		user.setSprinting(false);
		return TypedActionResult.pass(itemStack);

	}

	public UseAction getUseAction(ItemStack stack) {
		if (ClientStorage.magicka - this.cost >= 0) {
			ClientStorage.lastCastTick = System.currentTimeMillis();
			return UseAction.BOW;
		}
		return UseAction.NONE;
	}

	public int getMaxUseTime(ItemStack stack) {
		return 52000;
	}

}
