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

//? if >=1.21.4 {
package net.raphimc.immediatelyfast.injection.mixins.sign_text_buffering;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.blockentity.AbstractSignRenderer;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.level.block.entity.SignText;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.feature.sign_text_buffering.SignAtlasRenderTarget;
import net.raphimc.immediatelyfast.injection.interfaces.ISignText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if >=1.21.10 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
//?}
//? if (>=1.21.4 && <=1.21.11) {
/*import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.phys.Vec3;
import net.raphimc.immediatelyfast.feature.sign_text_buffering.NoTextTransformPoseStack;
import org.spongepowered.asm.mixin.injection.Redirect;
*///?}
//? if >=26.1.2 {
import net.minecraft.util.LightCoordsUtil;
//?}
//? if =1.21.4 {
/*import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.BlockPos;
import org.joml.Matrix4f;
*///?}

@Mixin(AbstractSignRenderer.class)
public abstract class MixinAbstractSignRenderer {
    @Shadow
    @Final
    private Font font;

    //? if >=26.1.2 {
    @Shadow
    protected abstract void submitSignText(final SignRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final SignText signText);
    //?}
    //? if (>=1.21.10 && <=1.21.11) {
    /*@Shadow
    protected abstract void submitSignText(final SignRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final boolean isFront);
    *///?}

    //? if =1.21.4 {
    /*@Shadow
    protected abstract void renderSignText(final BlockPos pos, final SignText signText, final PoseStack poseStack, final MultiBufferSource bufferSource, final int lightCoords, final int textLineHeight, final int maxLineWidth, final boolean isFront);
    *///?}

    //? if (>=1.21.4 && <=1.21.11) {
    /*@Shadow
    protected abstract void translateSignText(final PoseStack poseStack, final boolean isFront, final Vec3 offset);

    @Shadow
    protected abstract Vec3 getTextOffset();
    *///?}

    //? if >=1.21.10 {
    //? if >=26.1.2 {
    @Inject(method = "submitSignText", at = @At("HEAD"), cancellable = true)
    private void renderBufferedSignText(final SignRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final SignText signText, final CallbackInfo ci) {
    //?}
    //? if (>=1.21.10 && <=1.21.11) {
    /*@Inject(method = "submitSignText", at = @At("HEAD"), cancellable = true)
    private void renderBufferedSignText(final SignRenderState state, final PoseStack poseStack, final SubmitNodeCollector submitNodeCollector, final boolean isFront, final CallbackInfo ci) {
    *///?}
        //? if (>=1.21.10 && <=1.21.11) {
        /*if (poseStack instanceof NoTextTransformPoseStack) {
            return;
        }
        final SignText signText = isFront ? state.frontText : state.backText;
        *///?}
        if (!(signText instanceof ISignText mixinSignText)) {
            return;
        }
        if (!mixinSignText.immediatelyFast$shouldCache()) {
            return;
        }
        SignAtlasRenderTarget.Slot slot = ImmediatelyFast.signTextCache.slotCache.getIfPresent(signText);
        if (slot == null) {
            final int width = this.immediatelyFast$getTextWidth(signText, state.isTextFilteringEnabled, state.maxTextLineWidth);
            final int height = 4 * state.textLineHeight;
            if (width <= 0 || height <= 0) {
                mixinSignText.immediatelyFast$setShouldCache(false);
                return;
            }
            final int padding = signText.hasGlowingText() ? 2 : 0;
            slot = ImmediatelyFast.signTextCache.signAtlasRenderTarget.findSlot(width + padding, height + padding);
            if (slot != null) {
                //? if >=26.1.2 {
                ImmediatelyFast.signTextCache.signAtlasRenderTarget.renderText(slot, this.font, mixinSignText, state,
                        (textPoseStack, targetCollector) -> this.submitSignText(state, textPoseStack, targetCollector, signText));
                //?}
                //? if (>=1.21.10 && <=1.21.11) {
                /*ImmediatelyFast.signTextCache.signAtlasRenderTarget.renderText(slot, this.font, mixinSignText, state,
                        (textPoseStack, targetCollector) -> this.submitSignText(state, textPoseStack, targetCollector, isFront));
                *///?}
                ImmediatelyFast.signTextCache.slotCache.put(signText, slot);
            } else {
                ImmediatelyFast.LOGGER.warn("Failed to find a free slot for sign text (" + ImmediatelyFast.signTextCache.slotCache.size() + " sign texts in atlas). Falling back to immediate mode rendering.");
                mixinSignText.immediatelyFast$setShouldCache(false);
                return;
            }
        }
        final float u0 = ((float) slot.x) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float u1 = ((float) slot.x + (float) slot.width) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float v0 = 1F - ((float) slot.y) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float v1 = 1F - ((float) slot.y + (float) slot.height) / SignAtlasRenderTarget.ATLAS_SIZE;
        //? if >=26.1.2 {
        final int lightCoords = signText.hasGlowingText() ? LightCoordsUtil.FULL_BRIGHT : state.lightCoords;
        //?}
        //? if (>=1.21.10 && <=1.21.11) {
        /*final int lightCoords = signText.hasGlowingText() ? LightTexture.FULL_BRIGHT : state.lightCoords;
        *///?}
        poseStack.pushPose();
        //? if (>=1.21.10 && <=1.21.11) {
        /*this.translateSignText(poseStack, isFront, this.getTextOffset());
        *///?}
        poseStack.translate(-slot.width / 2F, -slot.height / 2F, 0F);
        final SignAtlasRenderTarget.Slot finalSlot = slot;
        submitNodeCollector.submitCustomGeometry(poseStack, ImmediatelyFast.signTextCache.renderType, (entry, vertexConsumer) -> {
            vertexConsumer.addVertex(entry, 0F, finalSlot.height, 0F).setColor(255, 255, 255, 255).setUv(u0, v1).setLight(lightCoords);
            vertexConsumer.addVertex(entry, finalSlot.width, finalSlot.height, 0F).setColor(255, 255, 255, 255).setUv(u1, v1).setLight(lightCoords);
            vertexConsumer.addVertex(entry, finalSlot.width, 0F, 0F).setColor(255, 255, 255, 255).setUv(u1, v0).setLight(lightCoords);
            vertexConsumer.addVertex(entry, 0F, 0F, 0F).setColor(255, 255, 255, 255).setUv(u0, v0).setLight(lightCoords);
        });
        poseStack.popPose();
        ci.cancel();
    }
    //?}

