//? if >=1.21.11 {
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
package net.raphimc.immediatelyfast.util;

import com.mojang.blaze3d.pipeline.RenderTarget;
//?}
//? if >=26.2 {
import com.mojang.blaze3d.platform.TextureUtil;
//?}
//? if >=1.21.11 {
import com.mojang.blaze3d.systems.RenderSystem;
//?}
//? if =26.3 {
import com.mojang.renderpearl.api.textures.AddressMode;
import com.mojang.renderpearl.api.textures.FilterMode;
//?}
//? if (>=1.21.11 && <=26.2) {
/*import com.mojang.blaze3d.textures.AddressMode;
import com.mojang.blaze3d.textures.FilterMode;
*/
//?}
//? if >=1.21.11 {
import net.minecraft.client.renderer.texture.AbstractTexture;
//?}
//? if >=26.2 {
import net.minecraft.client.renderer.texture.Dumpable;
import net.minecraft.resources.Identifier;
//?}

//? if >=26.2 {
import java.nio.file.Path;

public class RenderTargetTexture extends AbstractTexture implements Dumpable {
//?}
//? if (>=1.21.11 && <=26.1.2) {
/*public class RenderTargetTexture extends AbstractTexture {
*/
//?}
//? if >=1.21.11 {

    public RenderTargetTexture(final RenderTarget renderTarget) {
        this.texture = renderTarget.getColorTexture();
        this.textureView = renderTarget.getColorTextureView();
        this.sampler = RenderSystem.getSamplerCache().getSampler(AddressMode.CLAMP_TO_EDGE, AddressMode.CLAMP_TO_EDGE, FilterMode.NEAREST, FilterMode.NEAREST, false);
    }

    @Override
    public void close() {
    }

//?}
    //? if >=26.2 {
    @Override
    public void dumpContents(final Identifier selfId, final Path dir) {
        TextureUtil.writeAsPNG(dir, selfId.toDebugFileName(), this.getTexture(), 0, argb -> argb);
    }

    //?}
//? if >=1.21.11 {
}
//?}
