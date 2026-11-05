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

//? if (>=1.21.1 && <=1.21.4) {
/*package net.raphimc.immediatelyfast.injection.mixins.hud_batching.compat;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.raphimc.immediatelyfast.feature.batching.HudBatchingBufferSource;
import net.raphimc.immediatelyfast.feature.core.BatchableBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//? if =1.21.1 {
/^import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.Font;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.Slice;
^///?}

@Mixin(GuiGraphics.class)
public abstract class MixinGuiGraphics {
    @Shadow
    public abstract void flush();

    @Shadow
    public MultiBufferSource.BufferSource bufferSource;

    //? if =1.21.1 {
    /^@Shadow
    @Final
    private PoseStack pose;

    @Shadow
    protected abstract void flushIfManaged();

    @WrapMethod(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V")
    private void renderItemDecorations(final Font textRenderer, final ItemStack stack, final int x, final int y, final String countOverride, final Operation<Void> original) {
        if (this.bufferSource instanceof HudBatchingBufferSource hudBatchingBufferSource) {
            hudBatchingBufferSource.setRenderingItemDecorations(true);
        }
        try {
            original.call(textRenderer, stack, x, y, countOverride);
        } finally {
            if (this.bufferSource instanceof HudBatchingBufferSource hudBatchingBufferSource) {
                hudBatchingBufferSource.setRenderingItemDecorations(false);
            }
        }
    }

    @WrapMethod(method = "flush()V")
    private void restoreDepthTestState(final Operation<Void> original) {
        final boolean currentDepthTestState = GlStateManager.DEPTH.mode.enabled;
        original.call();
        if (this.bufferSource instanceof BatchableBufferSource && GlStateManager.DEPTH.mode.enabled != currentDepthTestState) {
            if (currentDepthTestState) {
                RenderSystem.enableDepthTest();
            } else {
                RenderSystem.disableDepthTest();
            }
        }
    }
    ^///?}

    //? if =1.21.4 {
    /^@Inject(method = "renderItemCooldown", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(Lnet/minecraft/client/renderer/rendertype/RenderType;IIIIII)V"))
    private void forceDraw(final CallbackInfo ci) {
    ^///?}
    //? if =1.21.1 {
    /^@Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;getCooldownPercent(Lnet/minecraft/world/item/Item;F)F")), at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;fill(Lnet/minecraft/client/renderer/rendertype/RenderType;IIIII)V"))
    private void forceDraw(final CallbackInfo ci) {
    ^///?}
        if (this.bufferSource instanceof BatchableBufferSource) {
            this.flush();
        }
    }

    //? if =1.21.1 {
    /^@Redirect(method = "applyScissor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;flushIfManaged()V"))
    private void drawIfBatching(final GuiGraphics instance) {
        if (this.bufferSource instanceof BatchableBufferSource) {
            this.flush();
        } else {
            this.flushIfManaged();
        }
    }

    @Inject(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;pushPose()V", shift = At.Shift.AFTER))
    private void translateZForAllItemOverlays(final CallbackInfo ci) {
        if (this.bufferSource instanceof BatchableBufferSource) {
            this.pose.translate(0F, 0F, 200F);
        }
    }

    @WrapWithCondition(method = "renderItemDecorations(Lnet/minecraft/client/gui/Font;Lnet/minecraft/world/item/ItemStack;IILjava/lang/String;)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/PoseStack;translate(FFF)V"))
    private boolean translateZEarlier(final PoseStack instance, final float x, final float y, final float z) {
        return !(this.bufferSource instanceof BatchableBufferSource);
    }
    ^///?}
}
*///?}
