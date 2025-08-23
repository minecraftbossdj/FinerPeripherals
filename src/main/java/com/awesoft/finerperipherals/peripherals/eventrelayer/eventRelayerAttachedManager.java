package com.awesoft.finerperipherals.peripherals.eventrelayer;

import dan200.computercraft.api.peripheral.IComputerAccess;

import java.util.HashSet;
import java.util.Set;

public class eventRelayerAttachedManager {
    private static eventRelayerAttachedManager instance; // Singleton instance
    private Set<IComputerAccess> connectedComputers = new HashSet<>();// List of connected computers

    // Private constructor to prevent direct instantiation
    private eventRelayerAttachedManager() {
    }

    // Static method to get the instance of the manager
    public static eventRelayerAttachedManager getInstance() {
        if (instance == null) {
            instance = new eventRelayerAttachedManager();
        }
        return instance;
    }

    // Method to add a connected computer
    public void addComputer(IComputerAccess computer) {
        connectedComputers.add(computer);
    }

    public void sendMessageToComputer(int targetId, String eventName, Object... args) {
        for (IComputerAccess computer : connectedComputers) {
            if (computer.getID() == targetId) { // Check if the computer ID matches
                computer.queueEvent(eventName, args); // Send the event
                return; // Exit once the target computer is found
            }
        }
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
