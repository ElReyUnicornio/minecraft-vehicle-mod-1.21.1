package com.eru.network;

import com.eru.KeyPress;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.math.BlockPos;

public record UpdateControlsPayload(KeyPress payload) implements CustomPayload {
        public static final CustomPayload.Id<UpdateControlsPayload> ID = new CustomPayload.Id<>(ModNetwork.UPDATE_CONTROLS_PACKET_ID);
    public static final PacketCodec<RegistryByteBuf, UpdateControlsPayload> CODEC = PacketCodec.tuple(KeyPress.PACKET_CODEC, UpdateControlsPayload::payload, UpdateControlsPayload::new);

        @Override
        public CustomPayload.Id<? extends CustomPayload> getId() {
            return ID;
        }
}
