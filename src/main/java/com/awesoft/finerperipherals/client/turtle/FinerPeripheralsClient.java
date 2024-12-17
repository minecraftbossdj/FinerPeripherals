package com.awesoft.finerperipherals.client.turtle;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.TurtleRegistry;
import dan200.computercraft.api.client.ComputerCraftAPIClient;
import dan200.computercraft.api.client.FabricComputerCraftAPIClient;
import dan200.computercraft.api.client.turtle.TurtleUpgradeModeller;
import dan200.computercraft.client.ComputerCraftClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;

public class FinerPeripheralsClient  implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FinerPeripherals.LOGGER.info("client!");
        FabricComputerCraftAPIClient.registerTurtleUpgradeModeller(TurtleRegistry.TurtleRegistryMain.CHATBOX_TURTLE.get(), new chatBoxRenderer());
        FabricComputerCraftAPIClient.registerTurtleUpgradeModeller(TurtleRegistry.TurtleRegistryMain.COMPASS_TURTLE.get(), TurtleUpgradeModeller.flatItem());
    }

}
