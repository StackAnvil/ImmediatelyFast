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
package net.raphimc.immediatelyfast.injection.mixins.hud_batching.consumer;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
import net.raphimc.immediatelyfast.feature.batching.BatchingRenderLayers;
import net.raphimc.immediatelyfast.feature.batching.BlendFuncDepthFuncState;
import net.raphimc.immediatelyfast.injection.processors.InjectAboveEverything;
import net.raphimc.immediatelyfast.injection.processors.InjectOnAllReturns;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiGraphics.class, priority = 1500)
public abstract class MixinGuiGraphics {

    @Shadow
    @Final
    private PoseStack pose;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    protected abstract void fillGradient(final VertexConsumer vertexConsumer, final int startX, final int startY, final int endX, final int endY, final int z, final int colorStart, final int colorEnd);

    @Shadow
    @Final
    @Mutable
    private MultiBufferSource.BufferSource bufferSource;

    @Unique
    private MultiBufferSource.BufferSource immediatelyFast$originalVertexConsumers;

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V", at = @At("RETURN"))
    private void storeOriginalVertexConsumers(final CallbackInfo ci) {
        this.immediatelyFast$originalVertexConsumers = this.bufferSource;
    }

    @Inject(method = "fill(Lnet/minecraft/client/renderer/RenderType;IIIIII)V", at = @At("HEAD"), cancellable = true)
    private void fillIntoBuffer(final RenderType layer, int x1, int y1, int x2, int y2, final int z, int color, final CallbackInfo ci) {
        if (BatchingBuffers.FILL_CONSUMER != null) {
            ci.cancel();
            if (x1 < x2) {
                x1 = x1 ^ x2;
                x2 = x1 ^ x2;
                x1 = x1 ^ x2;
            }
            if (y1 < y2) {
                y1 = y1 ^ y2;
                y2 = y1 ^ y2;
                y1 = y1 ^ y2;
            }
            final Matrix4f matrix = this.pose.last().pose();
            color = this.immediatelyFast$mixWithShaderColor(color);

            final VertexConsumer vertexConsumer = BatchingBuffers.FILL_CONSUMER.getBuffer(layer);
            vertexConsumer.vertex(matrix, x1, y2, z).color(color).endVertex();
            vertexConsumer.vertex(matrix, x2, y2, z).color(color).endVertex();
            vertexConsumer.vertex(matrix, x2, y1, z).color(color).endVertex();
            vertexConsumer.vertex(matrix, x1, y1, z).color(color).endVertex();
        }
    }

    @Inject(method = "fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V", at = @At("HEAD"), cancellable = true)
    private void fillIntoBuffer(final RenderType layer, final int startX, final int startY, final int endX, final int endY, int colorStart, int colorEnd, final int z, final CallbackInfo ci) {
        if (BatchingBuffers.FILL_CONSUMER != null) {
            ci.cancel();
            colorStart = this.immediatelyFast$mixWithShaderColor(colorStart);
            colorEnd = this.immediatelyFast$mixWithShaderColor(colorEnd);
            this.fillGradient(BatchingBuffers.FILL_CONSUMER.getBuffer(layer), startX, startY, endX, endY, z, colorStart, colorEnd);
        }
    }

    @Inject(method = "innerBlit(Lnet/minecraft/resources/ResourceLocation;IIIIIFFFF)V", at = @At("HEAD"), cancellable = true)
    private void drawTexturedQuadIntoBuffer(final ResourceLocation texture, final int x1, final int x2, final int y1, final int y2, final int z, final float u1, final float u2, final float v1, final float v2, final CallbackInfo ci) {
        if (BatchingBuffers.TEXTURE_CONSUMER != null) {
            ci.cancel();
            final Matrix4f matrix = this.pose.last().pose();
            final float[] shaderColor = RenderSystem.getShaderColor();
            final int r = Mth.clamp((int) (shaderColor[0] * 255), 0, 255);
            final int g = Mth.clamp((int) (shaderColor[1] * 255), 0, 255);
            final int b = Mth.clamp((int) (shaderColor[2] * 255), 0, 255);
            final int a = Mth.clamp((int) (shaderColor[3] * 255), 0, 255);
            final VertexConsumer vertexConsumer = BatchingBuffers.TEXTURE_CONSUMER.getBuffer(BatchingRenderLayers.COLORED_TEXTURE.apply(this.minecraft.getTextureManager().getTexture(texture).getId(), BlendFuncDepthFuncState.current()));
            vertexConsumer.vertex(matrix, x1, y2, z).uv(u1, v2).color(r, g, b, a).endVertex();
            vertexConsumer.vertex(matrix, x2, y2, z).uv(u2, v2).color(r, g, b, a).endVertex();
            vertexConsumer.vertex(matrix, x2, y1, z).uv(u2, v1).color(r, g, b, a).endVertex();
            vertexConsumer.vertex(matrix, x1, y1, z).uv(u1, v1).color(r, g, b, a).endVertex();
        }
    }

