package com.devin12422.tweaks.item.weapon.onehanded.hammer.doom;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.block.Block;
import net.minecraft.command.argument.EntityAnchorArgumentType.EntityAnchor;
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
import net.minecraft.tag.Tag;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import com.devin12422.tweaks.ExampleMod;
import com.devin12422.tweaks.setup.ClientInit;
import com.devin12422.tweaks.setup.EntityInit;

import io.netty.buffer.Unpooled;

public class DoomEntity extends PersistentProjectileEntity {
	private static final TrackedData<Byte> LOYALTY;
	private static final TrackedData<Boolean> ENCHANTED;
	private ItemStack tridentStack;
	private boolean dealtDamage;
	public int returnTimer;

	public DoomEntity(EntityType<? extends DoomEntity> entityType, World world) {
		super(entityType, world);
		this.setNoGravity(true);

		this.tridentStack = new ItemStack(ExampleMod.DOOM_HAMMER);
	}

	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		if (this.world.getBlockState(blockHitResult.getBlockPos()).getHardness(world, blockHitResult.getBlockPos()) <= 1
				&& this.world.getBlockState(blockHitResult.getBlockPos()).getHardness(world,
						blockHitResult.getBlockPos()) >= 0
				|| this.world.getBlockState(blockHitResult.getBlockPos()).getBlock().asItem().getMaxCount() >= 1
						&& this.world.getBlockState(blockHitResult.getBlockPos()).getBlock()
								.getBlastResistance() <= 1000) {
			this.world.getBlockState(blockHitResult.getBlockPos()).getBlock();
			Block.dropStacks(this.world.getBlockState(blockHitResult.getBlockPos()), world,
					blockHitResult.getBlockPos());
			world.removeBlock(blockHitResult.getBlockPos(), false);
		}
		returnTimer += 10;
		this.shake = 1;
	}

	public DoomEntity(World world, LivingEntity owner, ItemStack stack) {
		super(EntityInit.DOOM_HAMMER_ENTITY, owner, world);
		this.setNoGravity(true);

		this.tridentStack = new ItemStack(ExampleMod.DOOM_HAMMER);
		this.tridentStack = stack.copy();
		this.dataTracker.set(LOYALTY, (byte) EnchantmentHelper.getLoyalty(stack));
		this.dataTracker.set(ENCHANTED, stack.hasGlint());
	}

	@Environment(EnvType.CLIENT)
	public DoomEntity(World world, double x, double y, double z) {
		super(EntityInit.DOOM_HAMMER_ENTITY, x, y, z, world);
		this.setNoGravity(true);

		this.tridentStack = new ItemStack(ExampleMod.DOOM_HAMMER);
	}

	protected void initDataTracker() {
		super.initDataTracker();
		this.dataTracker.startTracking(LOYALTY, (byte) 0);
		this.dataTracker.startTracking(ENCHANTED, false);
	}

	@SuppressWarnings("deprecation")
	public void tick() {
		if (world.isClient()) {

//			if (ClientInit.rAttack.isPressed()) {
//				System.out.println("dsa");
//				PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
//				passedData.writeInt(this.getId());
//				passedData.writeBlockPos(
//						new BlockPos(this.getOwner().getX(), this.getOwner().getEyeY(), this.getOwner().getZ()));
//				ClientSidePacketRegistry.INSTANCE.sendToServer(ExampleMod.CALL_HAMMER_PACKET_ID, passedData);
//			}

		}
		Entity entity = this.getOwner();
		if ((this.dealtDamage || this.isNoClip() || this.returnTimer >= 8) && entity != null) {
			int i = 1; // (Byte) this.dataTracker.get(LOYALTY);
			if (i > 0 && !this.isOwnerAlive()) {
				if (!this.world.isClient && this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED) {
					this.dropStack(this.asItemStack(), 0.1F);
				}

				this.remove(Entity.RemovalReason.DISCARDED);
			} else if (i > 0) {
				Vec3d vec3d = new Vec3d(entity.getX() - this.getX(), entity.getEyeY() - this.getY(),
						entity.getZ() - this.getZ());
				this.setPos(this.getX() + vec3d.x * 0.015D * (double) i, this.getY() + vec3d.y * 0.015D * (double) i,
						this.getZ() + vec3d.z * 0.015D * (double) i);
//			

				this.setVelocity(this.getVelocity().multiply(1.0D).add(vec3d.normalize().multiply(0.05D)));
				if (this.returnTimer == 0) {
					this.playSound(SoundEvents.ITEM_TRIDENT_RETURN, 10.0F, 1.0F);
				}

			}
		}
		++this.returnTimer;
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
	// First we need to actually get hold of the players that we want to send the
	// packets to.
	// A simple way is to obtain all players watching this position:
//    Stream<PlayerEntity> watchingPlayers = PlayerStream.watching(world,pos);
	// Look at the other methods of `PlayerStream` to capture different groups of
	// players.

	// We'll get to this later
//    PacketByteBuf passedData = new PacketByteBuf(Unpooled.buffer());
//	passedData.writeBlockPos(pos);

	// Then we'll send the packet to all the players
//    watchingPlayers.forEach(player ->
//            ServerSidePacketRegistry.INSTANCE.sendToPlayer(player,ExampleMod.PLAY_PARTICLE_PACKET_ID,passedData));
	// This will work in both multiplayer and singleplayer!
//	@Nullable
//	protected EntityHitResult getEntityCollision(Vec3d currentPosition, Vec3d nextPosition) {
//		return Enti ? null : */ super.getEntityCollision(currentPosition, nextPosition);
//	}
	public boolean isInsideWall = false;

	protected void checkBlockCollision() {
		Entity entity = this.getOwner();
		if (!((this.dealtDamage || this.isNoClip()) && entity != null || this.returnTimer >= 16))
			super.checkBlockCollision();
	}

	protected void onEntityHit(EntityHitResult entityHitResult) {
		Entity entity = entityHitResult.getEntity();
		if (entity == this.getOwner())
			return;
		float f = 8.0F;
		if (entity instanceof LivingEntity) {
			LivingEntity livingEntity = (LivingEntity) entity;
			f += EnchantmentHelper.getAttackDamage(this.tridentStack, livingEntity.getGroup());
		}

		Entity entity2 = this.getOwner();
		DamageSource damageSource = DamageSource.trident(this, (Entity) (entity2 == null ? this : entity2));
		this.dealtDamage = true;
//		world.syncWorldEvent(new LightningEntity(world, entity.getX(),entity.getY(),entity.getZ()));
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

		float g = 1.0F;
		world.playSoundFromEntity((PlayerEntity) this.getOwner(), this, soundEvent, SoundCategory.MASTER, 1.0F, 1.0F);
		this.returnTimer -= 20;
//		this.playSound(soundEvent, g, 1.0F);
	}

	protected SoundEvent getHitSound() {
		return ExampleMod.HAMMER_SOUND_EVENT;
	}

	public void onPlayerCollision(PlayerEntity player) {
		Entity entity = this.getOwner();
		if (entity == null || entity.getUuid() == player.getUuid()) {
			if (!this.world.isClient && (this.inGround || this.isNoClip() || this.dealtDamage || this.returnTimer >= 8)
					&& this.shake <= 0) {
				boolean bl = this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED
						|| this.pickupType == PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY
								&& player.getAbilities().creativeMode
						|| this.isNoClip() && this.getOwner().getUuid() == player.getUuid();
				if (this.pickupType == PersistentProjectileEntity.PickupPermission.ALLOWED
						&& !player.getInventory().insertStack(this.asItemStack())) {
					bl = false;
				}

				if (bl) {
					player.sendPickup(this, 1);
					this.remove(Entity.RemovalReason.DISCARDED);
				}

			}
		}
	}

	public void readCustomDataFromNbt(NbtCompound tag) {
		super.readCustomDataFromNbt(tag);
		if (tag.contains("Trident", 10)) {
			this.tridentStack = ItemStack.fromNbt(tag.getCompound("Trident"));
		}

		this.dealtDamage = tag.getBoolean("DealtDamage");
		this.dataTracker.set(LOYALTY, (byte) EnchantmentHelper.getLoyalty(this.tridentStack));
	}

	public void writeCustomDataToNbt(NbtCompound tag) {
		super.writeCustomDataToNbt(tag);
		tag.put("Trident", this.tridentStack.writeNbt(new NbtCompound()));
		tag.putBoolean("DealtDamage", this.dealtDamage);
	}

	public void age() {
		int i = (Byte) this.dataTracker.get(LOYALTY);
		if (this.pickupType != PersistentProjectileEntity.PickupPermission.ALLOWED || i <= 0) {
			super.age();
		}

	}

	protected float getDragInWater() {
		return 0.0F;
	}

	@Environment(EnvType.CLIENT)
	public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
		return true;
	}

	static {
		LOYALTY = DataTracker.registerData(DoomEntity.class, TrackedDataHandlerRegistry.BYTE);
		ENCHANTED = DataTracker.registerData(DoomEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
	}

	public Packet<?> createSpawnPacket() {
		Entity entity = this.getOwner();
		return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
	}

	protected Item getDefaultItem() {
		return ExampleMod.DOOM_HAMMER;
	}

	protected ItemStack getItem() {
		return new ItemStack(ExampleMod.DOOM_HAMMER);
	}
}
