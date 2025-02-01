package com.eru.entity;

import com.eru.Vehicle;
import com.eru.network.UpdateControlsPayload;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.*;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import com.eru.KeyPress;

import java.util.ArrayList;
import java.util.List;


public class CustomVehicleEntity extends Entity {
    public static int MAX_PASSENGERS = 4;
    private static final List<TrackedData<Boolean>> FORWARD = new ArrayList<>();
    private static final List<TrackedData<Boolean>> BACKWARD = new ArrayList<>();
    private static final List<TrackedData<Boolean>> LEFT = new ArrayList<>();
    private static final List<TrackedData<Boolean>> RIGHT = new ArrayList<>();
    private static final List<TrackedData<Boolean>> TURN_L = new ArrayList<>();
    private static final List<TrackedData<Boolean>> TURN_R = new ArrayList<>();


    static {
        for (int i = 0; i < MAX_PASSENGERS; i++) {
            FORWARD.add(DataTracker.registerData(CustomVehicleEntity.class, TrackedDataHandlerRegistry.BOOLEAN));
            BACKWARD.add(DataTracker.registerData(CustomVehicleEntity.class, TrackedDataHandlerRegistry.BOOLEAN));
            LEFT.add(DataTracker.registerData(CustomVehicleEntity.class, TrackedDataHandlerRegistry.BOOLEAN));
            RIGHT.add(DataTracker.registerData(CustomVehicleEntity.class, TrackedDataHandlerRegistry.BOOLEAN));
            TURN_L.add(DataTracker.registerData(CustomVehicleEntity.class, TrackedDataHandlerRegistry.BOOLEAN));
            TURN_R.add(DataTracker.registerData(CustomVehicleEntity.class, TrackedDataHandlerRegistry.BOOLEAN));
        }
    }

    public float MAX_VELOCITY = 3;
    private float velocityDecay;
    private float yawVelocity;
    private int lerpTicks;
    private double x;
    private double y;
    private double z;
    private double vehicleYaw;
    private double vehiclePitch;

    private KeyPress[] keys = new KeyPress[MAX_PASSENGERS];


