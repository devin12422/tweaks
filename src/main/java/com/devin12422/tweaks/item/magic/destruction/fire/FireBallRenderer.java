package com.devin12422.tweaks.item.magic.destruction.fire;

import com.devin12422.tweaks.item.magic.destruction.MagicProjectileRenderer;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class FireBallRenderer extends MagicProjectileRenderer<PersistentProjectileEntity, FireType>{

	public FireBallRenderer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public Item getBallItem() {
		return com.devin12422.tweaks.item.Items.FIRE_SPELL;
	}
	
}
