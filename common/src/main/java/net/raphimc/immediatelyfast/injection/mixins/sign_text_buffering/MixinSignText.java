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
package net.raphimc.immediatelyfast.injection.mixins.sign_text_buffering;

//? if <=26.2 {
/*import net.minecraft.network.chat.Component;

*///?}
import net.minecraft.util.FormattedCharSequence;
//? if <=26.2 {
/*import net.minecraft.world.item.DyeColor;

*///?}
import net.minecraft.world.level.block.entity.SignText;
import net.raphimc.immediatelyfast.injection.interfaces.ISignText;
import org.jetbrains.annotations.Nullable;
import org.objectweb.asm.Opcodes;
//? if <=26.2 {
/*import org.spongepowered.asm.mixin.Final;

*///?}
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if <=26.2 {
/*import java.util.Arrays;
import java.util.Objects;


*///?}
@Mixin(SignText.class)
public abstract class MixinSignText implements ISignText {
//? if <=26.2 {

    /*@Shadow
    @Final
    private Component[] messages;

    @Shadow
    @Final
    private Component[] filteredMessages;

    @Shadow
    @Final
    private DyeColor color;

    @Shadow
    @Final
    private boolean hasGlowingText;

*///?}

    @Shadow
    @Nullable
    private FormattedCharSequence[] renderMessages;

    @Unique
    private boolean immediatelyFast$shouldCache;

    @Unique
    private boolean immediatelyFast$checkedShouldCache;
//? if <=26.2 {

    /*@Unique
    private int immediatelyFast$cachedHashCode;

    @Unique
    private boolean immediatelyFast$calculatedHashCode;

*///?}

    @Inject(method = "getRenderMessages", at = @At("RETURN"))
    //? if >=26.2 {
    private void checkShouldCache(final CallbackInfoReturnable<FormattedCharSequence[]> cir) {
    //?}
    //? if <=26.1.2 {
    /*private void checkShouldCache(CallbackInfoReturnable<FormattedCharSequence[]> cir) {

    *///?}
        if (!this.immediatelyFast$checkedShouldCache) {
            this.immediatelyFast$checkedShouldCache = true;
            this.immediatelyFast$shouldCache = true;
            //? if >=1.21.11 {
            for (FormattedCharSequence line : this.renderMessages) {
            //?}
            //? if <=1.21.10 {
            /*for (FormattedCharSequence orderedText : this.renderMessages) {

            *///?}
                if (!this.immediatelyFast$shouldCache) {
                    break;
                }

                //? if >=26.2 {
                line.accept((_, style, _) -> {
                //?}
                //? if (>=1.21.11 && <=26.1.2) {
/*                line.accept((index, style, codePoint) -> {
*/
                //?}
                //? if <=1.21.10 {
                /*orderedText.accept((index, style, codePoint) -> {

                *///?}
                    if (style.isObfuscated()) {
                        this.immediatelyFast$shouldCache = false;
                        return false;
                    }

                    return true;
                });
            }
        }
    }

    @Inject(method = "getRenderMessages", at = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/entity/SignText;renderMessages:[Lnet/minecraft/util/FormattedCharSequence;", opcode = Opcodes.PUTFIELD))
    //? if >=26.2 {
    private void invalidateCache(final CallbackInfoReturnable<FormattedCharSequence[]> cir) {
    //?}
    //? if <=26.1.2 {
    /*private void invalidateCache(CallbackInfoReturnable<FormattedCharSequence[]> cir) {

    *///?}
        this.immediatelyFast$shouldCache = false;
        this.immediatelyFast$checkedShouldCache = false;
        //? if <=26.2 {
        /*this.immediatelyFast$cachedHashCode = 0;
        this.immediatelyFast$calculatedHashCode = false;

        *///?}
    }

    @Override
    public boolean immediatelyFast$shouldCache() {
        return this.immediatelyFast$shouldCache;
    }

    @Override
    public void immediatelyFast$setShouldCache(final boolean shouldCache) {
        this.immediatelyFast$shouldCache = shouldCache;
    }

    //? if <=26.2 {
    /*@Override

    *///?}
    //? if =26.2 {
/*    public boolean equals(final Object o) {
*/
    //?}
    //? if <=26.1.2 {
    /*public boolean equals(Object o) {

    *///?}
        //? if <=26.2 {
        /*if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        *///?}
        //? if =26.2 {
/*        final MixinSignText that = (MixinSignText) o;
*/
        //?}
        //? if <=26.1.2 {
        /*MixinSignText that = (MixinSignText) o;

        *///?}
        //? if <=26.2 {
        /*return hasGlowingText == that.hasGlowingText && color == that.color && Arrays.equals(messages, that.messages) && Arrays.equals(filteredMessages, that.filteredMessages);
    }

    @Override
    public int hashCode() {
        if (!this.immediatelyFast$calculatedHashCode) {
            this.immediatelyFast$calculatedHashCode = true;
            int result = Objects.hash(color, hasGlowingText);
            result = 31 * result + Arrays.hashCode(messages);
            result = 31 * result + Arrays.hashCode(filteredMessages);
            this.immediatelyFast$cachedHashCode = result;
        }

        return this.immediatelyFast$cachedHashCode;
    }


        *///?}
}
