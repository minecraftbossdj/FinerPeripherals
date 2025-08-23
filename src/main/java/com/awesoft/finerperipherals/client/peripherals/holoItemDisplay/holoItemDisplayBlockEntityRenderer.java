package com.awesoft.finerperipherals.client.peripherals.holoItemDisplay;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.peripherals.holoitemdisplay.holoItemDisplayBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.fabric.impl.biome.modification.BuiltInRegistryKeys;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.intellij.lang.annotations.Identifier;

import static net.minecraft.world.level.block.DirectionalBlock.FACING;

public class holoItemDisplayBlockEntityRenderer implements BlockEntityRenderer<holoItemDisplayBlockEntity> {
    private final ItemRenderer itemRenderer;

    public holoItemDisplayBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(holoItemDisplayBlockEntity blockEntity, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        // Get the stored item
        String itemID = blockEntity.getStoredItem();
        //FinerPeripherals.LOGGER.info(itemID);
        // Convert the string to a ResourceLocation (item ID)
            ResourceLocation itemLocation = new ResourceLocation(itemID);
            //FinerPeripherals.LOGGER.info(String.valueOf(itemLocation));
            // Get the item from the registry
            Item item = BuiltInRegistries.ITEM.get(itemLocation);
            //FinerPeripherals.LOGGER.info(String.valueOf(item));

        if (item != null) {
            // Create an ItemStack for that item (e.g., "minecraft:stone")
            ItemStack itemStack = new ItemStack(item);
            //FinerPeripherals.LOGGER.info(String.valueOf(itemStack));
            //if (!itemStack.isEmpty()) {
            // Push matrix stack for transformations
            matrices.pushPose();

            Direction facing = blockEntity.getBlockState().getValue(FACING);

            double mid = 0.125;

            // Translate to the center of the block
            switch (facing) {
                case UP -> matrices.translate(0.5, -0.1, 0.5);
                case DOWN -> matrices.translate(0.5, 0.25, 0.5); // default
                case NORTH-> matrices.translate(0.5, mid, 0.75);
                case SOUTH -> matrices.translate(0.5, mid, 0.25);
                case EAST -> matrices.translate(0.25, mid, 0.5);
                case WEST -> matrices.translate(0.75, mid, 0.5);

            }

            // Apply rotation
            long time = blockEntity.getLevel().getGameTime();
            matrices.mulPose(Axis.YP.rotationDegrees((time + tickDelta) * 4 % 360));

            // Scale the item (optional)
            matrices.scale(2f, 2f, 2f);

            if (blockEntity.getLevel() == null) {
                FinerPeripherals.LOGGER.info("Level is null! Cannot render item.");
                return;
            }

            // Render the item
            this.itemRenderer.renderStatic(
                    itemStack,
                    ItemDisplayContext.GROUND,
                    0xF000F0,
                    overlay,
                    matrices,
                    vertexConsumers,
                    blockEntity.getLevel(),
                    0
            );
            // Pop matrix stack to restore state
            matrices.popPose();
            //}
        } else {
            FinerPeripherals.LOGGER.info("FAKE ITEM NO REAL");
        }
    }
}
