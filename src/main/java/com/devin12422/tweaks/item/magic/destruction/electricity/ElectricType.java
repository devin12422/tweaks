package com.devin12422.tweaks.item.magic.destruction.electricity;

import net.minecraft.entity.mob.MobEntity;

import com.devin12422.tweaks.item.magic.SpellType;

import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;

public class ElectricType implements SpellType {

	public void applySpellEffect(EntityHitResult entityHitResult) {
		System.out.println(entityHitResult.getEntity().getType());
		if (entityHitResult.getEntity().isAlive()) {
			LivingEntity target = ((LivingEntity) entityHitResult.getEntity());

			if (target instanceof MobEntity) {
				((MobEntity) target).setAiDisabled(true);
				new java.util.Timer().schedule(new java.util.TimerTask() {
					@Override
					public void run() {
						((MobEntity) target).setAiDisabled(false);

						// your code here
					}
				}, 2000);
			}
		}

	}

	@Override
	public void hitBlockResult(EntityHitResult entityHitResult, boolean destructive) {
		// TODO Auto-generated method stub

	}

}
