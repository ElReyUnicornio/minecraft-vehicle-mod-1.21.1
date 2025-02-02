package com.eru;

import com.eru.entity.ModEntities;
import com.eru.network.ModNetwork;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Vehicle implements ModInitializer {
	public static final String MOD_ID = "vehicle";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEntities.register();
		ModNetwork.register();
	}
}