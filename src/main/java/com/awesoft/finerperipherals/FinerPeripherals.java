package com.awesoft.finerperipherals;

import com.awesoft.finerperipherals.blocks.chatbox.chatBoxBlock;
import com.awesoft.finerperipherals.blocks.chatbox.chatBoxBlockEntity;
import com.awesoft.finerperipherals.blocks.chatbox.chatBoxPeripheral;
import com.awesoft.finerperipherals.blocks.chatbox.pocket.ChatBoxUpgrade;
import com.awesoft.finerperipherals.events.ChatEvent;
import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.PeripheralLookup;
import dan200.computercraft.api.pocket.IPocketUpgrade;
import dan200.computercraft.api.pocket.PocketUpgradeDataProvider;
import dan200.computercraft.api.pocket.PocketUpgradeSerialiser;
import dan200.computercraft.api.upgrades.UpgradeBase;
import dan200.computercraft.impl.PocketUpgrades;
import dan200.computercraft.shared.platform.PlatformHelper;
import dan200.computercraft.shared.platform.RegistrationHelper;
import dan200.computercraft.shared.platform.RegistryEntry;
import dan200.computercraft.shared.pocket.peripherals.PocketSpeaker;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class FinerPeripherals implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("finerperipherals");

    static String MODID = "finerperipherals";

    public static final CreativeModeTab TAB = FabricItemGroup.builder()
            .icon(() -> new ItemStack(FinerPeripherals.CHATBOX_BLOCK))
            .title(Component.empty().append("Finer Peripherals"))
            .build();

    public static HashMap<String,Item> UPGRADE_MAP  = new HashMap<>();
    @Override
    public void onInitialize() {

        LOGGER.info("hello!");
        new ChatEvent().onInitialize();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,new ResourceLocation("finerperipherals","tab"),TAB);
        registerBlocks();
        registerPeripherals();
        registerItems();
        PocketRegistry.register();
        TurtleRegistry.register();


    }

    public static final chatBoxBlock CHATBOX_BLOCK = new chatBoxBlock(BlockBehaviour.Properties.of());

    private void registerBlocks()
    {
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation("finerperipherals","chatbox"),CHATBOX_BLOCK);
    }
    private void registerItems()
    {
        int v = BuiltInRegistries.ITEM.size();

        //register block items
        Registry.register(BuiltInRegistries.ITEM,new ResourceLocation("finerperipherals","chatbox"),new BlockItem(CHATBOX_BLOCK,new Item.Properties()));

        for (int i = v; i < BuiltInRegistries.ITEM.size(); i++) {
            int finalI = i;
            ItemGroupEvents.modifyEntriesEvent(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(TAB).get()).register(a->
            {
                a.accept(BuiltInRegistries.ITEM.byId(finalI));
            });
        }
    }



    private void registerPeripherals()
    {
        PeripheralLookup.get().registerForBlockEntity((a,b)->new chatBoxPeripheral(a),CHATBOX_BE);
    }


    public static BlockEntityType<chatBoxBlockEntity> CHATBOX_BE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation("finerperipherals", "chatbox_block_entity"),
            FabricBlockEntityTypeBuilder.create(chatBoxBlockEntity::new, CHATBOX_BLOCK).build()
    );
}
