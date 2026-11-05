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

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalCause;
//? if (>=1.21.10 && <=26.2) {
/*import com.mojang.blaze3d.buffers.GpuBufferSlice;
*/
//?}
//? if >=1.21.11 {
import com.mojang.blaze3d.systems.RenderSystem;
//?}
//? if =26.3 {
import com.mojang.renderpearl.api.buffers.GpuBufferSlice;
//?}
//? if >=26.1.2 {
import net.minecraft.client.renderer.Projection;
import net.minecraft.client.renderer.ProjectionMatrixBuffer;
//?}
//? if <=1.21.10 {
/*import java.util.concurrent.TimeUnit;

*///?}
//? if (>=1.21.10 && <=1.21.11) {
/*import net.minecraft.client.renderer.CachedOrthoProjectionMatrixBuffer;
*/
//?}
//? if >=1.21.11 {
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?}
//? if <=1.21.10 {
/*import net.minecraft.client.renderer.RenderType;

*///?}
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.level.block.entity.SignText;

//? if >=26.1.2 {
import java.time.Duration;
//?}
//? if =1.21.11 {
/*import java.util.concurrent.TimeUnit;
*/
//?}

public class SignTextCache implements ResourceManagerReloadListener {

    //? if >=1.21.11 {
    public final SignAtlasRenderTarget signAtlasRenderTarget;
    public final RenderType renderType;
    public final GpuBufferSlice signProjectionMatrix;
    //?}
    //? if =1.21.10 {
/*    public final SignAtlasRenderTarget signAtlasFramebuffer = new SignAtlasRenderTarget(0);
    public final CachedOrthoProjectionMatrixBuffer signProjectionMatrix = new CachedOrthoProjectionMatrixBuffer("immediatelyfast:sign_atlas_text", -1000F, 1000F, true);
    public final GpuBufferSlice signProjectionMatrixBuffer = this.signProjectionMatrix.getBuffer(SignAtlasRenderTarget.ATLAS_SIZE, SignAtlasRenderTarget.ATLAS_SIZE);
*/
    //?}
    //? if <=1.21.4 {
    /*public final SignAtlasRenderTarget signAtlasFramebuffer = new SignAtlasRenderTarget();

    *///?}
    public final Cache<SignText, SignAtlasRenderTarget.Slot> slotCache = CacheBuilder.newBuilder()
        //? if >=26.1.2 {
        .expireAfterAccess(Duration.ofSeconds(5L))
        //?}
        //? if <=1.21.11 {
        /*.expireAfterAccess(5, TimeUnit.SECONDS)

        *///?}
        .removalListener(notification -> {
            if (notification.getCause().equals(RemovalCause.EXPLICIT)) {
                return;
            }

            final SignAtlasRenderTarget.Slot slot = (SignAtlasRenderTarget.Slot) notification.getValue();
            if (slot != null) {
                slot.markFree();
            }
        })
        .build();
//? if >=1.21.11 {

    public SignTextCache() {
        RenderSystem.assertOnRenderThread();
        this.signAtlasRenderTarget = new SignAtlasRenderTarget(0);
        this.renderType = RenderTypes.text(this.signAtlasRenderTarget.getTextureId());
//?}
//? if >=26.1.2 {

        final Projection projection = new Projection();
        projection.setupOrtho(-1000F, 1000F, SignAtlasRenderTarget.ATLAS_SIZE, SignAtlasRenderTarget.ATLAS_SIZE, true);
        final ProjectionMatrixBuffer projectionMatrixBuffer = new ProjectionMatrixBuffer("ImmediatelyFast Sign Atlas");
        this.signProjectionMatrix = projectionMatrixBuffer.getBuffer(projection);
//?}
        //? if =1.21.11 {
/*        final CachedOrthoProjectionMatrixBuffer projectionMatrixBuffer = new CachedOrthoProjectionMatrixBuffer("ImmediatelyFast Sign Atlas", -1000F, 1000F, true);
        this.signProjectionMatrix = projectionMatrixBuffer.getBuffer(SignAtlasRenderTarget.ATLAS_SIZE, SignAtlasRenderTarget.ATLAS_SIZE);
*/
        //?}
    //? if >=1.21.11 {
    }
    //?}
    //? if <=1.21.10 {
    /*public final RenderType renderLayer = RenderType.text(this.signAtlasFramebuffer.getTextureId());

    *///?}
    //? if (>=1.21.4 && <=1.21.10) {
/*    public boolean lockFramebuffer = false;
*/
    //?}
    //? if =1.21.10 {
/*    public boolean lockViewport = false;
*/
    //?}

    public void clearCache() {
        //? if >=26.1.2 {
        RenderSystem.assertOnRenderThread();
        //?}
        this.slotCache.invalidateAll();
        //? if >=1.21.11 {
        this.signAtlasRenderTarget.clear();
        //?}
        //? if <=1.21.1 || =1.21.10 {
        /*this.signAtlasFramebuffer.clear();

        *///?}
        //? if =1.21.4 {
/*        this.signAtlasFramebuffer.clearAtlas();
*/
        //?}
    }

    @Override
    //? if >=1.21.10 {
    public void onResourceManagerReload(final ResourceManager manager) {
    //?}
    //? if <=1.21.4 {
    /*public void onResourceManagerReload(ResourceManager manager) {

    *///?}
        this.clearCache();
    }

}
