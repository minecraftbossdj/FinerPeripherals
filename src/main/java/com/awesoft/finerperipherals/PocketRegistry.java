package com.awesoft.finerperipherals;

import com.awesoft.finerperipherals.peripherals.chatbox.pocket.ChatBoxUpgrade;
import com.awesoft.finerperipherals.peripherals.debugPocket.DebugUpgrade;
import com.awesoft.finerperipherals.peripherals.eventrelayer.pocket.eventRelayerUpgrade;
import com.awesoft.finerperipherals.peripherals.geoexplorer.pocket.geoExplorerUpgrade;
import dan200.computercraft.api.pocket.PocketUpgradeSerialiser;
import dan200.computercraft.shared.platform.PlatformHelper;
import dan200.computercraft.shared.platform.RegistrationHelper;
import dan200.computercraft.shared.platform.RegistryEntry;
import dan200.computercraft.shared.pocket.peripherals.PocketModem;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

public class PocketRegistry {

    public static class pocketRegistryMain {

        static final RegistrationHelper<PocketUpgradeSerialiser<?>> REGISTRY = PlatformHelper.get().createRegistrationHelper(PocketUpgradeSerialiser.registryId());

        public static final RegistryEntry<PocketUpgradeSerialiser<ChatBoxUpgrade>> CHATBOX_POCKET =
                REGISTRY.register("chatbox_pocket", () -> PocketUpgradeSerialiser.simpleWithCustomItem(ChatBoxUpgrade::new));

        public static final RegistryEntry<PocketUpgradeSerialiser<geoExplorerUpgrade>> GEOEXPLORER_POCKET =
                REGISTRY.register("geoexplorer_pocket", () -> PocketUpgradeSerialiser.simpleWithCustomItem(geoExplorerUpgrade::new));

        public static final RegistryEntry<PocketUpgradeSerialiser<eventRelayerUpgrade>> EVENTRELAYER_POCKET =
                REGISTRY.register("eventrelayer_pocket", () -> PocketUpgradeSerialiser.simpleWithCustomItem(eventRelayerUpgrade::new));

        public static final RegistryEntry<PocketUpgradeSerialiser<DebugUpgrade>> DEBUG_POCKET =
                REGISTRY.register("debug_pocket", () -> PocketUpgradeSerialiser.simpleWithCustomItem(DebugUpgrade::new));

    }

    public static void register() {
        pocketRegistryMain.REGISTRY.register();
    }

}

