package com.devin12422.tweaks.item.weapon.onehanded.fist;

import com.devin12422.tweaks.item.weapon.onehanded.OneHandedWeaponItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class FistItem extends OneHandedWeaponItem implements Vanishable {
	public FistItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	public int getEnchantability() {
		return this.getMaterial().getEnchantability();
	}
//	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
//	      ItemStack itemStack = user.getStackInHand(hand);
//	      user.setCurrentHand(hand);
//	      return TypedActionResult.consume(itemStack);
//	   }
}
