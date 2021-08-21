package com.devin12422.tweaks.entities.goals;

import java.util.EnumSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;

public class ChainInGoal extends Goal {
	public static final float field_33760 = 0.02F;
	protected final MobEntity mob;
	protected Entity target;
	protected final float range;
	protected final float chance;
	private final boolean field_33761;
	private long lastPull;
	protected final Class<? extends LivingEntity> targetType;
	protected final TargetPredicate targetPredicate;

	public ChainInGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range) {
		this(mob, targetType, range, 0.02F);
		this.lastPull = System.currentTimeMillis();

	}

	public ChainInGoal(MobEntity mob, Class<? extends LivingEntity> targetType, float range, float chance) {
		this(mob, targetType, range, chance, false);
		this.lastPull = System.currentTimeMillis();

	}

	public ChainInGoal(MobEntity mobEntity, Class<? extends LivingEntity> class_, float f, float g, boolean bl) {
		this.mob = mobEntity;
		this.lastPull = System.currentTimeMillis();

		this.targetType = class_;
		this.range = f;
		this.chance = g;
		this.field_33761 = bl;
		this.setControls(EnumSet.of(Goal.Control.LOOK));
		if (class_ == PlayerEntity.class) {
			this.targetPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance((double) f)
					.setPredicate((livingEntity) -> {
						return EntityPredicates.rides(mobEntity).test(livingEntity);
					});
		} else {
			this.targetPredicate = TargetPredicate.createNonAttackable().setBaseMaxDistance((double) f);
		}

	}

	public boolean canStart() {
		if (this.lastPull + 3000 < System.currentTimeMillis())
			return false;
		if (this.mob.getRandom().nextFloat() >= this.chance) {
			return false;
		} else {
			if (this.mob.getTarget() != null) {
				this.target = this.mob.getTarget();
			}

			return this.target != null;
		}
	}

	public boolean shouldContinue() {
		return true;
	}

	public void start() {
		this.lastPull = System.currentTimeMillis();
	}

	public void stop() {
		this.target = null;
	}

	public void tick() {
		double d = this.field_33761 ? this.mob.getEyeY() : this.target.getEyeY();
		this.target.setVelocity(0, 10.0f, 0.0f);
		this.mob.getLookControl().lookAt(this.target.getX(), d, this.target.getZ());
	}
}
