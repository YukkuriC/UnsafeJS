package io.yukkuric.unsafejs.neoforge;

import io.yukkuric.unsafejs.UnsafeJS;
import net.neoforged.fml.common.Mod;

@Mod(UnsafeJS.MOD_ID)
public final class UnsafeJSNeoForge {
    public UnsafeJSNeoForge() {
        // Run our common setup.
        UnsafeJS.init();
    }
}
