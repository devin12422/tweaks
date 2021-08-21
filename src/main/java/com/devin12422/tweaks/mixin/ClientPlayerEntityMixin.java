package com.devin12422.tweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.devin12422.tweaks.util.ClientStorage;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
	@Redirect(method = "tickMovement()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/HungerManager;getFoodLevel()I"))
	private int injected(HungerManager bruh) {
	  return Math.round(ClientStorage.stamina+5);
	}
	@Inject(method = "setShowsDeathScreen(Z)V", at = @At(value = "HEAD"))
	public void onSetShowsDeathScreen(boolean shouldShow,CallbackInfo ci) {
	      ClientStorage.stamina = ClientStorage.maxStamina;
	      ClientStorage.magicka = ClientStorage.maxMagicka;

	   }
}
