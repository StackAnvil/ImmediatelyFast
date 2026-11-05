//? if >=1.21.4 {
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
package net.raphimc.immediatelyfast.injection.mixins.sign_text_buffering;

import com.mojang.blaze3d.ProjectionType;
//?}
//? if (>=1.21.10 && <=26.2) {
/*import com.mojang.blaze3d.buffers.GpuBufferSlice;
*/
//?}
//? if >=1.21.4 {
import com.mojang.blaze3d.systems.RenderSystem;
//?}
//? if (>=1.21.11 && <=26.2) {
/*import com.mojang.blaze3d.textures.GpuTextureView;
*/
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import com.mojang.blaze3d.vertex.ByteBufferBuilder;
*/
//?}
//? if >=1.21.4 {
import com.mojang.blaze3d.vertex.PoseStack;
//?}
//? if =26.3 {
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import com.mojang.renderpearl.api.commands.RenderPass;
//?}
//? if =1.21.4 {
/*import com.mojang.blaze3d.vertex.VertexConsumer;
*/
//?}
//? if >=1.21.4 {
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
//?}
//? if =1.21.4 {
/*import net.minecraft.client.renderer.FogParameters;
*/
//?}
//? if (>=1.21.4 && <=1.21.11) {
/*import net.minecraft.client.renderer.LightTexture;
*/
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.minecraft.client.renderer.MultiBufferSource;
*/
//?}
//? if >=1.21.10 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
//?}
//? if >=1.21.4 {
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
//?}
//? if >=1.21.10 {
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
//?}
//? if (>=1.21.10 && <=26.1.2) || =26.3 {
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
//?}
//? if >=1.21.10 {
import net.minecraft.client.renderer.fog.FogRenderer;
//?}
//? if =1.21.4 {
/*import net.minecraft.core.BlockPos;
*/
//?}
//? if >=1.21.4 {
import net.minecraft.util.FormattedCharSequence;
//?}
//? if >=26.1.2 {
import net.minecraft.util.LightCoordsUtil;
//?}
//? if >=1.21.4 {
import net.minecraft.world.level.block.entity.SignText;
//?}
//? if (>=1.21.4 && <=1.21.11) {
/*import net.minecraft.world.phys.Vec3;
*/
//?}
//? if >=1.21.4 {
import net.raphimc.immediatelyfast.ImmediatelyFast;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.feature.core.ByteBufferBuilderPool;
*/
//?}
//? if (>=1.21.4 && <=1.21.11) {
/*import net.raphimc.immediatelyfast.feature.sign_text_buffering.NoTextTransformPoseStack;
*/
//?}
//? if >=1.21.4 {
import net.raphimc.immediatelyfast.feature.sign_text_buffering.SignAtlasRenderTarget;
import net.raphimc.immediatelyfast.injection.interfaces.ISignText;
//?}
//? if =1.21.4 {
/*import org.joml.Matrix4f;
*/
//?}
//? if >=1.21.4 {
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
//?}
//? if (>=1.21.4 && <=1.21.11) {
/*import org.spongepowered.asm.mixin.injection.Redirect;
*/
//?}
//? if >=1.21.4 {
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
//?}
//? if =26.3 {
import java.util.Optional;
import java.util.OptionalDouble;
//?}
//? if >=1.21.4 {

@Mixin(AbstractSignRenderer.class)
public abstract class MixinAbstractSignRenderer {

    @Shadow
    @Final
    private Font font;

