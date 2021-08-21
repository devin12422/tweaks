package com.devin12422.tweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.devin12422.tweaks.item.weapon.onehanded.OneHandedWeaponItem;

import net.minecraft.entity.DamageUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.UseAction;

import org.spongepowered.asm.mixin.injection.At;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
	@Shadow
	public static boolean isUsingItem() {
		return isUsingItem();
	}

	@Shadow
	protected static int itemUseTimeLeft;

	@Shadow
	static protected ItemStack activeItemStack;

	@Inject(method = "damage", at = @At("HEAD"), cancellable = true)
	private static boolean onDamage(DamageSource source, float amount) {
		if (isUsingItem() && !activeItemStack.isEmpty()) {
			Item item = activeItemStack.getItem();
			if (!(item instanceof OneHandedWeaponItem)) {
				return false;
			} else {
				return item.getMaxUseTime(activeItemStack) - itemUseTimeLeft >= 5;
			}
		}
		return onDamage(source, amount);
	}
}