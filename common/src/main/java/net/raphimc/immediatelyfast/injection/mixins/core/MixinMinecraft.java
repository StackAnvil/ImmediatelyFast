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

import net.minecraft.client.Minecraft;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    //? if =26.3 {
    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;initRenderer(Lcom/mojang/renderpearl/api/device/GpuDevice;)V", shift = At.Shift.AFTER))
    //?}
    //? if =26.2 {
/*    @Inject(method = "<init>", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;initRenderer(Lcom/mojang/blaze3d/systems/GpuDevice;)V", shift = At.Shift.AFTER))
*/
    //?}
    //? if >=26.2 {
    private static void hookRendererInit(final CallbackInfo ci) {
        ImmediatelyFast.onRenderSystemInit();
    }

    //?}
    @Inject(method = "<init>", at = @At("RETURN"))
    //? if >=26.2 {
    private void hookConstructor(final CallbackInfo ci) {
    //?}
    //? if <=26.1.2 {
    /*private void initImmediatelyFast(CallbackInfo ci) {

    *///?}
        ImmediatelyFast.lateInit();
    }

    @Inject(method = "setLevel", at = @At("HEAD"))
    //? if >=26.2 {
    private void hookLevelChange(final CallbackInfo ci) {
    //?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private void hookLevelChange(CallbackInfo ci) {
*/
    //?}
        //? if >=1.21.11 {
        ImmediatelyFast.onLevelChange();
        //?}
    //? if <=1.21.10 {
    /*private void callOnWorldJoin(CallbackInfo ci) {
        ImmediatelyFast.onWorldJoin();

    *///?}
    }

}
