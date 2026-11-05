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

package net.raphimc.immediatelyfast.feature.sign_text_buffering;

import java.util.Optional;
import java.util.OptionalDouble;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.injection.interfaces.ISignText;
//? if >=1.21.4 {
import com.mojang.blaze3d.ProjectionType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import org.joml.Matrix4fStack;
//?}
//? if >=1.21.10 {
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.blockentity.state.SignRenderState;
import net.minecraft.client.renderer.fog.FogRenderer;
//?}
//? if (>=1.21.4 && <=26.1.2) {
/*import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import net.minecraft.client.renderer.MultiBufferSource;
import net.raphimc.immediatelyfast.feature.core.ByteBufferBuilderPool;
*///?}
//? if (>=1.21.10 && <=26.1.2) || =26.3 {
import net.minecraft.client.renderer.feature.FeatureRenderDispatcher;
//?}
//? if <=1.21.10 {
/*import net.minecraft.client.renderer.texture.AbstractTexture;
import org.lwjgl.opengl.GL11C;
*///?}
//? if >=1.21.11 {
import net.raphimc.immediatelyfast.util.RenderTargetTexture;
//?}
//? if <=1.21.4 {
/*import com.mojang.blaze3d.pipeline.RenderTarget;
*///?}
//? if (>=1.21.11 && <=26.2) {
/*import com.mojang.renderpearl.api.textures.GpuTextureView;
*///?}
//? if (>=1.21.4 && <=1.21.11) {
/*import net.raphimc.immediatelyfast.feature.sign_text_buffering.NoTextTransformPoseStack;
*///?}
//? if >=26.2 {
import com.mojang.renderpearl.api.GpuFormat;
import org.joml.Vector4f;
//?}
//? if <=1.21.1 {
/*import net.minecraft.server.packs.resources.ResourceManager;
*///?}
//? if =26.3 {
import com.mojang.renderpearl.api.commands.RenderPass;
//?}
//? if =1.21.10 {
/*import com.mojang.renderpearl.backend.opengl.GlDevice;
import com.mojang.renderpearl.backend.opengl.GlStateManager;
import com.mojang.renderpearl.backend.opengl.GlTexture;
import org.lwjgl.opengl.GL30C;
*///?}
//? if =1.21.4 {
/*import net.minecraft.client.renderer.FogParameters;
import org.joml.Matrix4f;
*///?}

//? if >=1.21.10 {
public class SignAtlasRenderTarget extends TextureTarget implements AutoCloseable {
//?}
//? if <=1.21.4 {
/*public class SignAtlasRenderTarget extends RenderTarget implements AutoCloseable {
*///?}
    //? if >=26.2 {
    private static final Vector4f CLEAR_COLOR = new Vector4f(0F);
    //?}

    //? if >=1.21.10 {
    public static final int ATLAS_SIZE = ImmediatelyFast.config.experimental_sign_atlas_size;
    //?}
    //? if <=1.21.4 {
    /*public static final int ATLAS_SIZE = 4096;
    *///?}

    //? if >=1.21.11 {
    private final int id;
    //?}

    private final Identifier textureId;

    private final Slot rootSlot;

    //? if >=1.21.10 {
    public SignAtlasRenderTarget(final int id) {
        //? if =26.3 {
        super("ImmediatelyFast Sign Atlas", ATLAS_SIZE, ATLAS_SIZE, GpuFormat.RGBA8_UNORM, GpuFormat.D32_FLOAT);
        //?}
        //? if =26.2 {
        /*super("ImmediatelyFast Sign Atlas", ATLAS_SIZE, ATLAS_SIZE, true, GpuFormat.RGBA8_UNORM);
        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
        /*super("ImmediatelyFast Sign Atlas", ATLAS_SIZE, ATLAS_SIZE, true);
        *///?}
        //? if =1.21.10 {
        /*super("ImmediatelyFast Sign Atlas FBO", ATLAS_SIZE, ATLAS_SIZE, false);
        *///?}
        //? if >=1.21.11 {
        this.id = id;
        //?}
        this.textureId = Identifier.fromNamespaceAndPath("immediatelyfast", "sign_atlas/" + id);
        //? if >=1.21.11 {
        Minecraft.getInstance().getTextureManager().register(this.textureId, new RenderTargetTexture(this));
        //?}
        //? if =1.21.10 {
        /*Minecraft.getInstance().getTextureManager().register(this.textureId, new FboTexture());
        *///?}
        this.rootSlot = new Slot(null, 0, 0, ATLAS_SIZE, ATLAS_SIZE);
    }
    //?}

