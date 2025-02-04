package com.eru;

import net.minecraft.client.input.Input;

public class KeyPress {
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

    public KeyPress(int id, Byte inputs) {
        this.passenger_id = id;

        this.forward  = (inputs & (1 << 0)) != 0;
        this.backward = (inputs & (1 << 1)) != 0;
        this.left     = (inputs & (1 << 2)) != 0;
        this.right    = (inputs & (1 << 3)) != 0;
        this.turn_l   = (inputs & (1 << 4)) != 0;
        this.turn_r   = (inputs & (1 << 5)) != 0;
    }

    public Byte toByte() {
        byte packedByte = 0;
        packedByte |= (byte) (this.forward  ? 1 << 0 : 0);
        packedByte |= (byte) (this.backward ? 1 << 1 : 0);
        packedByte |= (byte) (this.left     ? 1 << 2 : 0);
        packedByte |= (byte) (this.right    ? 1 << 3 : 0);
        packedByte |= (byte) (this.turn_l   ? 1 << 4 : 0);
        packedByte |= (byte) (this.turn_r   ? 1 << 5 : 0);

        return packedByte;
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
