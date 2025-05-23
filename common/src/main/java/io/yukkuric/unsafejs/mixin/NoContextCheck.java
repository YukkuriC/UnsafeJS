package io.yukkuric.unsafejs.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.latvian.mods.kubejs.script.KubeJSContext;
import dev.latvian.mods.rhino.*;
import dev.latvian.mods.rhino.type.TypeInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.AccessibleObject;

@Mixin(KubeJSContext.class)
public class NoContextCheck extends Context {
    public NoContextCheck(ContextFactory factory) {
        super(factory);
    }

    @Inject(method = "wrapAsJavaObject", at = @At("HEAD"), remap = false, cancellable = true)
    void noCheck1(Scriptable scope, Object javaObject, TypeInfo target, CallbackInfoReturnable<Scriptable> cir) {
        if (javaObject instanceof AccessibleObject || javaObject instanceof ClassLoader) {
            cir.setReturnValue(super.wrapAsJavaObject(scope, javaObject, target));
        }
    }

    @WrapOperation(method = "internalJsToJavaLast", at = @At(value = "INVOKE", target = "Ljava/lang/Class;isAssignableFrom(Ljava/lang/Class;)Z"), remap = false)
    boolean noCheck2(Class instance, Class<?> aClass, Operation<Boolean> original) {
        return false;
    }
}
