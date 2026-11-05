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
/*package net.raphimc.immediatelyfast.injection.mixins.fast_text_lookup;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.Font$GlyphVisitor$1")
public abstract class MixinFont_GlyphVisitor {
    //? if (>=1.21.11 && <=26.1.2) {
    /^@Unique
    private RenderType immediatelyFast$lastRenderType;
    ^///?}

    //? if =1.21.10 {
    /^@Unique
    private RenderType immediatelyFast$lastRenderLayer;
    ^///?}

    @Unique
    private VertexConsumer immediatelyFast$lastVertexConsumer;

    //? if (>=1.21.11 && <=26.1.2) {
    /^@SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/rendertype/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer reduceGetBufferCalls(final MultiBufferSource instance, final RenderType renderType) {
    ^///?}
    //? if =1.21.10 {
    /^@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/rendertype/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer reduceGetBufferCalls(final MultiBufferSource instance, final RenderType renderLayer) {
    ^///?}
        //? if (>=1.21.11 && <=26.1.2) {
        /^if (this.immediatelyFast$lastRenderType == renderType) {
            return this.immediatelyFast$lastVertexConsumer;
        }
        this.immediatelyFast$lastRenderType = renderType;
        ^///?}
        //? if =1.21.10 {
        /^if (this.immediatelyFast$lastRenderLayer == renderLayer) {
            return this.immediatelyFast$lastVertexConsumer;
        }
        this.immediatelyFast$lastRenderLayer = renderLayer;
        ^///?}
        //? if (>=1.21.11 && <=26.1.2) {
        /^this.immediatelyFast$lastVertexConsumer = instance.getBuffer(renderType);
        ^///?}
        //? if =1.21.10 {
        /^this.immediatelyFast$lastVertexConsumer = instance.getBuffer(renderLayer);
        ^///?}
        return this.immediatelyFast$lastVertexConsumer;
    }
}
*///?}
