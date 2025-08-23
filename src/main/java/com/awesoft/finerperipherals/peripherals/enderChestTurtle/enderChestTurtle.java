package com.awesoft.finerperipherals.peripherals.enderChestTurtle;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.AbstractTurtleUpgrade;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class enderChestTurtle extends AbstractTurtleUpgrade {
    private static class Peripheral extends enderChestPeripheral {
        private final ITurtleAccess turtle;

        public Peripheral(ITurtleAccess turtle) {
            super(turtle);
            this.turtle = turtle;
        }

        @Override
        public boolean equals(@Nullable IPeripheral other) {
            return (other instanceof enderChestPeripheral);
        }

    }


    public enderChestTurtle(ResourceLocation id, TurtleUpgradeType type, ItemStack stack) {
        super(id, type, stack);
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new enderChestPeripheral(turtle);
    }



}
