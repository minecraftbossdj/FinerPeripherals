package com.awesoft.finerperipherals.peripherals.geoexplorer.pocket;

import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxAttachedManager;
import com.awesoft.finerperipherals.peripherals.geoexplorer.geoExplorerPeripheral;
import dan200.computercraft.api.lua.IArguments;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.pocket.IPocketAccess;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class geoExplorerPeripheralPocket implements IPeripheral {

    IPocketAccess access;
    private int compId;

    public geoExplorerPeripheralPocket(IPocketAccess access)
    {
        this.access=access;
    }

    @Override
    public String getType() {
        return "geoExplorer";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof geoExplorerPeripheralPocket;
    }




    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        compId= computer.getID();
    }

    public static void relativeTraverseBlocks(Level world, BlockPos center, int radius, BiConsumer<BlockState, BlockPos> consumer) {
        traverseBlocks(world, center, radius, consumer, true);
    }

    public static void traverseBlocks(Level world, BlockPos center, int radius, BiConsumer<BlockState, BlockPos> consumer) {
        traverseBlocks(world, center, radius, consumer, false);
    }

    public static void traverseBlocks(Level world, BlockPos center, int radius, BiConsumer<BlockState, BlockPos> consumer, boolean relativePosition) {
        int x = center.getX();
        int y = center.getY();
        int z = center.getZ();
        for (int oX = x - radius; oX <= x + radius; oX++) {
            for (int oY = y - radius; oY <= y + radius; oY++) {
                for (int oZ = z - radius; oZ <= z + radius; oZ++) {
                    BlockPos subPos = new BlockPos(oX, oY, oZ);
                    BlockState blockState = world.getBlockState(subPos);
                    if (!blockState.isAir()) {
                        if (relativePosition) {
                            consumer.accept(blockState, new BlockPos(oX - x, oY - y, oZ - z));
                        } else {
                            consumer.accept(blockState, subPos);
                        }
                    }
                }
            }
        }
    }

    public static <T> List<String> tagsToList(@NotNull Supplier<Stream<TagKey<T>>> tags) {
        if (tags.get().findAny().isEmpty()) return Collections.emptyList();
        return tags.get().map(geoExplorerPeripheral::tagToString).toList();
    }

    public static <T> String tagToString(@NotNull TagKey<T> tag) {
        return tag.registry().location() + "/" + tag.location();
    }

    public static String convertToBlockId(String translationString) {
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

        // Convert "block.minecraft.dirt" to "minecraft:dirt"
        if (key.startsWith("block.")) {
            key = key.substring(6); // Remove the "block." prefix
        }

        // Replace the first period with a colon
        key = key.replaceFirst("\\.", ":");

        return key;
    }

    private static List<Map<String, ?>> scan(Level level, BlockPos center, int radius) {
        List<Map<String, ?>> result = new ArrayList<>();
        relativeTraverseBlocks(level, center, radius, (state, pos) -> {
            HashMap<String, Object> data = new HashMap<>(6);
            data.put("x", pos.getX());
            data.put("y", pos.getY());
            data.put("z", pos.getZ());



            Block block = state.getBlock();
            data.put("name", convertToBlockId(block.getName().toString()));
            data.put("tags", tagsToList(() -> block.builtInRegistryHolder().tags()));
            if (state.hasProperty(BlockStateProperties.HORIZONTAL_FACING)) {
                data.put("direction", state.getValue(BlockStateProperties.HORIZONTAL_FACING).getName());
            }
            if (state.hasProperty(BlockStateProperties.VERTICAL_DIRECTION)) {
                data.put("direction_vertical", state.getValue(BlockStateProperties.VERTICAL_DIRECTION).getName());
            }
            if (state.hasProperty(BlockStateProperties.HALF)) {
                data.put("half", state.getValue(BlockStateProperties.HALF).toString());
            }
            if (state.hasProperty(BlockStateProperties.AXIS)) {
                data.put("axis", state.getValue(BlockStateProperties.AXIS).toString());
            }
            if (state.hasProperty(BlockStateProperties.POWERED)) {
                data.put("powered", state.getValue(BlockStateProperties.POWERED));
            }
            if (state.hasProperty(BlockStateProperties.POWER)) {
                data.put("powerlvl", state.getValue(BlockStateProperties.POWER));
            }

            result.add(data);
        });

        return result;
    }

    private static final long COOLDOWN_TIME = 2000; // Cooldown in milliseconds (2 seconds)
    private final Map<String, Long> lastInvocationTimes = new HashMap<>(); // Store last use per player/computer

    @LuaFunction(mainThread = true)
    public final MethodResult scan(@NotNull IArguments arguments) throws LuaException {
        long currentTime = System.currentTimeMillis();
        long lastUsed = lastInvocationTimes.getOrDefault(String.valueOf(compId), 0L);

        if (currentTime - lastUsed < COOLDOWN_TIME) {
            return  MethodResult.of(false,"On cooldown!");
        }

        // Update the last invocation time
        lastInvocationTimes.put(String.valueOf(compId), currentTime);

        int radius = arguments.getInt(0);
        return  MethodResult.of(scan(access.getEntity().level(), access.getEntity().blockPosition(), radius));
    }

}
