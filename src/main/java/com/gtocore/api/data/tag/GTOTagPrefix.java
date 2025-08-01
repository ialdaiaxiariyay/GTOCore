package com.gtocore.api.data.tag;

import com.gtocore.api.data.material.GTOMaterialFlags;
import com.gtocore.common.data.GTOBlocks;

import com.gtolib.GTOCore;
import com.gtolib.api.data.chemical.material.GTOMaterialBuilder;
import com.gtolib.mixin.MaterialPropertiesAccessor;
import com.gtolib.utils.RLUtils;

import com.gregtechceu.gtceu.api.GTValues;
import com.gregtechceu.gtceu.api.block.OreBlock;
import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags;
import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialIconType;
import com.gregtechceu.gtceu.api.data.chemical.material.properties.PropertyKey;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import com.kyanite.deeperdarker.DeeperDarker;
import com.kyanite.deeperdarker.content.DDBlocks;
import earth.terrarium.adastra.common.registry.ModBlocks;
import vazkii.botania.common.block.BotaniaBlocks;

import static com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlags.NO_SMASHING;
import static com.gregtechceu.gtceu.api.data.tag.TagPrefix.Conditions.hasOreProperty;

@SuppressWarnings("unused")
public final class GTOTagPrefix extends TagPrefix {

    private int maxDamage;

    private GTOTagPrefix(String name) {
        super(name);
    }

    private static boolean needSmall(Material material) {
        var properties = ((MaterialPropertiesAccessor) GTOMaterialBuilder.getProperties(material)).getPropertyMap();
        int size = 0;
        for (var propertie : properties.keySet()) {
            if (propertie == PropertyKey.EMPTY || propertie == PropertyKey.HAZARD) continue;
            size++;
        }
        return size > 1;
    }

    public static void init() {
        TagPrefix.dustTiny.generationCondition(Conditions.hasDustProperty.and(mat -> needSmall(mat) || mat.hasFlag(GTOMaterialFlags.GENERATE_TINY_DUST)));
        TagPrefix.dustSmall.generationCondition(Conditions.hasDustProperty.and(mat -> needSmall(mat) || mat.hasFlag(GTOMaterialFlags.GENERATE_SMALL_DUST)));
    }

    private static TagPrefix ore(String name) {
        return new GTOTagPrefix(name).defaultTagPath("ores/%s").prefixOnlyTagPath("ores_in_ground/%s").unformattedTagPath("ores").materialIconType(MaterialIconType.ore).blockConstructor(OreBlock::new).miningToolTag(BlockTags.MINEABLE_WITH_PICKAXE).unificationEnabled(true).generationCondition(hasOreProperty);
    }

