//? if <=1.21.4 {
/*/^
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
 ^/
package net.raphimc.immediatelyfast.injection.mixins.hud_batching.compat;

import com.mojang.blaze3d.vertex.BufferUploader;

*///?}
//? if =1.20.4 {
/*import net.raphimc.immediatelyfast.feature.batching.BatchingBuffer;
*/
//?}
//? if <=1.21.4 {
/*import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
import org.spongepowered.asm.mixin.Mixin;

*///?}
//? if =1.20.4 {
/*import org.spongepowered.asm.mixin.Unique;
*/
//?}
//? if <=1.21.4 {
/*import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BufferUploader.class)
public abstract class MixinBufferRenderer {


*///?}
    //? if =1.21.4 {
/*    @Inject(method = {"draw", "drawWithShader"}, at = @At("HEAD"))
*/
    //?}
    //? if =1.20.4 {
/*    @Unique
    private static boolean immediatelyFast$isForceDrawing;

*/
    //?}
    //? if <=1.21.1 {
    /*@Inject(method = {"draw", "_drawWithShader"}, at = @At("HEAD"))

    *///?}
    //? if <=1.21.4 {
    /*private static void checkForDrawCallWhileBatching(CallbackInfo ci) {

    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*if (BatchingBuffers.isHudBatching()) {

        *///?}
        //? if =1.20.4 {
/*        // Force draw the current batch if
        // we are not already force drawing (prevent recursion)
        // and the buffer being drawn is not one of the IF batching buffers
        // and we are currently batching (just checks if one of the vertex consumers is set)
        // and there is data to draw
        if (!immediatelyFast$isForceDrawing && !BatchingBuffer.IS_DRAWING && BatchingBuffers.FILL_CONSUMER != null && BatchingBuffers.hasDataToDraw()) {
*/
        //?}
            // If some mod tries to directly draw something while we are batching, we should end the current batch and start a new one, so that the draw order is correct.
            //? if (>=1.21.1 && <=1.21.4) {
            /*BatchingBuffers.tryForceDrawHudBuffers();

            *///?}
            //? if =1.20.4 {
/*            immediatelyFast$isForceDrawing = true;
            BatchingBuffers.forceDrawBuffers();
            immediatelyFast$isForceDrawing = false;
*/
            //?}
        //? if <=1.21.4 {
        /*}
    }

}

        *///?}
