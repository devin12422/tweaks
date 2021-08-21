package com.devin12422.tweaks.item.magic.destruction.fire;

import com.devin12422.tweaks.item.magic.SpellType;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.hit.EntityHitResult;

public class FireType implements SpellType {

	public void applySpellEffect(EntityHitResult entityHitResult) {
		entityHitResult.getEntity().setOnFireFor(4);

	}

	@Override
	public void hitBlockResult(EntityHitResult entityHitResult, boolean destructive) {
		// TODO Auto-generated method stub

	}
}
