package com.awesoft.finerperipherals;

import com.awesoft.finerperipherals.blocks.chatbox.pocket.ChatBoxUpgrade;
import dan200.computercraft.api.pocket.PocketUpgradeSerialiser;
import dan200.computercraft.api.upgrades.UpgradeBase;
import dan200.computercraft.api.upgrades.UpgradeDataProvider;
import dan200.computercraft.impl.PocketUpgrades;
import dan200.computercraft.shared.platform.PlatformHelper;
import dan200.computercraft.shared.platform.RegistrationHelper;
import dan200.computercraft.shared.platform.RegistryEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class PocketRegistry {

    public static class pocketRegistryMain {
        static final RegistrationHelper<PocketUpgradeSerialiser<?>> REGISTRY = PlatformHelper.get().createRegistrationHelper(PocketUpgradeSerialiser.registryId());

        public static final RegistryEntry<PocketUpgradeSerialiser<ChatBoxUpgrade>> CHATBOX_POCKET =
                REGISTRY.register("chatbox_pocket", () -> PocketUpgradeSerialiser.simpleWithCustomItem(ChatBoxUpgrade::new));

    }

    public static void register() {
        pocketRegistryMain.REGISTRY.register();
    }

}

