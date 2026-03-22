package net.hackermdch.exparticle.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.TextureSheetParticle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SimpleAnimatedParticle.class)
public abstract class SimpleAnimatedParticleMixin extends TextureSheetParticle {
    protected SimpleAnimatedParticleMixin(ClientLevel level, double x, double y, double z) {
        super(level, x, y, z);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/particle/SimpleAnimatedParticle;setAlpha(F)V"), cancellable = true)
    private void onSetAlpha(CallbackInfo ci) {
        if (isManaged()) ci.cancel();
    }

    @Inject(method = "getLightColor", at = @At("HEAD"), cancellable = true)
    private void getLightColor(float partialTick, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(super.getLightColor(partialTick));
    }
}