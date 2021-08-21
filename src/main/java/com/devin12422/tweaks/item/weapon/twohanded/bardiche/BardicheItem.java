package com.devin12422.tweaks.item.weapon.twohanded.bardiche;

import com.devin12422.tweaks.item.weapon.twohanded.TwoHandedWeaponItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;

public class BardicheItem extends TwoHandedWeaponItem implements Vanishable {
	public BardicheItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
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
