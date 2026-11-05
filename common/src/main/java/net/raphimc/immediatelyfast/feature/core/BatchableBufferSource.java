//? if <=26.1.2 {
/*/^
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
 ^/
package net.raphimc.immediatelyfast.feature.core;


*///?}
//? if =1.20.4 {
/*import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.systems.RenderSystem;
*/
//?}
//? if <=26.1.2 {
/*import com.mojang.blaze3d.vertex.BufferBuilder;

*///?}
//? if (>=1.21.1 && <=26.1.2) {
/*import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import com.mojang.blaze3d.vertex.MeshData;

*///?}
//? if <=26.1.2 {
/*import com.mojang.blaze3d.vertex.VertexConsumer;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

*///?}
//? if (>=1.21.1 && <=26.1.2) {
/*import it.unimi.dsi.fastutil.objects.Object2ObjectSortedMaps;

*///?}
//? if <=26.1.2 {
/*import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.Reference2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceLinkedOpenHashSet;
import it.unimi.dsi.fastutil.objects.ReferenceSet;
import net.minecraft.client.renderer.MultiBufferSource;

*///?}
//? if (>=1.21.11 && <=26.1.2) {
/*import net.minecraft.client.renderer.rendertype.RenderSetup;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.resources.Identifier;
*/
//?}
//? if <=1.21.10 {
/*import net.minecraft.client.renderer.RenderType;

*///?}
//? if <=1.21.4 {
/*import net.minecraft.client.renderer.Sheets;

*///?}
//? if (>=1.21.1 && <=1.21.4) {
/*import net.minecraft.client.renderer.entity.layers.WolfCollarLayer;

*///?}
//? if <=1.21.10 {
/*import net.minecraft.resources.ResourceLocation;

*///?}
//? if =1.20.4 {
/*import net.minecraft.world.item.HorseArmorItem;
*/
//?}
//? if <=26.1.2 {
/*import net.raphimc.immediatelyfast.ImmediatelyFast;

*///?}
//? if (>=1.21.10 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.util.IrisCompat;
*/
//?}
//? if <=1.21.4 {
/*import net.raphimc.immediatelyfast.compat.IrisCompat;

*///?}
//? if <=26.1.2 {

/*import java.util.Arrays;

*///?}
//? if =1.20.4 {
/*import java.util.Collections;
*/
//?}
//? if <=26.1.2 {
/*import java.util.Map;

*///?}
//? if (>=1.21.1 && <=26.1.2) {
/*import java.util.SequencedMap;

*///?}
//? if =1.20.4 {
/*import java.util.Optional;
*/
//?}
//? if <=26.1.2 {
/*import java.util.Set;

public class BatchableBufferSource extends MultiBufferSource.BufferSource implements AutoCloseable {

    /^*
     * A fallback buffer has to be defined because Iris tries to release that buffer, so it can't be null. It should be fine
     * to reuse/release the buffer multiple times, as it won't ever be written into by minecraft or Iris.
     ^/

*///?}
    //? if (>=1.21.1 && <=26.1.2) {
    /*private final static ByteBufferBuilder FALLBACK_BUFFER = new ByteBufferBuilder(0);

    *///?}
    //? if =1.20.4 {
/*    private final static BufferBuilder FALLBACK_BUFFER = new BufferBuilder(0);
*/
    //?}

    //? if (>=1.21.11 && <=26.1.2) {
/*    protected final Map<RenderType, ReferenceSet<BufferBuilder>> dynamicBuffers = IrisCompat.IRIS_LOADED ? new Object2ObjectLinkedOpenHashMap<>() : new Reference2ObjectLinkedOpenHashMap<>();
    protected final Set<RenderType> activeRenderTypes = IrisCompat.IRIS_LOADED ? new ObjectLinkedOpenHashSet<>() : new ReferenceLinkedOpenHashSet<>();
*/
    //?}
    //? if (>=1.21.1 && <=1.21.10) {
    /*protected final Map<RenderType, ReferenceSet<BufferBuilder>> pendingBuffers = IrisCompat.IRIS_LOADED ? new Object2ObjectLinkedOpenHashMap<>() : new Reference2ObjectLinkedOpenHashMap<>();

    *///?}
    //? if =1.20.4 {
