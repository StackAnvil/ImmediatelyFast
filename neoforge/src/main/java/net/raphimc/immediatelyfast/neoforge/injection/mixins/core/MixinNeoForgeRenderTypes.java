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

//? if <=1.21.4 {
/*package net.raphimc.immediatelyfast.neoforge.injection.mixins.core;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if (>=1.21.1 && <=1.21.4) {
/^import org.spongepowered.asm.mixin.injection.ModifyArg;
^///?}
//? if =1.20.4 {
/^import net.minecraft.client.renderer.rendertype.RenderType;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
^///?}

@Mixin(targets = "net.neoforged.neoforge.client.NeoForgeRenderTypes$Internal", priority = 500)
public abstract class MixinNeoForgeRenderTypes {
    //? if =1.21.4 {
    /^@ModifyArg(method = {
        "getTextFiltered",
        "getTextIntensityFiltered",
        "getTextPolygonOffsetFiltered",
        "getTextIntensityPolygonOffsetFiltered",
        "getTextSeeThroughFiltered",
        "getTextIntensitySeeThroughFiltered"
    }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderType;create(Ljava/lang/String;Lcom/mojang/blaze3d/vertex/VertexFormat;Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;IZZLnet/minecraft/client/renderer/rendertype/RenderType$CompositeState;)Lnet/minecraft/client/renderer/rendertype/RenderType$CompositeRenderType;"), index = 5)
    private static boolean changeTranslucency(final boolean value) {
    ^///?}
    //? if =1.21.1 {
    /^@ModifyArg(method = {
        "getText",
        "getTextIntensity",
        "getTextPolygonOffset",
        "getTextIntensityPolygonOffset",
        "getTextSeeThrough",
        "getTextIntensitySeeThrough"
    }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderType;create(Ljava/lang/String;Lcom/mojang/blaze3d/vertex/VertexFormat;Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;IZZLnet/minecraft/client/renderer/rendertype/RenderType$CompositeState;)Lnet/minecraft/client/renderer/rendertype/RenderType$CompositeRenderType;"), index = 5)
    private static boolean changeTranslucency(final boolean value) {
    ^///?}
    //? if =1.20.4 {
    /^@Inject(method = {
        "getText",
        "getTextIntensity",
        "getTextPolygonOffset",
        "getTextIntensityPolygonOffset",
        "getTextSeeThrough",
        "getTextIntensitySeeThrough"
    }, at = @At(value = "RETURN"), remap = false) // Forge doesn't allow me to target the of() call for some reason
    private static void changeTranslucency(final CallbackInfoReturnable<RenderType> cir) {
    ^///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /^return false;
        ^///?}
        //? if =1.20.4 {
        /^cir.getReturnValue().sortOnUpload = false;
        ^///?}
    }
}
*///?}
