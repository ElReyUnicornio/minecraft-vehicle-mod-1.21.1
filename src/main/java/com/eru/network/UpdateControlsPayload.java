package com.eru.network;

import com.eru.KeyPress;
import com.eru.Vehicle;
import com.eru.VehicleClient;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record UpdateControlsPayload(Byte payload) implements CustomPayload {
    public static final CustomPayload.Id<UpdateControlsPayload> ID = new CustomPayload.Id<>(Identifier.of(Vehicle.MOD_ID, "update_controls"));
    public static final PacketCodec<RegistryByteBuf, UpdateControlsPayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BYTE, UpdateControlsPayload::payload,
            UpdateControlsPayload::new);

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
}