/*    protected final Map<RenderType, ReferenceSet<BufferBuilder>> fallbackBuffers = IrisCompat.IRIS_LOADED ? new Object2ObjectLinkedOpenHashMap<>() : new Reference2ObjectLinkedOpenHashMap<>();
*/
    //?}
    //? if <=1.21.10 {
    /*protected final Set<RenderType> activeLayers = IrisCompat.IRIS_LOADED ? new ObjectLinkedOpenHashSet<>() : new ReferenceLinkedOpenHashSet<>();

    *///?}

    //? if (>=1.21.11 && <=26.1.2) {
/*    protected boolean drawDynamicBuffersFirst = false;
*/
    //?}
    //? if <=1.21.10 {
    /*protected boolean drawFallbackLayersFirst = false;

    *///?}
//? if <=26.1.2 {

    /*public BatchableBufferSource() {

*///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*this(Object2ObjectSortedMaps.emptyMap());

        *///?}
        //? if =1.20.4 {
/*        this(ImmutableMap.of());
*/
        //?}
    //? if <=26.1.2 {
    /*}


    *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public BatchableBufferSource(final SequencedMap<RenderType, ByteBufferBuilder> fixedBuffers) {
        this(FALLBACK_BUFFER, fixedBuffers);
*/
    //?}
    //? if (>=1.21.1 && <=1.21.10) {
    /*public BatchableBufferSource(final SequencedMap<RenderType, ByteBufferBuilder> layerBuffers) {

    *///?}
    //? if =1.20.4 {
/*    public BatchableBufferSource(final Map<RenderType, BufferBuilder> layerBuffers) {
*/
    //?}
        //? if <=1.21.10 {
        /*this(FALLBACK_BUFFER, layerBuffers);

        *///?}
    //? if <=26.1.2 {
    /*}


    *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public BatchableBufferSource(final ByteBufferBuilder sharedBuffer, final SequencedMap<RenderType, ByteBufferBuilder> fixedBuffers) {
        super(sharedBuffer, fixedBuffers);
*/
    //?}
    //? if (>=1.21.1 && <=1.21.10) {
    /*public BatchableBufferSource(final ByteBufferBuilder fallbackBuffer, final SequencedMap<RenderType, ByteBufferBuilder> layerBuffers) {

    *///?}
    //? if =1.20.4 {
/*    public BatchableBufferSource(final BufferBuilder fallbackBuffer, final Map<RenderType, BufferBuilder> layerBuffers) {
*/
    //?}
        //? if <=1.21.10 {
        /*super(fallbackBuffer, layerBuffers);

        *///?}
    //? if <=26.1.2 {
    /*}

    @Override

    *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public VertexConsumer getBuffer(final RenderType renderType) {
        if (!this.drawDynamicBuffersFirst) {
            if (this.lastSharedType != null && this.lastSharedType != renderType && !this.fixedBuffers.containsKey(this.lastSharedType)) {
                this.drawDynamicBuffersFirst = true;
*/
    //?}
    //? if <=1.21.10 {
    /*public VertexConsumer getBuffer(final RenderType layer) {

    *///?}
        //? if =1.20.4 {
/*        final Optional<RenderType> newLayer = layer.asOptional();
*/
        //?}
        //? if <=1.21.10 {
        /*if (!this.drawFallbackLayersFirst) {

        *///?}
            //? if (>=1.21.1 && <=1.21.10) {
            /*if (this.lastSharedType != null && this.lastSharedType != layer && !this.fixedBuffers.containsKey(this.lastSharedType)) {
                this.drawFallbackLayersFirst = true;

            *///?}
            //? if =1.20.4 {
/*            if (!this.lastState.equals(newLayer)) {
                if (this.lastState.isPresent() && !this.fixedBuffers.containsKey(this.lastState.get())) {
                    this.drawFallbackLayersFirst = true;
                }
*/
            //?}
            //? if <=26.1.2 {
            /*}
        }

            *///?}
        //? if =1.20.4 {
/*        this.lastState = newLayer;
*/
        //?}

        //? if (>=1.21.1 && <=26.1.2) {
        /*if (IrisCompat.IRIS_LOADED) {
            IrisCompat.skipExtension.set(!IrisCompat.isRenderingLevel.getAsBoolean());

        *///?}
        //? if =1.20.4 {
