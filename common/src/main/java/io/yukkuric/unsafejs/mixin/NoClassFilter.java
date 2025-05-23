package io.yukkuric.unsafejs.mixin;

import dev.latvian.mods.kubejs.plugin.ClassFilter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClassFilter.class)
public class NoClassFilter {
    @Inject(method = "isAllowed", at = @At("HEAD"), cancellable = true, remap = false)
    void allowAll(String s, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
