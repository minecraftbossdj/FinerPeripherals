package com.awesoft.finerperipherals.peripherals.chatbox.turtle;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxPeripheral;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class chatBoxTurtle extends AbstractTurtleUpgrade {
    private static class Peripheral extends chatBoxPeripheral {
        private final BlockEntity entity;

        public Peripheral(BlockEntity entity) {
            super(entity);
            this.entity = entity;
        }


        @Override
        public boolean equals(@Nullable IPeripheral other) {
            return (other instanceof Peripheral);
        }

    }


    public chatBoxTurtle(ResourceLocation id, TurtleUpgradeType type, ItemStack stack) {
        super(id, type, stack);
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new Peripheral(turtle.getLevel().getBlockEntity(turtle.getPosition()));
    }



}
