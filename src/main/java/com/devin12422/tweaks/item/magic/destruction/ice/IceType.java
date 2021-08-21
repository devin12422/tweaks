package com.devin12422.tweaks.item.magic.destruction.ice;
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

public class IceType implements SpellType {

	public void applySpellEffect(EntityHitResult entityHitResult) {
		System.out.println(entityHitResult.getEntity().getType());
		if(entityHitResult.getEntity().isAlive()) {
			LivingEntity target = ((LivingEntity)entityHitResult.getEntity());
			
			target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 80, 2));
			
//			target.get;
//			target.fire(false);
			target.extinguish();
//			RendererAccess.INSTANCE;
		}
//		entityHitResult.getEntity().setVelocity(0, 10.0f, 0);;

	}

	@Override
	public void hitBlockResult(EntityHitResult entityHitResult, boolean destructive) {
		// TODO Auto-generated method stub

	}

}
