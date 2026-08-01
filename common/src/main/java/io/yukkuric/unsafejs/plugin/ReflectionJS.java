package io.yukkuric.unsafejs.plugin;

import dev.latvian.mods.rhino.NativeJavaObject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ReflectionJS {
    private static final Map<String, Class<?>> classCache = new HashMap<>();

    static {
        classCache.put("char", char.class);
        classCache.put("byte", byte.class);
        classCache.put("short", short.class);
        classCache.put("int", int.class);
        classCache.put("long", long.class);
        classCache.put("float", float.class);
        classCache.put("double", double.class);
        classCache.put("boolean", boolean.class);
    }

    public static Class<?> toRawClass(Object obj) throws ClassNotFoundException {
        if (obj instanceof NativeJavaObject kjsobj) obj = kjsobj.unwrap();
        if (obj instanceof String str) {
            var ret = classCache.get(str);
            if (ret != null) return ret;
            classCache.put(str, (ret = Class.forName(str)));
            return ret;
        }
        if (obj instanceof Class<?> cls) return cls;
        return obj.getClass();
    }

    public static Field getField(Object obj, String name) throws ClassNotFoundException, NoSuchFieldException {
        var cls = toRawClass(obj);
        Field field = null;
        while (cls != null) {
            try {
                field = cls.getDeclaredField(name);
                break;
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
                if (cls == null) throw e;
            }
        }
        field.setAccessible(true);
        return field;
    }

    public static Object fastGet(Object obj, String name) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        var field = getField(obj, name);
        return field.get(obj);
    }

    public static void fastSet(Object obj, String name, Object value) throws IllegalAccessException, NoSuchFieldException, ClassNotFoundException {
        var field = getField(obj, name);
        field.set(obj, value);
    }

    public static Method getMethod(Object obj, String name, Object... types) throws ClassNotFoundException, NoSuchMethodException {
        var cls = toRawClass(obj);
        Class<?>[] input = new Class[types.length];
        for (var i = 0; i < types.length; i++) input[i] = toRawClass(types[i]);
        Method method = null;
        while (cls != null) {
            try {
                method = cls.getDeclaredMethod(name, input);
                break;
            } catch (NoSuchMethodException e) {
                cls = cls.getSuperclass();
                if (cls == null) throw e;
            }
        }
        method.setAccessible(true);
        return method;
    }
}