    @Shadow
//?}
    //? if >=26.2 {
    protected abstract void submitSignText(final SignRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final SignText signText);
    //?}
    //? if =26.1.2 {
/*    protected abstract void submitSignText(SignRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, SignText signText);
*/
    //?}
    //? if =1.21.11 {
/*    protected abstract void submitSignText(SignRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, boolean isFront);
*/
    //?}
    //? if =1.21.10 {
/*    protected abstract void submitSignText(SignRenderState renderState, PoseStack matrices, SubmitNodeCollector queue, boolean front);
*/
    //?}
    //? if =1.21.4 {
/*    protected abstract void renderSignText(BlockPos pos, SignText signText, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int lineHeight, int lineWidth, boolean front);
*/
    //?}
//? if (>=1.21.4 && <=1.21.11) {
/*
    @Shadow
*/
//?}
    //? if =1.21.11 {
/*    protected abstract void translateSignText(PoseStack poseStack, boolean isFront, Vec3 offset);
*/
    //?}
    //? if (>=1.21.4 && <=1.21.10) {
/*    protected abstract void translateSignText(PoseStack matrices, boolean front, Vec3 textOffset);
*/
    //?}
//? if (>=1.21.4 && <=1.21.11) {
/*
    @Shadow
    protected abstract Vec3 getTextOffset();
*/
//?}

