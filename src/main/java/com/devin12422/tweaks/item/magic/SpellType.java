package com.devin12422.tweaks.item.magic;

import com.devin12422.tweaks.item.magic.destruction.MagicProjectileEntity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.registry.Registry;

public interface SpellType{
//	final int cost = 5;
//	default void getSpellEffect() {
//	}
	public default void applySpellEffect(EntityHitResult entityHitResult) {
	}

	default public void hitBlockResult(EntityHitResult entityHitResult, boolean destructive) {
	}

	default public void flyingTick(Entity owner) {
	}

	default public void inWaterTick() {
	}

	default public void inBlockTick() {
	}

	default public void inEnemyTick() {
	
	}
	default public String getType() {
		return type;
	}
	
	default public <T extends SpellType> EntityType<? extends PersistentProjectileEntity> createBallSpell(T spell, String ballName){
		return Registry.register(Registry.ENTITY_TYPE,
				new Identifier("tweaks", ballName+"_ball_entity"),
				FabricEntityTypeBuilder.create(SpawnGroup.MISC,
						(EntityType.EntityFactory<MagicProjectileEntity<T>>) MagicProjectileEntity<T>::new)
						.dimensions(EntityDimensions.fixed(0.3f, 0.3f)).build());
		
	}
	final String type = "magic";
}
