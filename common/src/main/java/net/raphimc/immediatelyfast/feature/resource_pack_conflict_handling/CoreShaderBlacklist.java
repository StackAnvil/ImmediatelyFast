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
package net.raphimc.immediatelyfast.feature.resource_pack_conflict_handling;

//?}
//? if =26.3 {
import com.mojang.renderpearl.api.pipeline.ShaderType;
//?}
//? if (>=1.21.10 && <=26.2) {
/*import com.mojang.blaze3d.shaders.ShaderType;
*/
//?}
//? if =1.21.10 {
/*import java.util.Set;
*/
//?}
//? if >=1.21.10 {
import net.minecraft.client.Minecraft;
//?}
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//?}
//? if =1.21.10 {
/*import net.minecraft.resources.ResourceLocation;
*/
//?}
//? if >=1.21.10 {
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;

//?}
//? if >=1.21.11 {
import java.util.Set;

//?}
//? if >=1.21.10 {
public class CoreShaderBlacklist {

//?}
    //? if >=1.21.11 {
    private static final Set<Identifier> BLACKLIST = Set.of(
    //?}
        //? if =26.3 {
        Identifier.withDefaultNamespace("core/text")
        //?}
        //? if =26.2 {
/*        Identifier.withDefaultNamespace("core/text"),
        Identifier.withDefaultNamespace("core/text_background")
*/
        //?}
        //? if =1.21.11 {
/*        Identifier.withDefaultNamespace("core/position_color"),
        Identifier.withDefaultNamespace("core/position_tex"),
        Identifier.withDefaultNamespace("core/position_tex_color"),
*/
        //?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        Identifier.withDefaultNamespace("core/rendertype_text"),
        Identifier.withDefaultNamespace("core/rendertype_text_background"),
        Identifier.withDefaultNamespace("core/rendertype_text_background_see_through"),
        Identifier.withDefaultNamespace("core/rendertype_text_intensity"),
        Identifier.withDefaultNamespace("core/rendertype_text_intensity_see_through"),
*/
        //?}
        //? if =26.1.2 {
/*        Identifier.withDefaultNamespace("core/rendertype_text_see_through")
*/
        //?}
        //? if =1.21.11 {
/*        Identifier.withDefaultNamespace("core/rendertype_text_see_through"),
        Identifier.withDefaultNamespace("core/rendertype_item_entity_translucent_cull")
*/
        //?}
    //? if =1.21.10 {
/*    private static final Set<ResourceLocation> BLACKLIST = Set.of(
        ResourceLocation.withDefaultNamespace("core/position_color"),
        ResourceLocation.withDefaultNamespace("core/position_tex"),
        ResourceLocation.withDefaultNamespace("core/position_tex_color"),
        ResourceLocation.withDefaultNamespace("core/rendertype_text"),
        ResourceLocation.withDefaultNamespace("core/rendertype_text_background"),
        ResourceLocation.withDefaultNamespace("core/rendertype_text_background_see_through"),
        ResourceLocation.withDefaultNamespace("core/rendertype_text_intensity"),
        ResourceLocation.withDefaultNamespace("core/rendertype_text_intensity_see_through"),
        ResourceLocation.withDefaultNamespace("core/rendertype_text_see_through"),
        ResourceLocation.withDefaultNamespace("core/rendertype_item_entity_translucent_cull")
*/
    //?}
    //? if >=1.21.10 {
    );

    static {
        if (false /* Set to true if updating the game version to validate the identifiers */) {
            final ResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
    //?}
            //? if >=1.21.11 {
            for (Identifier shaderIdentifier : BLACKLIST) {
            //?}
            //? if =1.21.10 {
/*            for (ResourceLocation shaderIdentifier : BLACKLIST) {
*/
            //?}
                //? if >=1.21.10 {
                final Resource vertexShaderResource = resourceManager.getResource(ShaderType.VERTEX.idConverter().idToFile(shaderIdentifier)).orElse(null);
                if (vertexShaderResource == null) {
                    throw new RuntimeException("Couldn't find vertex shader " + shaderIdentifier);
                }
                final Resource fragmentShaderResource = resourceManager.getResource(ShaderType.FRAGMENT.idConverter().idToFile(shaderIdentifier)).orElse(null);
                if (fragmentShaderResource == null) {
                    throw new RuntimeException("Couldn't find fragment shader " + shaderIdentifier);
                }
            }
        }
    }

                //?}
    //? if >=1.21.11 {
    public static Set<Identifier> getBlacklist() {
    //?}
    //? if =1.21.10 {
/*    public static Set<ResourceLocation> getBlacklist() {
*/
    //?}
        //? if >=1.21.10 {
        return BLACKLIST;
    }

}
        //?}
