//? if (>=1.21.10 && <=26.1.2) {
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

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
*/
//?}
//? if (>=1.21.11 && <=26.1.2) {
/*import net.minecraft.client.renderer.rendertype.RenderType;
*/
//?}
//? if =1.21.10 {
/*import net.minecraft.client.renderer.RenderType;
*/
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(targets = "net.minecraft.client.gui.Font$GlyphVisitor$1")
public abstract class MixinFont_GlyphVisitor {

    @Unique
*/
//?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private RenderType immediatelyFast$lastRenderType;
*/
    //?}
    //? if =1.21.10 {
/*    private RenderType immediatelyFast$lastRenderLayer;
*/
    //?}
//? if (>=1.21.10 && <=26.1.2) {
/*
    @Unique
    private VertexConsumer immediatelyFast$lastVertexConsumer;

*/
//?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/rendertype/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer reduceGetBufferCalls(MultiBufferSource instance, RenderType renderType) {
        if (this.immediatelyFast$lastRenderType == renderType) {
*/
    //?}
    //? if =1.21.10 {
/*    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/MultiBufferSource;getBuffer(Lnet/minecraft/client/renderer/RenderType;)Lcom/mojang/blaze3d/vertex/VertexConsumer;"))
    private VertexConsumer reduceGetBufferCalls(MultiBufferSource instance, RenderType renderLayer) {
        if (this.immediatelyFast$lastRenderLayer == renderLayer) {
*/
    //?}
            //? if (>=1.21.10 && <=26.1.2) {
/*            return this.immediatelyFast$lastVertexConsumer;
        }

*/
            //?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        this.immediatelyFast$lastRenderType = renderType;
        this.immediatelyFast$lastVertexConsumer = instance.getBuffer(renderType);
*/
        //?}
        //? if =1.21.10 {
/*        this.immediatelyFast$lastRenderLayer = renderLayer;
        this.immediatelyFast$lastVertexConsumer = instance.getBuffer(renderLayer);
*/
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        return this.immediatelyFast$lastVertexConsumer;
    }

}
*/
        //?}