    @Inject(method = "innerBlit(Lnet/minecraft/resources/ResourceLocation;IIIIIFFFFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void drawTexturedQuadIntoBuffer(final ResourceLocation texture, final int x1, final int x2, final int y1, final int y2, final int z, final float u1, final float u2, final float v1, final float v2, final float red, final float green, final float blue, final float alpha, final CallbackInfo ci) {
        if (BatchingBuffers.TEXTURE_CONSUMER != null) {
            ci.cancel();
            final Matrix4f matrix = this.pose.last().pose();
            final int color = this.immediatelyFast$mixWithShaderColor((int) (alpha * 255) << 24 | (int) (red * 255) << 16 | (int) (green * 255) << 8 | (int) (blue * 255));

            RenderSystem.enableBlend();
            final VertexConsumer vertexConsumer = BatchingBuffers.TEXTURE_CONSUMER.getBuffer(BatchingRenderLayers.COLORED_TEXTURE.apply(this.minecraft.getTextureManager().getTexture(texture).getId(), BlendFuncDepthFuncState.current()));
            vertexConsumer.vertex(matrix, x1, y2, z).uv(u1, v2).color(color).endVertex();
            vertexConsumer.vertex(matrix, x2, y2, z).uv(u2, v2).color(color).endVertex();
            vertexConsumer.vertex(matrix, x2, y1, z).uv(u2, v1).color(color).endVertex();
            vertexConsumer.vertex(matrix, x1, y1, z).uv(u1, v1).color(color).endVertex();
            RenderSystem.disableBlend();
        }
    }

    @InjectAboveEverything
    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V", at = @At("HEAD"))
    private void renderItemIntoBufferStart(final CallbackInfo ci) {
        if (BatchingBuffers.ITEM_MODEL_CONSUMER != null) {
            this.bufferSource = (MultiBufferSource.BufferSource) BatchingBuffers.ITEM_MODEL_CONSUMER;
        }
    }

    @InjectOnAllReturns
    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V", at = @At("RETURN"))
    private void renderItemIntoBufferEnd(final CallbackInfo ci) {
        if (BatchingBuffers.ITEM_MODEL_CONSUMER != null) {
            this.bufferSource = this.immediatelyFast$originalVertexConsumers;
        }
    }

    @InjectAboveEverything
    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"))
    private void renderItemOverlayIntoBufferStart(final CallbackInfo ci) {
        BatchingBuffers.beginItemOverlayRendering();
        if (BatchingBuffers.ITEM_OVERLAY_CONSUMER != null) {
            this.bufferSource = (MultiBufferSource.BufferSource) BatchingBuffers.ITEM_OVERLAY_CONSUMER;
        }
    }

    @InjectOnAllReturns
    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("RETURN"))
    private void renderItemOverlayIntoBufferEnd(final CallbackInfo ci) {
        BatchingBuffers.endItemOverlayRendering();
        if (BatchingBuffers.ITEM_OVERLAY_CONSUMER != null) {
            this.bufferSource = this.immediatelyFast$originalVertexConsumers;
        }
    }

    @Inject(method = "flush()V", at = @At("HEAD"), cancellable = true)
    private void dontDrawIfBatching(final CallbackInfo ci) {
        if (this.bufferSource != this.immediatelyFast$originalVertexConsumers) {
            ci.cancel();
        }
    }

    @Inject(method = "applyScissor", at = @At("HEAD"))
    private void forceDrawBatch(final CallbackInfo ci) {
        if (BatchingBuffers.isHudBatching() && BatchingBuffers.hasDataToDraw()) {
            BatchingBuffers.forceDrawBuffers();
        }
    }

    @Unique
    private int immediatelyFast$mixWithShaderColor(final int color) {
        final float[] shaderColor = RenderSystem.getShaderColor();
        final int argb = Mth.clamp((int) (shaderColor[3] * 255), 0, 255) << 24
            | Mth.clamp((int) (shaderColor[0] * 255), 0, 255) << 16
            | Mth.clamp((int) (shaderColor[1] * 255), 0, 255) << 8
            | Mth.clamp((int) (shaderColor[2] * 255), 0, 255);
        return FastColor.ARGB32.multiply(color, argb);
    }

}
