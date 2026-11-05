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

//? if (>=1.21.10 && <=26.1.2) {
/*package net.raphimc.immediatelyfast.injection.mixins.enhanced_batching;

import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import java.util.SequencedMap;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.raphimc.immediatelyfast.feature.core.BatchableBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(RenderBuffers.class)
public abstract class MixinRenderBuffers {
    //? if (>=1.21.11 && <=26.1.2) {
    /^@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;immediateWithBuffers(Ljava/util/SequencedMap;Lcom/mojang/blaze3d/vertex/ByteBufferBuilder;)Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", ordinal = 0))
    private MultiBufferSource.BufferSource replaceEntityBufferSource(final SequencedMap<RenderType, ByteBufferBuilder> fixedBuffers, final ByteBufferBuilder sharedBuffer) {
        return new BatchableBufferSource(sharedBuffer, fixedBuffers);
    }
    ^///?}

    //? if =1.21.10 {
    /^@Redirect(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;immediateWithBuffers(Ljava/util/SequencedMap;Lcom/mojang/blaze3d/vertex/ByteBufferBuilder;)Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;", ordinal = 0))
    private MultiBufferSource.BufferSource replaceEntityVertexConsumers(final SequencedMap<RenderType, ByteBufferBuilder> layerBuffers, final ByteBufferBuilder fallbackBuffer) {
        return new BatchableBufferSource(fallbackBuffer, layerBuffers);
    }
    ^///?}
}
*///?}
