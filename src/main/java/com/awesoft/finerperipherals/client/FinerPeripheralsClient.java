package com.awesoft.finerperipherals.client;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.TurtleRegistry;
import com.awesoft.finerperipherals.client.turtle.chatBoxRenderer;
import com.awesoft.finerperipherals.client.turtle.eventRelayerRenderer;
import com.awesoft.finerperipherals.client.turtle.geoExplorerRenderer;
import com.awesoft.finerperipherals.client.peripherals.holoItemDisplay.holoItemDisplayBlockEntityRenderer;

import com.awesoft.finerperipherals.network.holoItemDisplayPacket;
import com.awesoft.finerperipherals.peripherals.holoitemdisplay.holoItemDisplayBlockEntity;
import dan200.computercraft.api.client.FabricComputerCraftAPIClient;
import dan200.computercraft.api.client.turtle.TurtleUpgradeModeller;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;

public class FinerPeripheralsClient  implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        FinerPeripherals.LOGGER.info("client!");
        FabricComputerCraftAPIClient.registerTurtleUpgradeModeller(TurtleRegistry.TurtleRegistryMain.CHATBOX_TURTLE.get(), new chatBoxRenderer());
        FabricComputerCraftAPIClient.registerTurtleUpgradeModeller(TurtleRegistry.TurtleRegistryMain.COMPASS_TURTLE.get(), TurtleUpgradeModeller.flatItem());
        FabricComputerCraftAPIClient.registerTurtleUpgradeModeller(TurtleRegistry.TurtleRegistryMain.GEOEXPLORER_TURTLE.get(), new geoExplorerRenderer());
        FabricComputerCraftAPIClient.registerTurtleUpgradeModeller(TurtleRegistry.TurtleRegistryMain.EVENTRELAYER_TURTLE.get(), new eventRelayerRenderer());
        BlockEntityRenderers.register(FinerPeripherals.HOLOITEMDISPLAY_BE,  holoItemDisplayBlockEntityRenderer::new);
        registerReceiver();
    }

    public static void registerReceiver() {
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation("finerperipherals", "update_holo_item_display_nbt"), (client, handler, buf, responseSender) -> {
            // Decode the packet
            holoItemDisplayPacket packet = holoItemDisplayPacket.decode(buf);

            // Ensure we're executing on the main thread (important for interacting with the game world)
            client.execute(() -> {
                // Ensure the level (world) is loaded before accessing block entities
                if (client.level != null) {
                    BlockEntity blockEntity = client.level.getBlockEntity(packet.getPos());
                    if (blockEntity instanceof holoItemDisplayBlockEntity holoBlockEntity) {
                        // Apply the NBT data to the block entity
                        holoBlockEntity.load(packet.getNbtData());
                    }
                }
            });
        });
    }

}
