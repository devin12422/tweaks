package com.devin12422.tweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.entity.DamageUtil;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DamageUtil.class)
public class DamageUtilMixin
{
	@Inject(method = "getDamageLeft", at = @At("HEAD"), cancellable = true)
	private static void getDamageLeft(float damage, float armor, float armorToughness, CallbackInfoReturnable<Float> info)
	{
		info.setReturnValue(Math.max(0, damage - (armor / (3 - Math.min(2, armorToughness / 10)))));
	}
}