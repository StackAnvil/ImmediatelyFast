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

//? if =26.2 {
/*import com.mojang.blaze3d.GpuFormat;
*/
//?}
//? if =1.21.10 {
/*import com.mojang.blaze3d.opengl.GlDevice;
import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.opengl.GlTexture;
*/
//?}
//? if >=1.21.10 {
import com.mojang.blaze3d.pipeline.TextureTarget;
import com.mojang.blaze3d.systems.RenderSystem;
//?}
//? if =26.3 {
import com.mojang.renderpearl.api.GpuFormat;
//?}
//? if <=1.21.4 {
/*import com.mojang.blaze3d.pipeline.RenderTarget;

*///?}
import net.minecraft.client.Minecraft;
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
//? if <=1.21.10 {
/*import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.resources.ResourceLocation;

*///?}
//? if >=1.21.10 {
import net.raphimc.immediatelyfast.ImmediatelyFast;
//?}
//? if >=1.21.11 {
import net.raphimc.immediatelyfast.util.RenderTargetTexture;
//?}
//? if >=26.2 {
import org.joml.Vector4f;
//?}
//? if <=1.21.1 {
/*import net.minecraft.server.packs.resources.ResourceManager;

*///?}
//? if <=1.21.10 {
/*import org.lwjgl.opengl.GL11C;

*///?}
//? if =1.21.10 {
/*import org.lwjgl.opengl.GL30C;
*/
//?}

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
    private final Identifier textureId;
    //?}
    //? if <=1.21.10 {
    /*private final ResourceLocation textureId;

    *///?}
    private final Slot rootSlot;

    //? if >=1.21.10 {
    public SignAtlasRenderTarget(final int id) {
    //?}
        //? if =26.3 {
        super("ImmediatelyFast Sign Atlas", ATLAS_SIZE, ATLAS_SIZE, GpuFormat.RGBA8_UNORM, GpuFormat.D32_FLOAT);
        //?}
        //? if =26.2 {
/*        super("ImmediatelyFast Sign Atlas", ATLAS_SIZE, ATLAS_SIZE, true, GpuFormat.RGBA8_UNORM);
*/
        //?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        super("ImmediatelyFast Sign Atlas", ATLAS_SIZE, ATLAS_SIZE, true);
*/
        //?}
        //? if >=1.21.11 {
        this.id = id;
        this.textureId = Identifier.fromNamespaceAndPath("immediatelyfast", "sign_atlas/" + id);
        Minecraft.getInstance().getTextureManager().register(this.textureId, new RenderTargetTexture(this));
        //?}
        //? if =1.21.10 {
/*        super("ImmediatelyFast Sign Atlas FBO", ATLAS_SIZE, ATLAS_SIZE, false);
        this.textureId = ResourceLocation.fromNamespaceAndPath("immediatelyfast", "sign_atlas/" + id);
*/
        //?}
    //? if <=1.21.4 {
    /*public SignAtlasRenderTarget() {
        super(false);

    *///?}
        //? if =1.21.4 {
/*        this.resize(ATLAS_SIZE, ATLAS_SIZE);
*/
        //?}
        //? if <=1.21.1 {
        /*this.resize(ATLAS_SIZE, ATLAS_SIZE, Minecraft.ON_OSX);

        *///?}
        //? if <=1.21.4 {
        /*Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

        *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*this.textureId = ResourceLocation.fromNamespaceAndPath("immediatelyfast", "sign_atlas/" + this.colorTextureId);

        *///?}
        //? if =1.20.4 {
/*        this.textureId = new ResourceLocation("immediatelyfast", "sign_atlas/" + this.colorTextureId);
*/
        //?}
        //? if <=1.21.10 {
        /*Minecraft.getInstance().getTextureManager().register(this.textureId, new FboTexture());

        *///?}

        this.rootSlot = new Slot(null, 0, 0, ATLAS_SIZE, ATLAS_SIZE);
    //? if <=1.21.10 {
    /*}


    *///?}
    //? if =1.21.10 {
/*    public int bind(final boolean setViewport) {
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
*/
    //?}
    //? if <=1.21.4 {
    /*@Override
    public void close() {
        this.destroyBuffers();
        Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

    *///?}
    }

    public Slot findSlot(final int width, final int height) {
        return this.rootSlot.findSlot(width, height);
    }

    //? if <=1.21.1 || >=1.21.10 {
    public void clear() {
    //?}
        //? if =26.3 {
        RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(this.getColorTexture(), CLEAR_COLOR, this.getDepthTexture(), 0D);
        //?}
        //? if =26.2 {
/*        RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(this.getColorTexture(), CLEAR_COLOR, this.getDepthTexture(), 0F);
*/
        //?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(this.getColorTexture(), 0, this.getDepthTexture(), 1F);
*/
        //?}
        //? if =1.21.10 {
/*        RenderSystem.getDevice().createCommandEncoder().clearColorTexture(this.getColorTexture(), 0);
*/
        //?}
    //? if =1.21.4 {
/*    public void clearAtlas() {
        this.clear();
*/
    //?}
        //? if <=1.21.1 {
        /*this.clear(Minecraft.ON_OSX);

        *///?}
        //? if <=1.21.4 {
        /*Minecraft.getInstance().getMainRenderTarget().bindWrite(true);

        *///?}

        this.rootSlot.subSlot1 = null;
        this.rootSlot.subSlot2 = null;
    }

    //? if >=1.21.11 {
    public int getId() {
        return this.id;
    }

    public Identifier getTextureId() {
    //?}
    //? if <=1.21.10 {
    /*public ResourceLocation getTextureId() {

    *///?}
        return this.textureId;
    //? if >=1.21.10 {
    }

    @Override
    public void close() {
    //?}
        //? if >=1.21.11 {
        Minecraft.getInstance().getTextureManager().release(this.textureId);
        //?}
        //? if >=1.21.10 {
        this.destroyBuffers();
        //?}
    }

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
/*            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(SignAtlasRenderTarget.this.getColorTexture(), CLEAR_COLOR, SignAtlasRenderTarget.this.getDepthTexture(), 0F, this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height);
*/
            //?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            RenderSystem.getDevice().createCommandEncoder().clearColorAndDepthTextures(SignAtlasRenderTarget.this.getColorTexture(), 0, SignAtlasRenderTarget.this.getDepthTexture(), 1F, this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height);
