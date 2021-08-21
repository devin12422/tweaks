package com.devin12422.tweaks.item.weapon.onehanded.hammer;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import org.jetbrains.annotations.Nullable;

import com.devin12422.tweaks.ExampleMod;
import com.devin12422.tweaks.item.Items;
import com.devin12422.tweaks.setup.EntityInit;

public class HammerEntity extends PersistentProjectileEntity {
	private static final TrackedData<Boolean> ENCHANTED;
	private ItemStack tridentStack;
	private boolean dealtDamage;
	public int returnTimer;

	public HammerEntity(EntityType<? extends HammerEntity> entityType, World world) {
		super(entityType, world);
		this.tridentStack = new ItemStack(Items.IRON_HAMMER);
	}

	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		Direction bruh = blockHitResult.getSide();
		if (Math.abs(this.getVelocity().length()) > 0.3) {
			if (this.world.getBlockState(blockHitResult.getBlockPos()).getHardness(world,
					blockHitResult.getBlockPos()) <= .6
					|| this.world.getBlockState(blockHitResult.getBlockPos()).getBlock().asItem().getMaxCount() >= 1
							&& this.world.getBlockState(blockHitResult.getBlockPos()).getBlock()
									.getBlastResistance() <= 0.65) {
				this.world.getBlockState(blockHitResult.getBlockPos()).getBlock();
				Block.dropStacks(this.world.getBlockState(blockHitResult.getBlockPos()), world,
						blockHitResult.getBlockPos());
				world.removeBlock(blockHitResult.getBlockPos(), false);
			}

		}
		this.inGround = bruh == Direction.UP;
		if (!this.inGround)
			this.setVelocity((this.getBlockPos().getX() - blockHitResult.getPos().getX()) / 20,
					(this.getBlockPos().getY() - blockHitResult.getPos().getY()) / 20,
					(this.getBlockPos().getZ() - blockHitResult.getPos().getZ()) / 20);
		this.shake = 1;
	}

	public HammerEntity(World world, LivingEntity owner, ItemStack stack) {
		super(EntityInit.IRON_HAMMER_ENTITY, owner, world);
		this.tridentStack = new ItemStack(Items.IRON_HAMMER);
		this.tridentStack = stack.copy();
		this.dataTracker.set(ENCHANTED, stack.hasGlint());
	}

	@Environment(EnvType.CLIENT)
	public HammerEntity(World world, double x, double y, double z) {
		super(EntityInit.IRON_HAMMER_ENTITY, x, y, z, world);
		this.tridentStack = new ItemStack(Items.IRON_HAMMER);
	}

	protected void initDataTracker() {
		super.initDataTracker();

		this.dataTracker.startTracking(ENCHANTED, false);
	}

	public void tick() {
		this.setVelocity(this.getVelocity().multiply(1.0D, 0.8D, 1.0D));
		if (this.inGroundTime > 4) {

			this.dealtDamage = true;
		}


		super.tick();
	}

	private boolean isOwnerAlive() {
		Entity entity = this.getOwner();
		if (entity != null && entity.isAlive()) {
			return !(entity instanceof ServerPlayerEntity) || !entity.isSpectator();
		} else {
			return false;
		}
	}

	protected ItemStack asItemStack() {
		return this.tridentStack.copy();
	}

	@Environment(EnvType.CLIENT)
	public boolean isEnchanted() {
		return (Boolean) this.dataTracker.get(ENCHANTED);
	}

	@Nullable
	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
		return this.dealtDamage ? null : super.getEntityCollision(currentPosition, nextPosition);
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		float f = 8.0F;
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			f += EnchantmentHelper.getAttackDamage(this.tridentStack, livingEntity.getGroup());
		}

		Entity entity2 = this.getOwner();
		DamageSource damageSource = DamageSource.trident(this, (Entity) (entity2 == null ? this : entity2));
		this.dealtDamage = true;
		SoundEvent soundEvent = ExampleMod.HIT_HEAVY_SOUND_EVENT;
		if (entity.damage(damageSource, f)) {
			if (entity.getType() == EntityType.ENDERMAN) {
				return;
			}

			if (entity instanceof LivingEntity) {
				LivingEntity livingEntity2 = (LivingEntity) entity;
				if (entity2 instanceof LivingEntity) {
					EnchantmentHelper.onUserDamaged(livingEntity2, entity2);
					EnchantmentHelper.onTargetDamaged((LivingEntity) entity2, livingEntity2);
				}

				this.onHit(livingEntity2);
			}
		}

		this.setVelocity(this.getVelocity().multiply(0.2D, 0.2D, 0.2D));
		float g = 1.0F;

		this.playSound(soundEvent, g, 1.0F);
	}

	protected SoundEvent getHitSound() {
		return ExampleMod.HAMMER_SOUND_EVENT;
	}

	public void onPlayerCollision(PlayerEntity player) {
		Entity entity = this.getOwner();
		if (entity == null || entity.getUuid() == player.getUuid()) {
			super.onPlayerCollision(player);
		}
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		if (tag.contains("Trident", 10)) {
			this.tridentStack = ItemStack.fromNbt(tag.getCompound("Trident"));
		}

		this.dealtDamage = tag.getBoolean("DealtDamage");
//		this.dataTracker.set(LOYALTY, (byte) EnchantmentHelper.getLoyalty(this.tridentStack));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.put("Trident", this.tridentStack.writeNbt(new NbtCompound()));
		tag.putBoolean("DealtDamage", this.dealtDamage);
	}

	public void age() {
		if (this.pickupType != PersistentProjectileEntity.PickupPermission.ALLOWED) {
			super.age();
		}

	}

	protected float getDragInWater() {
		return 0.99F;
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return true;
	}

	static {
		ENCHANTED = DataTracker.registerData(HammerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	public Packet<?> createSpawnPacket() {
		Entity entity = this.getOwner();
		return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
	}

//	protected Item getDefaultItem() {
//		return ExampleMod.IRON_HAMMER;
//	}
//
//	protected ItemStack getItem() {
//		return new ItemStack(ExampleMod.IRON_HAMMER);
//	}
}
