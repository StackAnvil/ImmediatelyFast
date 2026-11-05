//? if <=1.21.1 {
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
package net.raphimc.immediatelyfast.injection.mixins.hud_batching.consumer;


*///?}
//? if =1.21.1 {
/*import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;

*///?}
//? if <=1.21.1 {
/*import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;

*///?}
//? if =1.20.4 {
/*import net.minecraft.client.renderer.RenderType;
*/
//?}
//? if <=1.21.1 {
/*import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;

*///?}
//? if =1.20.4 {
/*import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
*/
//?}
//? if <=1.21.1 {
/*import net.raphimc.immediatelyfast.feature.batching.BatchingRenderLayers;
import net.raphimc.immediatelyfast.feature.batching.BlendFuncDepthFuncState;

*///?}
//? if =1.21.1 {
/*import net.raphimc.immediatelyfast.feature.batching.HudBatchingBufferSource;

*///?}
//? if =1.20.4 {
/*import net.raphimc.immediatelyfast.injection.processors.InjectAboveEverything;
import net.raphimc.immediatelyfast.injection.processors.InjectOnAllReturns;
*/
//?}
//? if <=1.21.1 {
/*import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

*///?}
//? if =1.21.1 {
/*import org.spongepowered.asm.mixin.injection.ModifyVariable;

*///?}
//? if <=1.21.1 {
/*import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = GuiGraphics.class, priority = 1500)
public abstract class MixinGuiGraphics {

    @Shadow
    @Final
    private PoseStack pose;

    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow

*///?}
    //? if =1.20.4 {
/*    protected abstract void fillGradient(VertexConsumer vertexConsumer, int startX, int startY, int endX, int endY, int z, int colorStart, int colorEnd);

    @Shadow
    @Final
*/
    //?}
    //? if <=1.21.1 {
    /*@Mutable

    *///?}
    //? if =1.21.1 {
    /*public MultiBufferSource.BufferSource bufferSource;

    *///?}
    //? if =1.20.4 {
/*    private MultiBufferSource.BufferSource bufferSource;
*/
    //?}

    //? if =1.21.1 {
    /*@ModifyVariable(method = "fill(Lnet/minecraft/client/renderer/RenderType;IIIIII)V", at = @At("HEAD"), index = 6, argsOnly = true)
    private int mixColor(int color) {
        if (this.bufferSource instanceof HudBatchingBufferSource) {
            return this.immediatelyFast$mixWithShaderColor(color);
        }
        return color;

    *///?}
    //? if =1.20.4 {
/*    @Unique
    private MultiBufferSource.BufferSource immediatelyFast$originalVertexConsumers;

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;)V", at = @At("RETURN"))
    private void storeOriginalVertexConsumers(CallbackInfo ci) {
        this.immediatelyFast$originalVertexConsumers = this.bufferSource;
*/
    //?}
    //? if <=1.21.1 {
    /*}


    *///?}
    //? if =1.21.1 {
    /*@ModifyVariable(method = "fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V", at = @At("HEAD"), index = 5, argsOnly = true)
    private int mixStartColor(int color) {
        if (this.bufferSource instanceof HudBatchingBufferSource) {
            return this.immediatelyFast$mixWithShaderColor(color);

    *///?}
    //? if =1.20.4 {
/*    @Inject(method = "fill(Lnet/minecraft/client/renderer/RenderType;IIIIII)V", at = @At("HEAD"), cancellable = true)
    private void fillIntoBuffer(RenderType layer, int x1, int y1, int x2, int y2, int z, int color, CallbackInfo ci) {
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
*/
    //?}
        //? if <=1.21.1 {
        /*}

        *///?}
        //? if =1.21.1 {
        /*return color;

        *///?}
    //? if <=1.21.1 {
    /*}


    *///?}
    //? if =1.21.1 {
    /*@ModifyVariable(method = "fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V", at = @At("HEAD"), index = 6, argsOnly = true)
    private int mixEndColor(int color) {
        if (this.bufferSource instanceof HudBatchingBufferSource) {
            return this.immediatelyFast$mixWithShaderColor(color);

    *///?}
    //? if =1.20.4 {
