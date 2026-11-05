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
package net.raphimc.immediatelyfast.injection.mixins.core.compat;

import com.mojang.blaze3d.shaders.CompiledShader;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.CompiledShaderProgram;
import net.raphimc.immediatelyfast.injection.interfaces.IShaderProgram;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CompiledShaderProgram.class)
public abstract class MixinShaderProgram implements IShaderProgram {

    @Unique
    private CompiledShader immediatelyFast$vertexShader;

    @Unique
    private CompiledShader immediatelyFast$fragmentShader;

    @Inject(method = "link", at = @At("RETURN"))
    private static void storeShaderReferences(final CompiledShader vertexShader, final CompiledShader fragmentShader, final VertexFormat format, final CallbackInfoReturnable<CompiledShaderProgram> cir) {
        if (cir.getReturnValue() instanceof IShaderProgram mixinShaderProgram) {
            mixinShaderProgram.immediatelyFast$setVertexShader(vertexShader);
            mixinShaderProgram.immediatelyFast$setFragmentShader(fragmentShader);
        }
    }

    @Override
    public CompiledShader immediatelyFast$getVertexShader() {
        return this.immediatelyFast$vertexShader;
    }

    @Override
    public void immediatelyFast$setVertexShader(final CompiledShader vertexShader) {
        this.immediatelyFast$vertexShader = vertexShader;
    }

    @Override
    public CompiledShader immediatelyFast$getFragmentShader() {
        return this.immediatelyFast$fragmentShader;
    }

    @Override
    public void immediatelyFast$setFragmentShader(final CompiledShader fragmentShader) {
        this.immediatelyFast$fragmentShader = fragmentShader;
    }

}
