package com.devin12422.tweaks.item.weapon.onehanded.rapier;

import java.util.function.Consumer;

import com.devin12422.tweaks.item.weapon.onehanded.OneHandedWeaponItem;
import com.devin12422.tweaks.item.weapon.onehanded.hammer.HammerEntity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ArclightRapier extends OneHandedWeaponItem implements Vanishable {

	public ArclightRapier(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BOW;
	}

	public int getMaxUseTime(ItemStack stack) {
		return 10000;
	}

	public static double getAngle(double vx, double vy) {
		return Math.toDegrees(Math.atan2(vy, vx));
	}

	public static double getVelocityWithAngle(double vx, double vy) {
		return Math.sqrt(Math.pow(vx, 2) + Math.pow(vy, 2));
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (user instanceof PlayerEntity) {
			PlayerEntity playerEntity = (PlayerEntity) user;
			int i = this.getMaxUseTime(stack) - remainingUseTicks;
			if (i >= 10) {
				if (!world.isClient) {
					stack.damage(1, (LivingEntity) playerEntity, (Consumer) ((p) -> {
						((LivingEntity) p).sendToolBreakStatus(user.getActiveHand());
					}));

				}
				System.out.println(playerEntity.getHeadYaw());
				float pitch = playerEntity.getHeadYaw();
				System.out.println(pitch);
//dash through enemies
				float yaw = (float) (((playerEntity.getYaw() + 90) * Math.PI) / 180);
				playerEntity.setVelocity(Math.toRadians(Math.sin(pitch) * Math.cos(yaw)), 0.0f,
						Math.toRadians(Math.cos(pitch)));
				playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

			}
		}
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		ItemStack itemStack = user.getStackInHand(hand);
		if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
			return TypedActionResult.fail(itemStack);
		} else {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		}
	}
}
