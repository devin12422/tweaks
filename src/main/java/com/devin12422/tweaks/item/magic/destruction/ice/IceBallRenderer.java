package com.devin12422.tweaks.item.magic.destruction.ice;

import com.devin12422.tweaks.item.magic.destruction.MagicProjectileRenderer;

import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

public class IceBallRenderer extends MagicProjectileRenderer<PersistentProjectileEntity, IceType> {

	public IceBallRenderer(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public Item getBallItem() {
		return com.devin12422.tweaks.item.Items.ICE_SPELL;
	}

}