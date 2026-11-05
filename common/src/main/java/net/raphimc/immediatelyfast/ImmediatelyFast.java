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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.resources.ReloadableResourceManager;
import net.raphimc.immediatelyfast.feature.core.ImmediatelyFastConfig;
import net.raphimc.immediatelyfast.feature.core.ImmediatelyFastRuntimeConfig;
import net.raphimc.immediatelyfast.feature.sign_text_buffering.SignTextCache;
import net.raphimc.immediatelyfast.service.PlatformService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//? if >=1.21.10 {
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.system.MathUtil;
//?}
//? if (>=1.21.10 && <=26.1.2) {
/*import com.mojang.blaze3d.systems.GpuDevice;
import net.raphimc.immediatelyfast.util.IrisCompat;
*///?}
//? if <=1.21.4 {
/*import net.raphimc.immediatelyfast.apiimpl.ApiAccessImpl;
import net.raphimc.immediatelyfast.compat.IrisCompat;
import net.raphimc.immediatelyfastapi.ImmediatelyFastApi;
*///?}
//? if >=26.2 {
import com.mojang.renderpearl.api.device.DeviceInfo;
//?}
//? if <=1.21.1 {
/*import org.lwjgl.opengl.GL11C;
*///?}
//? if =1.21.4 {
/*import com.mojang.blaze3d.platform.GlUtil;
*///?}

public class ImmediatelyFast {
    public static final Logger LOGGER = LoggerFactory.getLogger("ImmediatelyFast");

    public static String VERSION;

    public static ImmediatelyFastConfig config;

    public static ImmediatelyFastRuntimeConfig runtimeConfig;

    public static SignTextCache signTextCache;

    public static void earlyInit() {
        if (ImmediatelyFast.config != null) {
            return;
        }
        ImmediatelyFast.loadConfig();
        //? if (>=1.21.1 && <=1.21.4) {
        /*if (ImmediatelyFast.config.experimental_screen_batching && !ImmediatelyFast.config.hud_batching) {
            ImmediatelyFast.LOGGER.warn("Screen Batching is enabled but HUD Batching is disabled. Disabling Screen Batching.");
            ImmediatelyFast.config.experimental_screen_batching = false;
        }
        *///?}
        //? if <=1.21.10 {
        /*if (!ImmediatelyFast.config.debug_only_and_not_recommended_disable_mod_conflict_handling) {
            //? if (>=1.21.1 && <=1.21.10) {
            /^if (ImmediatelyFast.config.experimental_sign_text_buffering) {
                //? if (>=1.21.4 && <=1.21.10) {
                /^¹if (PlatformService.INSTANCE.getModVersion("vulkanmod").isPresent()) {
                    ImmediatelyFast.LOGGER.warn("VulkanMod detected. Force disabling sign text buffering optimization.");
                    ImmediatelyFast.config.experimental_sign_text_buffering = false;
                }
                ¹^///?}
                //? if =1.21.1 {
                /^¹if (PlatformService.INSTANCE.getModVersion("effective").isPresent()) {
                    // https://github.com/RaphiMC/ImmediatelyFast/issues/339
                    ImmediatelyFast.LOGGER.warn("Effective mod detected. Force disabling sign text buffering optimization.");
                    ImmediatelyFast.config.experimental_sign_text_buffering = false;
                }
                ¹^///?}
            }
            ^///?}
            //? if =1.20.4 {
            /^if (ImmediatelyFast.config.hud_batching) {
                if (PlatformService.INSTANCE.getModVersion("mbd2").isPresent()) {
                    // https://github.com/Low-Drag-MC/Multiblocked2/issues/80
                    ImmediatelyFast.LOGGER.warn("Multiblocked2 mod detected. Force disabling HUD Batching optimization.");
                    ImmediatelyFast.config.hud_batching = false;
                } else if (PlatformService.INSTANCE.getModVersion("draconicevolution").isPresent()) {
                    // https://github.com/Draconic-Inc/Draconic-Evolution/issues/1868
                    ImmediatelyFast.LOGGER.warn("Draconic Evolution mod detected. Force disabling HUD Batching optimization.");
                    ImmediatelyFast.config.hud_batching = false;
                }
            }
            ^///?}
        }
        *///?}
        ImmediatelyFast.createRuntimeConfig();
        //? if <=1.21.4 {
        /*ImmediatelyFastApi.setApiImpl(new ApiAccessImpl());
        *///?}
        ImmediatelyFast.VERSION = PlatformService.INSTANCE.getModVersion("immediatelyfast").orElseThrow(NullPointerException::new);
        //? if >=26.2 {
        if (!ImmediatelyFast.config.debug_only_and_not_recommended_disable_mod_conflict_handling) {
            if (ImmediatelyFast.config.experimental_sign_text_buffering) {
                if (PlatformService.INSTANCE.getModVersion("iris").isPresent()) {
                    ImmediatelyFast.LOGGER.warn("Iris detected. Force disabling sign text buffering optimization.");
                    ImmediatelyFast.config.experimental_sign_text_buffering = false;
                }
            }
        }
        //?}
    }

