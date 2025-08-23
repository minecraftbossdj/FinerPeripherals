package com.awesoft.finerperipherals;

import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxBlock;
import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxBlockEntity;
import com.awesoft.finerperipherals.peripherals.chatbox.chatBoxPeripheral;
import com.awesoft.finerperipherals.events.ChatEvent;
import com.awesoft.finerperipherals.peripherals.eventrelayer.eventRelayerBlock;
import com.awesoft.finerperipherals.peripherals.eventrelayer.eventRelayerBlockEntity;
import com.awesoft.finerperipherals.peripherals.eventrelayer.eventRelayerPeripheral;
import com.awesoft.finerperipherals.peripherals.geoexplorer.geoExplorerBlock;
import com.awesoft.finerperipherals.peripherals.geoexplorer.geoExplorerBlockEntity;
import com.awesoft.finerperipherals.peripherals.geoexplorer.geoExplorerPeripheral;
import com.awesoft.finerperipherals.peripherals.holoitemdisplay.holoItemDisplayBlock;
import com.awesoft.finerperipherals.peripherals.holoitemdisplay.holoItemDisplayBlockEntity;
import com.awesoft.finerperipherals.peripherals.holoitemdisplay.holoItemDisplayPeripheral;
import dan200.computercraft.api.peripheral.PeripheralLookup;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class FinerPeripherals implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("finerperipherals");

    public static String MODID = "finerperipherals";

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

    /*
        //block naming
        public static final chatBoxBlock CHATBOX_BLOCK = new chatBoxBlock(BlockBehaviour.Properties.of());

        //block registry
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation("finerperipherals",periphName),CHATBOX_BLOCK);

        //block item registry
        Registry.register(BuiltInRegistries.ITEM,new ResourceLocation("finerperipherals",periphName),new BlockItem(CHATBOX_BLOCK,new Item.Properties()));

        //periph registry
        PeripheralLookup.get().registerForBlockEntity((a,b)->new chatBoxPeripheral(a),CHATBOX_BE);

        //block entity registry
        public static final BlockEntityType<chatBoxBlockEntity> CHATBOX_BE = Registry.register(
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                new ResourceLocation("finerperipherals", "chatbox_block_entity"),
                FabricBlockEntityTypeBuilder.create(chatBoxBlockEntity::new, CHATBOX_BLOCK).build()
        );
    */

    public static final chatBoxBlock CHATBOX_BLOCK = new chatBoxBlock(BlockBehaviour.Properties.copy(Blocks.STONE));
    public static final geoExplorerBlock GEOEXPLORER_BLOCK = new geoExplorerBlock(BlockBehaviour.Properties.copy(Blocks.STONE));
    public static final eventRelayerBlock EVENTRELAYER_BLOCK = new eventRelayerBlock(BlockBehaviour.Properties.copy(Blocks.STONE));
    public static final holoItemDisplayBlock HOLOITEMDISPLAY_BLOCK = new holoItemDisplayBlock(BlockBehaviour.Properties.copy(Blocks.STONE));

    private void registerBlocks()
    {
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation("finerperipherals","chatbox"),CHATBOX_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation("finerperipherals","geoexplorer"),GEOEXPLORER_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation("finerperipherals","eventrelayer"),EVENTRELAYER_BLOCK);
        Registry.register(BuiltInRegistries.BLOCK,new ResourceLocation("finerperipherals","holoitemdisplay"),HOLOITEMDISPLAY_BLOCK);

    }
    private void registerItems()
    {
        int v = BuiltInRegistries.ITEM.size();

        //register block items
        Registry.register(BuiltInRegistries.ITEM,new ResourceLocation("finerperipherals","chatbox"),new BlockItem(CHATBOX_BLOCK,new Item.Properties()));
        Registry.register(BuiltInRegistries.ITEM,new ResourceLocation("finerperipherals","geoexplorer"),new BlockItem(GEOEXPLORER_BLOCK,new Item.Properties()));
        Registry.register(BuiltInRegistries.ITEM,new ResourceLocation("finerperipherals","eventrelayer"),new BlockItem(EVENTRELAYER_BLOCK,new Item.Properties()));
        Registry.register(BuiltInRegistries.ITEM,new ResourceLocation("finerperipherals","holoitemdisplay"),new BlockItem(HOLOITEMDISPLAY_BLOCK,new Item.Properties()));


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
        PeripheralLookup.get().registerForBlockEntity((a,b)->new geoExplorerPeripheral(a),GEOEXPLORER_BE);
        PeripheralLookup.get().registerForBlockEntity((a,b)->new eventRelayerPeripheral(),EVENTRELAYER_BE);
        PeripheralLookup.get().registerForBlockEntity((a,b)->new holoItemDisplayPeripheral(a),HOLOITEMDISPLAY_BE);
    }


    public static BlockEntityType<chatBoxBlockEntity> CHATBOX_BE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation("finerperipherals", "chatbox_block_entity"),
            FabricBlockEntityTypeBuilder.create(chatBoxBlockEntity::new, CHATBOX_BLOCK).build()
    );
    public static BlockEntityType<geoExplorerBlockEntity> GEOEXPLORER_BE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation("finerperipherals", "geoexplorer_block_entity"),
            FabricBlockEntityTypeBuilder.create(geoExplorerBlockEntity::new, GEOEXPLORER_BLOCK).build()
    );
    public static BlockEntityType<eventRelayerBlockEntity> EVENTRELAYER_BE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation("finerperipherals", "eventrelayer_block_entity"),
            FabricBlockEntityTypeBuilder.create(eventRelayerBlockEntity::new, EVENTRELAYER_BLOCK).build()
    );
    public static BlockEntityType<holoItemDisplayBlockEntity> HOLOITEMDISPLAY_BE = Registry.register(
            BuiltInRegistries.BLOCK_ENTITY_TYPE,
            new ResourceLocation("finerperipherals", "holoitemdisplay_block_entity"),
            FabricBlockEntityTypeBuilder.create(holoItemDisplayBlockEntity::new, HOLOITEMDISPLAY_BLOCK).build()
    );

}
