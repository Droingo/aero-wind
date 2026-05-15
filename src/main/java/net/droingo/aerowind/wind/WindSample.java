package net.droingo.aerowind.wind;

import net.minecraft.world.phys.Vec3;

public record WindSample(Vec3 direction, double speed) {
}