    //? if >=1.21.10 {
    @Inject(method = "submitSignText", at = @At("HEAD"), cancellable = true)
    //?}
    //? if >=26.2 {
    private void renderBufferedSignText(final SignRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final SignText signText, final CallbackInfo ci) {
    //?}
    //? if =26.1.2 {
/*    private void renderBufferedSignText(SignRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, SignText signText, CallbackInfo ci) {
*/
    //?}
    //? if =1.21.11 {
/*    private void renderBufferedSignText(SignRenderState renderState, PoseStack poseStack, SubmitNodeCollector nodeCollector, boolean isFront, CallbackInfo ci) {
        if (poseStack instanceof NoTextTransformPoseStack) {
*/
    //?}
    //? if =1.21.10 {
/*    private void renderBufferedSignText(SignRenderState renderState, PoseStack matrices, SubmitNodeCollector queue, boolean front, CallbackInfo ci) {
*/
    //?}
    //? if =1.21.4 {
/*    @Inject(method = "renderSignText", at = @At("HEAD"), cancellable = true)
    private void renderBufferedSignText(BlockPos pos, SignText signText, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int lineHeight, int lineWidth, boolean front, CallbackInfo ci) {
*/
    //?}
        //? if (>=1.21.4 && <=1.21.10) {
/*        if (matrices instanceof NoTextTransformPoseStack) {
*/
        //?}
            //? if (>=1.21.4 && <=1.21.11) {
/*            return;
        }
*/
            //?}
        //? if =1.21.11 {
/*        final SignText signText = isFront ? renderState.frontText : renderState.backText;
*/
        //?}
        //? if =1.21.10 {
/*        final SignText signText = front ? renderState.frontText : renderState.backText;
*/
        //?}
        //? if >=1.21.10 {
        if (!(signText instanceof ISignText mixinSignText)) {
            return;
        }
        if (!mixinSignText.immediatelyFast$shouldCache()) {
        //?}
        //? if =1.21.4 {
/*        final ISignText iSignText = (ISignText) signText;
        if (!iSignText.immediatelyFast$shouldCache()) {
*/
        //?}
            //? if >=1.21.4 {
            return;
        }

        SignAtlasRenderTarget.Slot slot = ImmediatelyFast.signTextCache.slotCache.getIfPresent(signText);
        if (slot == null) {
            //?}
            //? if >=26.2 {
            final int width = this.immediatelyFast$getTextWidth(signText, state.isTextFilteringEnabled, state.maxTextLineWidth);
            final int height = 4 * state.textLineHeight;
            //?}
            //? if (>=1.21.10 && <=26.1.2) {
/*            final int width = this.immediatelyFast$getTextWidth(signText, renderState.isTextFilteringEnabled, renderState.maxTextLineWidth);
            final int height = 4 * renderState.textLineHeight;
*/
            //?}
            //? if =1.21.4 {
/*            final int width = this.immediatelyFast$getTextWidth(signText, lineWidth);
            final int height = 4 * lineHeight;
*/
            //?}
            //? if >=1.21.4 {
            if (width <= 0 || height <= 0) {
            //?}
                //? if >=1.21.10 {
                mixinSignText.immediatelyFast$setShouldCache(false);
                //?}
                //? if =1.21.4 {
/*                iSignText.immediatelyFast$setShouldCache(false);
*/
                //?}
                //? if >=1.21.4 {
                return;
            }
            final int padding = signText.hasGlowingText() ? 2 : 0;

                //?}
            //? if >=1.21.11 {
            slot = ImmediatelyFast.signTextCache.signAtlasRenderTarget.findSlot(width + padding, height + padding);
            //?}
            //? if (>=1.21.4 && <=1.21.10) {
/*            slot = ImmediatelyFast.signTextCache.signAtlasFramebuffer.findSlot(width + padding, height + padding);
*/
            //?}
            //? if >=1.21.4 {
            if (slot != null) {
            //?}
                //? if =1.21.4 {
/*                final Matrix4f projectionMatrix = new Matrix4f().setOrtho(0F, SignAtlasRenderTarget.ATLAS_SIZE, SignAtlasRenderTarget.ATLAS_SIZE, 0F, -1000F, 1000F);
*/
                //?}
                //? if >=1.21.4 {
                RenderSystem.backupProjectionMatrix();
                //?}
                //? if >=1.21.11 {
                RenderSystem.setProjectionMatrix(ImmediatelyFast.signTextCache.signProjectionMatrix, ProjectionType.ORTHOGRAPHIC);
                //?}
                //? if =1.21.10 {
/*                RenderSystem.setProjectionMatrix(ImmediatelyFast.signTextCache.signProjectionMatrixBuffer, ProjectionType.ORTHOGRAPHIC);
*/
                //?}
                //? if =1.21.4 {
/*                RenderSystem.setProjectionMatrix(projectionMatrix, ProjectionType.ORTHOGRAPHIC);
*/
                //?}
                //? if >=1.21.4 {
                final Matrix4fStack modelViewMatrix = RenderSystem.getModelViewStack();
                //?}
                //? if >=1.21.11 {
                modelViewMatrix.pushMatrix().identity();
                //?}
                //? if (>=1.21.4 && <=1.21.10) {
/*                modelViewMatrix.pushMatrix();
                modelViewMatrix.identity();
*/
                //?}
                //? if >=1.21.10 {
                final GpuBufferSlice fog = RenderSystem.getShaderFog();
                RenderSystem.setShaderFog(Minecraft.getInstance().gameRenderer.fogRenderer.getBuffer(FogRenderer.FogMode.NONE));
                //?}
                //? if (>=1.21.11 && <=26.2) {
/*                final GpuTextureView previousColorTextureOverride = RenderSystem.outputColorTextureOverride;
                final GpuTextureView previousDepthTextureOverride = RenderSystem.outputDepthTextureOverride;
                RenderSystem.outputColorTextureOverride = ImmediatelyFast.signTextCache.signAtlasRenderTarget.getColorTextureView();
                RenderSystem.outputDepthTextureOverride = ImmediatelyFast.signTextCache.signAtlasRenderTarget.getDepthTextureView();
*/
                //?}
                //? if (>=1.21.11 && <=26.1.2) {
/*                final ByteBufferBuilder bufferBuilder = ByteBufferBuilderPool.borrowBufferBuilder();
*/
                //?}
                //? if >=26.1.2 {
                mixinSignText.immediatelyFast$setShouldCache(false);
                //?}
                //? if =1.21.4 {
/*                final FogParameters fog = RenderSystem.getShaderFog();
                RenderSystem.setShaderFog(FogParameters.NO_FOG);
*/
                //?}
                //? if (>=1.21.4 && <=1.21.10) {
/*                final ByteBufferBuilder bufferAllocator = ByteBufferBuilderPool.borrowBufferAllocator();
*/
                //?}
                //? if =1.21.10 {
/*                final int previousFbo = ImmediatelyFast.signTextCache.signAtlasFramebuffer.bind(true);
*/
                //?}
                //? if =1.21.4 {
/*                ImmediatelyFast.signTextCache.signAtlasFramebuffer.bindWrite(true);
*/
                //?}
                //? if (>=1.21.4 && <=1.21.10) {
/*                ImmediatelyFast.signTextCache.lockFramebuffer = true;
*/
                //?}
                //? if =1.21.10 {
/*                ImmediatelyFast.signTextCache.lockViewport = true;
*/
                //?}

                //? if >=1.21.4 {
                try {
                //?}
                    //? if (>=1.21.11 && <=26.1.2) {
/*                    final MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(bufferBuilder);
                    final SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();
*/
                    //?}
                    //? if =26.1.2 {
/*                    final FeatureRenderDispatcher renderDispatcher = new FeatureRenderDispatcher(submitNodeStorage, Minecraft.getInstance().getModelManager(), bufferSource, Minecraft.getInstance().getAtlasManager(), null, null, this.font, Minecraft.getInstance().gameRenderer.getGameRenderState());
*/
                    //?}
                    //? if >=26.1.2 {
                    final PoseStack textPoseStack = new PoseStack();
                    //?}
                    //? if =1.21.11 {
/*                    final FeatureRenderDispatcher renderDispatcher = new FeatureRenderDispatcher(submitNodeStorage, Minecraft.getInstance().getBlockRenderer(), bufferSource, Minecraft.getInstance().getAtlasManager(), null, null, this.font);
                    final PoseStack textPoseStack = new NoTextTransformPoseStack();
*/
                    //?}
                    //? if >=1.21.11 {
                    textPoseStack.translate(slot.x, slot.y, 0F);
                    textPoseStack.translate(slot.width / 2F, slot.height / 2F, 0F);
                    //?}
                    //? if >=26.2 {
                    state.drawOutline = true; // Always render outline, regardless of distance to sign
                    final SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();
                    this.submitSignText(state, textPoseStack, submitNodeStorage, signText);
                    //?}
                    //? if =26.3 {
                    try (
                        final FeatureRenderDispatcher.PreparedFrame frame = Minecraft.getInstance().gameRenderer.featureRenderDispatcher().prepareFrame(submitNodeStorage);
                        final RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                            () -> "Picture in picture",
                            ImmediatelyFast.signTextCache.signAtlasRenderTarget.getColorTextureView(), Optional.empty(),
                            ImmediatelyFast.signTextCache.signAtlasRenderTarget.getDepthTextureView(), OptionalDouble.empty()
                        )
                    ) {
                        RenderSystem.bindDefaultUniforms(renderPass);
                        FeatureRenderDispatcher.renderAllFeatures(renderPass, frame);
                    }
                    //?}
                    //? if =26.2 {
/*                    Minecraft.getInstance().gameRenderer.featureRenderDispatcher().renderAllFeatures(submitNodeStorage);
*/
                    //?}
                    //? if (>=1.21.4 && <=1.21.10) {
/*                    final MultiBufferSource.BufferSource immediate = MultiBufferSource.immediate(bufferAllocator);
*/
                    //?}
                    //? if =1.21.10 {
/*                    final SubmitNodeStorage orderedRenderCommandQueue = new SubmitNodeStorage();
                    final FeatureRenderDispatcher renderDispatcher = new FeatureRenderDispatcher(orderedRenderCommandQueue, Minecraft.getInstance().getBlockRenderer(), immediate, Minecraft.getInstance().getAtlasManager(), null, null, this.font);
*/
                    //?}
                    //? if (>=1.21.4 && <=1.21.10) {
/*                    final PoseStack matrixStack = new NoTextTransformPoseStack();
                    matrixStack.translate(slot.x, slot.y, 0F);
                    matrixStack.translate(slot.width / 2F, slot.height / 2F, 0F);
*/
                    //?}
                    //? if (>=1.21.10 && <=26.1.2) {
/*                    renderState.drawOutline = true; // Always render outline, regardless of distance to sign
*/
                    //?}
                    //? if =26.1.2 {
/*                    this.submitSignText(renderState, textPoseStack, submitNodeStorage, signText);
*/
                    //?}
                    //? if =1.21.11 {
/*                    this.submitSignText(renderState, textPoseStack, submitNodeStorage, isFront);
*/
                    //?}
                    //? if =1.21.10 {
/*                    this.submitSignText(renderState, matrixStack, orderedRenderCommandQueue, front);
*/
                    //?}
                    //? if (>=1.21.10 && <=26.1.2) {
/*                    renderDispatcher.renderAllFeatures();
*/
                    //?}
                    //? if (>=1.21.11 && <=26.1.2) {
/*                    bufferSource.endBatch();
*/
                    //?}
                    //? if =1.21.4 {
/*                    this.renderSignText(Minecraft.getInstance().cameraEntity.blockPosition(), signText, matrixStack, immediate, light, lineHeight, lineWidth, front);
*/
                    //?}
                    //? if (>=1.21.4 && <=1.21.10) {
/*                    immediate.endBatch();
*/
                    //?}
                    //? if (>=1.21.10 && <=26.1.2) {
/*                    renderDispatcher.close();
*/
                    //?}
                //? if >=1.21.4 {
                } finally {
                //?}
                    //? if >=26.1.2 {
                    mixinSignText.immediatelyFast$setShouldCache(true);
                    //?}
                    //? if (>=1.21.11 && <=26.1.2) {
/*                    ByteBufferBuilderPool.returnBufferBuilderSafe(bufferBuilder);
*/
                    //?}
                    //? if (>=1.21.11 && <=26.2) {
/*                    RenderSystem.outputColorTextureOverride = previousColorTextureOverride;
                    RenderSystem.outputDepthTextureOverride = previousDepthTextureOverride;
*/
                    //?}
                    //? if =1.21.10 {
/*                    ImmediatelyFast.signTextCache.lockViewport = false;
*/
                    //?}
                    //? if (>=1.21.4 && <=1.21.10) {
/*                    ImmediatelyFast.signTextCache.lockFramebuffer = false;
*/
                    //?}
                    //? if =1.21.10 {
/*                    ImmediatelyFast.signTextCache.signAtlasFramebuffer.unbind(previousFbo);
*/
                    //?}
                    //? if =1.21.4 {
/*                    Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
*/
                    //?}
                    //? if (>=1.21.4 && <=1.21.10) {
/*                    ByteBufferBuilderPool.returnBufferAllocatorSafe(bufferAllocator);
*/
                    //?}
                    //? if >=1.21.4 {
                    RenderSystem.setShaderFog(fog);
                    modelViewMatrix.popMatrix();
                    RenderSystem.restoreProjectionMatrix();
                }
                    //?}

                //? if >=1.21.4 {
                ImmediatelyFast.signTextCache.slotCache.put(signText, slot);
            } else {
                ImmediatelyFast.LOGGER.warn("Failed to find a free slot for sign text (" + ImmediatelyFast.signTextCache.slotCache.size() + " sign texts in atlas). Falling back to immediate mode rendering.");
                //?}
                //? if >=1.21.10 {
                mixinSignText.immediatelyFast$setShouldCache(false);
                //?}
                //? if =1.21.4 {
/*                iSignText.immediatelyFast$setShouldCache(false);
*/
                //?}
                //? if >=1.21.4 {
                return;
            }
        }