    //? if =1.21.10 {
    /*public int bind(final boolean setViewport) {
        final int previousFramebuffer = GL11C.glGetInteger(GL30C.GL_FRAMEBUFFER_BINDING);
        final int fbo = ((GlTexture) SignAtlasRenderTarget.this.colorTexture).getFbo(((GlDevice) RenderSystem.getDevice()).directStateAccess(), null);
        GL30C.glBindFramebuffer(GL30C.GL_FRAMEBUFFER, fbo);
        if (setViewport) {
            GL11C.glViewport(0, 0, ATLAS_SIZE, ATLAS_SIZE);
        }
        return previousFramebuffer;
    }

    public void unbind(final int previousFbo) {
        GL30C.glBindFramebuffer(GL30C.GL_FRAMEBUFFER, previousFbo);
        GL11C.glViewport(0, 0, Minecraft.getInstance().getWindow().getWidth(), Minecraft.getInstance().getWindow().getHeight());
    }
    *///?}

    //? if <=1.21.4 {
    /*public SignAtlasRenderTarget() {
        super(false);
        //? if =1.21.4 {
        /^this.resize(ATLAS_SIZE, ATLAS_SIZE);
        ^///?}
        //? if <=1.21.1 {
        /^this.resize(ATLAS_SIZE, ATLAS_SIZE, Minecraft.ON_OSX);
        ^///?}
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        //? if (>=1.21.1 && <=1.21.4) {
        /^this.textureId = Identifier.fromNamespaceAndPath("immediatelyfast", "sign_atlas/" + this.colorTextureId);
        ^///?}
        //? if =1.20.4 {
        /^this.textureId = new Identifier("immediatelyfast", "sign_atlas/" + this.colorTextureId);
        ^///?}
        Minecraft.getInstance().getTextureManager().register(this.textureId, new FboTexture());
        this.rootSlot = new Slot(null, 0, 0, ATLAS_SIZE, ATLAS_SIZE);
    }

    @Override
    public void close() {
        this.destroyBuffers();
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
    }
    *///?}

    public Slot findSlot(final int width, final int height) {
        return this.rootSlot.findSlot(width, height);
    }

    //? if <=1.21.1 || >=1.21.10 {
    public void clear() {
        //? if =26.3 {
        RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(this.getColorTexture(), CLEAR_COLOR, this.getDepthTexture(), 0D);
        //?}
        //? if =26.2 {
        /*RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(this.getColorTexture(), CLEAR_COLOR, this.getDepthTexture(), 0F);
        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
        /*RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(this.getColorTexture(), 0, this.getDepthTexture(), 1F);
        *///?}
        //? if =1.21.10 {
        /*RenderSystem.getDevice().createCommandEncoder().clearColorTexture(this.getColorTexture(), 0);
        *///?}
        //? if <=1.21.1 {
        /*this.clear(Minecraft.ON_OSX);
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
        *///?}
        this.rootSlot.subSlot1 = null;
        this.rootSlot.subSlot2 = null;
    }
    //?}

    //? if >=1.21.11 {
    public int getId() {
        return this.id;
    }
    //?}

    //? if =1.21.4 {
    /*public void clearAtlas() {
        this.clear();
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

        this.rootSlot.subSlot1 = null;
        this.rootSlot.subSlot2 = null;
    }
    *///?}

