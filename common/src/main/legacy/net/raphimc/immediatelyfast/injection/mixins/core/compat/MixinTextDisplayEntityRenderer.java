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

package net.raphimc.immediatelyfast.injection.mixins.core.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.world.entity.Display;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if =1.21.4 {
/*import net.minecraft.client.renderer.entity.state.TextDisplayEntityRenderState;
*///?}

@Mixin(DisplayRenderer.TextDisplayRenderer.class)
public abstract class MixinTextDisplayEntityRenderer {
    //? if =1.21.4 {
    /*@Inject(method = "renderInner(Lnet/minecraft/client/renderer/entity/state/TextDisplayEntityRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;lines()Ljava/util/List;", ordinal = 1))
    private void drawBackgroundImmediately(final TextDisplayEntityRenderState textDisplayEntityRenderState, final PoseStack matrixStack, final MultiBufferSource vertexConsumerProvider, final int i, final float f, final CallbackInfo ci) {
        if ((textDisplayEntityRenderState.textRenderState.flags() & Display.TextDisplay.FLAG_SEE_THROUGH) != 0 && vertexConsumerProvider instanceof MultiBufferSource.BufferSource immediate) {
            immediate.endBatch();
        }
    }
    *///?}

    //? if <=1.21.1 {
    /*@Inject(method = "renderInner(Lnet/minecraft/world/entity/Display$TextDisplay;Lnet/minecraft/world/entity/Display$TextDisplay$TextRenderState;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;IF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Display$TextDisplay$CachedInfo;lines()Ljava/util/List;", ordinal = 1))
    private void drawBackgroundImmediately(final Display.TextDisplay textDisplayEntity, final Display.TextDisplay.TextRenderState data, final PoseStack matrixStack, final MultiBufferSource vertexConsumerProvider, final int i, final float f, final CallbackInfo ci) {
        if ((data.flags() & Display.TextDisplay.FLAG_SEE_THROUGH) != 0 && vertexConsumerProvider instanceof MultiBufferSource.BufferSource immediate) {
            immediate.endBatch();
        }
    }
    *///?}
}
