/*
 * This file is part of ImmediatelyFast - https://github.com/RaphiMC/ImmediatelyFast
 * Copyright (C) 2023-2026 RK_01/RaphiMC and contributors
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.raphimc.immediatelyfast.injection.mixins.hud_batching.compat;

import com.mojang.blaze3d.vertex.BufferUploader;
import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if =1.20.4 {
/*import net.raphimc.immediatelyfast.feature.batching.BatchingBuffer;
import org.spongepowered.asm.mixin.Unique;
*///?}

@Mixin(BufferUploader.class)
public abstract class MixinBufferRenderer {
    //? if =1.20.4 {
    /*@Unique
    private static boolean immediatelyFast$isForceDrawing;
    *///?}

    //? if =1.21.4 {
    /*@Inject(method = {"draw", "drawWithShader"}, at = @At("HEAD"))
    private static void checkForDrawCallWhileBatching(final CallbackInfo ci) {
    *///?}
    //? if <=1.21.1 {
    /*@Inject(method = {"draw", "_drawWithShader"}, at = @At("HEAD"))
    private static void checkForDrawCallWhileBatching(final CallbackInfo ci) {
    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*if (BatchingBuffers.isHudBatching()) {
            // If some mod tries to directly draw something while we are batching, we should end the current batch and start a new one, so that the draw order is correct.
            BatchingBuffers.tryForceDrawHudBuffers();
        }
        *///?}
        //? if =1.20.4 {
        /*if (!immediatelyFast$isForceDrawing && !BatchingBuffer.IS_DRAWING && BatchingBuffers.FILL_CONSUMER != null && BatchingBuffers.hasDataToDraw()) {
            // If some mod tries to directly draw something while we are batching, we should end the current batch and start a new one, so that the draw order is correct.
            immediatelyFast$isForceDrawing = true;
            BatchingBuffers.forceDrawBuffers();
            immediatelyFast$isForceDrawing = false;
        }
        *///?}
    }
}
