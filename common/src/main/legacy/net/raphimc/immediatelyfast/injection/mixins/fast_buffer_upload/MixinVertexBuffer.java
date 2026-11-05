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

package net.raphimc.immediatelyfast.injection.mixins.fast_buffer_upload;

import com.mojang.blaze3d.vertex.VertexBuffer;
import java.nio.ByteBuffer;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if <=1.21.1 {
/*import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL15C;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.Redirect;
*///?}
//? if =1.21.4 {
/*import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.renderpearl.api.buffers.BufferUsage;
import com.mojang.renderpearl.api.buffers.GpuBuffer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
*///?}

//? if =1.21.4 {
/*@Mixin(VertexBuffer.class)
public abstract class MixinVertexBuffer {
*///?}
//? if <=1.21.1 {
/*@Mixin(value = VertexBuffer.class, priority = 500)
public abstract class MixinVertexBuffer {
*///?}
    //? if =1.21.4 {
    /*@Shadow
    @Final
    private BufferUsage usage;

    @WrapWithCondition(method = "uploadVertexBuffer", at = @At(value = "INVOKE", target = "Lcom/mojang/renderpearl/api/buffers/GpuBuffer;resize(I)V"))
    private boolean onlyResizeIfNeeded(final GpuBuffer instance, final int newSize) {
        return !ImmediatelyFast.runtimeConfig.fast_buffer_upload || this.usage == BufferUsage.STATIC_WRITE || newSize > instance.size;
    }
    *///?}

    //? if <=1.21.1 {
    /*@Unique
    private int immediatelyFast$vertexBufferSize;

    @Unique
    private int immediatelyFast$indexBufferSize;

    @Redirect(method = "uploadVertexBuffer", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private void optimizeVertexDataUploading(final int target, final ByteBuffer data, final int usage) {
        if (!ImmediatelyFast.runtimeConfig.fast_buffer_upload || usage == GL15C.GL_STATIC_DRAW || data.remaining() > this.immediatelyFast$vertexBufferSize) {
            this.immediatelyFast$vertexBufferSize = data.remaining();
            RenderSystem.glBufferData(target, data, usage);
        } else {
            GL15C.glBufferSubData(target, 0, data);
        }
    }

    //? if =1.21.1 {
    /^@Redirect(method = {"uploadIndexBuffer(Lcom/mojang/blaze3d/vertex/ByteBufferBuilder$Result;)V", "uploadIndexBuffer(Lcom/mojang/blaze3d/vertex/MeshData$DrawState;Ljava/nio/ByteBuffer;)Lcom/mojang/blaze3d/systems/RenderSystem$AutoStorageIndexBuffer;"}, at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private void optimizeIndexDataUploading(final int target, final ByteBuffer data, final int usage) {
    ^///?}
    //? if =1.20.4 {
    /^@Redirect(method = "uploadIndexBuffer", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;glBufferData(ILjava/nio/ByteBuffer;I)V"))
    private void optimizeIndexDataUploading(final int target, final ByteBuffer data, final int usage) {
    ^///?}
        if (!ImmediatelyFast.runtimeConfig.fast_buffer_upload || usage == GL15C.GL_STATIC_DRAW || data.remaining() > this.immediatelyFast$indexBufferSize) {
            this.immediatelyFast$indexBufferSize = data.remaining();
            RenderSystem.glBufferData(target, data, usage);
        } else {
            GL15C.glBufferSubData(target, 0, data);
        }
    }
    *///?}
}
