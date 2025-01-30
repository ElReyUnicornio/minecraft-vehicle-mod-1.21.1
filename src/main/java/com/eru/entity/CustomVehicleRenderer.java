package com.eru.entity;

import com.eru.VehicleClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class CustomVehicleRenderer extends EntityRenderer<CustomVehicleEntity> {
    private final CustomVehicleModel model;

    public CustomVehicleRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new CustomVehicleModel(ctx.getPart(VehicleClient.MODEL_VEHICLE_LAYER));
        this.shadowRadius = 1.0f;
    }

    @Override
    public Identifier getTexture(CustomVehicleEntity entity) {
        return Identifier.of("vehicle", "textures/entity/custom_vehicle_txt.png");
    }

    @Override
    public void render(CustomVehicleEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
        matrices.push();
        matrices.translate(0.0, 6.5, 0);
        matrices.scale(4.0f, -4.0f, -4.0f);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
        this.model.render(matrices, vertexConsumers.getBuffer(this.model.getLayer(this.getTexture(entity))), light, OverlayTexture.DEFAULT_UV, 1);
        matrices.pop();
    }
}
