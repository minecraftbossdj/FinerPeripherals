package com.awesoft.finerperipherals.peripherals.debugPocket;

import dan200.computercraft.api.peripheral.IComputerAccess;

import java.util.HashSet;
import java.util.Set;

public class debugAttachedManager {
    private static debugAttachedManager instance; // Singleton instance
    private Set<IComputerAccess> connectedComputers = new HashSet<>();// List of connected computers

    // Private constructor to prevent direct instantiation
    private debugAttachedManager() {
    }

    // Static method to get the instance of the manager
    public static debugAttachedManager getInstance() {
        if (instance == null) {
            instance = new debugAttachedManager();
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