                //?}
        //? if >=26.2 {
        final float u0 = ((float) slot.x) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float u1 = ((float) slot.x + (float) slot.width) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float v0 = 1F - ((float) slot.y) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float v1 = 1F - ((float) slot.y + (float) slot.height) / SignAtlasRenderTarget.ATLAS_SIZE;
        final int lightCoords = signText.hasGlowingText() ? LightCoordsUtil.FULL_BRIGHT : state.lightCoords;
        //?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        final float u1 = ((float) slot.x) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float u2 = ((float) slot.x + (float) slot.width) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float v1 = 1F - ((float) slot.y) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float v2 = 1F - ((float) slot.y + (float) slot.height) / SignAtlasRenderTarget.ATLAS_SIZE;
*/
        //?}
        //? if =26.1.2 {
/*        final int light = signText.hasGlowingText() ? LightCoordsUtil.FULL_BRIGHT : renderState.lightCoords;
*/
        //?}
        //? if (>=1.21.4 && <=1.21.10) {
/*        float u1 = ((float) slot.x) / SignAtlasRenderTarget.ATLAS_SIZE;
        float u2 = ((float) slot.x + (float) slot.width) / SignAtlasRenderTarget.ATLAS_SIZE;
        float v1 = 1F - ((float) slot.y) / SignAtlasRenderTarget.ATLAS_SIZE;
        float v2 = 1F - ((float) slot.y + (float) slot.height) / SignAtlasRenderTarget.ATLAS_SIZE;
*/
        //?}
        //? if (>=1.21.10 && <=1.21.11) {
/*        final int light = signText.hasGlowingText() ? LightTexture.FULL_BRIGHT : renderState.lightCoords;
*/
        //?}

