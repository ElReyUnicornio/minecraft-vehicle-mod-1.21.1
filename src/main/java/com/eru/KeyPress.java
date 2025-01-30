package com.eru;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.input.Input;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;

public class KeyPress {
    public static final PacketCodec<ByteBuf, KeyPress> PACKET_CODEC = new PacketCodec<ByteBuf, KeyPress>() {
        public KeyPress decode(ByteBuf byteBuf) {
            return new KeyPress(new PacketByteBuf(byteBuf));
        }

        public void encode(ByteBuf byteBuf, KeyPress kp) {
            PacketByteBuf buf = new PacketByteBuf(byteBuf);

            buf.writeInt(kp.passenger_id);
            buf.writeBoolean(kp.forward);
            buf.writeBoolean(kp.backward);
            buf.writeBoolean(kp.left);
            buf.writeBoolean(kp.right);
            buf.writeBoolean(kp.turn_l);
            buf.writeBoolean(kp.turn_r);
        }
    };;
    public int passenger_id;
    public boolean forward;
    public boolean backward;
    public boolean left;
    public boolean right;
    public boolean turn_l;
    public boolean turn_r;

    public KeyPress() {
        this.passenger_id = 0;
        this.forward = false;
        this.backward = false;
        this.left = false;
        this.right = false;
        this.turn_l = false;
        this.turn_r = false;
    }

    public KeyPress(Input i, boolean tl, boolean tr, int id) {
        this.passenger_id = id;
        this.forward = i.pressingForward;
        this.backward = i.pressingBack;
        this.left = i.pressingLeft;
        this.right = i.pressingRight;
        this.turn_l = tl;
        this.turn_r = tr;
    }

    public KeyPress(PacketByteBuf pbb) {
        this.passenger_id = pbb.readInt();

        this.forward = pbb.readBoolean();
        this.backward = pbb.readBoolean();
        this.left = pbb.readBoolean();
        this.right = pbb.readBoolean();
        this.turn_l = pbb.readBoolean();
        this.turn_r = pbb.readBoolean();
    }

    @Override
    public String toString() {
        String s = "PressedKeys: ";

        s += this.forward ? "F" : "-";
        s += this.backward ? "B" : "-";
        s += this.left ? "L" : "-";
        s += this.right ? "R" : "-";
        s += this.turn_l ? "TL" : "-";
        s += this.turn_r ? "TR" : "-";

        return s;
    }
}
