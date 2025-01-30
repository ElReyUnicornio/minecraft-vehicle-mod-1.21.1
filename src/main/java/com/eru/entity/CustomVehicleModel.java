package com.eru.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class CustomVehicleModel extends EntityModel<CustomVehicleEntity> {
    private final ModelPart bone3;
    private final ModelPart bone2;
    private final ModelPart bone;
    private final ModelPart bb_main;
    public CustomVehicleModel(ModelPart root) {
        this.bone3 = root.getChild("bone3");
        this.bone2 = root.getChild("bone2");
        this.bone = root.getChild("bone");
        this.bb_main = root.getChild("bb_main");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData bone3 = modelPartData.addChild("bone3", ModelPartBuilder.create().uv(0, 22).cuboid(4.0F, 0.0F, -7.0F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F))
                .uv(34, 19).cuboid(-5.0F, -1.0F, 4.0F, 10.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(26, 22).cuboid(-5.0F, 0.0F, -7.0F, 1.0F, 1.0F, 12.0F, new Dilation(0.0F))
                .uv(34, 11).cuboid(-5.0F, -1.0F, -8.0F, 10.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, 2.0F));

        ModelPartData bone2 = modelPartData.addChild("bone2", ModelPartBuilder.create().uv(36, 3).cuboid(-4.0F, -3.0F, 4.0F, 8.0F, 1.0F, 1.0F, new Dilation(0.0F))
                .uv(0, 35).cuboid(-4.0F, -3.0F, -4.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F))
                .uv(0, 11).cuboid(-4.0F, -2.0F, -4.0F, 8.0F, 2.0F, 9.0F, new Dilation(0.0F))
                .uv(36, 0).cuboid(-4.0F, -1.0F, -6.0F, 8.0F, 1.0F, 2.0F, new Dilation(0.0F))
                .uv(34, 14).cuboid(-4.0F, -3.0F, -7.0F, 8.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(18, 35).cuboid(3.0F, -3.0F, -4.0F, 1.0F, 1.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 22.0F, 2.0F));

        ModelPartData bone = modelPartData.addChild("bone", ModelPartBuilder.create(), ModelTransform.pivot(1.0F, 18.0F, 3.15F));

        ModelPartData cube_r1 = bone.addChild("cube_r1", ModelPartBuilder.create().uv(40, 5).cuboid(2.0F, -4.0F, 3.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(40, 35).cuboid(-5.0F, -4.0F, 3.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.3491F, 0.0F, 0.0F));

        ModelPartData cube_r2 = bone.addChild("cube_r2", ModelPartBuilder.create().uv(36, 5).cuboid(2.0F, -3.0F, -4.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F))
                .uv(36, 35).cuboid(-5.0F, -3.0F, -4.0F, 1.0F, 4.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, -1.9F, -0.48F, 0.0F, 0.0F));

        ModelPartData bb_main = modelPartData.addChild("bb_main", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -9.0F, -1.5F, 8.0F, 1.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
    @Override
    public void setAngles(CustomVehicleEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        bone3.render(matrices, vertices, light, overlay);
        bone2.render(matrices, vertices, light, overlay);
        bone.render(matrices, vertices, light, overlay);
        bb_main.render(matrices, vertices, light, overlay);
    }
}
