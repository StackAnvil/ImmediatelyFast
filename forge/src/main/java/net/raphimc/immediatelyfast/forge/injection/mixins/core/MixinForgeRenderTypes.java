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

package net.raphimc.immediatelyfast.forge.injection.mixins.core;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if (>=1.21.1 && <=1.21.4) {
import org.spongepowered.asm.mixin.injection.ModifyArg;
//?}
//? if =1.20.4 {
/*import net.minecraft.client.renderer.RenderType;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
*///?}

@Mixin(targets = "net.minecraftforge.client.ForgeRenderTypes$Internal", priority = 500)
public abstract class MixinForgeRenderTypes {
    //? if (>=1.21.1 && <=1.21.4) {
    // Match the overload independently of its inherited Minecraft method name.
    @ModifyArg(method = {
        "getText",
        "getTextIntensity",
        "getTextPolygonOffset",
        "getTextIntensityPolygonOffset",
        "getTextSeeThrough",
        "getTextIntensitySeeThrough"
    }, at = @At(value = "INVOKE", target = "Lnet/minecraftforge/client/ForgeRenderTypes$Internal;*(Ljava/lang/String;Lcom/mojang/blaze3d/vertex/VertexFormat;Lcom/mojang/blaze3d/vertex/VertexFormat$Mode;IZZLnet/minecraft/client/renderer/RenderType$CompositeState;)Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;", ordinal = 0, remap = false), index = 5)
    private static boolean changeTranslucency(final boolean value) {
    //?}
    //? if =1.20.4 {
    /*@Inject(method = {
        "getText",
        "getTextIntensity",
        "getTextPolygonOffset",
        "getTextIntensityPolygonOffset",
        "getTextSeeThrough",
        "getTextIntensitySeeThrough"
    }, at = @At(value = "RETURN"), remap = false) // Forge workaround: modify the returned render type
    private static void changeTranslucency(final CallbackInfoReturnable<RenderType> cir) {
    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        return false;
        //?}
        //? if =1.20.4 {
        /*cir.getReturnValue().sortOnUpload = false;
        *///?}
    }
}