        //? if >=1.21.11 {
        poseStack.pushPose();
        //?}
        //? if =1.21.11 {
/*        this.translateSignText(poseStack, isFront, this.getTextOffset());
*/
        //?}
        //? if >=1.21.11 {
        poseStack.translate(-slot.width / 2F, -slot.height / 2F, 0F);
        //?}
        //? if =1.21.4 {
/*        if (signText.hasGlowingText()) {
            light = LightTexture.FULL_BRIGHT;
        }

*/
        //?}
        //? if (>=1.21.4 && <=1.21.10) {
/*        matrices.pushPose();
        this.translateSignText(matrices, front, this.getTextOffset());
        matrices.translate(-slot.width / 2F, -slot.height / 2F, 0F);
*/
        //?}
        //? if >=1.21.10 {
        final SignAtlasRenderTarget.Slot finalSlot = slot;
        //?}
        //? if >=26.2 {
        submitNodeCollector.submitCustomGeometry(poseStack, ImmediatelyFast.signTextCache.renderType, (entry, vertexConsumer) -> {
            vertexConsumer.addVertex(entry, 0F, finalSlot.height, 0F).setColor(255, 255, 255, 255).setUv(u0, v1).setLight(lightCoords);
            vertexConsumer.addVertex(entry, finalSlot.width, finalSlot.height, 0F).setColor(255, 255, 255, 255).setUv(u1, v1).setLight(lightCoords);
            vertexConsumer.addVertex(entry, finalSlot.width, 0F, 0F).setColor(255, 255, 255, 255).setUv(u1, v0).setLight(lightCoords);
            vertexConsumer.addVertex(entry, 0F, 0F, 0F).setColor(255, 255, 255, 255).setUv(u0, v0).setLight(lightCoords);
        //?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        nodeCollector.submitCustomGeometry(poseStack, ImmediatelyFast.signTextCache.renderType, (entry, vertexConsumer) -> {
*/
        //?}
        //? if =1.21.10 {
/*        queue.submitCustomGeometry(matrices, ImmediatelyFast.signTextCache.renderLayer, (entry, vertexConsumer) -> {
*/
        //?}
            //? if (>=1.21.10 && <=26.1.2) {
/*            vertexConsumer.addVertex(entry, 0F, finalSlot.height, 0F).setColor(255, 255, 255, 255).setUv(u1, v2).setLight(light);
            vertexConsumer.addVertex(entry, finalSlot.width, finalSlot.height, 0F).setColor(255, 255, 255, 255).setUv(u2, v2).setLight(light);
            vertexConsumer.addVertex(entry, finalSlot.width, 0F, 0F).setColor(255, 255, 255, 255).setUv(u2, v1).setLight(light);
            vertexConsumer.addVertex(entry, 0F, 0F, 0F).setColor(255, 255, 255, 255).setUv(u1, v1).setLight(light);
*/
            //?}
        //? if >=1.21.10 {
        });
        //?}
        //? if >=1.21.11 {
        poseStack.popPose();
        //?}
        //? if =1.21.4 {
/*        final Matrix4f matrix4f = matrices.last().pose();
        final VertexConsumer vertexConsumer = vertexConsumers.getBuffer(ImmediatelyFast.signTextCache.renderLayer);
        vertexConsumer.addVertex(matrix4f, 0F, slot.height, 0F).setColor(255, 255, 255, 255).setUv(u1, v2).setLight(light);
        vertexConsumer.addVertex(matrix4f, slot.width, slot.height, 0F).setColor(255, 255, 255, 255).setUv(u2, v2).setLight(light);
        vertexConsumer.addVertex(matrix4f, slot.width, 0F, 0F).setColor(255, 255, 255, 255).setUv(u2, v1).setLight(light);
        vertexConsumer.addVertex(matrix4f, 0F, 0F, 0F).setColor(255, 255, 255, 255).setUv(u1, v1).setLight(light);
*/
        //?}
        //? if (>=1.21.4 && <=1.21.10) {
/*        matrices.popPose();
*/
        //?}
//? if >=1.21.4 {

