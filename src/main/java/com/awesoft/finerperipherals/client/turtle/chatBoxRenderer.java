package com.awesoft.finerperipherals.client.turtle;

import com.awesoft.finerperipherals.FinerPeripherals;
import com.awesoft.finerperipherals.TurtleRegistry;
import com.awesoft.finerperipherals.peripherals.chatbox.turtle.chatBoxTurtle;
import com.mojang.math.Transformation;
import dan200.computercraft.api.client.TransformedModel;
import dan200.computercraft.api.client.turtle.TurtleUpgradeModeller;
import dan200.computercraft.api.turtle.ITurtleAccess;
import dan200.computercraft.api.turtle.TurtleSide;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class chatBoxRenderer implements TurtleUpgradeModeller<chatBoxTurtle> {

    @Override
    public TransformedModel getModel(chatBoxTurtle upgrade, ITurtleAccess turtle, TurtleSide side) {
        var matrix = new Matrix4f();
        matrix.set(new float[]{
                0.0f, 0.0f, -1.0f, 1.0f + (side == TurtleSide.LEFT ? -0.40625f : 0.40625f),
                1.0f, 0.0f, 0.0f, 0.0f,
                0.0f, -1.0f, 0.0f, 1.0f,
                0.0f, 0.0f, 0.0f, 1.0f,
        });
        matrix.transpose();
        matrix.rotateLocalX((float)Math.toRadians(90));
        if (side == TurtleSide.RIGHT) {
            matrix.translateLocal(-1/2.5f,1.25f,1/5.3f);
        } else {
            matrix.translateLocal(-1/17f,1.25f,1/5.3f);
        }
        matrix.scale(0.55f);

        return TransformedModel.of(FinerPeripherals.CHATBOX_BLOCK.asItem().getDefaultInstance(), new Transformation(matrix));
    }
}
