/*
 * Copyright (c) 2022 TeamMoeg
 *
 * This file is part of Thermopolium.
 *
 * Thermopolium is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * Thermopolium is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Thermopolium. If not, see <https://www.gnu.org/licenses/>.
 */

package com.teammoeg.thermopolium.client;

import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.MathHelper;

public class THParticle extends SpriteTexturedParticle {
	protected float originalScale = 1.3F;

	protected THParticle(ClientWorld world, double x, double y, double z) {
		super(world, x, y, z);
	}

	public THParticle(ClientWorld world, double x, double y, double z, double motionX, double motionY, double motionZ) {
		super(world, x, y, z, motionX, motionY, motionZ);
	}

	public IParticleRenderType getRenderType() {
		return IParticleRenderType.PARTICLE_SHEET_TRANSLUCENT;
	}

	@Override
	public void renderParticle(IVertexBuilder worldRendererIn, ActiveRenderInfo entityIn, float pt) {
		float age = (this.age + pt) / maxAge * 32.0F;

		age = MathHelper.clamp(age, 0.0F, 1.0F);

		this.particleScale = originalScale * age;
		super.renderParticle(worldRendererIn, entityIn, pt);
	}

	public void tick() {
		this.prevPosX = posX;
		this.prevPosY = posY;
		this.prevPosZ = posZ;
		if (age >= maxAge)
			setExpired();
		this.age++;

		this.motionY -= 0.04D * particleGravity;
		move(motionX, motionY, motionZ);

		if (posY == prevPosY) {
			this.motionX *= 1.1D;
			this.motionZ *= 1.1D;
		}

		this.motionX *= 0.96D;
		this.motionY *= 0.96D;
		this.motionZ *= 0.96D;

		if (onGround) {
			this.motionX *= 0.67D;
			this.motionZ *= 0.67D;
		}
	}

}
