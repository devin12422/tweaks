package com.devin12422.tweaks.entities.models;

import com.devin12422.tweaks.entities.ChainDemon;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

public class ChainDemonModel extends AnimatedGeoModel<ChainDemon> {
	@Override
	public Identifier getModelLocation(ChainDemon object) {
		return new Identifier("tweaks", "geo/chaindemon.geo.json");
	}

	@Override
	public Identifier getTextureLocation(ChainDemon object) {
		return new Identifier("tweaks", "textures/entities/chaindemon.png");
	}

	@Override
	public Identifier getAnimationFileLocation(ChainDemon object) {
		return new Identifier("tweaks", "animations/chain.animation.json");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void setLivingAnimations(ChainDemon entity, Integer uniqueID, AnimationEvent customPredicate) {
		super.setLivingAnimations(entity, uniqueID, customPredicate);
		IBone head = this.getAnimationProcessor().getBone("head");

		EntityModelData extraData = (EntityModelData) customPredicate.getExtraDataOfType(EntityModelData.class).get(0);
		if (head != null) {
			head.setRotationX(extraData.headPitch * ((float) Math.PI / 180F));
			head.setRotationY(extraData.netHeadYaw * ((float) Math.PI / 180F));
		}
	}
}