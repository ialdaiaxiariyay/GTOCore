package com.gtocore.integration.emi.multipage;

import com.gtocore.client.gui.PatternPreview;

import com.gtolib.api.machine.MultiblockDefinition;

import com.gregtechceu.gtceu.api.machine.MultiblockMachineDefinition;
import com.gregtechceu.gtceu.api.pattern.predicates.SimplePredicate;
import com.gregtechceu.gtceu.integration.emi.multipage.MultiblockInfoEmiCategory;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import com.lowdragmc.lowdraglib.emi.ModularEmiRecipe;
import com.lowdragmc.lowdraglib.emi.ModularForegroundRenderWidget;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.jei.ModularWrapper;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.ListEmiIngredient;
import dev.emi.emi.api.widget.WidgetHolder;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public final class MultiblockInfoEmiRecipe extends ModularEmiRecipe<Widget> {

    private static final Widget MULTIBLOCK = new Widget(0, 0, 160, 160);

    private final MultiblockMachineDefinition definition;

    public MultiblockInfoEmiRecipe(MultiblockMachineDefinition definition) {
        super(() -> MULTIBLOCK);
        this.definition = definition;
        widget = () -> PatternPreview.getPatternWidget(definition);
        var pattern = definition.getPatternFactory().get();
        if (pattern != null && pattern.predicates != null) {
            Set<Set<Item>> parts = new ObjectOpenHashSet<>();
            for (var predicate : pattern.predicates) {
                ArrayList<SimplePredicate> predicates = new ArrayList<>(predicate.common);
                predicates.addAll(predicate.limited);
                for (SimplePredicate simplePredicate : predicates) {
                    if (simplePredicate == null || simplePredicate.candidates == null) continue;
                    Set<Item> items = new ObjectOpenHashSet<>();
                    for (var itemStack : simplePredicate.getCandidates()) {
                        var item = itemStack.getItem();
                        if (item == Items.AIR) continue;
                        items.add(item);
                    }
                    if (items.size() > 1) parts.add(items);
                }
            }
            parts.forEach(p -> inputs.add(new ListEmiIngredient(p.stream().map(EmiStack::of).toList(), 1)));
        }
        MultiblockDefinition.of(definition).getPatterns()[0].parts().forEach(i -> super.inputs.add(EmiStack.of(i)));
    }

    @Override
    public List<Widget> getFlatWidgetCollection(Widget widgetIn) {
        return Collections.emptyList();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return MultiblockInfoEmiCategory.CATEGORY;
    }

    @Override
    public @Nullable ResourceLocation getId() {
        return definition.getId();
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        var widget = this.widget.get();
        var modular = new ModularWrapper<>(widget);
        modular.setRecipeWidget(0, 0);

        synchronized (CACHE_OPENED) {
            CACHE_OPENED.add(modular);
        }
        widgets.add(new CustomModularEmiRecipe(modular, Collections.emptyList()));
        widgets.add(new ModularForegroundRenderWidget(modular));
    }

    @Override
    public void addTempWidgets(WidgetHolder widgets) {
        if (TEMP_CACHE != null) {
            TEMP_CACHE.modularUI.triggerCloseListeners();
            TEMP_CACHE = null;
        }

        PatternPreview widget = (PatternPreview) this.widget.get();
        ModularWrapper<PatternPreview> modular = new ModularWrapper<>(widget);
        modular.setRecipeWidget(0, 0);
        widgets.add(new CustomModularEmiRecipe(modular, Collections.emptyList()));
        TEMP_CACHE = modular;
    }
}
