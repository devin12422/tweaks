package com.devin12422.tweaks.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.devin12422.tweaks.client.BarElements;

import java.util.Objects;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Final @Shadow private MinecraftClient client;
    private BarElements oneBarElements;
    private boolean showOneBar = false;

    @Inject(at = @At("TAIL"), method = "render")
    public void render(MatrixStack matrixStack, float tickDelta, CallbackInfo info) {
        oneBarElements = new BarElements(matrixStack);
        showOneBar = true; // This var exists because it also shows whether oneBarElements is initialized

        boolean barsVisible = !client.options.hudHidden && Objects.requireNonNull(client.interactionManager).hasStatusBars();
        if(showOneBar && barsVisible) oneBarElements.renderOneBar();
    }

    // Injections to vanilla methods

    @Inject(method = "renderStatusBars", at = @At(value = "INVOKE"), cancellable = true)
    private void renderStatusBars(MatrixStack matrices, CallbackInfo ci){
        if(showOneBar) ci.cancel();
    }
    @Inject(method = "renderExperienceBar", at = @At(value = "INVOKE"), cancellable = true)
    private void renderExperienceBar(MatrixStack matrices,int i, CallbackInfo ci){
        if(showOneBar) ci.cancel();
    }
}