    public Identifier getTextureId() {
        return this.textureId;
    }

    //? if >=1.21.10 {
    @Override
    public void close() {
        //? if >=1.21.11 {
        Minecraft.getInstance().getTextureManager().release(this.textureId);
        //?}
        this.destroyBuffers();
    }
    //?}

    public class Slot {
        public final int x;

        public final int y;

        public final int width;

        public final int height;

        public final Slot parentSlot;

        public Slot subSlot1;

        public Slot subSlot2;

        public boolean occupied;

        public Slot(final Slot parentSlot, final int x, final int y, final int width, final int height) {
            this.parentSlot = parentSlot;
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }

        public void markFree() {
            if (this.subSlot1 != null || this.subSlot2 != null) {
                throw new UnsupportedOperationException("Cannot mark slot as free if it has sub slots");
            }
            if (!this.occupied) {
                throw new UnsupportedOperationException("Cannot mark slot as free if it is not occupied");
            }
            this.occupied = false;
            removeUnoccupiedSubSlots(this);
            //? if =26.3 {
            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(SignAtlasRenderTarget.this.getColorTexture(), CLEAR_COLOR, SignAtlasRenderTarget.this.getDepthTexture(), 0D, this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height, 0);
            //?}
            //? if =26.2 {
            /*RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(SignAtlasRenderTarget.this.getColorTexture(), CLEAR_COLOR, SignAtlasRenderTarget.this.getDepthTexture(), 0F, this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height);
            *///?}
            //? if (>=1.21.11 && <=26.1.2) {
            /*RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(SignAtlasRenderTarget.this.getColorTexture(), 0, SignAtlasRenderTarget.this.getDepthTexture(), 1F, this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height);
            *///?}
            //? if =1.21.10 {
            /*GlStateManager._scissorBox(this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height);
            GlStateManager._enableScissorTest();
            final int previousFbo = SignAtlasRenderTarget.this.bind(false);
            GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT);
            SignAtlasRenderTarget.this.unbind(previousFbo);
            GlStateManager._disableScissorTest();
            *///?}
            //? if <=1.21.4 {
            /*GL11C.glScissor(this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height);
            GL11C.glEnable(GL11C.GL_SCISSOR_TEST);
            *///?}
            //? if =1.21.4 {
            /*SignAtlasRenderTarget.this.clear();
            *///?}
            //? if <=1.21.1 {
            /*SignAtlasRenderTarget.this.clear(Minecraft.ON_OSX);
            *///?}
            //? if <=1.21.4 {
            /*GL11C.glDisable(GL11C.GL_SCISSOR_TEST);
            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
            *///?}
        }

        public Slot findSlot(final int width, final int height) {
            if (this.subSlot1 != null && this.subSlot2 != null) {
                Slot slot = this.subSlot1.findSlot(width, height);
                if (slot == null) {
                    slot = this.subSlot2.findSlot(width, height);
                }

                return slot;
            } else if (this.occupied) {
                return null;
            } else {
                if (width > this.width || height > this.height) {
                    return null;
                } else if (width == this.width && height == this.height) {
                    this.occupied = true;
                    return this;
                } else {
                    final int k = this.width - width;
                    final int l = this.height - height;
                    if (k > l) {
                        this.subSlot1 = new Slot(this, this.x, this.y, width, this.height);
                        this.subSlot2 = new Slot(this, this.x + width, this.y, this.width - width, this.height);
                    } else {
                        this.subSlot1 = new Slot(this, this.x, this.y, this.width, height);
                        this.subSlot2 = new Slot(this, this.x, this.y + height, this.width, this.height - height);
                    }

                    return this.subSlot1.findSlot(width, height);
                }
            }
        }

