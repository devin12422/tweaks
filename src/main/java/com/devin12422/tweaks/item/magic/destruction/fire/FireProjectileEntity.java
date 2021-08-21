package com.devin12422.tweaks.item.magic.destruction.fire;

import com.devin12422.tweaks.item.magic.SpellType;
import com.devin12422.tweaks.item.magic.destruction.MagicProjectileEntity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.world.World;

public class FireProjectileEntity extends MagicProjectileEntity<FireType>{

	public FireProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
		// TODO Auto-generated constructor stub
	}
	public FireProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, LivingEntity livingEntity,
			World world, SpellType element) {
		super(entityType, livingEntity, world, element);
		// TODO Auto-generated constructor stub
	}

	public FireProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, double d, double e,
			double f, World world, SpellType element) {
		super(entityType, d, e, f, world, element);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public ParticleEffect getParticleType() {
		return ParticleTypes.SMALL_FLAME;
	}

}
