package com.devin12422.tweaks.item.weapon.onehanded.hammer.doom;

import java.util.Vector;
import java.util.function.Consumer;

import com.devin12422.tweaks.item.weapon.onehanded.OneHandedWeaponItem;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class DoomItem extends OneHandedWeaponItem implements Vanishable {
	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
	private float attackDamage;

	public DoomItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
		super(toolMaterial, attackDamage, attackSpeed, settings);
		this.attackDamage = attackDamage;
		Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID,
				"Weapon modifier", (double) this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID,
				"Weapon modifier", (double) attackSpeed, EntityAttributeModifier.Operation.ADDITION));
//      builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Tool modifier", -2.9000000953674316D, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.SPEAR;
	}

	public int getMaxUseTime(ItemStack stack) {
		return 32000;
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
					DoomEntity tridentEntity = new DoomEntity(world, playerEntity, stack);

					tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 4.0f, 1.0F);
//					if (user.isSneaking()) {
//				
//					}
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