/*        final BufferBuilder bufferBuilder = this.getOrCreateBufferBuilder(layer);
        if (bufferBuilder.building() && !layer.canConsolidateConsecutiveGeometry()) {
            throw new IllegalStateException("Tried to write shared vertices into the same buffer");
*/
        //?}
        //? if <=26.1.2 {
        /*}


        *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*final BufferBuilder bufferBuilder;

        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        final boolean hasBufferForRenderType = renderType.canConsolidateConsecutiveGeometry() && this.dynamicBuffers.containsKey(renderType);
        if (!renderType.canConsolidateConsecutiveGeometry()) {
            bufferBuilder = new BufferBuilder(this.getNextByteBufferBuilder(), renderType.mode(), renderType.format());
            this.lastSharedType = renderType;
        } else if (hasBufferForRenderType) {
            bufferBuilder = this.dynamicBuffers.get(renderType).iterator().next();
        } else if (this.fixedBuffers.containsKey(renderType)) {
            bufferBuilder = new BufferBuilder(this.fixedBuffers.get(renderType), renderType.mode(), renderType.format());
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*final boolean hasBufferForRenderLayer = layer.canConsolidateConsecutiveGeometry() && this.pendingBuffers.containsKey(layer);
        if (!layer.canConsolidateConsecutiveGeometry()) {
            bufferBuilder = new BufferBuilder(this.getNextBufferAllocator(), layer.mode(), layer.format());
            this.lastSharedType = layer;
        } else if (hasBufferForRenderLayer) {
            bufferBuilder = this.pendingBuffers.get(layer).iterator().next();
        } else if (this.fixedBuffers.containsKey(layer)) {
            bufferBuilder = new BufferBuilder(this.fixedBuffers.get(layer), layer.mode(), layer.format());

        *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*} else {

        *///?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            bufferBuilder = new BufferBuilder(this.getNextByteBufferBuilder(), renderType.mode(), renderType.format());
            this.lastSharedType = renderType;
*/
            //?}
            //? if (>=1.21.1 && <=1.21.10) {
            /*bufferBuilder = new BufferBuilder(this.getNextBufferAllocator(), layer.mode(), layer.format());
            this.lastSharedType = layer;

            *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*}

        if (IrisCompat.IRIS_LOADED) {
            IrisCompat.skipExtension.set(false);
        }


        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        if (hasBufferForRenderType) {
            if ((ImmediatelyFast.config.debug_only_use_last_usage_for_batch_ordering || renderType.name.contains("immediatelyfast:renderlast")) && this.activeRenderTypes.contains(renderType)) { // Fix for https://github.com/RaphiMC/ImmediatelyFast/issues/181
                this.activeRenderTypes.remove(renderType);
                this.activeRenderTypes.add(renderType);
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*if (hasBufferForRenderLayer) {
            if ((ImmediatelyFast.config.debug_only_use_last_usage_for_batch_ordering || layer.name.contains("immediatelyfast:renderlast")) && this.activeLayers.contains(layer)) { // Fix for https://github.com/RaphiMC/ImmediatelyFast/issues/181
                this.activeLayers.remove(layer);
                this.activeLayers.add(layer);

        *///?}
        //? if =1.20.4 {
/*        if (!bufferBuilder.building()) {
            if (IrisCompat.IRIS_LOADED && !IrisCompat.isRenderingLevel.getAsBoolean()) {
                IrisCompat.iris$beginWithoutExtending.accept(bufferBuilder, layer.mode(), layer.format());
            } else {
                bufferBuilder.begin(layer.mode(), layer.format());
*/
        //?}
            //? if <=26.1.2 {
            /*}

            *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*} else {

        *///?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            this.dynamicBuffers.computeIfAbsent(renderType, k -> new ReferenceLinkedOpenHashSet<>()).add(bufferBuilder);
            this.activeRenderTypes.add(renderType);
*/
            //?}
            //? if (>=1.21.1 && <=1.21.10) {
            /*this.pendingBuffers.computeIfAbsent(layer, k -> new ReferenceLinkedOpenHashSet<>()).add(bufferBuilder);

            *///?}
            //? if =1.20.4 {
