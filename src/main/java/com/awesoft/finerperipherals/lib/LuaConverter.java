package com.awesoft.finerperipherals.lib;

import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.shared.util.NBTUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.awesoft.finerperipherals.peripherals.geoexplorer.geoExplorerPeripheral.tagsToList;

public class LuaConverter {


    public static String convertToID(String translationString) {
        // Check if the string contains "key="
        int keyIndex = translationString.indexOf("key='");
        if (keyIndex == -1) {
            throw new IllegalArgumentException("Invalid translation string: " + translationString);
        }

        // Extract the part after "key='"
        int startIndex = keyIndex + 5; // Skip "key='"
        int endIndex = translationString.indexOf("'", startIndex); // Find the closing quote
        if (endIndex == -1) {
            throw new IllegalArgumentException("Invalid translation string: " + translationString);
        }

        // Get the key value
        String key = translationString.substring(startIndex, endIndex);

        // Convert "block.minecraft.dirt" to "minecraft:dirt" or "item.minecraft.compass" to "minecraft:compass"
        if (key.startsWith("block.")) {
            key = key.substring(6); // Remove the "block." prefix
        } else if (key.startsWith("item.")) {
            key = key.substring(5); // Remove the "item." prefix
        }

        // Replace the first period with a colon
        key = key.replaceFirst("\\.", ":");

        return key;
    }

    public static Map<String, Object> stackToObject(@NotNull ItemStack stack) {
        if (stack.isEmpty()) return new HashMap<>();
        Map<String, Object> map = itemToObject(stack.getItem());
        CompoundTag nbt = stack.copy().getOrCreateTag();
        map.put("count", stack.getCount());
        map.put("displayName", stack.getDisplayName().getString());
        map.put("maxStackSize", stack.getMaxStackSize());
        map.put("nbt", NBTUtil.toLua(nbt));
        return map;
    }

    public static Map<String, Object> itemToObject(@NotNull Item item) {
        Map<String, Object> map = new HashMap<>();
        map.put("tags", tagsToList(() -> item.builtInRegistryHolder().tags()));
        map.put("name", convertToID(String.valueOf(item.getName(item.getDefaultInstance()))));
        return map;
    }

    public static List<Object> containerToList(@NotNull Container container) {
        List<Object> List = new ArrayList<>();
        for (int i = 0; i <= container.getContainerSize()-1; i++) {
            if (!LuaConverter.stackToObject(container.getItem(i)).isEmpty()) {
                List.add(LuaConverter.stackToObject(container.getItem(i)));
            } else {
                List.add(null);
            }
        }
        return List;
    }

}
