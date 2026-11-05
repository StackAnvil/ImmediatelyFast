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

//? if <=1.21.1 {
/*package net.raphimc.immediatelyfast.feature.batching;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;
import java.util.function.Function;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.rendertype.RenderType;
import static net.minecraft.Util.memoize;
//? if =1.20.4 {
/^import com.google.common.cache.CacheBuilder;
import org.apache.commons.lang3.tuple.Pair;
^///?}

public class BatchingRenderLayers {
    //? if =1.21.1 {
    /^public static final BiFunction<Integer, BlendFuncDepthFuncState, RenderType> TEXTURE = memoize((id, blendFuncDepthFunc) -> new ImmediatelyFastRenderLayer("texture", VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX, false, () -> {
        blendFuncDepthFunc.saveAndApply();
        RenderSystem.setShaderTexture(0, id);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
    }, blendFuncDepthFunc::revert));
    ^///?}

    //? if =1.21.1 {
    /^public static final BiFunction<Integer, BlendFuncDepthFuncState, RenderType> COLORED_TEXTURE = memoize((id, blendFuncDepthFunc) -> new ImmediatelyFastRenderLayer("colored_texture", VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR, false, () -> {
        blendFuncDepthFunc.saveAndApply();
        RenderSystem.setShaderTexture(0, id);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
    }, blendFuncDepthFunc::revert));
    ^///?}
    //? if =1.20.4 {
    /^public static final BiFunction<Integer, BlendFuncDepthFuncState, RenderType> COLORED_TEXTURE = memoize((id, blendFuncDepthFunc) -> new ImmediatelyFastRenderLayer("texture", VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX_COLOR, false, () -> {
        blendFuncDepthFunc.saveAndApply();
        RenderSystem.setShaderTexture(0, id);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShader(GameRenderer::getPositionTexColorShader);
    }, blendFuncDepthFunc::revert));
    ^///?}

    //? if =1.20.4 {
    /^public static <A> Function<A, RenderType> memoizeTemp(final Function<A, RenderType> function) {
        return new Function<>() {
            private final Map<A, RenderType> cache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.SECONDS).<A, RenderType>build().asMap();

            public RenderType apply(final A arg1) {
                return this.cache.computeIfAbsent(arg1, function);
            }
        };
    }

    public static <A, B> BiFunction<A, B, RenderType> memoizeTemp(final BiFunction<A, B, RenderType> function) {
        return new BiFunction<>() {
            private final Map<Pair<A, B>, RenderType> cache = CacheBuilder.newBuilder().expireAfterAccess(1, TimeUnit.SECONDS).<Pair<A, B>, RenderType>build().asMap();

            public RenderType apply(final A arg1, final B arg2) {
                return this.cache.computeIfAbsent(Pair.of(arg1, arg2), (pair) -> function.apply(pair.getLeft(), pair.getRight()));
            }
        };
    }
    ^///?}

    private static class ImmediatelyFastRenderLayer extends RenderType {

        private ImmediatelyFastRenderLayer(final String name, final VertexFormat.Mode drawMode, final VertexFormat vertexFormat, final boolean translucent, final Runnable startAction, final Runnable endAction) {
            super("immediatelyfast_" + name, vertexFormat, drawMode, 2048, false, translucent, startAction, endAction);
        }

    }

    //? if =1.20.4 {
    /^public static class WrappedRenderLayer extends RenderType {

        public WrappedRenderLayer(final RenderType renderLayer, final Runnable additionalStartAction, final Runnable additionalEndAction) {
            super(renderLayer.name, renderLayer.format(), renderLayer.mode(), renderLayer.bufferSize(), renderLayer.affectsCrumbling(), renderLayer.sortOnUpload, () -> {
                renderLayer.setupRenderState();
                additionalStartAction.run();
            }, () -> {
                renderLayer.clearRenderState();
                additionalEndAction.run();
            });
        }

    }
    ^///?}
}
*///?}