/*            this.activeLayers.add(layer);
        } else if ((ImmediatelyFast.config.debug_only_use_last_usage_for_batch_ordering || layer.name.contains("immediatelyfast:renderlast")) && this.activeLayers.contains(layer)) { // Fix for https://github.com/RaphiMC/ImmediatelyFast/issues/181
            this.activeLayers.remove(layer);
*/
            //?}
            //? if <=1.21.10 {
            /*this.activeLayers.add(layer);

            *///?}
        //? if <=26.1.2 {
        /*}

        *///?}

        //? if <=26.1.2 {
        /*return bufferBuilder;
    }

    @Override
    public void endLastBatch() {

        *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*this.lastSharedType = null;

        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        this.drawDynamicBuffersFirst = false;
*/
        //?}
        //? if =1.20.4 {
/*        this.lastState = Optional.empty();
*/
        //?}
        //? if <=1.21.10 {
        /*this.drawFallbackLayersFirst = false;

        *///?}

        //? if (>=1.21.11 && <=26.1.2) {
/*        int sortedRenderTypesLength = 0;
        final RenderType[] sortedRenderTypes = new RenderType[this.activeRenderTypes.size()];
        for (RenderType renderType : this.activeRenderTypes) {
            if (!this.fixedBuffers.containsKey(renderType)) {
                sortedRenderTypes[sortedRenderTypesLength++] = renderType;
*/
        //?}
        //? if <=1.21.10 {
        /*int sortedLayersLength = 0;
        final RenderType[] sortedLayers = new RenderType[this.activeLayers.size()];
        for (RenderType layer : this.activeLayers) {
            if (!this.fixedBuffers.containsKey(layer)) {
                sortedLayers[sortedLayersLength++] = layer;

        *///?}
            //? if <=26.1.2 {
            /*}
        }

            *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        if (sortedRenderTypesLength == 0) {
*/
        //?}
        //? if <=1.21.10 {
        /*if (sortedLayersLength == 0) {

        *///?}
            //? if <=26.1.2 {
            /*return;
        }


            *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        Arrays.sort(sortedRenderTypes, (t1, t2) -> Integer.compare(this.getRenderTypeOrder(t1), this.getRenderTypeOrder(t2)));
        for (int i = 0; i < sortedRenderTypesLength; i++) {
            this.endBatch(sortedRenderTypes[i]);
*/
        //?}
        //? if <=1.21.10 {
        /*Arrays.sort(sortedLayers, (l1, l2) -> Integer.compare(this.getLayerOrder(l1), this.getLayerOrder(l2)));
        for (int i = 0; i < sortedLayersLength; i++) {
            this.endBatch(sortedLayers[i]);

        *///?}
        //? if <=26.1.2 {
        /*}
    }

    @Override
    public void endBatch() {

        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        if (this.activeRenderTypes.isEmpty()) {
*/
        //?}
        //? if <=1.21.10 {
        /*if (this.activeLayers.isEmpty()) {

        *///?}
            //? if <=26.1.2 {
            /*this.close();
            return;
        }

        this.endLastBatch();

            *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        for (RenderType renderType : this.fixedBuffers.keySet()) {
            this.endBatch(renderType);
*/
        //?}
        //? if <=1.21.10 {
        /*for (RenderType layer : this.fixedBuffers.keySet()) {
            this.endBatch(layer);

        *///?}
        //? if <=26.1.2 {
        /*}
    }

    @Override

        *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public void endBatch(final RenderType renderType) {
        if (this.drawDynamicBuffersFirst) {
*/
    //?}
    //? if <=1.21.10 {
    /*public void endBatch(final RenderType layer) {
        if (this.drawFallbackLayersFirst) {

    *///?}
            //? if <=26.1.2 {
            /*this.endLastBatch();
        }


            *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        this.drawDirect(renderType);
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*this.drawDirect(layer);

        *///?}
    //? if (>=1.21.1 && <=26.1.2) {
    /*}

    @Override
    public void close() {
        this.lastSharedType = null;

    *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        this.drawDynamicBuffersFirst = false;
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*this.drawFallbackLayersFirst = false;

        *///?}

        //? if (>=1.21.11 && <=26.1.2) {
/*        for (Set<BufferBuilder> buffers : this.dynamicBuffers.values()) {
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*for (Set<BufferBuilder> buffers : this.pendingBuffers.values()) {

        *///?}
            //? if (>=1.21.1 && <=26.1.2) {
            /*for (BufferBuilder bufferBuilder : buffers) {

            *///?}
                //? if (>=1.21.11 && <=26.1.2) {
