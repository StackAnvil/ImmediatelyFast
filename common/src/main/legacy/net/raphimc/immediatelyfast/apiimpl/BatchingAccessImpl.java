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

package net.raphimc.immediatelyfast.apiimpl;

import net.raphimc.immediatelyfast.ImmediatelyFast;
import net.raphimc.immediatelyfast.feature.batching.BatchingBuffers;
import net.raphimc.immediatelyfastapi.BatchingAccess;

//? if (>=1.21.1 && <=1.21.4) {
/*@Deprecated
public class BatchingAccessImpl implements BatchingAccess {
*///?}
//? if =1.20.4 {
/*public class BatchingAccessImpl implements BatchingAccess {
*///?}
    //? if (>=1.21.1 && <=1.21.4) {
    /*private boolean warned = false;
    *///?}

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Override
    @Deprecated
    public void beginHudBatching() {
    *///?}
    //? if =1.20.4 {
    /*@Override
    public void beginHudBatching() {
    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*this.warn();
        *///?}
        //? if =1.20.4 {
        /*BatchingBuffers.beginHudBatching();
        *///?}
    }

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Override
    @Deprecated
    public void endHudBatching() {
    *///?}
    //? if =1.20.4 {
    /*@Override
    public void endHudBatching() {
    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*this.warn();
        *///?}
        //? if =1.20.4 {
        /*BatchingBuffers.endHudBatching();
        *///?}
    }

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Override
    @Deprecated
    public boolean isHudBatching() {
    *///?}
    //? if =1.20.4 {
    /*@Override
    public boolean isHudBatching() {
    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*this.warn();
        *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*return false;
        *///?}
        //? if =1.20.4 {
        /*return BatchingBuffers.isHudBatching();
        *///?}
    }

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Override
    @Deprecated
    public boolean hasDataToDraw() {
    *///?}
    //? if =1.20.4 {
    /*@Override
    public boolean hasDataToDraw() {
    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*this.warn();
        *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*return false;
        *///?}
        //? if =1.20.4 {
        /*return BatchingBuffers.hasDataToDraw();
        *///?}
    }

    //? if (>=1.21.1 && <=1.21.4) {
    /*@Override
    @Deprecated
    public void forceDrawBuffers() {
    *///?}
    //? if =1.20.4 {
    /*@Override
    public void forceDrawBuffers() {
    *///?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*this.warn();
        *///?}
        //? if =1.20.4 {
        /*BatchingBuffers.forceDrawBuffers();
        *///?}
    }

    //? if (>=1.21.1 && <=1.21.4) {
    /*private void warn() {
        if (!this.warned) {
            this.warned = true;
            ImmediatelyFast.LOGGER.error("A mod tried to use the ImmediatelyFast batching API, but it is no longer available in 1.21.");
            ImmediatelyFast.LOGGER.error("Mojang added basic batching into the DrawContext class. ImmediatelyFast now uses and extends this system, so this method is no longer needed.");
            ImmediatelyFast.LOGGER.error("To migrate your mod, simply remove all calls to the ImmediatelyFast batching API and make sure to use the DrawContext for your HUD rendering.");
            ImmediatelyFast.LOGGER.error("Here is a stack trace to help you find the offending code:", new Exception());
        }
    }
    *///?}
}