        ci.cancel();
//?}
    //? if (>=1.21.4 && <=1.21.11) {
/*    }

*/
    //?}
    //? if (>=1.21.10 && <=1.21.11) {
/*    @Redirect(method = "submitSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/AbstractSignRenderer;translateSignText(Lcom/mojang/blaze3d/vertex/PoseStack;ZLnet/minecraft/world/phys/Vec3;)V"))
*/
    //?}
    //? if =1.21.11 {
/*    private void dontApplyTextTransform(AbstractSignRenderer instance, PoseStack poseStack, boolean isFront, Vec3 offset) {
        if (poseStack instanceof NoTextTransformPoseStack) {
*/
    //?}
    //? if =1.21.4 {
/*    @Redirect(method = "renderSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/AbstractSignRenderer;translateSignText(Lcom/mojang/blaze3d/vertex/PoseStack;ZLnet/minecraft/world/phys/Vec3;)V"))
*/
    //?}
    //? if (>=1.21.4 && <=1.21.10) {
/*    private void dontApplyTextTransform(AbstractSignRenderer instance, PoseStack matrices, boolean front, Vec3 textOffset) {
        if (matrices instanceof NoTextTransformPoseStack) {
*/
    //?}
            //? if (>=1.21.4 && <=1.21.11) {
/*            return;
        }

*/
            //?}
        //? if =1.21.11 {
/*        this.translateSignText(poseStack, isFront, offset);
*/
        //?}
        //? if (>=1.21.4 && <=1.21.10) {
/*        this.translateSignText(matrices, front, textOffset);
*/
        //?}
    //? if >=1.21.4 {
    }

    @Unique
    //?}
    //? if >=1.21.10 {
    private int immediatelyFast$getTextWidth(final SignText signText, final boolean filterText, final int maxLineWidth) {
    //?}
        //? if >=1.21.11 {
        final FormattedCharSequence[] renderMessages = signText.getRenderMessages(filterText, textComponent -> {
            final List<FormattedCharSequence> list = this.font.split(textComponent, maxLineWidth);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.getFirst();
        //?}
        //? if =1.21.10 {
/*        final FormattedCharSequence[] orderedTexts = signText.getRenderMessages(filterText, text -> {
            final List<FormattedCharSequence> list = this.font.split(text, maxLineWidth);
*/
        //?}
    //? if =1.21.4 {
/*    private int immediatelyFast$getTextWidth(final SignText signText, final int lineWidth) {
        final FormattedCharSequence[] orderedTexts = signText.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), text -> {
            final List<FormattedCharSequence> list = this.font.split(text, lineWidth);
*/
    //?}
            //? if (>=1.21.4 && <=1.21.10) {
/*            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
*/
            //?}
        //? if >=1.21.4 {
        });

        int width = 0;
        //?}
        //? if >=1.21.11 {
        for (FormattedCharSequence line : renderMessages) {
            width = Math.max(width, this.font.width(line));
        //?}
        //? if (>=1.21.4 && <=1.21.10) {
/*        for (FormattedCharSequence orderedText : orderedTexts) {
            width = Math.max(width, this.font.width(orderedText));
*/
        //?}
        //? if >=1.21.4 {
        }
        if (width % 2 != 0) {
            width++; // Fixes issue which squishes the text when the width is odd (Test text: "hhhl")
        }

        return width;
    }

}
        //?}
