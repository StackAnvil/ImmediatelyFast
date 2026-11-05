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

package net.raphimc.immediatelyfast.injection.mixins.screen_batching;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
//? if (>=1.21.1 && <=1.21.4) {
/*import net.minecraft.client.gui.GuiGraphics;
*///?}
//? if <=1.21.1 {
/*import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?}
//? if =1.21.4 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
*///?}
//? if =1.21.1 {
/*import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Unique;
*///?}

//? if =1.21.4 {
/*@Mixin(AbstractContainerScreen.class)
public abstract class MixinAbstractContainerScreen {
*///?}
//? if <=1.21.1 {
/*@Mixin(value = AbstractContainerScreen.class, priority = 500)
public abstract class MixinAbstractContainerScreen {
*///?}
    //? if =1.21.4 {
    /*@WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderSlots(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private void batchContainerItems(final AbstractContainerScreen<?> instance, final GuiGraphics drawContext, final Operation<Void> original) {
        if (ImmediatelyFast.runtimeConfig.experimental_screen_batching) {
            BatchingBuffers.runBatched(drawContext, () -> original.call(instance, drawContext));
        } else {
            original.call(instance, drawContext);
        }
    }
    *///?}

    //? if =1.21.1 {
    /*@Unique
    private MultiBufferSource.BufferSource immediatelyFast$prevVertexConsumers;

    @Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;hoveredSlot:Lnet/minecraft/world/inventory/Slot;", ordinal = 0))
    private void beginBatching(final GuiGraphics drawContext, final int mouseX, final int mouseY, final float delta, final CallbackInfo ci) {
        this.immediatelyFast$prevVertexConsumers = BatchingBuffers.beginHudBatching(drawContext);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderLabels(Lnet/minecraft/client/gui/GuiGraphics;II)V"))
    private void endBatching(final GuiGraphics context, final int mouseX, final int mouseY, final float delta, final CallbackInfo ci) {
        BatchingBuffers.endHudBatching(context, this.immediatelyFast$prevVertexConsumers);
    }
    *///?}

    //? if =1.20.4 {
    /*@Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;hoveredSlot:Lnet/minecraft/world/inventory/Slot;", ordinal = 0))
    private void beginBatching(final CallbackInfo ci) {
        BatchingBuffers.beginHudBatching();
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderLabels(Lnet/minecraft/client/gui/GuiGraphics;II)V"))
    private void endBatching(final CallbackInfo ci) {
        BatchingBuffers.endHudBatching();
    }
    *///?}
}
