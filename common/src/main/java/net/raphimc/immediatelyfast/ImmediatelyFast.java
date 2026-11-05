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
package net.raphimc.immediatelyfast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
//? if =26.2 {
/*import com.mojang.blaze3d.systems.DeviceInfo;
*/
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*import com.mojang.blaze3d.systems.GpuDevice;
*/
//?}
//? if >=1.21.10 {
import com.mojang.blaze3d.systems.RenderSystem;
//?}
//? if =26.3 {
import com.mojang.renderpearl.api.device.DeviceInfo;
//?}
//? if =1.21.4 {
/*import com.mojang.blaze3d.platform.GlUtil;
*/
//?}
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
//? if <=1.21.4 {
/*import net.raphimc.immediatelyfast.apiimpl.ApiAccessImpl;
import net.raphimc.immediatelyfast.compat.IrisCompat;

*///?}
import net.raphimc.immediatelyfast.feature.core.ImmediatelyFastConfig;
import net.raphimc.immediatelyfast.feature.core.ImmediatelyFastRuntimeConfig;
import net.raphimc.immediatelyfast.feature.sign_text_buffering.SignTextCache;
import net.raphimc.immediatelyfast.service.PlatformService;
//? if (>=1.21.10 && <=26.1.2) {
/*import net.raphimc.immediatelyfast.util.IrisCompat;
*/
//?}
//? if >=1.21.10 {
import org.lwjgl.system.MathUtil;
//?}
//? if <=1.21.4 {
/*import net.raphimc.immediatelyfastapi.ImmediatelyFastApi;

*///?}
//? if <=1.21.1 {
/*import org.lwjgl.opengl.GL11C;

*///?}
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ImmediatelyFast {

    public static final Logger LOGGER = LoggerFactory.getLogger("ImmediatelyFast");
    public static String VERSION;
    public static ImmediatelyFastConfig config;
    public static ImmediatelyFastRuntimeConfig runtimeConfig;

    public static SignTextCache signTextCache;

    public static void earlyInit() {
        //? if >=1.21.10 {
        if (ImmediatelyFast.config != null) {
        //?}
        //? if <=1.21.4 {
        /*if (config != null) {

        *///?}
            return;
        }

        ImmediatelyFast.loadConfig();
        //? if >=1.21.11 {
        ImmediatelyFast.createRuntimeConfig();
        VERSION = PlatformService.INSTANCE.getModVersion("immediatelyfast").orElseThrow(NullPointerException::new);
        //?}

        //? if =1.21.10 || >=26.2 {
        if (!ImmediatelyFast.config.debug_only_and_not_recommended_disable_mod_conflict_handling) {
            if (ImmediatelyFast.config.experimental_sign_text_buffering) {
        //?}
                //? if >=26.2 {
                if (PlatformService.INSTANCE.getModVersion("iris").isPresent()) {
                    LOGGER.warn("Iris detected. Force disabling sign text buffering optimization.");
                //?}
        //? if (>=1.21.1 && <=1.21.4) {
        /*if (config.experimental_screen_batching && !config.hud_batching) {
            LOGGER.warn("Screen Batching is enabled but HUD Batching is disabled. Disabling Screen Batching.");
            config.experimental_screen_batching = false;
        }

        *///?}
//? if <=1.21.4 {

        /*if (!config.debug_only_and_not_recommended_disable_mod_conflict_handling) {

*///?}
            //? if (>=1.21.1 && <=1.21.4) {
            /*if (config.experimental_sign_text_buffering) {

            *///?}
                //? if (>=1.21.4 && <=1.21.10) {
/*                if (PlatformService.INSTANCE.getModVersion("vulkanmod").isPresent()) {
                    LOGGER.warn("VulkanMod detected. Force disabling sign text buffering optimization.");
*/
                //?}
                    //? if =1.21.10 || >=26.2 {
                    ImmediatelyFast.config.experimental_sign_text_buffering = false;
                    //?}
                //? if =1.21.1 {
                /*if (PlatformService.INSTANCE.getModVersion("effective").isPresent()) {
                    // https://github.com/RaphiMC/ImmediatelyFast/issues/339
                    LOGGER.warn("Effective mod detected. Force disabling sign text buffering optimization.");

                *///?}
                    //? if (>=1.21.1 && <=1.21.4) {
                    /*config.experimental_sign_text_buffering = false;

                    *///?}
            //? if =1.20.4 {
/*            if (config.hud_batching) {
                if (PlatformService.INSTANCE.getModVersion("mbd2").isPresent()) {
                    // https://github.com/Low-Drag-MC/Multiblocked2/issues/80
                    LOGGER.warn("Multiblocked2 mod detected. Force disabling HUD Batching optimization.");
                    config.hud_batching = false;
                } else if (PlatformService.INSTANCE.getModVersion("draconicevolution").isPresent()) {
                    // https://github.com/Draconic-Inc/Draconic-Evolution/issues/1868
                    LOGGER.warn("Draconic Evolution mod detected. Force disabling HUD Batching optimization.");
                    config.hud_batching = false;
*/
            //?}
                //? if <=1.21.10 || >=26.2 {
                }
            }
        }

                //?}
        //? if <=1.21.10 {
        /*ImmediatelyFast.createRuntimeConfig();

        *///?}
        //? if <=1.21.4 {
        /*ImmediatelyFastApi.setApiImpl(new ApiAccessImpl());


        *///?}
        //? if <=1.21.10 {
        /*VERSION = PlatformService.INSTANCE.getModVersion("immediatelyfast").orElseThrow(NullPointerException::new);


        *///?}
        //System.load("C:\\Program Files\\RenderDoc\\renderdoc.dll");
    }

    //? if >=1.21.10 {
    public static void onRenderSystemInit() {
    //?}
        //? if >=26.2 {
        final DeviceInfo deviceInfo = RenderSystem.getDevice().getDeviceInfo();
        final String gpuVendor = deviceInfo.vendorName();
        final String gpuModel = deviceInfo.name();
        final String backendName = deviceInfo.backendName();
        final String backendVersion = deviceInfo.driverInfo();
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        final GpuDevice gpuDevice = RenderSystem.getDevice();
        final String gpuVendor = gpuDevice.getVendor();
        final String gpuModel = gpuDevice.getRenderer();
        final String backendName = gpuDevice.getBackendName();
        final String backendVersion = gpuDevice.getVersion();
*/
        //?}
        //? if >=1.21.10 {
        LOGGER.info("Initializing ImmediatelyFast " + VERSION + " on " + gpuModel + " (" + gpuVendor + ") with " + backendName + " " + backendVersion);
        //?}
    //? if <=1.21.4 {
    /*public static void windowInit() {

    *///?}
        //? if =1.21.4 {
/*        final String gpuVendor = GlUtil.getVendor();
        final String gpuModel = GlUtil.getRenderer();
        final String glVersion = GlUtil.getOpenGLVersion();
*/
        //?}
        //? if <=1.21.1 {
        /*final String gpuVendor = GL11C.glGetString(GL11C.GL_VENDOR);
        final String gpuModel = GL11C.glGetString(GL11C.GL_RENDERER);
        final String glVersion = GL11C.glGetString(GL11C.GL_VERSION);

        *///?}
        //? if <=1.21.4 {
        /*LOGGER.info("Initializing ImmediatelyFast " + VERSION + " on " + gpuModel + " (" + gpuVendor + ") with OpenGL " + glVersion);

        *///?}

        //? if >=1.21.10 {
        final String gpuVendorLower = gpuVendor.toLowerCase();
        final boolean isNvidia = gpuVendorLower.startsWith("nvidia");
        final boolean isAmd = gpuVendorLower.startsWith("ati") || gpuVendorLower.startsWith("amd");
        final boolean isIntel = gpuVendorLower.startsWith("intel");
        final boolean isApple = gpuVendorLower.startsWith("apple");
        //?}
        //? if <=1.21.4 {
        /*boolean isNvidia = false;
        boolean isAmd = false;
        boolean isIntel = false;
        boolean isApple = false;
        if (gpuVendor != null) {
            final String gpuVendorLower = gpuVendor.toLowerCase();

        *///?}

        //? if >=1.21.10 {
        Objects.requireNonNull(ImmediatelyFast.config, "Config not loaded yet");
        Objects.requireNonNull(ImmediatelyFast.runtimeConfig, "Runtime config not created yet");
        //?}
            //? if <=1.21.4 {
            /*isNvidia = gpuVendorLower.startsWith("nvidia");
            isAmd = gpuVendorLower.startsWith("ati") || gpuVendorLower.startsWith("amd");
            isIntel = gpuVendorLower.startsWith("intel");
            isApple = gpuVendorLower.startsWith("apple");
        }

            *///?}

        //? if >=26.2 {
        ImmediatelyFast.runtimeConfig.fix_slow_buffer_upload_on_apple_gpu &= isApple && backendName.equals("OpenGL") && !(deviceInfo.underlyingExtensions().contains("GL_ARB_direct_state_access") || deviceInfo.underlyingExtensions().contains("GL_ARB_buffer_storage"));
        if (ImmediatelyFast.runtimeConfig.avoid_redundant_framebuffer_switching && !ImmediatelyFast.config.debug_only_and_not_recommended_disable_hardware_conflict_handling && isIntel && backendName.equals("OpenGL") && (gpuModel.contains("UHD Graphics") || gpuModel.contains("Xe Graphics"))) {
            LOGGER.warn("Intel UHD Graphics or Intel Xe Graphics detected. Force disabling redundant framebuffer switching optimization.");
            ImmediatelyFast.runtimeConfig.avoid_redundant_framebuffer_switching = false;
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
/*        if (ImmediatelyFast.config.fix_slow_buffer_upload_on_apple_gpu && isApple && backendName.equals("OpenGL") && !(gpuDevice.getEnabledExtensions().contains("GL_ARB_direct_state_access") || gpuDevice.getEnabledExtensions().contains("GL_ARB_buffer_storage"))) {
            ImmediatelyFast.runtimeConfig.disable_fast_buffer_upload = true;
*/
        //?}
        //? if <=1.21.4 {
        /*Objects.requireNonNull(config, "Config not loaded yet");
        Objects.requireNonNull(runtimeConfig, "Runtime config not created yet");

        if (config.fast_buffer_upload && isApple && !config.debug_only_and_not_recommended_disable_hardware_conflict_handling) {
            LOGGER.warn("Apple GPU detected. Disabling fast buffer upload.");
            runtimeConfig.fast_buffer_upload = false;

        *///?}
        //? if <=26.1.2 {
        /*}

        if (!ImmediatelyFast.config.debug_only_and_not_recommended_disable_mod_conflict_handling) {

        *///?}
            //? if (>=1.21.1 && <=26.1.2) {
            /*PlatformService.INSTANCE.getModVersion("iris").ifPresent(version -> {
                ImmediatelyFast.LOGGER.info("Found Iris " + version + ". Enabling compatibility.");

            *///?}
            //? if =1.20.4 {
/*            PlatformService.INSTANCE.getModVersion("iris").or(() -> PlatformService.INSTANCE.getModVersion("oculus")).ifPresent(version -> {
                ImmediatelyFast.LOGGER.info("Found Iris/Oculus " + version + ". Enabling compatibility.");
*/
            //?}
                //? if <=26.1.2 {
                /*IrisCompat.init();
            });

                *///?}
        }
    }

    public static void lateInit() {
        //? if >=1.21.10 {
        if (ImmediatelyFast.config.experimental_sign_text_buffering) {
            ImmediatelyFast.signTextCache = new SignTextCache();
            if (PlatformService.INSTANCE.getModVersion("neoforge").isEmpty()) { // NeoForge uses an event. Handled in ImmediatelyFastNeoForge
                ((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(ImmediatelyFast.signTextCache);
        //?}
        //? if <=1.21.4 {
        /*if (config.experimental_sign_text_buffering) {
            signTextCache = new SignTextCache();

        *///?}
            //? if =1.21.4 {
/*            if (!PlatformService.INSTANCE.getModVersion("neoforge").isPresent()) { // NeoForge uses an event. Handled in ImmediatelyFastNeoForge
                ((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(signTextCache);
*/
            //?}
            //? if >=1.21.4 {
            }
            //?}
            //? if <=1.21.1 {
            /*((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(signTextCache);

            *///?}
        }
    }

    //? if >=1.21.11 {
    public static void onLevelChange() {
    //?}
    //? if <=1.21.10 {
    /*public static void onWorldJoin() {

    *///?}
        //? if >=1.21.10 {
        if (ImmediatelyFast.signTextCache != null) {
            ImmediatelyFast.signTextCache.clearCache();
        //?}
        //? if <=1.21.4 {
        /*if (signTextCache != null) {
            signTextCache.clearCache();

        *///?}
        }
    }

    public static void loadConfig() {
        final Path configFile = PlatformService.INSTANCE.getConfigDirectory().resolve("immediatelyfast.json");
        if (Files.isRegularFile(configFile)) {
            try {
                //? if >=1.21.10 {
                ImmediatelyFast.config = new Gson().fromJson(Files.readString(configFile), ImmediatelyFastConfig.class);
                //?}
                //? if <=1.21.4 {
                /*config = new Gson().fromJson(Files.readString(configFile), ImmediatelyFastConfig.class);

                *///?}
            } catch (Throwable e) {
                LOGGER.error("Failed to load ImmediatelyFast config. Resetting it.", e);
            }
        }
        //? if >=1.21.10 {
        if (ImmediatelyFast.config == null) {
            ImmediatelyFast.config = new ImmediatelyFastConfig();
        //?}
        //? if <=1.21.4 {
        /*if (config == null) {
            config = new ImmediatelyFastConfig();

        *///?}
        }
//? if >=1.21.10 {

        if (!MathUtil.mathIsPoT(ImmediatelyFast.config.font_atlas_size)) {
            LOGGER.warn("Font atlas size " + ImmediatelyFast.config.font_atlas_size + " is not a power of two! Rounding up to the next power of two.");
            ImmediatelyFast.config.font_atlas_size = MathUtil.mathRoundPoT(ImmediatelyFast.config.font_atlas_size);
        }
        if (!MathUtil.mathIsPoT(ImmediatelyFast.config.map_atlas_size)) {
            LOGGER.warn("Map atlas size " + ImmediatelyFast.config.map_atlas_size + " is not a power of two! Rounding up to the next power of two.");
            ImmediatelyFast.config.map_atlas_size = MathUtil.mathRoundPoT(ImmediatelyFast.config.map_atlas_size);
        }
        if (!MathUtil.mathIsPoT(ImmediatelyFast.config.experimental_sign_atlas_size)) {
            LOGGER.warn("Sign atlas size " + ImmediatelyFast.config.experimental_sign_atlas_size + " is not a power of two! Rounding up to the next power of two.");
            ImmediatelyFast.config.experimental_sign_atlas_size = MathUtil.mathRoundPoT(ImmediatelyFast.config.experimental_sign_atlas_size);
        }

//?}
        try {
            //? if >=1.21.10 {
            Files.writeString(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(ImmediatelyFast.config));
            //?}
            //? if <=1.21.4 {
            /*Files.writeString(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(config));

            *///?}
        } catch (Throwable e) {
            LOGGER.error("Failed to save ImmediatelyFast config.", e);
        }
    }

    public static void createRuntimeConfig() {
        //? if >=1.21.10 {
        ImmediatelyFast.runtimeConfig = new ImmediatelyFastRuntimeConfig(ImmediatelyFast.config);
        //?}
        //? if <=1.21.4 {
        /*runtimeConfig = new ImmediatelyFastRuntimeConfig(config);

        *///?}
    }

}