        private static void removeUnoccupiedSubSlots(final Slot slot) {
            if (slot == null) {
                return;
            }
            removeUnoccupiedSubSlots(slot.parentSlot);
            final boolean subSlot1Unoccupied = slot.subSlot1 != null && !hasOccupiedSlot(slot.subSlot1);
            final boolean subSlot2Unoccupied = slot.subSlot2 != null && !hasOccupiedSlot(slot.subSlot2);

            if (subSlot1Unoccupied && subSlot2Unoccupied) {
                slot.subSlot1 = null;
                slot.subSlot2 = null;
            }
        }

        private static boolean hasOccupiedSlot(final Slot slot) {
            if (slot == null) {
                return false;
            }
            if (slot.occupied) {
                return true;
            }
            return hasOccupiedSlot(slot.subSlot1) || hasOccupiedSlot(slot.subSlot2);
        }
    }

    //? if <=1.21.10 {
    /*private class FboTexture extends AbstractTexture {
        //? if =1.21.10 {
        /^private FboTexture() {
            this.texture = SignAtlasRenderTarget.this.colorTexture;
            this.textureView = RenderSystem.getDevice().createTextureView(this.texture);
        }

        @Override
        public void close() {
        }
        ^///?}

        //? if <=1.21.1 {
        /^@Override
        public void load(final ResourceManager manager) {
        }
        ^///?}

        //? if <=1.21.4 {
        /^@Override
        public void releaseId() {
        }

        @Override
        public int getId() {
            return SignAtlasRenderTarget.this.colorTextureId;
        }
        ^///?}
    }
    *///?}

