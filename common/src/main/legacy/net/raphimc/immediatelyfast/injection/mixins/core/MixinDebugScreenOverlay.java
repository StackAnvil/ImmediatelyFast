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

import java.util.List;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
//? if (>=1.21.1 && <=1.21.4) {
/*import net.raphimc.immediatelyfast.feature.core.ByteBufferBuilderPool;
*///?}
//? if =1.20.4 {
/*import net.raphimc.immediatelyfast.feature.core.BufferBuilderPool;
*///?}

@Mixin(value = DebugScreenOverlay.class, priority = 9999)
public abstract class MixinDebugScreenOverlay {
    @Inject(method = "getSystemInformation", at = @At("RETURN"))
    private void appendAllocationInfo(final CallbackInfoReturnable<List<String>> cir) {
        if (ImmediatelyFast.config.dont_add_info_into_debug_hud) {
            return;
        }
        cir.getReturnValue().add("");
        cir.getReturnValue().add("ImmediatelyFast " + ImmediatelyFast.VERSION);
        //? if (>=1.21.1 && <=1.21.4) {
        /*cir.getReturnValue().add("Buffer Pool: " + ByteBufferBuilderPool.getSize());
        *///?}
        //? if =1.20.4 {
        /*cir.getReturnValue().add("Buffer Pool: " + BufferBuilderPool.getAllocatedSize());
        *///?}
    }
}