    public CustomVehicleEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        for (int i = 0; i < MAX_PASSENGERS; i++) {
            builder.add(FORWARD.get(i), false);
            builder.add(BACKWARD.get(i), false);
            builder.add(LEFT.get(i), false);
            builder.add(RIGHT.get(i), false);
            builder.add(TURN_L.get(i), false);
            builder.add(TURN_R.get(i), false);
        }
    }

    public void updateDataTrackers(int id) {
        this.dataTracker.set(FORWARD.get(id), this.keys[id].forward);
        this.dataTracker.set(BACKWARD.get(id), this.keys[id].backward);
        this.dataTracker.set(LEFT.get(id), this.keys[id].left);
        this.dataTracker.set(RIGHT.get(id), this.keys[id].right);
        this.dataTracker.set(TURN_L.get(id), this.keys[id].turn_l);
        this.dataTracker.set(TURN_R.get(id), this.keys[id].turn_r);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {

    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {

    }

    @Override
    public void tick() {
        super.tick();
        this.updatePositionAndRotation();
        this.setRotation(this.getYaw(), this.getPitch());
        if (this.isLogicalSideForUpdatingMovement()) {
            this.tickMovement();
            this.move(MovementType.SELF, this.getVelocity());
        }
    }

    private void tickMovement() {
        if (this.hasPassengers()) {
            float horizontal = 0.0f;
            float vertical = 0.0f;
            float yaw = 0.0f;
            float f = 2.0f;
            Vehicle.LOGGER.info("has passenger...");
            List<Entity> list = this.getPassengerList();
            if (!list.isEmpty()) {
                for (int i = 0; i < list.size(); i++) {
                    if (this.keys[i] == null) this.keys[i] = new KeyPress();
                    KeyPress currKey = this.keys[i];
                    if (currKey.forward) vertical += this.MAX_VELOCITY / MAX_PASSENGERS;
                    if (currKey.left) horizontal += this.MAX_VELOCITY / MAX_PASSENGERS;
                    if (currKey.turn_r) yaw += this.MAX_VELOCITY / MAX_PASSENGERS;
                    if (currKey.backward) vertical -= this.MAX_VELOCITY / MAX_PASSENGERS;
                    if (currKey.right) horizontal -= this.MAX_VELOCITY / MAX_PASSENGERS;
                    if (currKey.turn_l) yaw -= this.MAX_VELOCITY / MAX_PASSENGERS;
                }

                Vehicle.LOGGER.info("Moving...");
                Vehicle.LOGGER.info("horizontal: {}", horizontal);
                Vehicle.LOGGER.info("vertical: {}", vertical);
                Vehicle.LOGGER.info("YAW: {}", yaw * f);
                this.setYaw(this.getYaw() + yaw * f);
                this.setVelocity(rotateVelocity(new Vec3d(horizontal, -1.0f, vertical), this.getYaw()));
            }
        }
    }

    public Vec3d rotateVelocity(Vec3d velocity, float yawDegrees) {
        double yawRadians = Math.toRadians(yawDegrees);

        double cos = Math.cos(yawRadians);
        double sin = Math.sin(yawRadians);

        double newX = velocity.x * cos - velocity.z * sin;
        double newZ = velocity.x * sin + velocity.z * cos;

        return new Vec3d(newX, velocity.y, newZ);
    }

    public void updateControls(KeyPress kp) {
        if (this.keys[kp.passenger_id] == null) this.keys[kp.passenger_id] = new KeyPress();

        boolean requiresUpdate = kp.forward != this.keys[kp.passenger_id].forward
                || kp.backward != this.keys[kp.passenger_id].backward
                || kp.left != this.keys[kp.passenger_id].left
                || kp.right != this.keys[kp.passenger_id].right
                || kp.turn_l != this.keys[kp.passenger_id].turn_l
                || kp.turn_r != this.keys[kp.passenger_id].turn_r;

        this.keys[kp.passenger_id] = kp;
        this.updateDataTrackers(kp.passenger_id);

        if (this.getWorld().isClient && requiresUpdate) {
            ClientPlayNetworking.send(new UpdateControlsPayload(kp));
        }
    }

    @Override
    public void updateTrackedPositionAndAngles(double x, double y, double z, float yaw, float pitch, int interpolationSteps) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.vehicleYaw = (double) yaw;
        this.vehiclePitch = (double) pitch;
        this.lerpTicks = 10;
    }

    private void updatePositionAndRotation() {
        if (this.isLogicalSideForUpdatingMovement()) {
            this.lerpTicks = 0;
            this.updateTrackedPosition(this.getX(), this.getY(), this.getZ());
        }

        if (this.lerpTicks > 0) {
            this.lerpPosAndRotation(this.lerpTicks, this.x, this.y, this.z, this.vehicleYaw, this.vehiclePitch);
            this.lerpTicks--;
        }
    }

    @Override
    protected double getGravity() {
        return 0.04;
    }

    @Override
    public Direction getMovementDirection() {
        return this.getHorizontalFacing().rotateYClockwise();
    }

    @Override
    public boolean canHit() {
        return !this.isRemoved();
    }

    @Override
    public boolean collidesWith(Entity other) {
        return canCollide(this, other);
    }

    public static boolean canCollide(Entity entity, Entity other) {
        return (other.isCollidable() || other.isPushable()) && !entity.isConnectedThroughVehicle(other);
    }

    @Override
    public boolean isCollidable() {
        return false;
    }

    @Override
    public boolean isPushable() {
        return true;
    }

    @Override
    public void pushAwayFrom(Entity entity) {
        if (entity instanceof PlayerEntity) {
            super.pushAwayFrom(entity);
        }
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        if (player.shouldCancelInteraction()) {
            return ActionResult.SUCCESS;
        } else {
            if (!this.getWorld().isClient) {
                return player.startRiding(this) ? ActionResult.CONSUME : ActionResult.PASS;
            }
            return ActionResult.PASS;
        }
    }

    @Override
    protected boolean canAddPassenger(Entity passenger) {
        return this.getPassengerList().size() < MAX_PASSENGERS;
    }

    @Override
    public Vec3d getPassengerRidingPos(Entity passenger) {
        return super.getPassengerRidingPos(passenger).add(0.5, 0.2, 0.0);
    }

    @Override
    protected Vec3d getPassengerAttachmentPos(Entity passenger, EntityDimensions dimensions, float scaleFactor) {
        float z = 0.0f;
        float x = 0.0f;
        if (this.getPassengerList().size() > 1) {
            int i = this.getPassengerList().indexOf(passenger);
            x -= Math.round(i % 2);
            if (i == 0) {
                z = 0.0F;
            } else {
                z = (float) Math.floor((double) i / 2);
                z *= 0.5f;
            }
        }

        return new Vec3d(rotateVelocity(new Vec3d(x, 1.2, z), this.getYaw()).toVector3f());
    }

    protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);
        passenger.setBodyYaw(this.getYaw());
        passenger.prevYaw += this.yawVelocity;
        passenger.setYaw(passenger.getYaw() + this.yawVelocity);
        passenger.setHeadYaw(passenger.getHeadYaw() + this.yawVelocity);
        clampPassengerYaw(passenger);
    }

    protected void clampPassengerYaw(Entity passenger) {
        passenger.setBodyYaw(this.getYaw());
        float f = MathHelper.wrapDegrees(passenger.getYaw() - this.getYaw());
        float g = MathHelper.clamp(f, -45.0F, 45.0F);
        passenger.prevYaw += g - f;
        passenger.setYaw(passenger.getYaw() + g - f);
        passenger.setHeadYaw(passenger.getYaw());
    }

    public int getPassenger(Entity passenger) {
        return this.getPassengerList().indexOf(passenger);
    }
}
