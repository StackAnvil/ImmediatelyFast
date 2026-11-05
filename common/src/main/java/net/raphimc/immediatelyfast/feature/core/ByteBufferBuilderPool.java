//? if (>=1.21.1 && <=26.1.2) {
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

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.ByteBufferBuilder;
import it.unimi.dsi.fastutil.objects.Reference2ObjectMap;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import it.unimi.dsi.fastutil.objects.ReferenceList;
import net.raphimc.immediatelyfast.ImmediatelyFast;

public class ByteBufferBuilderPool {

    private static final ReferenceList<Entry> FREE = new ReferenceArrayList<>();
    private static final ReferenceList<Entry> IN_USE = new ReferenceArrayList<>();

*///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    private static final Reference2ObjectMap<ByteBufferBuilder, Entry> BUFFER_BUILDER_MAPPING = new Reference2ObjectOpenHashMap<>();
*/
    //?}
    //? if (>=1.21.1 && <=1.21.10) {
    /*private static final Reference2ObjectMap<ByteBufferBuilder, Entry> BUFFER_ALLOCATOR_MAPPING = new Reference2ObjectOpenHashMap<>();

    *///?}
//? if (>=1.21.1 && <=26.1.2) {

    /*private ByteBufferBuilderPool() {
    }


*///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public static ByteBufferBuilder borrowBufferBuilder() {
*/
    //?}
    //? if (>=1.21.1 && <=1.21.10) {
    /*public static ByteBufferBuilder borrowBufferAllocator() {

    *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*RenderSystem.assertOnRenderThread();
        Entry entry;
        if (FREE.isEmpty()) {
            entry = new Entry(new ByteBufferBuilder(256));
        } else {
            entry = FREE.removeFirst();

        *///?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            if (entry.bufferBuilder.pointer == 0L) { // If the buffer was closed while in the pool
                BUFFER_BUILDER_MAPPING.remove(entry.bufferBuilder);
*/
            //?}
            //? if (>=1.21.1 && <=1.21.10) {
            /*if (entry.bufferAllocator.pointer == 0L) { // If the buffer was closed while in the pool
                BUFFER_ALLOCATOR_MAPPING.remove(entry.bufferAllocator);

            *///?}
                //? if (>=1.21.1 && <=26.1.2) {
                /*entry = new Entry(new ByteBufferBuilder(256));
            }
        }
        IN_USE.add(entry);

                *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        BUFFER_BUILDER_MAPPING.put(entry.bufferBuilder, entry);
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*BUFFER_ALLOCATOR_MAPPING.put(entry.bufferAllocator, entry);

        *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*entry.onBorrow();

        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        return entry.bufferBuilder;
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*return entry.bufferAllocator;

        *///?}
    //? if (>=1.21.1 && <=26.1.2) {
    /*}


    *///?}
    //? if (>=1.21.11 && <=26.1.2) {
/*    public static void returnBufferBuilderSafe(final ByteBufferBuilder bufferBuilder) {
*/
    //?}
    //? if (>=1.21.1 && <=1.21.10) {
    /*public static void returnBufferAllocatorSafe(final ByteBufferBuilder bufferAllocator) {

    *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*RenderSystem.assertOnRenderThread();

        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        final Entry entry = BUFFER_BUILDER_MAPPING.get(bufferBuilder);
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*final Entry entry = BUFFER_ALLOCATOR_MAPPING.get(bufferAllocator);

        *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*if (!IN_USE.remove(entry)) {
            return;
        }
        entry.onReturn();
        FREE.addFirst(entry);
    }

    public static int getSize() {
        return FREE.size() + IN_USE.size();
    }


        *///?}
    //? if (>=1.21.10 && <=26.1.2) {
/*    public static long getAllocatedBytes() {
        long total = 0;
        for (Entry entry : FREE) {
*/
    //?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            total += entry.bufferBuilder.capacity;
*/
            //?}
            //? if =1.21.10 {
/*            total += entry.bufferAllocator.capacity;
*/
            //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        }
        for (Entry entry : IN_USE) {
*/
        //?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            total += entry.bufferBuilder.capacity;
*/
            //?}
            //? if =1.21.10 {
/*            total += entry.bufferAllocator.capacity;
*/
            //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        }
        return total;
    }

*/
        //?}
    //? if (>=1.21.1 && <=26.1.2) {
    /*public static void onEndFrame() {
        if (!IN_USE.isEmpty()) {

    *///?}
            // Reclaim all buffer builders that were not returned to the pool this and the last frame
            // Reclaim all buffers that were not returned to the pool this and the last frame
            //? if (>=1.21.1 && <=26.1.2) {
            /*IN_USE.removeIf(entry -> {
                if (entry.inUseOverMultipleFrames) {

            *///?}
                    //? if (>=1.21.11 && <=26.1.2) {
/*                    ImmediatelyFast.LOGGER.warn("!!! Possible memory leak detected!!! A BufferBuilder was not returned to the pool. This is not a bug in ImmediatelyFast.");
*/
                    //?}
                    //? if (>=1.21.1 && <=1.21.10) {
                    /*ImmediatelyFast.LOGGER.warn("!!! Possible memory leak detected!!! A BufferAllocator was not returned to the pool. This is not a bug in ImmediatelyFast.");

                    *///?}
                    //? if (>=1.21.1 && <=26.1.2) {
                    /*ImmediatelyFast.LOGGER.warn("Allocation stack trace:");
                    if (entry.allocationStackTrace != null) {
                        for (StackTraceElement element : entry.allocationStackTrace) {
                            ImmediatelyFast.LOGGER.warn("\tat {}", element.toString());
                        }
                    } else {
                        ImmediatelyFast.LOGGER.warn("\t<No stack trace available. Enable debug_only_detailed_memory_leak_detection in the config to get stack traces>");
                    }
                    return true;
                }
                return false;
            });

            // Mark all as in use over multiple frames
            for (Entry entry : IN_USE) {
                entry.inUseOverMultipleFrames = true;
            }
        }

        FREE.removeIf(entry -> {
            if (entry.shouldBeClosed()) {

                    *///?}
                //? if (>=1.21.11 && <=26.1.2) {
/*                entry.bufferBuilder.close();
                BUFFER_BUILDER_MAPPING.remove(entry.bufferBuilder);
*/
                //?}
                //? if (>=1.21.1 && <=1.21.10) {
                /*entry.bufferAllocator.close();
                BUFFER_ALLOCATOR_MAPPING.remove(entry.bufferAllocator);

                *///?}
                //? if (>=1.21.1 && <=26.1.2) {
                /*return true;
            }
            return false;
        });
    }

    private static class Entry {


                *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        private final ByteBufferBuilder bufferBuilder;
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*private final ByteBufferBuilder bufferAllocator;

        *///?}
        //? if (>=1.21.1 && <=26.1.2) {
        /*private long lastAccessTime;
        private boolean inUseOverMultipleFrames;
        private StackTraceElement[] allocationStackTrace;


        *///?}
        //? if (>=1.21.11 && <=26.1.2) {
/*        public Entry(final ByteBufferBuilder bufferBuilder) {
            this.bufferBuilder = bufferBuilder;
*/
        //?}
        //? if (>=1.21.1 && <=1.21.10) {
        /*public Entry(final ByteBufferBuilder bufferAllocator) {
            this.bufferAllocator = bufferAllocator;

        *///?}
            //? if (>=1.21.1 && <=26.1.2) {
            /*this.lastAccessTime = System.currentTimeMillis();
        }

        public boolean shouldBeClosed() {
            return System.currentTimeMillis() - this.lastAccessTime > 60 * 1000;
        }

        public void onBorrow() {
            this.lastAccessTime = System.currentTimeMillis();
            if (ImmediatelyFast.config.debug_only_detailed_memory_leak_detection) {
                this.allocationStackTrace = Thread.currentThread().getStackTrace();
            }
        }

        public void onReturn() {

            *///?}
            //? if (>=1.21.11 && <=26.1.2) {
/*            this.bufferBuilder.discard();
*/
            //?}
            //? if (>=1.21.1 && <=1.21.10) {
            /*this.bufferAllocator.discard();

            *///?}
            //? if (>=1.21.1 && <=26.1.2) {
            /*this.inUseOverMultipleFrames = false;
            this.allocationStackTrace = null;
        }

    }

}

            *///?}
