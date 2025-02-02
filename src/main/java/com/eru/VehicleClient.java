package com.eru;

import com.eru.entity.CustomVehicleModel;
import com.eru.entity.CustomVehicleRenderer;
import com.eru.entity.ModEntities;
import com.eru.network.UpdateControlsPayload;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class VehicleClient implements ClientModInitializer {
	public static final EntityModelLayer MODEL_VEHICLE_LAYER = new EntityModelLayer(Identifier.of("vehicle", "custom_vehicle"), "main");

	@Override
	public void onInitializeClient() {
		EntityRendererRegistry.register(ModEntities.Custom_vehicle, CustomVehicleRenderer::new);
		EntityModelLayerRegistry.registerModelLayer(MODEL_VEHICLE_LAYER, CustomVehicleModel::getTexturedModelData);

		KeyBinds.register();
	}
}