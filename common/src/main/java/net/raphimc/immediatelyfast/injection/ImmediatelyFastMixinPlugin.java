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

package net.raphimc.immediatelyfast.injection;

import java.util.List;
import java.util.Set;
import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.service.PlatformService;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
//? if =1.20.4 {
/*import net.raphimc.immediatelyfast.injection.processors.InjectAboveEverythingProcessor;
import net.raphimc.immediatelyfast.injection.processors.InjectOnAllReturnsProcessor;
*///?}

public class ImmediatelyFastMixinPlugin implements IMixinConfigPlugin {
    private String mixinPackage;

    @Override
    public void onLoad(final String mixinPackage) {
        this.mixinPackage = mixinPackage + ".";

        ImmediatelyFast.earlyInit();
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(final String targetClassName, final String mixinClassName) {
        if (!mixinClassName.startsWith(this.mixinPackage)) {
            return false;
        }
        final String mixinName = mixinClassName.substring(this.mixinPackage.length());
        final String packageName = mixinName.substring(0, mixinName.lastIndexOf('.'));
        //? if >=1.21.10 {
        if (!ImmediatelyFast.config.enhanced_batching && packageName.startsWith("enhanced_batching")) {
            return false;
        }
        //?}
        if (!ImmediatelyFast.config.font_atlas_resizing && packageName.startsWith("font_atlas_resizing")) {
            return false;
        }
        if (!ImmediatelyFast.config.map_atlas_generation && packageName.startsWith("map_atlas_generation")) {
            return false;
        }
        //? if >=1.21.10 {
        if (!ImmediatelyFast.config.skip_text_translucency_sorting && packageName.startsWith("skip_text_translucency_sorting")) {
            return false;
        }
        //?}
        //? if <=1.21.4 {
        /*if (!ImmediatelyFast.config.hud_batching && packageName.startsWith("hud_batching")) {
            return false;
        }
        *///?}
        if (!ImmediatelyFast.config.fast_text_lookup && packageName.startsWith("fast_text_lookup")) {
            return false;
        }
        //? if >=1.21.10 {
        if (!ImmediatelyFast.config.avoid_redundant_framebuffer_switching && packageName.startsWith("avoid_redundant_framebuffer_switching")) {
            return false;
        }
        if (!ImmediatelyFast.config.fix_slow_buffer_upload_on_apple_gpu && packageName.startsWith("fix_slow_buffer_upload_on_apple_gpu")) {
            return false;
        }
        //?}
        //? if >=26.1.2 {
        if (!ImmediatelyFast.config.batch_animated_item_updates && packageName.startsWith("batch_animated_item_updates")) {
            return false;
        }
        //?}
        //? if >=1.21.11 {
        if (!ImmediatelyFast.config.fast_gui_layer_lookup && packageName.startsWith("fast_gui_layer_lookup")) {
            return false;
        }
        //?}
        //? if >=1.21.10 {
        if (ImmediatelyFast.config.experimental_disable_resource_pack_conflict_handling && packageName.startsWith("resource_pack_conflict_handling")) {
            return false;
        }
        //?}
        //? if <=1.21.4 {
        /*if (!ImmediatelyFast.config.fast_buffer_upload && packageName.startsWith("fast_buffer_upload")) {
            return false;
        }
        if (!ImmediatelyFast.config.experimental_disable_error_checking && packageName.startsWith("disable_error_checking")) {
            return false;
        }
        *///?}
        if (!ImmediatelyFast.config.experimental_sign_text_buffering && packageName.startsWith("sign_text_buffering")) {
            return false;
        }
        //? if >=1.21.10 {
        if (!ImmediatelyFast.config.debug_only_print_additional_error_information && packageName.startsWith("print_additional_error_information")) {
            return false;
        }
        //?}
        //? if >=26.2 {
        if (packageName.startsWith("enhanced_batching.compat.iris") && PlatformService.INSTANCE.getModVersion("iris").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/582
            return false;
        }
        //?}
        //? if <=1.21.4 {
        /*if (!ImmediatelyFast.config.experimental_screen_batching && packageName.startsWith("screen_batching")) {
            return false;
        }
        *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*if (packageName.startsWith("core.compat.iris") && PlatformService.INSTANCE.getModVersion("iris").isEmpty()) {
            return false;
        } else //? if =1.21.4 {
               /^if (packageName.startsWith("core.compat.kupe") && PlatformService.INSTANCE.getModVersion("kupe").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/485
            return false;
        } else if (packageName.startsWith("hud_batching.compat.appleskin") && PlatformService.INSTANCE.getModVersion("appleskin").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/314
            return false;
        }
               ^///?}
               //? if =1.21.1 {
               /^if (packageName.startsWith("core.compat.rei") && PlatformService.INSTANCE.getModVersion("roughlyenoughitems").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/571
            return false;
        } else if (packageName.startsWith("core.compat.kupe") && PlatformService.INSTANCE.getModVersion("kupe").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/485
            return false;
        } else if (packageName.startsWith("hud_batching.compat.appleskin") && PlatformService.INSTANCE.getModVersion("appleskin").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/314
            return false;
        } else if (packageName.startsWith("hud_batching.compat.journeymap") && PlatformService.INSTANCE.getModVersion("journeymap").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/316
            return false;
        } else if (packageName.startsWith("hud_batching.compat.xaerominimap") && PlatformService.INSTANCE.getModVersion("xaerominimap").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/319
            return false;
        } else if (packageName.startsWith("hud_batching.compat.ftbchunks") && PlatformService.INSTANCE.getModVersion("ftbchunks").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/318
            return false;
        } else if (packageName.startsWith("hud_batching.compat.irons_spellbooks") && PlatformService.INSTANCE.getModVersion("irons_spellbooks").isEmpty()) { // https://github.com/RaphiMC/ImmediatelyFast/issues/519
            return false;
        }
               ^///?}
        *///?}
        //? if =1.20.4 {
        /*if (packageName.startsWith("hud_batching.compat.armorchroma") && PlatformService.INSTANCE.getModVersion("armorchroma").isEmpty()) { // https://github.com/A5b84/armor-chroma-fabric
            return false;
        } else if (packageName.startsWith("hud_batching.compat.appleskin") && PlatformService.INSTANCE.getModVersion("appleskin").isEmpty()) { // https://github.com/squeek502/AppleSkin
            return false;
        } else if (packageName.startsWith("hud_batching.compat.highlighter") && PlatformService.INSTANCE.getModVersion("highlighter").isEmpty()) { // https://github.com/AHilyard/Highlighter
            return false;
        } else if (packageName.startsWith("hud_batching.compat.itemborders") && PlatformService.INSTANCE.getModVersion("itemborders").isEmpty()) { // https://github.com/AHilyard/ItemBorders
            return false;
        } else if (packageName.startsWith("core.compat.webdisplays") && PlatformService.INSTANCE.getModVersion("webdisplays").isEmpty()) { // https://github.com/CinemaMod/webdisplays
            return false;
        }
        *///?}
        return true;
    }

    @Override
    public void acceptTargets(final Set<String> myTargets, final Set<String> otherTargets) {
    }

    @Override
    public List<String> getMixins() {
        return null;
    }

    @Override
    public void preApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {
    }

    @Override
    public void postApply(final String targetClassName, final ClassNode targetClass, final String mixinClassName, final IMixinInfo mixinInfo) {
        //? if =1.20.4 {
        /*InjectAboveEverythingProcessor.process(targetClass);
        InjectOnAllReturnsProcessor.process(targetClass);
        *///?}
    }
}