    //? if >=1.21.10 {
    /** Draws into a new atlas slot and restores the previous rendering state. */
    public void renderText(final Slot slot, final Font font, final ISignText cacheState, final SignRenderState state, final BiConsumer<PoseStack, SubmitNodeCollector> drawText) {
        RenderSystem.backupProjectionMatrix();
        //? if >=1.21.11 {
        RenderSystem.setProjectionMatrix(ImmediatelyFast.signTextCache.signProjectionMatrix, ProjectionType.ORTHOGRAPHIC);
        //?}
        //? if =1.21.10 {
        /*RenderSystem.setProjectionMatrix(ImmediatelyFast.signTextCache.signProjectionMatrixBuffer, ProjectionType.ORTHOGRAPHIC);
        *///?}
        final Matrix4fStack modelViewMatrix = RenderSystem.getModelViewStack();
        //? if >=1.21.11 {
        modelViewMatrix.pushMatrix().identity();
        //?}
        //? if =1.21.10 {
        /*modelViewMatrix.pushMatrix();
        modelViewMatrix.identity();
        *///?}
        final GpuBufferSlice fog = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(Minecraft.getInstance().gameRenderer.fogRenderer.getBuffer(FogRenderer.FogMode.NONE));
        //? if (>=1.21.11 && <=26.2) {
        /*final GpuTextureView previousColorTextureOverride = RenderSystem.outputColorTextureOverride;
        final GpuTextureView previousDepthTextureOverride = RenderSystem.outputDepthTextureOverride;
        RenderSystem.outputColorTextureOverride = this.getColorTextureView();
        RenderSystem.outputDepthTextureOverride = this.getDepthTextureView();
        *///?}
        //? if (>=1.21.10 && <=26.1.2) {
        /*final ByteBufferBuilder bufferBuilder = ByteBufferBuilderPool.borrowBufferBuilder();
        *///?}
        //? if >=26.1.2 {
        cacheState.immediatelyFast$setShouldCache(false);
        //?}
        //? if =1.21.10 {
        /*final int previousFbo = this.bind(true);
        ImmediatelyFast.signTextCache.lockFramebuffer = true;
        ImmediatelyFast.signTextCache.lockViewport = true;
        *///?}
        //? if =26.3 {
        try {
            final PoseStack textPoseStack = new PoseStack();
            textPoseStack.translate(slot.x, slot.y, 0F);
            textPoseStack.translate(slot.width / 2F, slot.height / 2F, 0F);
            state.drawOutline = true; // Always render outline, regardless of distance to sign
            final SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();
            drawText.accept(textPoseStack, submitNodeStorage);
            try (
                final FeatureRenderDispatcher.PreparedFrame frame = Minecraft.getInstance().gameRenderer.featureRenderDispatcher().prepareFrame(submitNodeStorage);
                final RenderPass renderPass = RenderSystem.getDevice().createCommandEncoder().createRenderPass(
                    () -> "Picture in picture",
                    this.getColorTextureView(), Optional.empty(),
                    this.getDepthTextureView(), OptionalDouble.empty()
                )
            ) {
                RenderSystem.bindDefaultUniforms(renderPass);
                FeatureRenderDispatcher.renderAllFeatures(renderPass, frame);
            }
        } finally {
            cacheState.immediatelyFast$setShouldCache(true);
            RenderSystem.setShaderFog(fog);
            modelViewMatrix.popMatrix();
            RenderSystem.restoreProjectionMatrix();
        }
        //?}
        //? if =26.2 {
        /*try {
            final PoseStack textPoseStack = new PoseStack();
            textPoseStack.translate(slot.x, slot.y, 0F);
            textPoseStack.translate(slot.width / 2F, slot.height / 2F, 0F);
            state.drawOutline = true; // Always render outline, regardless of distance to sign
            final SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();
            drawText.accept(textPoseStack, submitNodeStorage);
            Minecraft.getInstance().gameRenderer.featureRenderDispatcher().renderAllFeatures(submitNodeStorage);
        } finally {
            cacheState.immediatelyFast$setShouldCache(true);
            RenderSystem.outputColorTextureOverride = previousColorTextureOverride;
            RenderSystem.outputDepthTextureOverride = previousDepthTextureOverride;
            RenderSystem.setShaderFog(fog);
            modelViewMatrix.popMatrix();
            RenderSystem.restoreProjectionMatrix();
        }
        *///?}
        //? if =26.1.2 {
        /*try {
            final MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(bufferBuilder);
            final SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();
            final FeatureRenderDispatcher renderDispatcher = new FeatureRenderDispatcher(submitNodeStorage, Minecraft.getInstance().getModelManager(), bufferSource, Minecraft.getInstance().getAtlasManager(), null, null, font, Minecraft.getInstance().gameRenderer.getGameRenderState());
            final PoseStack textPoseStack = new PoseStack();
            textPoseStack.translate(slot.x, slot.y, 0F);
            textPoseStack.translate(slot.width / 2F, slot.height / 2F, 0F);
            state.drawOutline = true; // Always render outline, regardless of distance to sign
            drawText.accept(textPoseStack, submitNodeStorage);
            renderDispatcher.renderAllFeatures();
            bufferSource.endBatch();
            renderDispatcher.close();
        } finally {
            cacheState.immediatelyFast$setShouldCache(true);
            ByteBufferBuilderPool.returnBufferBuilderSafe(bufferBuilder);
            RenderSystem.outputColorTextureOverride = previousColorTextureOverride;
            RenderSystem.outputDepthTextureOverride = previousDepthTextureOverride;
            RenderSystem.setShaderFog(fog);
            modelViewMatrix.popMatrix();
            RenderSystem.restoreProjectionMatrix();
        }
        *///?}
        //? if =1.21.11 {
        /*try {
            final MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(bufferBuilder);
            final SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();
            final FeatureRenderDispatcher renderDispatcher = new FeatureRenderDispatcher(submitNodeStorage, Minecraft.getInstance().getBlockRenderer(), bufferSource, Minecraft.getInstance().getAtlasManager(), null, null, font);
            final PoseStack textPoseStack = new NoTextTransformPoseStack();
            textPoseStack.translate(slot.x, slot.y, 0F);
            textPoseStack.translate(slot.width / 2F, slot.height / 2F, 0F);
            state.drawOutline = true; // Always render outline, regardless of distance to sign
            drawText.accept(textPoseStack, submitNodeStorage);
            renderDispatcher.renderAllFeatures();
            bufferSource.endBatch();
            renderDispatcher.close();
        } finally {
            ByteBufferBuilderPool.returnBufferBuilderSafe(bufferBuilder);
            RenderSystem.outputColorTextureOverride = previousColorTextureOverride;
            RenderSystem.outputDepthTextureOverride = previousDepthTextureOverride;
            RenderSystem.setShaderFog(fog);
            modelViewMatrix.popMatrix();
            RenderSystem.restoreProjectionMatrix();
        }
        *///?}
        //? if =1.21.10 {
        /*try {
            final MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(bufferBuilder);
            final SubmitNodeStorage submitNodeStorage = new SubmitNodeStorage();
            final FeatureRenderDispatcher renderDispatcher = new FeatureRenderDispatcher(submitNodeStorage, Minecraft.getInstance().getBlockRenderer(), bufferSource, Minecraft.getInstance().getAtlasManager(), null, null, font);
            final PoseStack textPoseStack = new NoTextTransformPoseStack();
            textPoseStack.translate(slot.x, slot.y, 0F);
            textPoseStack.translate(slot.width / 2F, slot.height / 2F, 0F);
            state.drawOutline = true; // Always render outline, regardless of distance to sign
            drawText.accept(textPoseStack, submitNodeStorage);
            renderDispatcher.renderAllFeatures();
            bufferSource.endBatch();
            renderDispatcher.close();
        } finally {
            ImmediatelyFast.signTextCache.lockViewport = false;
            ImmediatelyFast.signTextCache.lockFramebuffer = false;
            this.unbind(previousFbo);
            ByteBufferBuilderPool.returnBufferBuilderSafe(bufferBuilder);
            RenderSystem.setShaderFog(fog);
            modelViewMatrix.popMatrix();
            RenderSystem.restoreProjectionMatrix();
        }
        *///?}
    }
    //?}

