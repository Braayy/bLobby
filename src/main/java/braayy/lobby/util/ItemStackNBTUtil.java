package braayy.lobby.util;

import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.lang.reflect.Method;

@SuppressWarnings("ConstantConditions")
public class ItemStackNBTUtil {

    private static Class<?> NBT_TAG_COMPOUND_CLASS;

    private static Method SET_STRING_METHOD;
    private static Method GET_STRING_METHOD;

    private static Method AS_NMS_COPY_METHOD;
    private static Method AS_BUKKIT_MIRROR_METHOD;

    private static Method GET_TAG_METHOD;
    private static Method SET_TAG_METHOD;

    static {
        try {
            NBT_TAG_COMPOUND_CLASS = getNMSClass("NBTTagCompound");

            SET_STRING_METHOD = NBT_TAG_COMPOUND_CLASS.getDeclaredMethod("setString", String.class, String.class);
            GET_STRING_METHOD = NBT_TAG_COMPOUND_CLASS.getDeclaredMethod("getString", String.class);

            Class<?> craftItemStackClass = getOBCClass("inventory.CraftItemStack");

            AS_NMS_COPY_METHOD = craftItemStackClass.getDeclaredMethod("asNMSCopy", ItemStack.class);

            Class<?> itemStackClass = getNMSClass("ItemStack");

            AS_BUKKIT_MIRROR_METHOD = craftItemStackClass.getDeclaredMethod("asCraftMirror", itemStackClass);

            GET_TAG_METHOD = itemStackClass.getDeclaredMethod("getTag");
            SET_TAG_METHOD = itemStackClass.getDeclaredMethod("setTag", NBT_TAG_COMPOUND_CLASS);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static Object getTag(ItemStack stack) {
        try {
            Object nmsStack = AS_NMS_COPY_METHOD.invoke(null, stack);

            Object tag = GET_TAG_METHOD.invoke(nmsStack);

            return tag != null ? tag : NBT_TAG_COMPOUND_CLASS.newInstance();
        } catch (Exception exception) {
            exception.printStackTrace();

            return null;
        }
    }

    public static ItemStack setTag(ItemStack stack, Object tag) {
        try {
            Object nmsStack = AS_NMS_COPY_METHOD.invoke(null, stack);

            SET_TAG_METHOD.invoke(nmsStack, tag);

            return (ItemStack) AS_BUKKIT_MIRROR_METHOD.invoke(null, nmsStack);
        } catch (Exception exception) {
            exception.printStackTrace();

            return null;
        }
    }

    public static void setString(Object tag, String key, String value) {
        try {
            SET_STRING_METHOD.invoke(tag, key, value);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static String getString(Object tag, String key) {
        try {
            return (String) GET_STRING_METHOD.invoke(tag, key);
        } catch (Exception exception) {
            exception.printStackTrace();

            return null;
        }
    }

    public static Class<?> getNMSClass(String name) {
        try {
            String version = Bukkit.getServer().getClass().getName().split("\\.")[3];

            return Class.forName("net.minecraft.server." + version + '.' + name);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public static Class<?> getOBCClass(String name) {
        try {
            String version = Bukkit.getServer().getClass().getName().split("\\.")[3];

            return Class.forName("org.bukkit.craftbukkit." + version + '.' + name);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

}