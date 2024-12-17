package com.awesoft.finerperipherals.blocks.chatbox.turtle;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.blocks.chatbox.chatBoxPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.*;
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
            FinerPeripherals.LOGGER.info("turtle periph constructor yeaaaaaaaaa");
        }


        @Override
        public boolean equals(@Nullable IPeripheral other) {
            return (other instanceof Peripheral);
        }

    }


    public chatBoxTurtle(ResourceLocation id, TurtleUpgradeType type, ItemStack stack) {
        super(id, type, stack);
        FinerPeripherals.LOGGER.info("turtle constructor yeaaaaaaaaa");
    }

    @Override
    public IPeripheral createPeripheral(ITurtleAccess turtle, TurtleSide side) {
        FinerPeripherals.LOGGER.info("create periph yeaaaaaaaa");
        return new Peripheral(turtle.getLevel().getBlockEntity(turtle.getPosition()));
    }

}
