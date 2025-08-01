package com.gtocore.common.data;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagEntry;

public final class GTOTags {

    private static final ResourceLocation RED_ALLOY_INGOT = new ResourceLocation("morered:red_alloy_ingot");

    public static boolean cache;

    public static boolean entryFilter(TagEntry tagEntry) {
        if (cache) return false;
        if (tagEntry.getId().equals(RED_ALLOY_INGOT)) {
            cache = true;
            return true;
        }
        return false;
    }
}
