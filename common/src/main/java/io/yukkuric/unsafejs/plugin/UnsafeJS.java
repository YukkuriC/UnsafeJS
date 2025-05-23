package io.yukkuric.unsafejs.plugin;

import com.mojang.datafixers.types.Type;
import dev.latvian.mods.rhino.NativeJavaObject;
import sun.misc.Unsafe;

import java.lang.reflect.AccessFlag;
import java.lang.reflect.Field;

public class UnsafeJS {
	private static final Unsafe theUnsafe;

	static {
		try {
			var getter = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe = (Unsafe) getter.get(null);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	public static void setField(Object obj, Object fieldRaw, Object value) throws NoSuchFieldException, ClassNotFoundException {
		Field field;
		if (fieldRaw instanceof Field) field = (Field) fieldRaw;
		else if (fieldRaw instanceof String str) field = ReflectionJS.getField(obj, str);
		else throw new ClassCastException("expected Field or String, got %s".formatted(fieldRaw.getClass().getName()));

		var isStatic = field.accessFlags().contains(AccessFlag.STATIC);
		var base = isStatic ? theUnsafe.staticFieldBase(field) : obj;
		var offset = isStatic ? theUnsafe.staticFieldOffset(field) : theUnsafe.objectFieldOffset(field);
		theUnsafe.putObject(base, offset, value);
	}
}
