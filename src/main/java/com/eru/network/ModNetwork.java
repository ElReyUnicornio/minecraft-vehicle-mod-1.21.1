package com.eru.network;

import com.eru.KeyPress;
import com.eru.entity.CustomVehicleEntity;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;

public class ModNetwork {
    public static void register() {
        PayloadTypeRegistry.playC2S().register(UpdateControlsPayload.ID, UpdateControlsPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(UpdateControlsPayload.ID, (payload, context) -> {

            context.server().execute(() -> {
                PlayerEntity ep = context.player();
                if (ep.getVehicle() != null && ep.getVehicle() instanceof CustomVehicleEntity) {
                    int id = ((CustomVehicleEntity) ep.getVehicle()).getPassenger(context.player());
                    KeyPress kp = new KeyPress(id, payload.payload());
                    ((CustomVehicleEntity) ep.getVehicle()).updateControls(kp);
                }
            });
        });
    }
}