    //? if =1.21.4 {
    /*public void renderText(final Slot slot, final BiConsumer<PoseStack, MultiBufferSource.BufferSource> drawText) {
        final Matrix4f projectionMatrix = new Matrix4f().setOrtho(0F, SignAtlasRenderTarget.ATLAS_SIZE, SignAtlasRenderTarget.ATLAS_SIZE, 0F, -1000F, 1000F);
        RenderSystem.backupProjectionMatrix();
        RenderSystem.setProjectionMatrix(projectionMatrix, ProjectionType.ORTHOGRAPHIC);
        final Matrix4fStack modelViewMatrix = RenderSystem.getModelViewStack();
        modelViewMatrix.pushMatrix();
        modelViewMatrix.identity();
        final FogParameters fog = RenderSystem.getShaderFog();
        RenderSystem.setShaderFog(FogParameters.NO_FOG);
        final ByteBufferBuilder bufferBuilder = ByteBufferBuilderPool.borrowBufferBuilder();
        this.bindWrite(true);
        ImmediatelyFast.signTextCache.lockFramebuffer = true;

        try {
            final MultiBufferSource.BufferSource bufferSource = MultiBufferSource.immediate(bufferBuilder);
            final PoseStack textPoseStack = new NoTextTransformPoseStack();
            textPoseStack.translate(slot.x, slot.y, 0F);
            textPoseStack.translate(slot.width / 2F, slot.height / 2F, 0F);
            drawText.accept(textPoseStack, bufferSource);
            bufferSource.endBatch();
        } finally {
            ImmediatelyFast.signTextCache.lockFramebuffer = false;
            Minecraft.getInstance().getMainRenderTarget().bindWrite(true);
            ByteBufferBuilderPool.returnBufferBuilderSafe(bufferBuilder);
            RenderSystem.setShaderFog(fog);
            modelViewMatrix.popMatrix();
            RenderSystem.restoreProjectionMatrix();
        }
    }
    *///?}
}
