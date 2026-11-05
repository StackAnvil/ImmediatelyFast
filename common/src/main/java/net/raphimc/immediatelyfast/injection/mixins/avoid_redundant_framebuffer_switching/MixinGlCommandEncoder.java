//? if >=1.21.10 {
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
package net.raphimc.immediatelyfast.injection.mixins.avoid_redundant_framebuffer_switching;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
//?}
//? if =26.3 {
import com.mojang.renderpearl.backend.opengl.GlConst;
import com.mojang.renderpearl.backend.opengl.GlStateManager;
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*import com.mojang.blaze3d.opengl.GlCommandEncoder;
*/
//?}
//? if (>=1.21.10 && <=26.2) {
/*import com.mojang.blaze3d.opengl.GlConst;
import com.mojang.blaze3d.opengl.GlStateManager;
*/
//?}
//? if >=26.2 {
import net.raphimc.immediatelyfast.ImmediatelyFast;
//?}
//? if >=1.21.10 {
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//?}
//? if =26.3 {
@Mixin(targets = "com.mojang.renderpearl.backend.opengl.GlCommandEncoder")
//?}
//? if =26.2 {
/*@Mixin(targets = "com.mojang.blaze3d.opengl.GlCommandEncoder")
*/
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*@Mixin(GlCommandEncoder.class)
*/
//?}
//? if >=1.21.10 {
public abstract class MixinGlCommandEncoder {

//?}
    //? if =26.3 {
    @WrapWithCondition(method = "submitRenderPass", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/backend/opengl/GlStateManager;_glBindFramebuffer(II)V"))
    //?}
    //? if =26.2 {
/*    @WrapWithCondition(method = "submitRenderPass", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_glBindFramebuffer(II)V"))
*/
    //?}
    //? if >=26.2 {
    private boolean dontUnbindFramebuffer(final int target, final int framebuffer) {
        return !ImmediatelyFast.runtimeConfig.avoid_redundant_framebuffer_switching;
    //?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    @WrapWithCondition(method = "finishRenderPass", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/opengl/GlStateManager;_glBindFramebuffer(II)V"))
    private boolean dontUnbindFramebuffer(int target, int framebuffer) {
        return false;
*/
    //?}
    //? if >=1.21.10 {
    }

    // https://github.com/RaphiMC/ImmediatelyFast/issues/351
    @Inject(method = "presentTexture", at = @At("HEAD"))
    //?}
    //? if >=26.2 {
    private void unbindFramebufferBeforePresenting(final CallbackInfo ci) {
    //?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    private void unbindFramebufferBeforePresenting(CallbackInfo ci) {
*/
    //?}
        //? if >=1.21.10 {
        GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, 0);
    }

}
        //?}
