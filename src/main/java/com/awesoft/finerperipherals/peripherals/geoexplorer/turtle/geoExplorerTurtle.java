package com.awesoft.finerperipherals.peripherals.geoexplorer.turtle;

import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxPeripheral;
import com.awesoft.finerperipherals.peripherals.geoexplorer.geoExplorerPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.AbstractTurtleUpgrade;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.Nullable;

public class geoExplorerTurtle extends AbstractTurtleUpgrade {
    private static class Peripheral extends geoExplorerPeripheral {
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


    public geoExplorerTurtle(ResourceLocation id, TurtleUpgradeType type, ItemStack stack) {
        super(id, type, stack);
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        return new Peripheral(turtle.getLevel().getBlockEntity(turtle.getPosition()));
    }



}
