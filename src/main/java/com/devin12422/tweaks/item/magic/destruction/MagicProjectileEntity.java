package com.devin12422.tweaks.item.magic.destruction;

import com.devin12422.tweaks.item.Items;
import com.devin12422.tweaks.item.magic.SpellType;
import com.devin12422.tweaks.item.magic.destruction.fire.FireType;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.client.MinecraftClient;

public class MagicProjectileEntity<T extends SpellType> extends PersistentProjectileEntity {
	public Identifier TEXTURE = new Identifier("tweaks", "textures/item/spell/destruction/projectile/fire.png");
	public float damageMultiplier = 1.0f;
	private T t;

//private int lifetime;
	public void set(T t) {
		this.t = t;
		this.setNoGravity(true);

		TEXTURE = new Identifier("tweaks", "textures/item/spell/destruction/projectile/" + get().getType() + ".png");

	}

	private SpellType spelltype;

	public T get() {
		return t;
	}

	protected boolean tryPickup(PlayerEntity player) {
		return false;
	}

	public MagicProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
		super(entityType, world);
		set((T) new FireType());

	}

	public MagicProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, double d, double e,
			double f, World world, SpellType element) {
		super(entityType, d, e, f, world);
		set((T) element);
		this.spelltype = element;
//		set(new T());
	}

	public MagicProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, LivingEntity livingEntity,
			World world, SpellType element) {
		super(entityType, livingEntity, world);
		set((T) element);
		this.spelltype = element;
	}

	public Identifier getTexture() {
		return this.TEXTURE;
	}

	public void tick() {
		Entity entity = this.getOwner();
		if (this.world.isClient || (entity == null || !entity.isRemoved())
				&& this.world.isChunkLoaded(this.getBlockPos()) && this.age < 60) {

			super.tick();
			if(this.isSubmergedInWater()) {
				this.discard();
			}
//			System.out.println(this.age);
//            if(T.particle != null)
			this.world.addParticle(getParticleType(), this.getX(), this.getY() + (this.getHeight() / 2), this.getZ(),
					0f, 0f, 0f);

			HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
			if (hitResult.getType() != HitResult.Type.MISS) {
				this.onCollision(hitResult);
			}
			Vec3d velocity = this.getVelocity();

			this.setVelocity(velocity.multiply((double) 1.0101010101f));

		} else {
			this.discard();
		}
	}

	public Packet<?> createSpawnPacket() {
		Entity entity = this.getOwner();
		return new EntitySpawnS2CPacket(this, entity == null ? 0 : entity.getId());
	}

	public ParticleEffect getParticleType() {
		return ParticleTypes.SOUL_FIRE_FLAME;
	}

	protected void initDataTracker() {
		super.initDataTracker();
//		this.dataTracker.startTracking(ENCHANTED, false);
	}

	public void onEntityHit(EntityHitResult entityHitResult) {
		super.onEntityHit(entityHitResult);
		get().applySpellEffect(entityHitResult);
		this.discard();
	}

//	   protected boolean canHit(Entity entity) {
//		      if (!entity.isSpectator() && entity.isAlive() && entity.collides()) {
//		         Entity entity2 = this.getOwner();
//		         return entity2 == null || this.leftOwner || !entity2.isConnectedThroughVehicle(entity);
//		      } else {
//		         return false;
//		      }/**

	protected void onBlockHit(BlockHitResult blockHitResult) {
		super.onBlockHit(blockHitResult);
		this.discard();
	}

	@Override
	protected ItemStack asItemStack() {
		// TODO Auto-generated method stub

		return null;

	}
}