    //? if =1.21.4 {
    /*@Inject(method = "renderSignText", at = @At("HEAD"), cancellable = true)
    private void renderBufferedSignText(final BlockPos pos, final SignText signText, final PoseStack poseStack, final MultiBufferSource bufferSource, int lightCoords, final int textLineHeight, final int maxLineWidth, final boolean isFront, final CallbackInfo ci) {
        if (poseStack instanceof NoTextTransformPoseStack) {
            return;
        }
        final ISignText mixinSignText = (ISignText) signText;
        if (!mixinSignText.immediatelyFast$shouldCache()) {
            return;
        }

        SignAtlasRenderTarget.Slot slot = ImmediatelyFast.signTextCache.slotCache.getIfPresent(signText);
        if (slot == null) {
            final int width = this.immediatelyFast$getTextWidth(signText, maxLineWidth);
            final int height = 4 * textLineHeight;
            if (width <= 0 || height <= 0) {
                mixinSignText.immediatelyFast$setShouldCache(false);
                return;
            }
            final int padding = signText.hasGlowingText() ? 2 : 0;

            slot = ImmediatelyFast.signTextCache.signAtlasRenderTarget.findSlot(width + padding, height + padding);
            if (slot != null) {
                final int textLight = lightCoords;
                ImmediatelyFast.signTextCache.signAtlasRenderTarget.renderText(slot, (textPoseStack, targetBuffers) ->
                        this.renderSignText(Minecraft.getInstance().cameraEntity.blockPosition(), signText, textPoseStack, targetBuffers, textLight, textLineHeight, maxLineWidth, isFront));
                ImmediatelyFast.signTextCache.slotCache.put(signText, slot);
            } else {
                ImmediatelyFast.LOGGER.warn("Failed to find a free slot for sign text (" + ImmediatelyFast.signTextCache.slotCache.size() + " sign texts in atlas). Falling back to immediate mode rendering.");
                mixinSignText.immediatelyFast$setShouldCache(false);
                return;
            }
        }

        final float u0 = ((float) slot.x) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float u1 = ((float) slot.x + (float) slot.width) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float v0 = 1F - ((float) slot.y) / SignAtlasRenderTarget.ATLAS_SIZE;
        final float v1 = 1F - ((float) slot.y + (float) slot.height) / SignAtlasRenderTarget.ATLAS_SIZE;

        if (signText.hasGlowingText()) {
            lightCoords = LightTexture.FULL_BRIGHT;
        }

        poseStack.pushPose();
        this.translateSignText(poseStack, isFront, this.getTextOffset());
        poseStack.translate(-slot.width / 2F, -slot.height / 2F, 0F);
        final Matrix4f matrix4f = poseStack.last().pose();
        final VertexConsumer vertexConsumer = bufferSource.getBuffer(ImmediatelyFast.signTextCache.renderType);
        vertexConsumer.addVertex(matrix4f, 0F, slot.height, 0F).setColor(255, 255, 255, 255).setUv(u0, v1).setLight(lightCoords);
        vertexConsumer.addVertex(matrix4f, slot.width, slot.height, 0F).setColor(255, 255, 255, 255).setUv(u1, v1).setLight(lightCoords);
        vertexConsumer.addVertex(matrix4f, slot.width, 0F, 0F).setColor(255, 255, 255, 255).setUv(u1, v0).setLight(lightCoords);
        vertexConsumer.addVertex(matrix4f, 0F, 0F, 0F).setColor(255, 255, 255, 255).setUv(u0, v0).setLight(lightCoords);
        poseStack.popPose();

        ci.cancel();
    }
    *///?}

