package com.awesoft.finerperipherals.peripherals.enderChestTurtle;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.lib.LuaConverter;
import com.mojang.authlib.GameProfile;
import dan200.computercraft.api.detail.VanillaDetailRegistries;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.shared.platform.FabricContainerTransfer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.PlayerEnderChestContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.apache.logging.log4j.core.jmx.Server;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

import static dan200.computercraft.core.util.ArgumentHelpers.assertBetween;

public class enderChestPeripheral implements IPeripheral {

    ITurtleAccess turtle;
    private int compId;


    public enderChestPeripheral(ITurtleAccess turtle)
    {
        this.turtle=turtle;
    }

    @Override
    public String getType() {
        return "enderchest";
    }

    @Override
    public boolean equals(IPeripheral other) {
        return other instanceof enderChestPeripheral;
    }

    public ServerPlayer getPlayer() {
        if (turtle != null) {
            MinecraftServer server = turtle.getLevel().getServer();
            GameProfile plrProfile = turtle.getOwningPlayer();
            if (server != null && plrProfile != null) {
                try {
                    return server.getPlayerList().getPlayer(plrProfile.getId());
                } catch (RuntimeException e) {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public final Container getTurtleInv() {
        if (turtle != null) {
            return turtle.getInventory();
        } else {return null;}
    }

    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        compId= computer.getID();
    }

    @LuaFunction
    public final MethodResult getInventory() {
        return MethodResult.of(LuaConverter.containerToList(turtle.getInventory()));
    }

    public PlayerEnderChestContainer getEnderInventory() {
        return getPlayer() != null ? getPlayer().getEnderChestInventory() : null;
    }

    @LuaFunction(mainThread = true)
    public MethodResult list() {
        var inventory = getEnderInventory();
        if (inventory == null) {
            return MethodResult.of(false, "Player isn't present");
        }
        Map<Integer, Map<String, ?>> result = new HashMap<>();
        var size = inventory.getContainerSize();
        for (var i = 0; i < size; i++) {
            var stack = inventory.getItem(i);
            if (!stack.isEmpty()) result.put(i + 1, VanillaDetailRegistries.ITEM_STACK.getBasicDetails(stack));
        }
        return MethodResult.of(result);
    }
    @LuaFunction(mainThread = true)
    public MethodResult size() {
        var inventory = getEnderInventory();
        if (inventory == null) {
            return MethodResult.of(false, "Player isn't present");
        }
        return MethodResult.of(inventory.getContainerSize());
    }
    @LuaFunction(mainThread = true)
    public MethodResult getItemLimit(int slot) {
        var inventory = getEnderInventory();
        if (inventory == null) {
            return MethodResult.of(false, "Player isn't present");
        }
        return MethodResult.of(inventory.getItem(slot - 1).getMaxStackSize());
    }

    public MethodResult moveTo(Container from, Container to, int fromSlot, int limit, int toSlot) {
        ItemStack fromItem = from.getItem(fromSlot - 1);
        ItemStack toItem = to.getItem(toSlot - 1);

        if (fromItem.isEmpty()) {
            return MethodResult.of(false, "From slot is empty!");
        }

        if (toItem.isEmpty()) {
            if (fromItem.getCount() < limit) {
                return MethodResult.of(false, "From slot has too few items!");
            }

            ItemStack moved = fromItem.copy();
            moved.setCount(limit);

            fromItem.shrink(limit);
            from.setItem(fromSlot - 1, fromItem);
            to.setItem(toSlot - 1, moved);

            return MethodResult.of(true, limit);
        }

        if (toItem.getItem() != fromItem.getItem()) {
            return MethodResult.of(false, "To slot doesn't contain the same item!");
        }

        if (!Objects.equals(toItem.getTag(), fromItem.getTag())) {
            return MethodResult.of(false, "Both items' NBT do not match.");
        }

        int transferable = Math.min(limit, fromItem.getCount());
        int space = toItem.getMaxStackSize() - toItem.getCount();
        int movedAmount = Math.min(transferable, space);

        fromItem.shrink(movedAmount);
        toItem.grow(movedAmount);

        from.setItem(fromSlot - 1, fromItem);
        to.setItem(toSlot - 1, toItem);

        return MethodResult.of(true, movedAmount);
    }

    @LuaFunction(mainThread = true)
    public MethodResult pushItems(IComputerAccess computer, int fromSlot, int limit, int toSlot
    ) throws LuaException {
        var enderInv = getEnderInventory();
        if (enderInv == null) {
            return MethodResult.of(false, "Player isn't present");
        }

        // Find location to transfer to
        var turtleInv = getTurtleInv();
        if (turtleInv == null) {return MethodResult.of(false,"Turtle inv doesnt exist I guess? idk bro (report bug)");}

        assertBetween(fromSlot, 1, enderInv.getContainerSize(), "From slot out of range (%s)");
        assertBetween(toSlot, 1, turtleInv.getContainerSize(), "To slot out of range (%s)");
        assertBetween(limit,1,enderInv.getItem(fromSlot-1).getMaxStackSize(),"Stack Size out of range (%s)");

        return moveTo(enderInv,turtleInv,fromSlot,limit,toSlot);

    }

    @LuaFunction(mainThread = true)
    public MethodResult pullItems(IComputerAccess computer, int fromSlot, int limit, int toSlot
    ) throws LuaException {
        var enderInv = getEnderInventory();
        if (enderInv == null) {
            return MethodResult.of(false, "Player isn't present");
        }

        var turtleInv = getTurtleInv();
        if (turtleInv == null) {return MethodResult.of(false,"Turtle inv doesnt exist I guess? idk bro (report bug)");}

        return moveTo(turtleInv,enderInv,fromSlot,limit,toSlot);
    }

    public boolean canAddItem(Container inventory, ItemStack p_19184_) {
        boolean flag = false;
        for (int i = 0; i < inventory.getContainerSize(); i++) {
            var itemstack = inventory.getItem(i);
            if (itemstack.isEmpty() || ItemStack.isSameItemSameTags(itemstack, p_19184_) && itemstack.getCount() < itemstack.getMaxStackSize()) {
                flag = true;
                break;
            }
        }

        return flag;
    }
}
