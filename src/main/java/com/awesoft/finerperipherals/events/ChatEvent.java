package com.awesoft.finerperipherals.events;


import com.awesoft.finerperipherals.blocks.chatbox.chatBoxAttachedManager;
import dan200.computercraft.api.peripheral.IComputerAccess;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.minecraft.server.level.ServerPlayer;

import java.util.Set;

public class ChatEvent {

    public void onInitialize() {
        // Register a chat message listener
        ServerMessageEvents.CHAT_MESSAGE.register((message, sender, params) -> {
            ServerPlayer player = sender;
            if (player != null) {
                // Extract the chat message
                String chatMessage = message.signedContent();
                String user = player.getName().getString();

                // Send the event to all computers with the chat_data peripheral
                sendMessageToComputersWithPeripheral(user, chatMessage);
            }
        });
    }


    /*
    private static String getChatMessageContent(PlayerChatMessage message) {
        // Check if the message is signed
        if (message.hasSignature()) {
            // If signed, return signed content
            return message.signedContent().toString();
        } else {
            // If unsigned, return unsigned content
            if (message.unsignedContent() != null) {
                return message.unsignedContent().getString();
            }
            return "i uh... dont know how you got this, please contact mod developer!";
        }
    }
*/

    private static void sendMessageToComputersWithPeripheral(String user, String message) {
        // Get the instance of the PeripheralManager (singleton)
        chatBoxAttachedManager manager = chatBoxAttachedManager.getInstance();

        // Get the list of connected computers from the manager
        Set<IComputerAccess> connectedComputers = manager.getConnectedComputers();

        // Loop through the connected computers and queue the event for each
        for (IComputerAccess computer : connectedComputers) {
            sendMessageToComputer(computer, user, message);
        }
    }

    private static void sendMessageToComputer(IComputerAccess computer, String playerUsername, String message) {
        // Send an event to the computer's peripheral (you can queue events here)
        computer.queueEvent("chat", playerUsername, message);
    }

}
