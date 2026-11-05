//? if <=1.21.4 {
/*/^
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
 ^/
package net.raphimc.immediatelyfast.injection.mixins.fast_text_lookup;

import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.Font;

*///?}
//? if <=1.21.1 {
/*import net.minecraft.client.gui.font.FontSet;

*///?}
//? if <=1.21.4 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;

*///?}
//? if <=1.21.1 {
/*import net.minecraft.resources.ResourceLocation;

*///?}
//? if <=1.21.4 {
/*import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Font.StringRenderOutput.class)
public abstract class MixinTextRenderer_Drawer {

    @Unique
    private RenderType immediatelyFast$lastRenderLayer;

    @Unique
    private VertexConsumer immediatelyFast$lastVertexConsumer;


*///?}
    //? if =1.21.4 {
/*    @Redirect(method = "finish", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
*/
    //?}
    //? if <=1.21.1 {
    /*@Unique
    private ResourceLocation immediatelyFast$lastFont;

    @Unique
    private FontSet immediatelyFast$lastFontStorage;

    @Redirect(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))

    *///?}
    //? if <=1.21.4 {
    /*private VertexConsumer reduceGetBufferCalls(MultiBufferSource instance, RenderType renderLayer) {
        // The buffer got drawn while rendering the text, so we need to reset the cached data

    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*final boolean isBufferInvalid = this.immediatelyFast$lastVertexConsumer instanceof BufferBuilder bufferBuilder && !bufferBuilder.building;

        *///?}
        //? if =1.20.4 {
/*        final boolean isBufferInvalid = this.immediatelyFast$lastVertexConsumer instanceof BufferBuilder bufferBuilder && !bufferBuilder.building();
*/
        //?}
//? if <=1.21.4 {

        /*if (!isBufferInvalid && this.immediatelyFast$lastRenderLayer == renderLayer) {
            return this.immediatelyFast$lastVertexConsumer;
        }

        this.immediatelyFast$lastRenderLayer = renderLayer;
        return this.immediatelyFast$lastVertexConsumer = instance.getBuffer(renderLayer);
    }


*///?}
    //? if <=1.21.1 {
    /*@Redirect(method = "accept", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/Font;getFontSet(Lnet/minecraft/resources/ResourceLocation;)Lnet/minecraft/client/gui/font/FontSet;"))
    private FontSet reduceGetFontStorageCalls(Font instance, ResourceLocation id) {
        if (this.immediatelyFast$lastFont == id) {
            return this.immediatelyFast$lastFontStorage;
        }

        this.immediatelyFast$lastFont = id;
        return this.immediatelyFast$lastFontStorage = instance.getFontSet(id);
    }


    *///?}
//? if <=1.21.4 {
/*}

*///?}