    //? if >=1.21.10 {
    public static void onRenderSystemInit() {
        //? if >=26.2 {
        final DeviceInfo deviceInfo = RenderSystem.getDevice().getDeviceInfo();
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
        /*final GpuDevice gpuDevice = RenderSystem.getDevice();
        *///?}
        //? if >=26.2 {
        final String gpuVendor = deviceInfo.vendorName();
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
        /*final String gpuVendor = gpuDevice.getVendor();
        *///?}
        //? if >=26.2 {
        final String gpuModel = deviceInfo.name();
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
        /*final String gpuModel = gpuDevice.getRenderer();
        *///?}
        //? if >=26.2 {
        final String backendName = deviceInfo.backendName();
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
        /*final String backendName = gpuDevice.getBackendName();
        *///?}
        //? if >=26.2 {
        final String backendVersion = deviceInfo.driverInfo();
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
        /*final String backendVersion = gpuDevice.getVersion();
        *///?}
        ImmediatelyFast.LOGGER.info("Initializing ImmediatelyFast " + ImmediatelyFast.VERSION + " on " + gpuModel + " (" + gpuVendor + ") with " + backendName + " " + backendVersion);
        final String gpuVendorLower = gpuVendor.toLowerCase();
        final boolean isNvidia = gpuVendorLower.startsWith("nvidia");
        final boolean isAmd = gpuVendorLower.startsWith("ati") || gpuVendorLower.startsWith("amd");
        final boolean isIntel = gpuVendorLower.startsWith("intel");
        final boolean isApple = gpuVendorLower.startsWith("apple");
        Objects.requireNonNull(ImmediatelyFast.config, "Config not loaded yet");
        Objects.requireNonNull(ImmediatelyFast.runtimeConfig, "Runtime config not created yet");
        //? if >=26.2 {
        ImmediatelyFast.runtimeConfig.fix_slow_buffer_upload_on_apple_gpu &= isApple && backendName.equals("OpenGL") && !(deviceInfo.underlyingExtensions().contains("GL_ARB_direct_state_access") || deviceInfo.underlyingExtensions().contains("GL_ARB_buffer_storage"));
        if (ImmediatelyFast.runtimeConfig.avoid_redundant_framebuffer_switching && !ImmediatelyFast.config.debug_only_and_not_recommended_disable_hardware_conflict_handling && isIntel && backendName.equals("OpenGL") && (gpuModel.contains("UHD Graphics") || gpuModel.contains("Xe Graphics"))) {
            ImmediatelyFast.LOGGER.warn("Intel UHD Graphics or Intel Xe Graphics detected. Force disabling redundant framebuffer switching optimization.");
            ImmediatelyFast.runtimeConfig.avoid_redundant_framebuffer_switching = false;
        }
        //?}
        //? if (>=1.21.10 && <=26.1.2) {
        /*if (ImmediatelyFast.config.fix_slow_buffer_upload_on_apple_gpu && isApple && backendName.equals("OpenGL") && !(gpuDevice.getEnabledExtensions().contains("GL_ARB_direct_state_access") || gpuDevice.getEnabledExtensions().contains("GL_ARB_buffer_storage"))) {
            ImmediatelyFast.runtimeConfig.disable_fast_buffer_upload = true;
        }
        if (!ImmediatelyFast.config.debug_only_and_not_recommended_disable_mod_conflict_handling) {
            PlatformService.INSTANCE.getModVersion("iris").ifPresent(version -> {
                ImmediatelyFast.LOGGER.info("Found Iris " + version + ". Enabling compatibility.");
                IrisCompat.init();
            });
        }
        *///?}
    }
    //?}

