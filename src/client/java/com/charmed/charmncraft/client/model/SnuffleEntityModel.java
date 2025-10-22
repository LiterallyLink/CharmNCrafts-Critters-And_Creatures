package com.charmed.charmncraft.client.model;

import com.charmed.charmncraft.entity.SnuffleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class SnuffleEntityModel extends EntityModel<SnuffleEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart tail;
    private final ModelPart legFrontLeft;
    private final ModelPart legFrontRight;
    private final ModelPart legBackLeft;
    private final ModelPart legBackRight;

    public SnuffleEntityModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.tail = root.getChild("tail");
        this.legFrontLeft = root.getChild("leg_front_left");
        this.legFrontRight = root.getChild("leg_front_right");
        this.legBackLeft = root.getChild("leg_back_left");
        this.legBackRight = root.getChild("leg_back_right");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        // Body - fluffy round body
        modelPartData.addChild("body",
            ModelPartBuilder.create()
                .uv(0, 0)
                .cuboid(-5.0F, -8.0F, -7.0F, 10.0F, 10.0F, 14.0F),
            ModelTransform.pivot(0.0F, 14.0F, 0.0F));

        // Head - large fluffy head
        modelPartData.addChild("head",
            ModelPartBuilder.create()
                .uv(0, 24)
                .cuboid(-4.0F, -6.0F, -4.0F, 8.0F, 8.0F, 8.0F),
            ModelTransform.pivot(0.0F, 10.0F, -7.0F));

        // Tail - small fluffy tail
        modelPartData.addChild("tail",
            ModelPartBuilder.create()
                .uv(40, 0)
                .cuboid(-2.0F, 0.0F, 0.0F, 4.0F, 4.0F, 4.0F),
            ModelTransform.pivot(0.0F, 10.0F, 7.0F));

        // Legs
        modelPartData.addChild("leg_front_left",
            ModelPartBuilder.create()
                .uv(0, 40)
                .cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 8.0F, 3.0F),
            ModelTransform.pivot(3.0F, 16.0F, -5.0F));

        modelPartData.addChild("leg_front_right",
            ModelPartBuilder.create()
                .uv(0, 40)
                .cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 8.0F, 3.0F),
            ModelTransform.pivot(-3.0F, 16.0F, -5.0F));

        modelPartData.addChild("leg_back_left",
            ModelPartBuilder.create()
                .uv(0, 40)
                .cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 8.0F, 3.0F),
            ModelTransform.pivot(3.0F, 16.0F, 5.0F));

        modelPartData.addChild("leg_back_right",
            ModelPartBuilder.create()
                .uv(0, 40)
                .cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 8.0F, 3.0F),
            ModelTransform.pivot(-3.0F, 16.0F, 5.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(SnuffleEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        // Head rotation
        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;

        // Leg animation
        this.legFrontLeft.pitch = (float) Math.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;
        this.legFrontRight.pitch = (float) Math.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
        this.legBackLeft.pitch = (float) Math.cos(limbAngle * 0.6662F + (float) Math.PI) * 1.4F * limbDistance;
        this.legBackRight.pitch = (float) Math.cos(limbAngle * 0.6662F) * 1.4F * limbDistance;

        // Tail wag
        this.tail.yaw = (float) Math.sin(animationProgress * 0.067F) * 0.3F;
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        this.root.render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
