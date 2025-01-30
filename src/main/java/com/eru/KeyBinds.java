package com.eru;

import com.eru.entity.CustomVehicleEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class KeyBinds {
    public static KeyBinding turn_l;
    public static KeyBinding turn_r;
    public static KeyBinding ctrl;

    public static void register() {
        turn_l = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.vehicle.tl", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_LEFT, "category.vehicle.main"));
        turn_r = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.vehicle.tr", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT, "category.vehicle.main"));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null || client.player.getVehicle() == null)
                return;

            if (!(client.player.getVehicle() instanceof CustomVehicleEntity cv))
                return;

            KeyPress pk = new KeyPress(client.player.input, turn_l.isPressed(), turn_r.isPressed(), cv.getPassenger(client.player));
            cv.updateControls(pk);
        });

    }
}
