//? if >=1.21.10 {
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
package net.raphimc.immediatelyfast.injection.mixins.skip_text_translucency_sorting;

//?}
//? if >=1.21.11 {
import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderTypes;
//?}
//? if =1.21.10 {
/*import net.minecraft.client.renderer.RenderType;
*/
//?}
//? if >=1.21.10 {
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//?}
//? if >=1.21.11 {
import org.spongepowered.asm.mixin.injection.Redirect;
//?}
//? if =1.21.10 {
/*import org.spongepowered.asm.mixin.injection.ModifyArg;
*/
//?}

//? if >=1.21.11 {
@Mixin(value = RenderTypes.class, priority = 500)
//?}
//? if =1.21.10 {
/*@Mixin(value = RenderType.class, priority = 500)
*/
//?}
//? if >=1.21.10 {
public abstract class MixinRenderTypes {

//?}
    //? if >=1.21.11 {
    @Redirect(method = {
    //?}
        //? if >=26.1.2 {
        "lambda$static$22" /*TEXT_POLYGON_OFFSET*/,
        "lambda$static$23" /*TEXT_INTENSITY_POLYGON_OFFSET*/,
        "lambda$static$25" /*TEXT_INTENSITY_SEE_THROUGH*/
        //?}
        //? if =1.21.11 {
/*        "method_75949" /^TEXT_POLYGON_OFFSET^/,
        "method_75948" /^TEXT_INTENSITY_POLYGON_OFFSET^/,
        "method_75946" /^TEXT_INTENSITY_SEE_THROUGH^/
*/
        //?}
    //? if >=1.21.11 {
    }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/rendertype/RenderSetup$RenderSetupBuilder;sortOnUpload()Lnet/minecraft/client/renderer/rendertype/RenderSetup$RenderSetupBuilder;"))
    //?}
    //? if >=26.2 {
    private static RenderSetup.RenderSetupBuilder disableTranslucencySorting(final RenderSetup.RenderSetupBuilder instance) {
    //?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private static RenderSetup.RenderSetupBuilder disableTranslucencySorting(RenderSetup.RenderSetupBuilder instance) {
*/
    //?}
        //? if >=1.21.11 {
        return instance;
        //?}
    //? if =1.21.10 {
/*    @ModifyArg(method = {
        "method_34834",
        "method_34833",
        "method_36437",
        "method_36436",
        "method_37348",
        "method_37347"
    }, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderType;create(Ljava/lang/String;IZZLcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/client/renderer/RenderType$CompositeState;)Lnet/minecraft/client/renderer/RenderType$CompositeRenderType;"), index = 3)
    private static boolean changeTranslucency(boolean value) {
        return false;
*/
    //?}
    //? if >=1.21.10 {
    }

}
    //?}
