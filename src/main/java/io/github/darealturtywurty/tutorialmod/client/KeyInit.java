package io.github.darealturtywurty.tutorialmod.client;

import com.mojang.blaze3d.platform.InputConstants;

import io.github.darealturtywurty.tutorialmod.TutorialMod;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.ClientRegistry;

public final class KeyInit {
    public static KeyMapping exampleKeyMapping;

    private KeyInit() {
    }

    public static void init() {
        exampleKeyMapping = registerKey("example_key", KeyMapping.CATEGORY_GAMEPLAY, InputConstants.KEY_S);
    }

    private static KeyMapping registerKey(String name, String category, int keycode) {
        final var key = new KeyMapping("key." + TutorialMod.MODID + "." + name, keycode, category);
        ClientRegistry.registerKeyBinding(key);
        return key;
    }
}