    //? if <=1.21.4 {
    /*public static void windowInit() {
        //? if =1.21.4 {
        /^final String gpuVendor = GlUtil.getVendor();
        ^///?}
        //? if <=1.21.1 {
        /^final String gpuVendor = GL11C.glGetString(GL11C.GL_VENDOR);
        ^///?}
        //? if =1.21.4 {
        /^final String gpuModel = GlUtil.getRenderer();
        ^///?}
        //? if <=1.21.1 {
        /^final String gpuModel = GL11C.glGetString(GL11C.GL_RENDERER);
        ^///?}
        //? if =1.21.4 {
        /^final String glVersion = GlUtil.getOpenGLVersion();
        ^///?}
        //? if <=1.21.1 {
        /^final String glVersion = GL11C.glGetString(GL11C.GL_VERSION);
        ^///?}
        ImmediatelyFast.LOGGER.info("Initializing ImmediatelyFast " + ImmediatelyFast.VERSION + " on " + gpuModel + " (" + gpuVendor + ") with OpenGL " + glVersion);
        boolean isNvidia = false;
        boolean isAmd = false;
        boolean isIntel = false;
        boolean isApple = false;
        if (gpuVendor != null) {
            final String gpuVendorLower = gpuVendor.toLowerCase();

            isNvidia = gpuVendorLower.startsWith("nvidia");
            isAmd = gpuVendorLower.startsWith("ati") || gpuVendorLower.startsWith("amd");
            isIntel = gpuVendorLower.startsWith("intel");
            isApple = gpuVendorLower.startsWith("apple");
        }
        Objects.requireNonNull(ImmediatelyFast.config, "Config not loaded yet");
        Objects.requireNonNull(ImmediatelyFast.runtimeConfig, "Runtime config not created yet");
        if (ImmediatelyFast.config.fast_buffer_upload && isApple && !ImmediatelyFast.config.debug_only_and_not_recommended_disable_hardware_conflict_handling) {
            ImmediatelyFast.LOGGER.warn("Apple GPU detected. Disabling fast buffer upload.");
            ImmediatelyFast.runtimeConfig.fast_buffer_upload = false;
        }
        if (!ImmediatelyFast.config.debug_only_and_not_recommended_disable_mod_conflict_handling) {
            //? if (>=1.21.1 && <=1.21.4) {
            /^PlatformService.INSTANCE.getModVersion("iris").ifPresent(version -> {
                ImmediatelyFast.LOGGER.info("Found Iris " + version + ". Enabling compatibility.");
                IrisCompat.init();
            });
            ^///?}
            //? if =1.20.4 {
            /^PlatformService.INSTANCE.getModVersion("iris").or(() -> PlatformService.INSTANCE.getModVersion("oculus")).ifPresent(version -> {
                ImmediatelyFast.LOGGER.info("Found Iris/Oculus " + version + ". Enabling compatibility.");
                IrisCompat.init();
            });
            ^///?}
        }
    }
    *///?}