    //? if (>=1.21.4 && <=1.21.11) {
    /*//? if (>=1.21.10 && <=1.21.11) {
    /^@Redirect(method = "submitSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/AbstractSignRenderer;translateSignText(Lcom/mojang/blaze3d/vertex/PoseStack;ZLnet/minecraft/world/phys/Vec3;)V"))
    private void dontApplyTextTransform(final AbstractSignRenderer instance, final PoseStack poseStack, final boolean isFront, final Vec3 offset) {
    ^///?}
    //? if =1.21.4 {
    /^@Redirect(method = "renderSignText", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/blockentity/AbstractSignRenderer;translateSignText(Lcom/mojang/blaze3d/vertex/PoseStack;ZLnet/minecraft/world/phys/Vec3;)V"))
    private void dontApplyTextTransform(final AbstractSignRenderer instance, final PoseStack poseStack, final boolean isFront, final Vec3 offset) {
    ^///?}
        if (poseStack instanceof NoTextTransformPoseStack) {
            return;
        }
        this.translateSignText(poseStack, isFront, offset);
    }
    *///?}

    //? if >=1.21.10 {
    @Unique
    private int immediatelyFast$getTextWidth(final SignText signText, final boolean filterText, final int maxLineWidth) {
        //? if >=1.21.11 {
        final FormattedCharSequence[] renderMessages = signText.getRenderMessages(filterText, textComponent -> {
            final List<FormattedCharSequence> list = this.font.split(textComponent, maxLineWidth);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.getFirst();
        });
        //?}
        //? if =1.21.10 {
        /*final FormattedCharSequence[] renderMessages = signText.getRenderMessages(filterText, textComponent -> {
            final List<FormattedCharSequence> list = this.font.split(textComponent, maxLineWidth);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        *///?}
        int width = 0;
        for (FormattedCharSequence line : renderMessages) {
            width = Math.max(width, this.font.width(line));
        }
        if (width % 2 != 0) {
            width++; // Fixes issue which squishes the text when the width is odd (Test text: "hhhl")
        }
        return width;
    }
    //?}

    //? if =1.21.4 {
    /*@Unique
    private int immediatelyFast$getTextWidth(final SignText signText, final int maxLineWidth) {
        final FormattedCharSequence[] renderMessages = signText.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), textComponent -> {
            final List<FormattedCharSequence> list = this.font.split(textComponent, maxLineWidth);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });

        int width = 0;
        for (FormattedCharSequence line : renderMessages) {
            width = Math.max(width, this.font.width(line));
        }
        if (width % 2 != 0) {
            width++; // Fixes issue which squishes the text when the width is odd (Test text: "hhhl")
        }

        return width;
    }
    *///?}
}
//?}
