package com.devin12422.tweaks.item.magic.destruction;

import com.devin12422.tweaks.item.magic.BaseSpellItem;
import com.devin12422.tweaks.item.magic.SpellType;
import com.devin12422.tweaks.util.ClientStorage;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;

public class ProjectileSpellItem<T extends SpellType> extends BaseSpellItem<T> {

	private EntityType<MagicProjectileEntity<SpellType>> bruh;

	public ProjectileSpellItem(Settings settings, int cost, SpellType spell,
			EntityType<MagicProjectileEntity<SpellType>> type) {
		super(settings, 5, spell);
		this.bruh = type;
		// TODO Auto-generated constructor stub
	}

	public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
		if (ClientStorage.magicka - this.cost >= 0) {

			if (user instanceof PlayerEntity) {
				PlayerEntity playerEntity = (PlayerEntity) user;
//			int i = this.getMaxUseTime(stack) - remainingUseTicks;
				if (!world.isClient) {
					ClientStorage.lastCastTick = System.currentTimeMillis();

					MagicProjectileEntity<T> tridentEntity = new MagicProjectileEntity<T>(this.bruh,
							playerEntity.getX(), playerEntity.getEyeY(), playerEntity.getZ(), world, this.get());
//				tridentEntity.set(this.get());
					tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F,
							0.4f, 1.0F);
					tridentEntity.setOwner(user);
					ClientStorage.magicka -= this.cost;
					world.spawnEntity(tridentEntity);
					world.playSoundFromEntity((PlayerEntity) null, tridentEntity, SoundEvents.ITEM_FIRECHARGE_USE,
							SoundCategory.PLAYERS, 1.0F, 1.0F);
				}
				playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));

			}
		}
	}

}
