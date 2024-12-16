package com.awesoft.finerperipherals.blocks.chatbox;

import dan200.computercraft.api.peripheral.IComputerAccess;

import java.util.HashSet;
import java.util.Set;

public class chatBoxAttachedManager {
    private static chatBoxAttachedManager instance; // Singleton instance
    private Set<IComputerAccess> connectedComputers = new HashSet<>();// List of connected computers

    // Private constructor to prevent direct instantiation
    private chatBoxAttachedManager() {
    }

    // Static method to get the instance of the manager
    public static chatBoxAttachedManager getInstance() {
        if (instance == null) {
            instance = new chatBoxAttachedManager();
        }
        return instance;
    }

    // Method to add a connected computer
    public void addComputer(IComputerAccess computer) {
        connectedComputers.add(computer);
    }

    // Method to get all connected computers
    public Set<IComputerAccess> getConnectedComputers() {
        return connectedComputers;
    }

    // Method to remove a connected computer
    public void removeComputer(IComputerAccess computer) {
        connectedComputers.remove(computer);
    }
}