    public static void lateInit() {
        if (ImmediatelyFast.config.experimental_sign_text_buffering) {
            ImmediatelyFast.signTextCache = new SignTextCache();
            //? if >=1.21.10 {
            if (PlatformService.INSTANCE.getModVersion("neoforge").isEmpty()) { // NeoForge uses an event. Handled in ImmediatelyFastNeoForge
                ((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(ImmediatelyFast.signTextCache);
            }
            //?}
            //? if =1.21.4 {
            /*if (!PlatformService.INSTANCE.getModVersion("neoforge").isPresent()) { // NeoForge uses an event. Handled in ImmediatelyFastNeoForge
                ((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(ImmediatelyFast.signTextCache);
            }
            *///?}
            //? if <=1.21.1 {
            /*((ReloadableResourceManager) Minecraft.getInstance().getResourceManager()).registerReloadListener(ImmediatelyFast.signTextCache);
            *///?}
        }
    }

    //? if >=1.21.11 {
    public static void onLevelChange() {
        if (ImmediatelyFast.signTextCache != null) {
            ImmediatelyFast.signTextCache.clearCache();
        }
    }
    //?}

    //? if <=1.21.10 {
    /*public static void onWorldJoin() {
        if (ImmediatelyFast.signTextCache != null) {
            ImmediatelyFast.signTextCache.clearCache();
        }
    }
    *///?}

    public static void loadConfig() {
        final Path configFile = PlatformService.INSTANCE.getConfigDirectory().resolve("immediatelyfast.json");
        if (Files.isRegularFile(configFile)) {
            try {
                ImmediatelyFast.config = new Gson().fromJson(Files.readString(configFile), ImmediatelyFastConfig.class);
            } catch (Throwable e) {
                ImmediatelyFast.LOGGER.error("Failed to load ImmediatelyFast config. Resetting it.", e);
            }
        }
        if (ImmediatelyFast.config == null) {
            ImmediatelyFast.config = new ImmediatelyFastConfig();
        }
        //? if >=1.21.10 {
        if (!MathUtil.mathIsPoT(ImmediatelyFast.config.font_atlas_size)) {
            ImmediatelyFast.LOGGER.warn("Font atlas size " + ImmediatelyFast.config.font_atlas_size + " is not a power of two! Rounding up to the next power of two.");
            ImmediatelyFast.config.font_atlas_size = MathUtil.mathRoundPoT(ImmediatelyFast.config.font_atlas_size);
        }
        if (!MathUtil.mathIsPoT(ImmediatelyFast.config.map_atlas_size)) {
            ImmediatelyFast.LOGGER.warn("Map atlas size " + ImmediatelyFast.config.map_atlas_size + " is not a power of two! Rounding up to the next power of two.");
            ImmediatelyFast.config.map_atlas_size = MathUtil.mathRoundPoT(ImmediatelyFast.config.map_atlas_size);
        }
        if (!MathUtil.mathIsPoT(ImmediatelyFast.config.experimental_sign_atlas_size)) {
            ImmediatelyFast.LOGGER.warn("Sign atlas size " + ImmediatelyFast.config.experimental_sign_atlas_size + " is not a power of two! Rounding up to the next power of two.");
            ImmediatelyFast.config.experimental_sign_atlas_size = MathUtil.mathRoundPoT(ImmediatelyFast.config.experimental_sign_atlas_size);
        }
        //?}
        try {
            Files.writeString(configFile, new GsonBuilder().setPrettyPrinting().create().toJson(ImmediatelyFast.config));
        } catch (Throwable e) {
            ImmediatelyFast.LOGGER.error("Failed to save ImmediatelyFast config.", e);
        }
    }

    public static void createRuntimeConfig() {
        ImmediatelyFast.runtimeConfig = new ImmediatelyFastRuntimeConfig(ImmediatelyFast.config);
    }
}
