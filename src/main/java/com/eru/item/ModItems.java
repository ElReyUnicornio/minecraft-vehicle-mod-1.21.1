package com.eru.item;

import com.eru.Vehicle;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item CustomVehicleItem = new Item(new Item.Settings());

    public static void register() {
        Registry.register(Registries.ITEM, Identifier.of(Vehicle.MOD_ID, "custom_vehicle_item"), CustomVehicleItem);

        Vehicle.LOGGER.info("Mod items registered...");
    }
}
