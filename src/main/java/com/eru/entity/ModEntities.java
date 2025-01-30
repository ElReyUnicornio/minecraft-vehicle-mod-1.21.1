package com.eru.entity;

import com.eru.Vehicle;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<CustomVehicleEntity> Custom_vehicle = Registry.register(Registries.ENTITY_TYPE, Identifier.of(Vehicle.MOD_ID, "custom_vehicle"), EntityType.Builder.create(CustomVehicleEntity::new, SpawnGroup.MISC).dimensions(3,1f).alwaysUpdateVelocity(true).build());

    public static void register() {
        Vehicle.LOGGER.info("Mod entities registered...");
    }
}
