/*
 * This file is part of Dynamic Surroundings, licensed under the MIT License (MIT).
 *
 * Copyright (c) OreCruncher
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.blockartistry.mod.DynSurround.client.fx.particle;

import org.blockartistry.mod.DynSurround.client.PlayerSoundEffectHandler;
import org.blockartistry.mod.DynSurround.client.fx.SoundEffect;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityCloudFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

@SideOnly(Side.CLIENT)
public class EntitySteamJetFX extends EntityJetFX {

	private static final SoundEffect STEAM_FIZZ = new SoundEffect("minecraft:random.fizz");

	protected static final class EntitySteamCloudFX extends EntityCloudFX {
		public EntitySteamCloudFX(final World world, final double x, final double y, final double z, final double dX,
				final double dY, final double dZ) {
			super(world, x, y, z, dX, dY, dZ);
		}

		@Override
		public void onUpdate() {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;

			if (this.particleAge++ >= this.particleMaxAge) {
				this.setDead();
			}

			this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
			this.moveEntity(this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.9599999785423279D;
			this.motionY *= 0.9599999785423279D;
			this.motionZ *= 0.9599999785423279D;

			if (this.onGround) {
				this.motionX *= 0.699999988079071D;
				this.motionZ *= 0.699999988079071D;
			}
		}
	}

	public EntitySteamJetFX(final int strength, final World world, final double x, final double y, final double z) {
		super(strength, world, x, y, z);
	}

	@Override
	public void playSound() {
		final int x = MathHelper.floor_double(this.posX);
		final int y = MathHelper.floor_double(this.posY);
		final int z = MathHelper.floor_double(this.posZ);
		PlayerSoundEffectHandler.playSoundAt(x, y, z, STEAM_FIZZ, 0);
	}

	@Override
	protected EntityFX getJetParticle() {
		final double motionX = RANDOM.nextGaussian() * 0.02D;
		final double motionZ = RANDOM.nextGaussian() * 0.02D;
		return new EntitySteamCloudFX(this.worldObj, this.posX, this.posY, this.posZ, motionX, 0.1F, motionZ);
	}

}