    public static final TagPrefix MOON_STONE = ore("moon_stone").registerOre(() -> ModBlocks.MOON_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), RLUtils.ad("block/moon_stone"));
    public static final TagPrefix MARS_STONE = ore("mars_stone").registerOre(() -> ModBlocks.MARS_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), RLUtils.ad("block/mars_stone"));
    public static final TagPrefix VENUS_STONE = ore("venus_stone").registerOre(() -> ModBlocks.VENUS_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), RLUtils.ad("block/venus_stone"));
    public static final TagPrefix MERCURY_STONE = ore("mercury_stone").registerOre(() -> ModBlocks.MERCURY_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), RLUtils.ad("block/mercury_stone"));
    public static final TagPrefix GLACIO_STONE = ore("glacio_stone").registerOre(() -> ModBlocks.GLACIO_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), RLUtils.ad("block/glacio_stone"));
    public static final TagPrefix TITAN_STONE = ore("titan_stone").registerOre(() -> GTOBlocks.TITAN_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GTOCore.id("block/titan_stone"));
    public static final TagPrefix PLUTO_STONE = ore("pluto_stone").registerOre(() -> GTOBlocks.PLUTO_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GTOCore.id("block/pluto_stone"));
    public static final TagPrefix IO_STONE = ore("io_stone").registerOre(() -> GTOBlocks.IO_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GTOCore.id("block/io_stone"));
    public static final TagPrefix GANYMEDE_STONE = ore("ganymede_stone").registerOre(() -> GTOBlocks.GANYMEDE_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GTOCore.id("block/ganymede_stone"));
    public static final TagPrefix ENCELADUS_STONE = ore("enceladus_stone").registerOre(() -> GTOBlocks.ENCELADUS_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GTOCore.id("block/enceladus_stone"));
    public static final TagPrefix CERES_STONE = ore("ceres_stone").registerOre(() -> GTOBlocks.CERES_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), GTOCore.id("block/ceres_stone"));
    public static final TagPrefix SCULK_STONE = ore("sculk_stone").registerOre(() -> DDBlocks.SCULK_STONE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).requiresCorrectToolForDrops().strength(3.5F, 4.5F), DeeperDarker.rl("block/sculk_stone"));
    public static final TagPrefix GLOOMSLATE = ore("gloomslate").registerOre(() -> DDBlocks.GLOOMSLATE.get().defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_BROWN).requiresCorrectToolForDrops().strength(4.0F, 5.0F), DeeperDarker.rl("block/gloomslate"));
    public static final TagPrefix LIVING_STONE = ore("living_rock").registerOre(() -> BotaniaBlocks.livingrock.defaultBlockState(), null, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).requiresCorrectToolForDrops().strength(3.0F, 3.0F), RLUtils.bot("block/livingrock"));
    private static final MaterialIconType NANITES_ICON = new MaterialIconType("nanites");
    public static final TagPrefix CATALYST = new GTOTagPrefix("catalyst").maxDamage(10000).idPattern("%s_catalyst").defaultTagPath("catalyst/%s").unformattedTagPath("catalyst").materialAmount(GTValues.M).materialIconType(new MaterialIconType("catalyst")).unificationEnabled(true).generateItem(true).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_CATALYST));
    public static final TagPrefix NANITES = new GTOTagPrefix("nanites").idPattern("%s_nanites").defaultTagPath("nanites/%s").unformattedTagPath("nanites").materialAmount(GTValues.M).materialIconType(NANITES_ICON).unificationEnabled(true).generateItem(true).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_NANITES));
    public static final TagPrefix CONTAMINABLE_NANITES = new GTOTagPrefix("contaminable_nanites").idPattern("contaminable_%s_nanites").defaultTagPath("contaminable_nanites/%s").unformattedTagPath("contaminable_nanites").materialAmount(GTValues.M).materialIconType(NANITES_ICON).unificationEnabled(true).generateItem(true).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_NANITES));
    public static final TagPrefix MILLED = new GTOTagPrefix("milled").idPattern("milled_%s").defaultTagPath("milleds/%s").unformattedTagPath("milleds").materialAmount(GTValues.M).materialIconType(new MaterialIconType("milled")).unificationEnabled(true).generateItem(true).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_MILLED));
    public static final TagPrefix CURVED_PLATE = new GTOTagPrefix("curved_plate").idPattern("curved_%s_plate").defaultTagPath("curved_plates/%s").unformattedTagPath("curved_plates").materialAmount(GTValues.M).materialIconType(new MaterialIconType("curved_plate")).unificationEnabled(true).generateItem(true).enableRecycling().generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_CURVED_PLATE) || mat.hasFlag(MaterialFlags.GENERATE_ROTOR) || ((mat.hasProperty(PropertyKey.FLUID_PIPE) || mat.hasProperty(PropertyKey.ITEM_PIPE)) && !mat.hasFlag(NO_SMASHING) && mat.getMass() < 240 && mat.getBlastTemperature() < 3600));
    public static final TagPrefix MOTOR_ENCLOSURE = new GTOTagPrefix("motor_enclosure").idPattern("%s_motor_enclosure").defaultTagPath("motor_enclosures/%s").unformattedTagPath("motor_enclosures").materialAmount(GTValues.M << 1).materialIconType(new MaterialIconType("motor_enclosure")).unificationEnabled(true).generateItem(true).enableRecycling().generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_COMPONENT));
    public static final TagPrefix PUMP_BARREL = new GTOTagPrefix("pump_barrel").idPattern("%s_pump_barrel").defaultTagPath("pump_barrels/%s").unformattedTagPath("pump_barrels").materialAmount(GTValues.M * 5 / 2).materialIconType(new MaterialIconType("pump_barrel")).unificationEnabled(true).generateItem(true).enableRecycling().generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_COMPONENT));
    public static final TagPrefix PISTON_HOUSING = new GTOTagPrefix("piston_housing").idPattern("%s_piston_housing").defaultTagPath("piston_housings/%s").unformattedTagPath("piston_housings").materialAmount(GTValues.M * 3).materialIconType(new MaterialIconType("piston_housing")).unificationEnabled(true).generateItem(true).enableRecycling().generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_COMPONENT));
    public static final TagPrefix EMITTER_BASES = new GTOTagPrefix("emitter_base").idPattern("%s_emitter_base").defaultTagPath("emitter_bases/%s").unformattedTagPath("emitter_bases").materialAmount(GTValues.M << 2).materialIconType(new MaterialIconType("emitter_base")).unificationEnabled(true).generateItem(true).enableRecycling().generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_COMPONENT));
    public static final TagPrefix SENSOR_CASING = new GTOTagPrefix("sensor_casing").idPattern("%s_sensor_casing").defaultTagPath("sensor_casings/%s").unformattedTagPath("sensor_casings").materialAmount(GTValues.M * 9 / 2).materialIconType(new MaterialIconType("sensor_casing")).unificationEnabled(true).generateItem(true).enableRecycling().generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_COMPONENT));
    public static final TagPrefix FIELD_GENERATOR_CASING = new GTOTagPrefix("field_generator_casing").idPattern("%s_field_generator_casing").defaultTagPath("field_generator_casing/%s").unformattedTagPath("field_generator_casing").materialAmount(GTValues.M << 3).materialIconType(new MaterialIconType("field_generator_casing")).unificationEnabled(true).generateItem(true).enableRecycling().generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_COMPONENT));
    public static final TagPrefix ROUGH_BLANK = new GTOTagPrefix("rough_blank").idPattern("%s_rough_blank").defaultTagPath("rough_blank/%s").unformattedTagPath("rough_blank").materialAmount(GTValues.M * 9).materialIconType(MaterialIconType.rawOreBlock).miningToolTag(BlockTags.MINEABLE_WITH_PICKAXE).unificationEnabled(true).generateBlock(true).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_CERAMIC));
    public static final TagPrefix BRICK = new GTOTagPrefix("brick").idPattern("%s_brick").defaultTagPath("brick/%s").unformattedTagPath("brick").materialAmount(GTValues.M).materialIconType(MaterialIconType.ingot).unificationEnabled(true).generateItem(true).enableRecycling().generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_CERAMIC));
    public static final TagPrefix FLAKES = new GTOTagPrefix("flake").idPattern("%s_flake").defaultTagPath("flake/%s").unformattedTagPath("flake").materialAmount(GTValues.M / 4).materialIconType(new MaterialIconType("flake")).unificationEnabled(true).generateItem(true).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_CERAMIC));
    public static final TagPrefix ARTIFICIAL_GEM = new GTOTagPrefix("artificial_gem").idPattern("artificial_%s_gem").defaultTagPath("artificial_gem/%s").unformattedTagPath("artificial_gem").materialAmount(GTValues.M << 2).materialIconType(new MaterialIconType("artificial_gem")).unificationEnabled(true).generateItem(true).enableRecycling().tooltip((m, l) -> l.add(Component.translatable("gtocore.tooltip.artificial_gem").withStyle(ChatFormatting.GRAY))).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_ARTIFICIAL_GEM));
    public static final TagPrefix COIN = new GTOTagPrefix("coin").idPattern("%s_coin").defaultTagPath("coin/%s").unformattedTagPath("coin").materialAmount(GTValues.M * 32).materialIconType(new MaterialIconType("coin")).unificationEnabled(true).generateItem(true).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_COIN));
    public static final TagPrefix CRYSTAL_SEED = new GTOTagPrefix("crystal_seed").idPattern("%s_crystal_seed").defaultTagPath("crystal_seed/%s").unformattedTagPath("crystal_seed").materialAmount(GTValues.M / 8).materialIconType(new MaterialIconType("crystal_seed")).unificationEnabled(true).generateItem(true).generationCondition(mat -> mat.hasFlag(GTOMaterialFlags.GENERATE_CRYSTAL_SEED));
    public static final TagPrefix SUPERCONDUCTOR_BASE = new GTOTagPrefix("superconductor_base").idPattern("%s_superconductor_base").defaultTagPath("superconductor_base/%s").unformattedTagPath("superconductor_base").materialAmount(GTValues.M / 2).materialIconType(new MaterialIconType("superconductor_base")).unificationEnabled(true).generateItem(true).generationCondition(mat -> {
        var property = mat.getProperty(PropertyKey.WIRE);
        return property != null && property.isSuperconductor() && property.getVoltage() < GTValues.VA[GTValues.MAX];
    });

    public int maxDamage() {
        return this.maxDamage;
    }

    /**
     * @return {@code this}.
     */
    private GTOTagPrefix maxDamage(final int maxDamage) {
        this.maxDamage = maxDamage;
        return this;
    }
}
