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

package net.raphimc.immediatelyfast.compat;

import java.util.List;
//? if =1.21.4 {
/*import net.minecraft.client.renderer.CoreShaders;
import net.minecraft.client.renderer.ShaderProgram;
*///?}

public class CoreShaderBlacklist {
    //? if =1.21.4 {
    /*private static final List<ShaderProgram> BLACKLIST = List.of(
        CoreShaders.POSITION_COLOR,
        CoreShaders.POSITION_TEX,
        CoreShaders.POSITION_TEX_COLOR,
        CoreShaders.RENDERTYPE_TEXT,
        CoreShaders.RENDERTYPE_TEXT_BACKGROUND,
        CoreShaders.RENDERTYPE_TEXT_BACKGROUND_SEE_THROUGH,
        CoreShaders.RENDERTYPE_TEXT_INTENSITY,
        CoreShaders.RENDERTYPE_TEXT_INTENSITY_SEE_THROUGH,
        CoreShaders.RENDERTYPE_TEXT_SEE_THROUGH,
        CoreShaders.RENDERTYPE_ITEM_ENTITY_TRANSLUCENT_CULL
    );
    *///?}
    //? if <=1.21.1 {
    /*private static final List<String> BLACKLIST = List.of(
        "position_color",
        "position_tex",
        "position_tex_color",
        "rendertype_text",
        "rendertype_text_background",
        "rendertype_text_background_see_through",
        "rendertype_text_intensity",
        "rendertype_text_intensity_see_through",
        "rendertype_text_see_through",
        "rendertype_entity_translucent_cull",
        "rendertype_item_entity_translucent_cull"
    );
    *///?}

    //? if <=1.21.1 {
    /*public static boolean isBlacklisted(final String name) {
        return BLACKLIST.contains(name);
    }
    *///?}

    //? if =1.21.4 {
    /*public static List<ShaderProgram> getBlacklist() {
    *///?}
    //? if <=1.21.1 {
    /*public static List<String> getBlacklist() {
    *///?}
        return BLACKLIST;
    }
}
