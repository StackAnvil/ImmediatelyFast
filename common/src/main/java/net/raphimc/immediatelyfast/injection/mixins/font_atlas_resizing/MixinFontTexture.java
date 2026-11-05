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

package net.raphimc.immediatelyfast.injection.mixins.font_atlas_resizing;

import net.minecraft.client.gui.font.FontTexture;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
//? if >=1.21.1 {
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//?}
//? if =1.20.4 {
/*import net.minecraft.client.gui.font.GlyphRenderTypes;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
*///?}

@Mixin(FontTexture.class)
public abstract class MixinFontTexture {
    @Unique
    private boolean immediatelyFast$shouldResizeFontAtlas;

    //? if >=1.21.10 {
    @Unique
    private int immediatelyFast$fontAtlasSize;

    //? if >=26.2 {
    @Inject(method = "<init>", at = @At(value = "CTOR_HEAD"))
    private void cacheConfigState(final CallbackInfo ci) {
    //?}
    //? if (>=1.21.10 && <=26.1.2) {
    /*@Inject(method = "<init>", at = @At(value = "CTOR_HEAD", unsafe = true))
    private void cacheConfigState(final CallbackInfo ci) {
    *///?}
        this.immediatelyFast$shouldResizeFontAtlas = ImmediatelyFast.runtimeConfig.font_atlas_resizing;
        this.immediatelyFast$fontAtlasSize = ImmediatelyFast.config.font_atlas_size;
    }
    //?}

    //? if >=1.21.11 {
    @ModifyConstant(method = "*", constant = @Constant(intValue = 256))
    private int modifyTextureSize(final int original) {
        return this.immediatelyFast$shouldResizeFontAtlas ? this.immediatelyFast$fontAtlasSize : 256;
    }

    @SuppressWarnings("MixinAnnotationTarget")
    @ModifyConstant(method = "*", constant = @Constant(floatValue = 256F))
    private float modifyTextureSize(final float original) {
        return this.immediatelyFast$shouldResizeFontAtlas ? this.immediatelyFast$fontAtlasSize : 256F;
    }
    //?}

    //? if <=1.21.4 {
    /*//? if (>=1.21.1 && <=1.21.4) {
    /^@Inject(method = "<init>", at = @At(value = "CTOR_HEAD", unsafe = true))
    private void checkFontAtlasResizing(final CallbackInfo ci) {
    ^///?}
    //? if =1.20.4 {
    /^@ModifyVariable(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/texture/AbstractTexture;<init>()V", shift = At.Shift.AFTER), argsOnly = true)
    private GlyphRenderTypes checkFontAtlasResizing(final GlyphRenderTypes value) {
    ^///?}
        this.immediatelyFast$shouldResizeFontAtlas = ImmediatelyFast.runtimeConfig.font_atlas_resizing;
        //? if =1.20.4 {
        /^return value;
        ^///?}
    }
    *///?}

    //? if <=1.21.10 {
    /*@ModifyConstant(method = "*", constant = @Constant(intValue = 256))
    private int modifyGlyphAtlasTextureSize(final int original) {
        //? if =1.21.10 {
        /^return this.immediatelyFast$shouldResizeFontAtlas ? this.immediatelyFast$fontAtlasSize : 256;
        ^///?}
        //? if <=1.21.4 {
        /^return this.immediatelyFast$shouldResizeFontAtlas ? 2048 : 256;
        ^///?}
    }

    //? if (>=1.21.4 && <=1.21.10) {
    /^@SuppressWarnings("MixinAnnotationTarget")
    @ModifyConstant(method = "*", constant = @Constant(floatValue = 256F))
    private float modifyGlyphAtlasTextureSize(final float original) {
    ^///?}
    //? if <=1.21.1 {
    /^@ModifyConstant(method = "*", constant = @Constant(floatValue = 256F))
    private float modifyGlyphAtlasTextureSize(final float original) {
    ^///?}
        //? if =1.21.10 {
        /^return this.immediatelyFast$shouldResizeFontAtlas ? this.immediatelyFast$fontAtlasSize : 256F;
        ^///?}
        //? if <=1.21.4 {
        /^return this.immediatelyFast$shouldResizeFontAtlas ? 2048F : 256F;
        ^///?}
    }
    *///?}
}
