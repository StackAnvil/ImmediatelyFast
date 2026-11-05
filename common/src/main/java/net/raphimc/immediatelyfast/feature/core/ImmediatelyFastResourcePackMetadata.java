//? if <=26.2 {
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

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

*///?}
//? if <=1.21.10 {
/*import java.util.Collections;
import java.util.List;

*///?}
//? if <=26.2 {
/*import net.minecraft.server.packs.metadata.MetadataSectionType;


*///?}
//? if (>=1.21.11 && <=26.2) {
/*import java.util.Collections;
import java.util.List;

*/
//?}
//? if =26.2 {
/*public record ImmediatelyFastResourcePackMetadata(List<String> compatibleFeatures, List<String> incompatibleFeatures) {
*/
//?}
//? if <=26.1.2 {
/*public record ImmediatelyFastResourcePackMetadata(List<String> compatibleFeatures) {

*///?}

    //? if =26.2 {
/*    public static final ImmediatelyFastResourcePackMetadata DEFAULT = new ImmediatelyFastResourcePackMetadata(Collections.emptyList(), Collections.emptyList());
*/
    //?}
    //? if <=26.1.2 {
    /*public static final ImmediatelyFastResourcePackMetadata DEFAULT = new ImmediatelyFastResourcePackMetadata(Collections.emptyList());

    *///?}
    //? if <=26.2 {
    /*public static final Codec<ImmediatelyFastResourcePackMetadata> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(

    *///?}
            //? if =26.2 {
/*            Codec.STRING.listOf().optionalFieldOf("compatible_features", Collections.emptyList()).forGetter(ImmediatelyFastResourcePackMetadata::compatibleFeatures),
            Codec.STRING.listOf().optionalFieldOf("incompatible_features", Collections.emptyList()).forGetter(ImmediatelyFastResourcePackMetadata::incompatibleFeatures)
*/
            //?}
            //? if <=26.1.2 {
            /*Codec.STRING.listOf().fieldOf("compatible_features").forGetter(ImmediatelyFastResourcePackMetadata::compatibleFeatures)

            *///?}
        //? if <=26.2 {
        /*).apply(instance, ImmediatelyFastResourcePackMetadata::new)
    );

        *///?}
    //? if (>=1.21.4 && <=26.2) {
/*    public static final MetadataSectionType<ImmediatelyFastResourcePackMetadata> SERIALIZER = new MetadataSectionType<>("immediatelyfast", CODEC);
*/
    //?}
    //? if <=1.21.1 {
    /*public static final MetadataSectionType<ImmediatelyFastResourcePackMetadata> SERIALIZER = MetadataSectionType.fromCodec("immediatelyfast", CODEC);

    *///?}
//? if <=26.2 {

/*}

*///?}
