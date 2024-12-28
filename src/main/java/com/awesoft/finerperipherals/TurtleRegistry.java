package com.awesoft.finerperipherals;

import com.awesoft.finerperipherals.peripherals.chatbox.turtle.chatBoxTurtle;
import com.awesoft.finerperipherals.peripherals.compass.compassTurtle;
import com.awesoft.finerperipherals.peripherals.eventrelayer.turtle.eventRelayerTurtle;
import com.awesoft.finerperipherals.peripherals.geoexplorer.turtle.geoExplorerTurtle;
import dan200.computercraft.api.turtle.TurtleUpgradeSerialiser;
import dan200.computercraft.api.turtle.TurtleUpgradeType;
import dan200.computercraft.shared.platform.PlatformHelper;
import dan200.computercraft.shared.platform.RegistrationHelper;
import dan200.computercraft.shared.platform.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TurtleRegistry {

    public static class TurtleRegistryMain {
        static final RegistrationHelper<TurtleUpgradeSerialiser<?>> REGISTRY = PlatformHelper.get().createRegistrationHelper(TurtleUpgradeSerialiser.registryId());

        public static final RegistryEntry<TurtleUpgradeSerialiser<chatBoxTurtle>> CHATBOX_TURTLE =
                REGISTRY.register("chatbox_turtle", () -> TurtleUpgradeSerialiser.simpleWithCustomItem((id, item) -> new chatBoxTurtle(new ResourceLocation("finerperipherals","chatbox_turtle"), TurtleUpgradeType.PERIPHERAL, new ItemStack(FinerPeripherals.CHATBOX_BLOCK))));

        public static final RegistryEntry<TurtleUpgradeSerialiser<compassTurtle>> COMPASS_TURTLE =
                REGISTRY.register("compass_turtle", () -> TurtleUpgradeSerialiser.simpleWithCustomItem((id, item) -> new compassTurtle(new ResourceLocation("finerperipherals","compass_turtle"), TurtleUpgradeType.PERIPHERAL, new ItemStack(Items.COMPASS))));

        public static final RegistryEntry<TurtleUpgradeSerialiser<geoExplorerTurtle>> GEOEXPLORER_TURTLE =
                REGISTRY.register("geoexplorer_turtle", () -> TurtleUpgradeSerialiser.simpleWithCustomItem((id, item) -> new geoExplorerTurtle(new ResourceLocation("finerperipherals","geoexplorer_turtle"), TurtleUpgradeType.PERIPHERAL, new ItemStack(FinerPeripherals.GEOEXPLORER_BLOCK))));

        public static final RegistryEntry<TurtleUpgradeSerialiser<eventRelayerTurtle>> EVENTRELAYER_TURTLE =
                REGISTRY.register("eventrelayer_turtle", () -> TurtleUpgradeSerialiser.simpleWithCustomItem((id, item) -> new eventRelayerTurtle(new ResourceLocation("finerperipherals","eventrelayer_turtle"), TurtleUpgradeType.PERIPHERAL, new ItemStack(FinerPeripherals.EVENTRELAYER_BLOCK))));
    }

    public static void register() {
        TurtleRegistryMain.REGISTRY.register();
        FinerPeripherals.LOGGER.info("register yeaaaaa");
    }

}