/*                final MeshData meshData = bufferBuilder.build();
                if (meshData != null) {
                    meshData.close();
*/
                //?}
                //? if (>=1.21.1 && <=1.21.10) {
                /*final MeshData builtBuffer = bufferBuilder.build();
                if (builtBuffer != null) {
                    builtBuffer.close();

                *///?}
                //? if (>=1.21.1 && <=26.1.2) {
                /*}

                *///?}
                //? if (>=1.21.11 && <=26.1.2) {
/*                ByteBufferBuilderPool.returnBufferBuilderSafe(bufferBuilder.buffer);
*/
                //?}
                //? if (>=1.21.1 && <=1.21.10) {
                /*ByteBufferBuilderPool.returnBufferAllocatorSafe(bufferBuilder.buffer);

                *///?}
            //? if (>=1.21.1 && <=26.1.2) {
            /*}
        }


            *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        this.activeRenderTypes.clear();
        this.dynamicBuffers.clear();
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*this.activeLayers.clear();
        this.pendingBuffers.clear();

        *///?}
    //? if (>=1.21.1 && <=26.1.2) {
    /*}


    *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public void drawDirect(final RenderType renderType) {
*/
    //?}
    //? if (>=1.21.1 && <=1.21.10) {
    /*public void drawDirect(final RenderType layer) {

    *///?}
        //? if <=26.1.2 {
        /*if (IrisCompat.IRIS_LOADED && !IrisCompat.isRenderingLevel.getAsBoolean()) {
            IrisCompat.renderWithExtendedVertexFormat.accept(false);
        }


        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        this.activeRenderTypes.remove(renderType);
        final Set<BufferBuilder> buffers = this.dynamicBuffers.remove(renderType);
*/
        //?}
        //? if <=1.21.10 {
        /*this.activeLayers.remove(layer);

        *///?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*final Set<BufferBuilder> buffers = this.pendingBuffers.remove(layer);

        *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*if (buffers != null) {
            for (BufferBuilder bufferBuilder : buffers) {

        *///?}
                //? if (>=1.21.11 && <=26.1.2) {
/*                final ByteBufferBuilder prevBufferBuilder = this.sharedBuffer;
*/
                //?}
                //? if (>=1.21.1 && <=1.21.10) {
                /*final ByteBufferBuilder prevBufferAllocator = this.sharedBuffer;

                *///?}
                //? if (>=1.21.1 && <=26.1.2) {
                /*this.sharedBuffer = bufferBuilder.buffer;

                *///?}
                //? if (>=1.21.11 && <=26.1.2) {
/*                this.endBatch(renderType, bufferBuilder);
                this.sharedBuffer = prevBufferBuilder;
                ByteBufferBuilderPool.returnBufferBuilderSafe(bufferBuilder.buffer);
*/
                //?}
                //? if (>=1.21.1 && <=1.21.10) {
                /*this.endBatch(layer, bufferBuilder);
                this.sharedBuffer = prevBufferAllocator;
                ByteBufferBuilderPool.returnBufferAllocatorSafe(bufferBuilder.buffer);

                *///?}
        //? if =1.20.4 {
/*        for (BufferBuilder bufferBuilder : this.getBufferBuilder(layer)) {
            if (bufferBuilder != null) {
                layer.end(bufferBuilder, RenderSystem.getVertexSorting());
*/
        //?}
            //? if <=26.1.2 {
            /*}
        }

            *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        if (this.lastSharedType == renderType) {
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*if (this.lastSharedType == layer) {

        *///?}
            //? if (>=1.21.1 && <=26.1.2) {
            /*this.lastSharedType = null;
        }

            *///?}
        //? if =1.20.4 {
/*        this.fallbackBuffers.remove(layer);
*/
        //?}
//? if <=26.1.2 {

        /*if (IrisCompat.IRIS_LOADED && !IrisCompat.isRenderingLevel.getAsBoolean()) {
            IrisCompat.renderWithExtendedVertexFormat.accept(true);
        }
    }


*///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public boolean hasActiveRenderTypes() {
        return !this.activeRenderTypes.isEmpty();
*/
    //?}
    //? if =1.20.4 {
/*    @Override
    public void close() {
        this.lastState = Optional.empty();
        this.drawFallbackLayersFirst = false;

        for (RenderType layer : this.activeLayers) {
            for (BufferBuilder bufferBuilder : this.getBufferBuilder(layer)) {
                final BufferBuilder.RenderedBuffer builtBuffer = bufferBuilder.endOrDiscardIfEmpty();
                if (builtBuffer != null) {
                    builtBuffer.release();
                }
            }
        }

        this.activeLayers.clear();
        this.fallbackBuffers.clear();
    }

*/
    //?}
    //? if <=1.21.10 {
    /*public boolean hasActiveLayers() {
        return !this.activeLayers.isEmpty();

    *///?}
    //? if <=26.1.2 {
    /*}


    *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    protected int getRenderTypeOrder(final RenderType renderType) {
        if (renderType == null) {
*/
    //?}
    //? if =1.20.4 {
/*    protected BufferBuilder getOrCreateBufferBuilder(final RenderType layer) {
        if (!layer.canConsolidateConsecutiveGeometry()) {
            return this.addNewFallbackBuffer(layer);
        } else if (this.fixedBuffers.containsKey(layer)) {
            return this.fixedBuffers.get(layer);
        } else if (this.fallbackBuffers.containsKey(layer)) {
            return this.fallbackBuffers.get(layer).iterator().next();
        } else {
            return this.addNewFallbackBuffer(layer);
        }
    }

    protected Set<BufferBuilder> getBufferBuilder(final RenderType layer) {
        if (this.fallbackBuffers.containsKey(layer)) {
            return this.fallbackBuffers.get(layer);
        } else if (this.fixedBuffers.containsKey(layer)) {
            return Collections.singleton(this.fixedBuffers.get(layer));
        } else {
            return Collections.emptySet();
        }
    }

    protected BufferBuilder addNewFallbackBuffer(final RenderType layer) {
        final BufferBuilder bufferBuilder = BufferBuilderPool.get();
        this.fallbackBuffers.computeIfAbsent(layer, k -> new ReferenceLinkedOpenHashSet<>()).add(bufferBuilder);
        return bufferBuilder;
    }

*/
    //?}
    //? if <=1.21.10 {
    /*protected int getLayerOrder(final RenderType layer) {
        if (layer == null) {

    *///?}
            //? if <=26.1.2 {
            /*return Integer.MAX_VALUE;
        }

        int order = 0;

            *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        final RenderSetup.TextureBinding textureBinding = renderType.state.textures.get("Sampler0");
        if (textureBinding != null) {
            final Identifier textureId = textureBinding.location();
            if (renderType.name.startsWith("text") || renderType.name.startsWith("neoforge_text")) {
                // Draws vanilla text over custom font text
                // Fixes https://github.com/RaphiMC/ImmediatelyFast/issues/81, https://github.com/RaphiMC/ImmediatelyFast/issues/287, https://github.com/RaphiMC/ImmediatelyFast/issues/288
                if (textureId.getNamespace().equals(Identifier.DEFAULT_NAMESPACE)) {
                    order = 2;
                } else {
*/
        //?}
        //? if <=1.21.4 {
        /*if (layer instanceof RenderType.CompositeRenderType multiPhase) {
            final ResourceLocation textureId = multiPhase.state().textureState.cutoutTexture().orElse(null);
            if (textureId != null) {

        *///?}
                //? if =1.21.4 {
/*                if (textureId.toString().startsWith("minecraft:textures/entity/wolf/")) {
*/
                //?}
                //? if =1.21.1 {
                /*if (textureId.getPath().startsWith("textures/entity/horse/")) {
                    final String horseTexturePath = textureId.getPath().substring("textures/entity/horse/".length());

                *///?}
                //? if =1.20.4 {
/*                if (textureId.getPath().startsWith(HorseArmorItem.TEX_FOLDER)) {
                    final String horseTexturePath = textureId.getPath().substring(HorseArmorItem.TEX_FOLDER.length());
*/
                //?}
                    //? if <=1.21.1 {
                    /*if (horseTexturePath.startsWith("horse_markings")) {
                        return 2;
                    } else if (horseTexturePath.startsWith("armor/")) {
                        return 3;

                    *///?}
                    //? if =1.21.1 {
                    /*} else {
                        return 1;
                    }
                } else if (textureId.toString().startsWith("minecraft:textures/entity/wolf/")) {

                    *///?}
                    //? if (>=1.21.1 && <=1.21.4) {
                    /*if (textureId.equals(WolfCollarLayer.WOLF_COLLAR_LOCATION)) {
                        return 2;

                    *///?}
                    //? if <=1.21.4 {
                    /*} else {
                        return 1;
                    }
                } else if (textureId.getPath().startsWith("textures/entity/villager/")) {
                    final String villagerTexturePath = textureId.getPath().substring("textures/entity/villager/".length());
                    if (villagerTexturePath.startsWith("type/")) {
                        return 2;
                    } else if (villagerTexturePath.startsWith("profession/")) {
                        return 3;
                    } else if (villagerTexturePath.startsWith("profession_level/")) {
                        return 4;
                    } else {
                        return 1;
                    }
                } else if (textureId.equals(Sheets.ARMOR_TRIMS_SHEET)) {

                    *///?}
                    //? if <=1.21.4 || (>=1.21.11 && <=26.1.2) {
                    /*order = 1;

                    *///?}
        //? if =1.21.10 {
/*        if (layer instanceof RenderType.CompositeRenderType multiPhase) {
            final ResourceLocation textureId = multiPhase.state.textureState.cutoutTexture().orElse(null);
            if (textureId != null) {
                if (layer.name.startsWith("text") || layer.name.startsWith("neoforge_text")) {
*/
        //?}
                //? if <=1.21.4 {
                /*} else if (layer.name.startsWith("text") || layer.name.startsWith("neoforge_text") || layer.name.startsWith("forge_text")) {

                *///?}
                    //? if <=1.21.10 {
                    /*// Draws vanilla text over custom font layers
                    // Fixes https://github.com/RaphiMC/ImmediatelyFast/issues/81, https://github.com/RaphiMC/ImmediatelyFast/issues/287, https://github.com/RaphiMC/ImmediatelyFast/issues/288
                    if (textureId.getNamespace().equals("minecraft")) {
                        order = 2;
                    } else {
                        order = 1;
                    }

                    *///?}
                //? if <=1.21.1 {
                /*} else if (textureId.getNamespace().equals("cataclysm")) { // https://github.com/RaphiMC/ImmediatelyFast/issues/371
                    // Appears to be fixed in 3.00+
                    if (textureId.getPath().equals("textures/entity/maledictus/phantom_halberd.png")) {
                        return 2;
                    } else if (textureId.getPath().equals("textures/entity/maledictus/phantom_halberd_discard.png")) {
                        return 1;
                    }

                *///?}
                //? if =1.20.4 {
/*                } else if (textureId.getNamespace().equals("alexscaves")) { // https://github.com/RaphiMC/ImmediatelyFast/issues/454
                    if (textureId.getPath().equals("textures/entity/hullbreaker_glow.png")) {
                        return 2;
                    } else if (textureId.getPath().equals("textures/entity/hullbreaker.png")) {
                        return 1;
                    }
                    if (textureId.getPath().equals("textures/entity/tremorzilla/tremorzilla_glow_powered.png")) {
                        return 2;
                    } else if (textureId.getPath().equals("textures/entity/tremorzilla/tremorzilla.png")) {
                        return 1;
                    }
*/
                //?}
                //? if <=26.1.2 {
                /*}
            }
        }


                *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        if (!renderType.sortOnUpload()) {
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*if (!layer.sortOnUpload()) {

        *///?}
        //? if =1.20.4 {
/*        if (!layer.sortOnUpload) {
*/
        //?}
            //? if <=26.1.2 {
            /*return order;
        } else {
            return 100_000_000 + order;
        }
    }


            *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private ByteBufferBuilder getNextByteBufferBuilder() {
*/
    //?}
    //? if (>=1.21.1 && <=1.21.10) {
    /*private ByteBufferBuilder getNextBufferAllocator() {

    *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*if (this.sharedBuffer != FALLBACK_BUFFER && this.lastSharedType == null && this.sharedBuffer.pointer != 0L) {
            return this.sharedBuffer;
        } else {

        *///?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            return ByteBufferBuilderPool.borrowBufferBuilder();
*/
            //?}
            //? if (>=1.21.1 && <=1.21.10) {
            /*return ByteBufferBuilderPool.borrowBufferAllocator();

            *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*}
    }


        *///?}
//? if <=26.1.2 {
/*}

*///?}
