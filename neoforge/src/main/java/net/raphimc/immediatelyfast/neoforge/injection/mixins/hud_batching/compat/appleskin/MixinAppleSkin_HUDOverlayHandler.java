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
package net.raphimc.immediatelyfast.neoforge.injection.mixins.hud_batching.compat.appleskin;


*///?}
//? if (>=1.21.1 && <=1.21.4) {
/*import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.gui.GuiGraphics;

*///?}
//? if <=1.21.4 {
/*import net.raphimc.immediatelyfast.ImmediatelyFast;

*///?}
//? if =1.20.4 {
/*import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
*/
//?}
//? if <=1.21.4 {
/*import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "squeek.appleskin.client.HUDOverlayHandler", remap = false)
@Pseudo
public abstract class MixinAppleSkin_HUDOverlayHandler {


*///?}
    //? if (>=1.21.1 && <=1.21.4) {
    /*@Inject(method = "drawExhaustionOverlay(FLnet/minecraft/world/entity/player/Player;Lnet/minecraft/client/gui/GuiGraphics;IIF)V", at = @At("RETURN"), remap = true)
    private static void forceDrawBatch(CallbackInfo ci, @Local(argsOnly = true) GuiGraphics drawContext) {
        if (ImmediatelyFast.runtimeConfig.hud_batching) {
            drawContext.flush();

    *///?}
    //? if =1.20.4 {
/*    @Inject(method = "drawExhaustionOverlay", at = @At("RETURN"))
    private static void forceDrawBatch(CallbackInfo ci) {
        if (ImmediatelyFast.runtimeConfig.hud_batching && BatchingBuffers.isHudBatching()) {
            BatchingBuffers.endHudBatching();
            BatchingBuffers.beginHudBatching();
*/
    //?}
        //? if <=1.21.4 {
        /*}
    }

}

        *///?}
