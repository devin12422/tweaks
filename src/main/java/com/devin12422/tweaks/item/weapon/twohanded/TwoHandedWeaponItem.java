package com.devin12422.tweaks.item.weapon.twohanded;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import java.util.function.Consumer;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShieldItem;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.item.Vanishable;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TwoHandedWeaponItem extends ToolItem implements Vanishable {
	private final float attackDamage;

	private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

	public TwoHandedWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Item.Settings settings) {
		super(toolMaterial, settings);
		this.attackDamage = (float) attackDamage + toolMaterial.getAttackDamage();
		Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
		builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID,
				"Weapon modifier", (double) this.attackDamage, EntityAttributeModifier.Operation.ADDITION));
		builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID,
				"Weapon modifier", (double) attackSpeed, EntityAttributeModifier.Operation.ADDITION));
		this.attributeModifiers = builder.build();
	}

	public float getAttackDamage() {
		return this.attackDamage;
	}

	public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
		return !miner.isCreative(); // mining if creative
	}

	public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
		Material material = state.getMaterial();
		return material != Material.PLANT && material != Material.REPLACEABLE_PLANT && !state.isIn(BlockTags.LEAVES)
				&& material != Material.GOURD ? 1.0F : 1.5F; // mine fast if leaves
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.BLOCK;
	}

	public int getMaxUseTime(ItemStack stack) {
		return 20;
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {

		ItemStack itemStack = user.getStackInHand(hand);
		user.setCurrentHand(hand);
		user.setSprinting(false);
//		do entity hit here and do heavy hit
		return TypedActionResult.pass(itemStack);

	}

	public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
		if (target.isBlocking()) {
			attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.BLINDNESS, 40, 1));
			attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 40, 3));
			attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.WEAKNESS, 40, 2));
			attacker.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 40, 1));

		}
		// posthit event
		stack.damage(1, (LivingEntity) attacker, (Consumer<LivingEntity>) ((e) -> { // damage the item and i guess check
																					// if it broke
			((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
		}));
		return true;
	}

	public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
		if (state.getHardness(world, pos) != 0.0F) {
			stack.damage(2, (LivingEntity) miner, (Consumer<LivingEntity>) ((e) -> {
				((LivingEntity) e).sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
			}));
		}

		return true;
	}

	public boolean isEffectiveOn(BlockState state) { // what it breaks good
		return false;
	}

	public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
		return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
	}
}
