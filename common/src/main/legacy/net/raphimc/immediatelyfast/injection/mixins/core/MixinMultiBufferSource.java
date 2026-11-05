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

package net.raphimc.immediatelyfast.injection.mixins.core;

import java.util.Map;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.feature.core.BatchableBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
//? if (>=1.21.1 && <=1.21.4) {
/*import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import java.util.SequencedMap;
*///?}
//? if =1.20.4 {
/*import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.Tesselator;
*///?}

@Mixin(MultiBufferSource.class)
public interface MixinMultiBufferSource {
    //? if (>=1.21.1 && <=1.21.4) {
    /*@Overwrite
    static MultiBufferSource.BufferSource immediateWithBuffers(final SequencedMap<RenderType, ByteBufferBuilder> layerBuffers, final ByteBufferBuilder fallbackBuffer) {
    *///?}
    //? if =1.20.4 {
    /*@Overwrite
    static MultiBufferSource.BufferSource immediateWithBuffers(final Map<RenderType, BufferBuilder> layerBuffers, BufferBuilder fallbackBuffer) {
    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*if (ImmediatelyFast.config.debug_only_and_not_recommended_disable_universal_batching) {
            return new MultiBufferSource.BufferSource(fallbackBuffer, layerBuffers);
        }
        *///?}
        //? if =1.20.4 {
        /*if (ImmediatelyFast.config.debug_only_and_not_recommended_disable_universal_batching || fallbackBuffer == null) {
            if (fallbackBuffer == null) { // Indicate that this shouldn't get batched regardless of the config
                fallbackBuffer = Tesselator.getInstance().getBuilder();
            }

            return new MultiBufferSource.BufferSource(fallbackBuffer, layerBuffers);
        }
        *///?}
        return new BatchableBufferSource(fallbackBuffer, layerBuffers);
    }
}
