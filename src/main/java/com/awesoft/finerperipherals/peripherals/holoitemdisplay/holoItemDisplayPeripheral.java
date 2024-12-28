package com.awesoft.finerperipherals.peripherals.holoitemdisplay;

import com.awesoft.finerperipherals.peripherals.eventrelayer.eventRelayerAttachedManager;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class holoItemDisplayPeripheral implements IPeripheral {

    private int compId;
    private IComputerAccess comp;
    BlockEntity blockEntity;

    public holoItemDisplayPeripheral(BlockEntity entity)
    {
        this.blockEntity=entity;
    }

    @Override
    public String getType() {
        return "holographic_item_display";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof holoItemDisplayBlock;
    }



    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        compId= computer.getID();
        comp= computer;
    }


    private static final Map<Integer, Integer> lastCalledMap = new HashMap<>();
    private static final int cooldownTime = 2000;

    private int getRemainingCooldownTime(int computerId) {
        int lastCalled = lastCalledMap.getOrDefault(computerId, 0);
        int currentTime = (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
        int elapsed = currentTime - lastCalled;

        // Handle potential negative wraparound
        if (elapsed < 0) {
            elapsed += Integer.MAX_VALUE;
        }

        return Math.max(0, cooldownTime - elapsed); // Ensure non-negative time
    }

    private boolean canCallFunction(int computerId) {
        int remainingTime = getRemainingCooldownTime(computerId);
        return remainingTime <= 0;
    }

    @LuaFunction(mainThread = true)
    public final MethodResult setItem(String id)  {

        if (!canCallFunction(compId)) {
            return  MethodResult.of(null,"On cooldown!");
        }

        // Update the last invocation time
        lastCalledMap.put(compId, (int) (System.currentTimeMillis() % Integer.MAX_VALUE));

        if (blockEntity instanceof holoItemDisplayBlockEntity) {
            holoItemDisplayBlockEntity customBlockEntity = (holoItemDisplayBlockEntity) blockEntity;

            if (BuiltInRegistries.ITEM.getOptional(new ResourceLocation(id)).isPresent()) {
                customBlockEntity.setStoredItem(id);
                return MethodResult.of(true);
            } else {
                return MethodResult.of(false, "Not a real item id!");
            }

        } else {
            return MethodResult.of(false, "yeah idk what happen ill be fr, please contact mod dev!");
        }
    }

    @LuaFunction(mainThread = true)
    public final MethodResult getOperationCooldown()  {

        return MethodResult.of(getRemainingCooldownTime(compId));
    }

}