*/
            //?}

            //? if =1.21.10 {
/*            GlStateManager._scissorBox(this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height);
            GlStateManager._enableScissorTest();
            final int previousFbo = SignAtlasRenderTarget.this.bind(false);
            GL11C.glClear(GL11C.GL_COLOR_BUFFER_BIT);
            SignAtlasRenderTarget.this.unbind(previousFbo);
            GlStateManager._disableScissorTest();
*/
            //?}
            //? if <=1.21.4 {
            /*GL11C.glScissor(this.x, ATLAS_SIZE - this.y - this.height, this.width, this.height);
            GL11C.glEnable(GL11C.GL_SCISSOR_TEST);

            *///?}
            //? if =1.21.4 {
/*            SignAtlasRenderTarget.this.clear();
*/
            //?}
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


    *///?}
        //? if =1.21.10 {
/*        private FboTexture() {
            this.texture = SignAtlasRenderTarget.this.colorTexture;
            this.textureView = RenderSystem.getDevice().createTextureView(this.texture);
*/
        //?}
        //? if <=1.21.1 {
        /*@Override
        public void load(ResourceManager manager) {
        }


        *///?}
        //? if <=1.21.4 {
        /*@Override
        public void releaseId() {

        *///?}
        //? if <=1.21.10 {
        /*}

        @Override

        *///?}
        //? if =1.21.10 {
/*        public void close() {
*/
        //?}
        //? if <=1.21.4 {
        /*public int getId() {
            return SignAtlasRenderTarget.this.colorTextureId;

        *///?}
        //? if <=1.21.10 {
        /*}

    }


        *///?}
}
