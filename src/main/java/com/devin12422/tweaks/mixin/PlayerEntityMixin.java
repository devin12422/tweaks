package com.devin12422.tweaks.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import com.devin12422.tweaks.PlayerAccess;
import com.devin12422.tweaks.item.weapon.onehanded.OneHandedWeaponItem;
import com.devin12422.tweaks.item.weapon.onehanded.fist.FistItem;
import com.devin12422.tweaks.util.ClientStorage;
import com.devin12422.tweaks.util.PlayerEntityExt;

import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.MinecraftClientGame;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.HungerManager;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.item.SwordItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityExt, PlayerAccess {

	public PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
		super(type, world);
		// TODO Auto-generated constructor stub
	}

	private int lastAttackedOffhandTicks;
	private Entity target;
	private boolean offHandAttack;
	private float maxStamina = 20;
	private float maxMagicka = 20;

	@Inject(method = "Lnet/minecraft/entity/player/PlayerEntity;tick()V", at = @At(value = "FIELD", target = "Lnet/minecraft/entity/player/PlayerEntity;lastAttackedTicks:I", ordinal = 0))
	private void tickMixin(CallbackInfo info) {
		lastAttackedOffhandTicks++;
	}

	@Override
	public void resetLastOffhandAttackTicks() {
		lastAttackedOffhandTicks = 0;
	}

	@ModifyVariable(method = "attack", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getAttackDamage(Lnet/minecraft/item/ItemStack;Lnet/minecraft/entity/EntityGroup;)F"), ordinal = 0)
	private float attackDamageMixin(float original) {
		Item item = ((PlayerEntity) (Object) this).getOffHandStack().getItem();
		if (this.offHandAttack) {
			if (item instanceof FistItem) {
				FistItem swordItem = (FistItem) item;
				return swordItem.getAttackDamage() + 1F;
			} else {
				MiningToolItem miningToolItem = (MiningToolItem) item;
				return miningToolItem.getAttackDamage() + 1F;
			}
		} else
			return original;
	}

	@ModifyVariable(method = "attack", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F", shift = Shift.BEFORE), ordinal = 1)
	private float attackEnchantmentDamageMixin(float original) {
		ItemStack itemStack = ((PlayerEntity) (Object) this).getOffHandStack();
		if (this.offHandAttack) {
			float h;
			if (this.target != null && this.target instanceof LivingEntity) {
				h = EnchantmentHelper.getAttackDamage(itemStack, ((LivingEntity) target).getGroup());
			} else {
				h = EnchantmentHelper.getAttackDamage(itemStack, EntityGroup.DEFAULT);
			}
			return h;
		} else
			return original;
	}

	@ModifyVariable(method = "attack", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/entity/player/PlayerEntity;getAttackCooldownProgress(F)F"), ordinal = 2)
	private float cooldownProgressMixin(float original) {
		if (this.offHandAttack) {
			return this.getAttackCooldownProgressOffhand(0.5F);
		} else
			return original;
	}

	@Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;resetLastAttackedTicks()V"))
	private void attackResetLastAttackedTickskMixin(CallbackInfo info) {
		if (this.offHandAttack) {
			this.resetLastOffhandAttackTicks();
		}
	}

	@Inject(method = "resetLastAttackedTicks", at = @At(value = "HEAD"), cancellable = true)
	private void resetLastAttackedTickskMixin(CallbackInfo info) {
		if (this.offHandAttack == true) {
			info.cancel();
		}
	}

	@Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;postHit(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/player/PlayerEntity;)V"))
	private void attackPostHitMixin(ItemStack itemstack, LivingEntity livingEntity, PlayerEntity playerEntity) {
		if (this.offHandAttack) {
			playerEntity.getOffHandStack().postHit(livingEntity, playerEntity);
		} else
			itemstack.postHit((LivingEntity) livingEntity, playerEntity);
	}

	@Inject(method = "attack", at = @At(value = "TAIL"))
	public void attackMixin(CallbackInfo info) {
		if (this.offHandAttack) {
			this.offHandAttack = false;
		}
	}

	@Inject(method = "attack", at = @At(value = "HEAD"))
	public void attackMixin(Entity target, CallbackInfo info) {
		if (target.isAttackable()) {
			this.target = target;
		}
	}

	@Inject(method = "spawnSweepAttackParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I"), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	public void spawnSweepAttackParticles(CallbackInfo info, double d, double e) {
		if (this.offHandAttack) {
			PlayerEntity playerEntity = (PlayerEntity) (Object) this;
			((ServerWorld) playerEntity.world).spawnParticles(ParticleTypes.ITEM_SNOWBALL, playerEntity.getX() + d,
					playerEntity.getBodyY(0.5D), playerEntity.getZ() + e, 0, d, 0.0D, e, 0.0D);
			info.cancel();
		}
	}

	@Shadow
	public float getAttackCooldownProgressPerTick() {
		return 1.0F;
	}

	@Override
	public void setOffhandAttack() {
		this.offHandAttack = true;
	}

	@Override
	public boolean isOffhandAttack() {
		return this.offHandAttack;
	}

	@Override
	public float getAttackCooldownProgressOffhand(float baseTime) {
		return MathHelper.clamp(
				((float) this.lastAttackedOffhandTicks + baseTime) / this.getAttackCooldownProgressPerTick(), 0.0F,
				1.0F);
	}

	@Shadow
	public final PlayerAbilities abilities = new PlayerAbilities();

	@Shadow
	private HungerManager getHungerManager() {
		return getHungerManager();
	}

	@Shadow
	private void addExhaustion(float exhaustion) {
	}

	public void addMaxStamina(float amount) {
		this.maxStamina += amount;
		ClientStorage.maxStamina = this.maxStamina;
	}

	public void addMaxMagicka(float amount) {
		this.maxMagicka += amount;
		ClientStorage.maxMagicka = this.maxMagicka;

	}

	public float getMaxStamina() {
		return this.maxStamina;
	}

	public float getMaxMagicka() {
		return this.maxMagicka;
	}

	@Inject(method = "writeCustomDataToNbt", at = @At("RETURN"))
	public void onWriteDataToTag(NbtCompound tag, CallbackInfo ci) {
		System.out.println("dsabruh1");
		tag.putFloat("maxStamina", this.maxStamina);
		tag.putFloat("maxMagicka", this.maxMagicka);
		tag.putFloat("stamina", ClientStorage.stamina);
		tag.putFloat("magicka", ClientStorage.magicka);

	}

	@Inject(method = "readCustomDataFromNbt", at = @At("RETURN"))
	public void onReadDataFromTag(NbtCompound tag, CallbackInfo ci) {
		System.out.println("dsabruh2");

		if (tag.contains("maxStamina") && tag.contains("maxMagicka") && tag.contains("stamina")
				&& tag.contains("magicka")) {
			this.maxStamina = tag.getFloat("maxStamina");
			this.maxMagicka = tag.getFloat("maxMagicka");
			ClientStorage.maxMagicka = this.maxMagicka;
			ClientStorage.maxStamina = this.maxStamina;

			ClientStorage.stamina = tag.getFloat("stamina");
			ClientStorage.magicka = tag.getFloat("magicka");

		} else {
			this.maxMagicka = 20;
			this.maxStamina = 20;
		}
	}

//	   protected void applyDamage(DamageSource source, float amount) {
	@Inject(method = "applyDamage", at = @At("HEAD"))
	public void onApplyDamage(CallbackInfo ci) {
		ClientStorage.lastExhaustTick = System.currentTimeMillis();
	}

	@Inject(method = "tick", at = @At("HEAD"))
	public void onTick(CallbackInfo ci) {
		if (ClientStorage.cooldown > 0) {
			ClientStorage.cooldown--;
		}
		if (abilities.creativeMode) {
			ClientStorage.stamina = 20;
			ClientStorage.magicka = 20;

		}
//		if()
		if (ClientStorage.magicka < maxMagicka && ClientStorage.lastCastTick + 3000 < System.currentTimeMillis()) {
			ClientStorage.magicka += 0.125;
		}
		if (ClientStorage.stamina < maxStamina && this.getHungerManager().getFoodLevel() >= 6
				&& ClientStorage.lastExhaustTick + 3000 < System.currentTimeMillis()) {
			ClientStorage.stamina += 0.125;
			this.addExhaustion(0.05f);
		}
	}

	@Redirect(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V", ordinal = 3))
	public void removeSprintingStamina(PlayerEntity playerEntity, float exhaustion) {
		if (playerEntity.world.getBlockState(new BlockPos(playerEntity.getPos().subtract(0, 0.93750, 0)))
				.getBlock() != Blocks.DIRT_PATH) {
			ClientStorage.stamina -= 0.04f;
			ClientStorage.lastExhaustTick = System.currentTimeMillis();
		}
	}

	@Redirect(method = "increaseTravelMotionStats", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addExhaustion(F)V", ordinal = 0))
	public void removeSwimmingStamina(PlayerEntity playerEntity, float exhaustion) {
		ClientStorage.stamina -= 0.02f;
		ClientStorage.lastExhaustTick = System.currentTimeMillis();
	}

	@Inject(method = "jump()V", at = @At(value = "HEAD"), cancellable = true)
	public void jumpCancel(CallbackInfo ci) {
		if (ClientStorage.stamina <= 1.0f && !abilities.creativeMode)
			ci.cancel();
	}

	@Inject(method = "jump()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V", shift = At.Shift.AFTER), cancellable = true)
	public void injected(CallbackInfo ci) {
		ClientStorage.stamina -= 0.6f;
		ClientStorage.lastExhaustTick = System.currentTimeMillis();

		ci.cancel();
	}

}
