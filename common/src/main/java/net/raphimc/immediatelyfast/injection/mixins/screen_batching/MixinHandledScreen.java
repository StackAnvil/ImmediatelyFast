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
package net.raphimc.immediatelyfast.injection.mixins.screen_batching;


*///?}
//? if =1.21.4 {
/*import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
*/
//?}
//? if (>=1.21.1 && <=1.21.4) {
/*import net.minecraft.client.gui.GuiGraphics;

*///?}
//? if <=1.21.4 {
/*import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;

*///?}
//? if =1.21.4 {
/*import net.raphimc.immediatelyfast.ImmediatelyFast;
*/
//?}
//? if =1.21.1 {
/*import net.minecraft.client.renderer.MultiBufferSource;

*///?}
//? if <=1.21.4 {
/*import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
import org.spongepowered.asm.mixin.Mixin;

*///?}
//? if =1.21.1 {
/*import org.spongepowered.asm.mixin.Unique;

*///?}
//? if <=1.21.4 {
/*import org.spongepowered.asm.mixin.injection.At;

*///?}
//? if <=1.21.1 {
/*import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

*///?}

//? if =1.21.4 {
/*@Mixin(AbstractContainerScreen.class)
*/
//?}
//? if <=1.21.1 {
/*@Mixin(value = AbstractContainerScreen.class, priority = 500)

*///?}
//? if <=1.21.4 {
/*public abstract class MixinHandledScreen {


*///?}
    //? if =1.21.4 {
/*    @WrapOperation(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderSlots(Lnet/minecraft/client/gui/GuiGraphics;)V"))
    private void batchContainerItems(AbstractContainerScreen<?> instance, GuiGraphics drawContext, Operation<Void> original) {
        if (ImmediatelyFast.runtimeConfig.experimental_screen_batching) {
            BatchingBuffers.runBatched(drawContext, () -> original.call(instance, drawContext));
        } else {
            original.call(instance, drawContext);
        }
*/
    //?}
    //? if =1.21.1 {
    /*@Unique
    private MultiBufferSource.BufferSource immediatelyFast$prevVertexConsumers;


    *///?}
    //? if <=1.21.1 {
    /*@Inject(method = "render", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;hoveredSlot:Lnet/minecraft/world/inventory/Slot;", ordinal = 0))

    *///?}
    //? if =1.21.1 {
    /*private void beginBatching(GuiGraphics drawContext, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.immediatelyFast$prevVertexConsumers = BatchingBuffers.beginHudBatching(drawContext);

    *///?}
    //? if =1.20.4 {
/*    private void beginBatching(CallbackInfo ci) {
        BatchingBuffers.beginHudBatching();
*/
    //?}
    //? if <=1.21.1 {
    /*}

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screens/inventory/AbstractContainerScreen;renderLabels(Lnet/minecraft/client/gui/GuiGraphics;II)V"))

    *///?}
    //? if =1.21.1 {
    /*private void endBatching(GuiGraphics context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        BatchingBuffers.endHudBatching(context, this.immediatelyFast$prevVertexConsumers);

    *///?}
    //? if =1.20.4 {
/*    private void endBatching(CallbackInfo ci) {
        BatchingBuffers.endHudBatching();
*/
    //?}
    //? if <=1.21.4 {
    /*}

}

    *///?}
