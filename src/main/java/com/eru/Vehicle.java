package com.eru;

import com.eru.entity.ModEntities;
import com.eru.item.ModItems;
import com.eru.network.ModNetwork;
import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.vehicle.BoatEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vehicle implements ModInitializer {
	public static final String MOD_ID = "vehicle";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItems.register();
		ModEntities.register();
		ModNetwork.register();
	}
}