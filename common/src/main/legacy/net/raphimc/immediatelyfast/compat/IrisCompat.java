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

import it.unimi.dsi.fastutil.booleans.BooleanConsumer;
import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.function.BooleanSupplier;
import net.lenni0451.reflect.accessor.FieldAccessor;
import net.raphimc.immediatelyfast.ImmediatelyFast;
//? if (>=1.21.1 && <=1.21.4) {
/*import net.lenni0451.reflect.stream.RStream;
*///?}
//? if =1.20.4 {
/*import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.VertexFormat;
*///?}

public class IrisCompat {
    public static boolean IRIS_LOADED = false;

    public static BooleanSupplier isRenderingLevel;

    public static BooleanConsumer renderWithExtendedVertexFormat;

    //? if (>=1.21.1 && <=1.21.4) {
    /*public static ThreadLocal<Boolean> skipExtension;
    *///?}

    //? if =1.20.4 {
    /*public static TriConsumer<BufferBuilder, VertexFormat.Mode, VertexFormat> iris$beginWithoutExtending;

    private static String[] PACKAGE_NAMES = new String[]{
        "net.coderbot.iris.vertices",
        "net.irisshaders.iris.vertices"
    };
    *///?}

    public static void init() {
        IRIS_LOADED = true;
        //? if (>=1.21.1 && <=1.21.4) {
        /*try {
            final Class<?> immediateStateClass = Class.forName("net.irisshaders.iris.vertices.ImmediateState");

            isRenderingLevel = FieldAccessor.makeGetter(BooleanSupplier.class, null, immediateStateClass.getDeclaredField("isRenderingLevel"));
            renderWithExtendedVertexFormat = FieldAccessor.makeSetter(BooleanConsumer.class, null, immediateStateClass.getDeclaredField("renderWithExtendedVertexFormat"));
            skipExtension = RStream.of(immediateStateClass).fields().by("skipExtension").get();
        } catch (Throwable t) {
            ImmediatelyFast.LOGGER.error("Failed to initialize Iris compatibility. Try updating Iris and ImmediatelyFast before reporting this on GitHub", t);
            System.exit(-1);
        }
        *///?}
        //? if =1.20.4 {
        /*Throwable throwable = null;
        for (int i = 0; i < PACKAGE_NAMES.length; i++) {
            String packageName = PACKAGE_NAMES[i];
            try {
                final MethodHandles.Lookup lookup = MethodHandles.lookup();
                final Class<?> immediateStateClass = Class.forName(packageName + ".ImmediateState");
                final Class<?> extendingBufferBuilderClass = Class.forName(packageName + ".ExtendingBufferBuilder");

                isRenderingLevel = FieldAccessor.makeGetter(BooleanSupplier.class, null, immediateStateClass.getDeclaredField("isRenderingLevel"));
                renderWithExtendedVertexFormat = FieldAccessor.makeSetter(BooleanConsumer.class, null, immediateStateClass.getDeclaredField("renderWithExtendedVertexFormat"));

                final MethodHandle iris$beginWithoutExtendingMH = lookup.findVirtual(extendingBufferBuilderClass, "iris$beginWithoutExtending", MethodType.methodType(void.class, VertexFormat.Mode.class, VertexFormat.class));
                final CallSite iris$beginWithoutExtendingCallSite = LambdaMetafactory.metafactory(lookup, "accept", MethodType.methodType(TriConsumer.class), MethodType.methodType(void.class, Object.class, Object.class, Object.class), iris$beginWithoutExtendingMH, iris$beginWithoutExtendingMH.type());
                iris$beginWithoutExtending = (TriConsumer<BufferBuilder, VertexFormat.Mode, VertexFormat>) iris$beginWithoutExtendingCallSite.getTarget().invoke();
                return;
            } catch (Throwable t) {
                throwable = t;
            }
        }
        ImmediatelyFast.LOGGER.error("Failed to initialize Iris compatibility. Try updating Iris and ImmediatelyFast before reporting this on GitHub", throwable);
        System.exit(-1);
        *///?}
    }
}
