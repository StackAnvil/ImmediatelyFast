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
/*package net.raphimc.immediatelyfast.injection.mixins.core.compat.kupe;

import java.util.function.Function;
import net.lenni0451.reflect.accessor.FieldAccessor;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderBuffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("UnresolvedMixinReference")
@Mixin(targets = "cc.cosmetica.kupe.impl.text.FormattedCharSeqRenderer", remap = false)
@Pseudo
public abstract class MixinKupe_FormattedCharSeqRenderer {

    @Unique
    private static final Function<Object, GuiGraphics> DRAW_CONTEXT_EXTRACTOR;

    static {
        try {
            final Class<?> poseCanvasClass = Class.forName("cc.cosmetica.kupe.impl.PoseCanvas");
            DRAW_CONTEXT_EXTRACTOR = FieldAccessor.makeDynamicGetter(Function.class, poseCanvasClass.getDeclaredField("graphics"));
        } catch (Throwable t) {
            throw new RuntimeException("Failed to initialize Kupe compatibility. Try updating Kupe and ImmediatelyFast before reporting this on GitHub", t);
        }
    }

    @Unique
    private Object immediatelyFast$canvas;

    @Inject(method = "render", at = @At("HEAD"))
    private void captureCanvas(final @Coerce Object canvas, final int x, final int y, final int colour, final CallbackInfo ci) {
        this.immediatelyFast$canvas = canvas;
    }

    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/RenderBuffers;bufferSource()Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;"))
    private MultiBufferSource.BufferSource useCorrectVertexConsumerProvider(final RenderBuffers instance) {
        return DRAW_CONTEXT_EXTRACTOR.apply(this.immediatelyFast$canvas).bufferSource;
    }

}
*///?}
