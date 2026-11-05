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

package net.raphimc.immediatelyfastapi;


//? if (>=1.21.1 && <=1.21.4) {
/*@Deprecated
public interface BatchingAccess {
*///?}
//? if =1.20.4 {
/*public interface BatchingAccess {
*///?}
    //? if (>=1.21.1 && <=1.21.4) {
    /*@Deprecated
    void beginHudBatching();
    *///?}
    //? if =1.20.4 {
    /*void beginHudBatching();
    *///?}

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Deprecated
    void endHudBatching();
    *///?}
    //? if =1.20.4 {
    /*void endHudBatching();
    *///?}

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Deprecated
    boolean isHudBatching();
    *///?}
    //? if =1.20.4 {
    /*boolean isHudBatching();
    *///?}

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Deprecated
    boolean hasDataToDraw();
    *///?}
    //? if =1.20.4 {
    /*boolean hasDataToDraw();
    *///?}

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Deprecated
    void forceDrawBuffers();
    *///?}
    //? if =1.20.4 {
    /*void forceDrawBuffers();
    *///?}
}
