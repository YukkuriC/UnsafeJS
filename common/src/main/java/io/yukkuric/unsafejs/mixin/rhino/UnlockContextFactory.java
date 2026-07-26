package io.yukkuric.unsafejs.mixin.rhino;

import dev.latvian.mods.rhino.ContextFactory;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.invoke.MethodHandles;

@Mixin(ContextFactory.class)
public class UnlockContextFactory {
    @Shadow
    @Final
    @Mutable
    private MethodHandles.Lookup methodHandlesLookup;
    @Inject(method = "<init>", at = @At("RETURN"))
    void replaceMethodHandle(CallbackInfo ci) {
        methodHandlesLookup = MethodHandles.lookup();
    }
}
