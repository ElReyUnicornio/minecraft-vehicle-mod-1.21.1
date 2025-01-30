package com.eru.network;

import com.eru.KeyPress;
import com.eru.Vehicle;
import com.eru.entity.CustomVehicleEntity;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.util.Identifier;

public class ModNetwork {
    public static Identifier UPDATE_CONTROLS_PACKET_ID = Identifier.of(Vehicle.MOD_ID, "update_controls");

    public static void register() {
        PayloadTypeRegistry.playC2S().register(UpdateControlsPayload.ID, UpdateControlsPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(UpdateControlsPayload.ID, (payload, context) -> {
            KeyPress kp = payload.payload();

            context.server().execute(() -> {
                PlayerEntity ep = context.player();
                if (ep.getVehicle() != null && ep.getVehicle() instanceof CustomVehicleEntity) {
                    ((CustomVehicleEntity) ep.getVehicle()).updateControls(kp);
                }
            });
        });
    }
}