/*    @Inject(method = "fillGradient(Lnet/minecraft/client/renderer/RenderType;IIIIIII)V", at = @At("HEAD"), cancellable = true)
    private void fillIntoBuffer(RenderType layer, int startX, int startY, int endX, int endY, int colorStart, int colorEnd, int z, CallbackInfo ci) {
        if (BatchingBuffers.FILL_CONSUMER != null) {
            ci.cancel();
            colorStart = this.immediatelyFast$mixWithShaderColor(colorStart);
            colorEnd = this.immediatelyFast$mixWithShaderColor(colorEnd);
            this.fillGradient(BatchingBuffers.FILL_CONSUMER.getBuffer(layer), startX, startY, endX, endY, z, colorStart, colorEnd);
*/
    //?}
        //? if <=1.21.1 {
        /*}

        *///?}
        //? if =1.21.1 {
        /*return color;

        *///?}
    //? if <=1.21.1 {
    /*}

    @Inject(method = "innerBlit(Lnet/minecraft/resources/ResourceLocation;IIIIIFFFF)V", at = @At("HEAD"), cancellable = true)
    private void drawTexturedQuadIntoBuffer(ResourceLocation texture, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2, CallbackInfo ci) {

    *///?}
        //? if =1.21.1 {
        /*if (this.bufferSource instanceof HudBatchingBufferSource) {

        *///?}
        //? if =1.20.4 {
/*        if (BatchingBuffers.TEXTURE_CONSUMER != null) {
*/
        //?}
            //? if <=1.21.1 {
            /*ci.cancel();
            final Matrix4f matrix = this.pose.last().pose();
            final float[] shaderColor = RenderSystem.getShaderColor();
            final int r = Mth.clamp((int) (shaderColor[0] * 255), 0, 255);
            final int g = Mth.clamp((int) (shaderColor[1] * 255), 0, 255);
            final int b = Mth.clamp((int) (shaderColor[2] * 255), 0, 255);
            final int a = Mth.clamp((int) (shaderColor[3] * 255), 0, 255);

            *///?}
            //? if =1.21.1 {
            /*if (r == 255 && g == 255 && b == 255 && a == 255) {
                final VertexConsumer vertexConsumer = this.bufferSource.getBuffer(BatchingRenderLayers.TEXTURE.apply(this.minecraft.getTextureManager().getTexture(texture).getId(), BlendFuncDepthFuncState.current()));
                vertexConsumer.addVertex(matrix, x1, y2, z).setUv(u1, v2);
                vertexConsumer.addVertex(matrix, x2, y2, z).setUv(u2, v2);
                vertexConsumer.addVertex(matrix, x2, y1, z).setUv(u2, v1);
                vertexConsumer.addVertex(matrix, x1, y1, z).setUv(u1, v1);
            } else {
                final VertexConsumer vertexConsumer = this.bufferSource.getBuffer(BatchingRenderLayers.COLORED_TEXTURE.apply(this.minecraft.getTextureManager().getTexture(texture).getId(), BlendFuncDepthFuncState.current()));
                vertexConsumer.addVertex(matrix, x1, y2, z).setUv(u1, v2).setColor(r, g, b, a);
                vertexConsumer.addVertex(matrix, x2, y2, z).setUv(u2, v2).setColor(r, g, b, a);
                vertexConsumer.addVertex(matrix, x2, y1, z).setUv(u2, v1).setColor(r, g, b, a);
                vertexConsumer.addVertex(matrix, x1, y1, z).setUv(u1, v1).setColor(r, g, b, a);
            }

            *///?}
            //? if =1.20.4 {
/*            final VertexConsumer vertexConsumer = BatchingBuffers.TEXTURE_CONSUMER.getBuffer(BatchingRenderLayers.COLORED_TEXTURE.apply(this.minecraft.getTextureManager().getTexture(texture).getId(), BlendFuncDepthFuncState.current()));
            vertexConsumer.vertex(matrix, x1, y2, z).uv(u1, v2).color(r, g, b, a).endVertex();
            vertexConsumer.vertex(matrix, x2, y2, z).uv(u2, v2).color(r, g, b, a).endVertex();
            vertexConsumer.vertex(matrix, x2, y1, z).uv(u2, v1).color(r, g, b, a).endVertex();
            vertexConsumer.vertex(matrix, x1, y1, z).uv(u1, v1).color(r, g, b, a).endVertex();
*/
            //?}
        //? if <=1.21.1 {
        /*}
    }

    @Inject(method = "innerBlit(Lnet/minecraft/resources/ResourceLocation;IIIIIFFFFFFFF)V", at = @At("HEAD"), cancellable = true)
    private void drawTexturedQuadIntoBuffer(ResourceLocation texture, int x1, int x2, int y1, int y2, int z, float u1, float u2, float v1, float v2, float red, float green, float blue, float alpha, CallbackInfo ci) {

        *///?}
        //? if =1.21.1 {
        /*if (this.bufferSource instanceof HudBatchingBufferSource) {

        *///?}
        //? if =1.20.4 {
