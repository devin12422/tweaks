package com.devin12422.tweaks.mixin;

import java.util.HashMap;
import java.util.Map;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.devin12422.tweaks.util.ClientStorage;
import com.google.common.collect.Maps;
import com.ibm.icu.impl.duration.impl.Utils;

import net.minecraft.client.Keyboard;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(KeyBinding.class)
public class DodgeMixin {
	private final static Map<InputUtil.Key, KeyBinding> keyToBindings = Maps.newHashMap();
	private static HashMap<KeyBinding, Long> keyTimesLastPressed = new HashMap<>();
	private static boolean needsInit = true;
	private static HashMap<KeyBinding, String> lookupKeyToDirection = new HashMap<>();

	private static HashMap<KeyBinding, Boolean> keyLastState = new HashMap<>();
	private static long lastDodge = System.currentTimeMillis();

	@Inject(method = "onKeyPressed", at = @At("HEAD"))
	private static void checkDodge(InputUtil.Key key, CallbackInfo ci) {
		if (!key.equals(null)) {
			KeyBinding keyBinding = (KeyBinding) keyToBindings.get(key);
			if (MinecraftClient.getInstance().currentScreen == null) {
				if (needsInit) {
					needsInit = false;
					lookupKeyToDirection.put(MinecraftClient.getInstance().options.keyBack, "back");
					lookupKeyToDirection.put(MinecraftClient.getInstance().options.keyLeft, "left");
					lookupKeyToDirection.put(MinecraftClient.getInstance().options.keyRight, "right");
					keyTimesLastPressed.put(MinecraftClient.getInstance().options.keyBack, System.currentTimeMillis());
					keyTimesLastPressed.put(MinecraftClient.getInstance().options.keyLeft, System.currentTimeMillis());
					keyTimesLastPressed.put(MinecraftClient.getInstance().options.keyRight, System.currentTimeMillis());
					keyLastState.put(MinecraftClient.getInstance().options.keyBack, false);
					keyLastState.put(MinecraftClient.getInstance().options.keyLeft, false);
					keyLastState.put(MinecraftClient.getInstance().options.keyRight, false);
				}

				for (Map.Entry<KeyBinding, Boolean> entry : keyLastState.entrySet()) {
					long curTime = System.currentTimeMillis();
					long lastTime = getLastKeyTime(keyBinding);
					boolean doublePressed = false;
					PlayerEntity player = MinecraftClient.getInstance().player;

					if (entry.getKey().isPressed()) {
						if (entry.getValue() && (player.isCreative() || ClientStorage.stamina >= 4)
								&& ClientStorage.cooldown <= 0) {
						

							if (lastTime + 100 < curTime && (lastTime + 100) - curTime > -200 && player.isOnGround()) {
								ClientStorage.cooldown = 16;
								ClientStorage.stamina -= 3;
								doublePressed = true;
							}
						}

						if (doublePressed) {
//							if(!player.isCreative()) player.applyStatusEffect();
							lastDodge = System.currentTimeMillis();
							ClientStorage.lastExhaustTick = System.currentTimeMillis();
							double f = 1.2f;
							double motionX = player.getVelocity().x;
							double motionZ = player.getVelocity().z;
							if (lookupKeyToDirection.get(entry.getKey()) == "left") {
								motionX = (double) (MathHelper.cos(player.getYaw() / 180.0F * (float) Math.PI)
										* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
								motionZ = (double) -(-MathHelper.sin(player.getYaw() / 180.0F * (float) Math.PI)
										* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
							}
							if (lookupKeyToDirection.get(entry.getKey()) == "right") {
								motionX = (double) -(MathHelper.cos(player.getYaw() / 180.0F * (float) Math.PI)
										* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
								motionZ = (double) (-MathHelper.sin(player.getYaw() / 180.0F * (float) Math.PI)
										* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
							}
							if (lookupKeyToDirection.get(entry.getKey()) == "back") {
								motionX = (double) -(-MathHelper.sin(player.getYaw() / 180.0F * (float) Math.PI)
										* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
								motionZ = (double) -(MathHelper.cos(player.getYaw() / 180.0F * (float) Math.PI)
										* MathHelper.cos(1 / 180.0F * (float) Math.PI) * f);
							}
							player.setVelocityClient(motionX, player.getVelocity().y - 2, motionZ);

						}
						entry.setValue(true);
						keyTimesLastPressed.put(keyBinding, curTime);
					} else {
						doublePressed = false;
						entry.setValue(false);

					}
				}

			}
		}
	}

	private static long getLastKeyTime(KeyBinding keybind) {
		if (!keyTimesLastPressed.containsKey(keybind)) {
			keyTimesLastPressed.put(keybind, -1L);
		}
		return keyTimesLastPressed.get(keybind);
	}

}
