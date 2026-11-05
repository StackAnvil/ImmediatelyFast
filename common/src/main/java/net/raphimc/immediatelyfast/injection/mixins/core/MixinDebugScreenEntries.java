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
package net.raphimc.immediatelyfast.injection.mixins.core;

import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.gui.components.debug.DebugScreenEntry;
import net.minecraft.client.gui.components.debug.DebugScreenEntryStatus;
import net.minecraft.client.gui.components.debug.DebugScreenProfile;
//?}
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
//? if =1.21.10 {
/*import net.minecraft.resources.ResourceLocation;
*/
//?}
//? if >=1.21.10 {
import net.raphimc.immediatelyfast.feature.core.ImmediatelyFastDebugScreenEntry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;
import java.util.Map;

@Mixin(DebugScreenEntries.class)
public abstract class MixinDebugScreenEntries {

    @Shadow
    @Final
    @Mutable
//?}
    //? if >=1.21.11 {
    public static Map<DebugScreenProfile, Map<Identifier, DebugScreenEntryStatus>> PROFILES;
    //?}
    //? if =1.21.10 {
/*    public static Map<DebugScreenProfile, Map<ResourceLocation, DebugScreenEntryStatus>> PROFILES;
*/
    //?}
//? if >=1.21.10 {

    @Shadow
//?}
    //? if >=26.2 {
    private static Identifier register(final Identifier identifier, final DebugScreenEntry entry) {
    //?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private static Identifier register(Identifier name, DebugScreenEntry entry) {
*/
    //?}
    //? if =1.21.10 {
/*    private static ResourceLocation register(ResourceLocation id, DebugScreenEntry entry) {
*/
    //?}
        //? if >=1.21.10 {
        return null;
    }

    @Inject(method = "<clinit>", at = @At("RETURN"))
        //?}
    //? if >=26.2 {
    private static void addImmediatelyFastEntry(final CallbackInfo ci) {
    //?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    private static void addImmediatelyFastEntry(CallbackInfo ci) {
*/
    //?}
        //? if >=1.21.11 {
        final Identifier entryId = register(ImmediatelyFastDebugScreenEntry.ENTRY_ID, new ImmediatelyFastDebugScreenEntry());
        final Map<DebugScreenProfile, Map<Identifier, DebugScreenEntryStatus>> profiles = new HashMap<>();
        for (Map.Entry<DebugScreenProfile, Map<Identifier, DebugScreenEntryStatus>> entry : PROFILES.entrySet()) {
            final Map<Identifier, DebugScreenEntryStatus> entries = new HashMap<>(entry.getValue());
            entries.put(entryId, DebugScreenEntryStatus.IN_OVERLAY);
        //?}
        //? if =1.21.10 {
/*        final ResourceLocation entryId = register(ImmediatelyFastDebugScreenEntry.ENTRY_ID, new ImmediatelyFastDebugScreenEntry());
        final Map<DebugScreenProfile, Map<ResourceLocation, DebugScreenEntryStatus>> profiles = new HashMap<>();
        for (Map.Entry<DebugScreenProfile, Map<ResourceLocation, DebugScreenEntryStatus>> entry : PROFILES.entrySet()) {
            final Map<ResourceLocation, DebugScreenEntryStatus> entries = new HashMap<>(entry.getValue());
            entries.put(entryId, DebugScreenEntryStatus.IN_F3);
*/
        //?}
            //? if >=1.21.10 {
            profiles.put(entry.getKey(), entries);
        }
        PROFILES = profiles;
    }

}
            //?}
