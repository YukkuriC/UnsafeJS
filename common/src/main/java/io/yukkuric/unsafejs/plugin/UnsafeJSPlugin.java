package io.yukkuric.unsafejs.plugin;

import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;

public class UnsafeJSPlugin implements KubeJSPlugin {
	public void registerBindings(BindingRegistry bindings) {
		bindings.add("Reflection", Reflection.class);
	}
}
