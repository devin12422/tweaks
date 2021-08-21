package com.devin12422.tweaks.item.weapon.onehanded.hammer;

import java.util.function.Consumer;

import com.devin12422.tweaks.item.weapon.onehanded.OneHandedWeaponItem;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.world.World;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;

public class HammerItem extends OneHandedWeaponItem implements Vanishable {

	public HammerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.NONE;
	}

	public int getMaxUseTime(ItemStack stack) {
		return 10000;
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
					HammerEntity tridentEntity = new HammerEntity(world, playerEntity, stack);
					tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F,this.getAttackDamage()/2, 1.0F);
					if (playerEntity.getAbilities().creativeMode) {
						tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
					}

					world.spawnEntity(tridentEntity);
//                     
					world.playSoundFromEntity((PlayerEntity) null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW,
							SoundCategory.PLAYERS, 1.0F, 1.0F);
					if (!playerEntity.getAbilities().creativeMode) {
						playerEntity.getInventory().removeOne(stack);
					}
				}

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

	public int getEnchantability() {
		return this.getMaterial().getEnchantability();
	}
}