/*        if (BatchingBuffers.TEXTURE_CONSUMER != null) {
*/
        //?}
            //? if <=1.21.1 {
            /*ci.cancel();
            final Matrix4f matrix = this.pose.last().pose();
            final int color = this.immediatelyFast$mixWithShaderColor((int) (alpha * 255) << 24 | (int) (red * 255) << 16 | (int) (green * 255) << 8 | (int) (blue * 255));

            RenderSystem.enableBlend();

            *///?}
            //? if =1.21.1 {
            /*final VertexConsumer vertexConsumer = this.bufferSource.getBuffer(BatchingRenderLayers.COLORED_TEXTURE.apply(this.minecraft.getTextureManager().getTexture(texture).getId(), BlendFuncDepthFuncState.current()));
            vertexConsumer.addVertex(matrix, x1, y2, z).setUv(u1, v2).setColor(color);
            vertexConsumer.addVertex(matrix, x2, y2, z).setUv(u2, v2).setColor(color);
            vertexConsumer.addVertex(matrix, x2, y1, z).setUv(u2, v1).setColor(color);
            vertexConsumer.addVertex(matrix, x1, y1, z).setUv(u1, v1).setColor(color);

            *///?}
            //? if =1.20.4 {
/*            final VertexConsumer vertexConsumer = BatchingBuffers.TEXTURE_CONSUMER.getBuffer(BatchingRenderLayers.COLORED_TEXTURE.apply(this.minecraft.getTextureManager().getTexture(texture).getId(), BlendFuncDepthFuncState.current()));
            vertexConsumer.vertex(matrix, x1, y2, z).uv(u1, v2).color(color).endVertex();
            vertexConsumer.vertex(matrix, x2, y2, z).uv(u2, v2).color(color).endVertex();
            vertexConsumer.vertex(matrix, x2, y1, z).uv(u2, v1).color(color).endVertex();
            vertexConsumer.vertex(matrix, x1, y1, z).uv(u1, v1).color(color).endVertex();
*/
            //?}
            //? if <=1.21.1 {
            /*RenderSystem.disableBlend();
        }
    }


            *///?}
    //? if =1.21.1 {
    /*@Inject(method = "flushIfUnmanaged", at = @At("HEAD"), cancellable = true)
    private void dontTryDrawIfBatching(CallbackInfo ci) {
        if (this.bufferSource instanceof HudBatchingBufferSource) {

    *///?}
    //? if =1.20.4 {
/*    @InjectAboveEverything
    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V", at = @At("HEAD"))
    private void renderItemIntoBufferStart(CallbackInfo ci) {
        if (BatchingBuffers.ITEM_MODEL_CONSUMER != null) {
            this.bufferSource = (MultiBufferSource.BufferSource) BatchingBuffers.ITEM_MODEL_CONSUMER;
        }
    }

    @InjectOnAllReturns
    @Inject(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V", at = @At("RETURN"))
    private void renderItemIntoBufferEnd(CallbackInfo ci) {
        if (BatchingBuffers.ITEM_MODEL_CONSUMER != null) {
            this.bufferSource = this.immediatelyFast$originalVertexConsumers;
        }
    }

    @InjectAboveEverything
    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("HEAD"))
    private void renderItemOverlayIntoBufferStart(CallbackInfo ci) {
        BatchingBuffers.beginItemOverlayRendering();
        if (BatchingBuffers.ITEM_OVERLAY_CONSUMER != null) {
            this.bufferSource = (MultiBufferSource.BufferSource) BatchingBuffers.ITEM_OVERLAY_CONSUMER;
        }
    }

    @InjectOnAllReturns
    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At("RETURN"))
    private void renderItemOverlayIntoBufferEnd(CallbackInfo ci) {
        BatchingBuffers.endItemOverlayRendering();
        if (BatchingBuffers.ITEM_OVERLAY_CONSUMER != null) {
            this.bufferSource = this.immediatelyFast$originalVertexConsumers;
        }
    }

    @Inject(method = "flush()V", at = @At("HEAD"), cancellable = true)
    private void dontDrawIfBatching(CallbackInfo ci) {
        if (this.bufferSource != this.immediatelyFast$originalVertexConsumers) {
*/
    //?}
            //? if <=1.21.1 {
            /*ci.cancel();
        }
    }


            *///?}
    //? if =1.21.1 {
    /*@WrapWithCondition(method = "renderItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;IIII)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;flush()V"))
    private boolean dontDrawIfBatching(GuiGraphics instance) {
        return !(instance.bufferSource instanceof HudBatchingBufferSource);

    *///?}
    //? if =1.20.4 {
/*    @Inject(method = "applyScissor", at = @At("HEAD"))
    private void forceDrawBatch(CallbackInfo ci) {
        if (BatchingBuffers.isHudBatching() && BatchingBuffers.hasDataToDraw()) {
            BatchingBuffers.forceDrawBuffers();
        }
*/
    //?}
    //? if <=1.21.1 {
    /*}

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

    *///?}
