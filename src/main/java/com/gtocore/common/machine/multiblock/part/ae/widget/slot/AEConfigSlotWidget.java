package com.gtocore.common.machine.multiblock.part.ae.widget.slot;

import com.gtocore.common.machine.multiblock.part.ae.widget.ConfigWidget;

import com.gregtechceu.gtceu.integration.ae2.slot.IConfigurableSlot;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import appeng.api.stacks.GenericStack;
import com.lowdragmc.lowdraglib.gui.widget.Widget;
import com.lowdragmc.lowdraglib.utils.Position;
import com.lowdragmc.lowdraglib.utils.Size;
import com.mojang.blaze3d.systems.RenderSystem;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.lowdragmc.lowdraglib.gui.util.DrawerHelper.drawGradientRect;

public class AEConfigSlotWidget extends Widget {

    final ConfigWidget parentWidget;
    final int index;
    static final int REMOVE_ID = 1000;
    static final int UPDATE_ID = 1001;
    static final int AMOUNT_CHANGE_ID = 1002;
    static final int PICK_UP_ID = 1003;
    boolean select = false;

    AEConfigSlotWidget(Position pos, Size size, ConfigWidget widget, int index) {
        super(pos, size);
        this.parentWidget = widget;
        this.index = index;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void drawInForeground(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        super.drawInForeground(graphics, mouseX, mouseY, partialTicks);
        IConfigurableSlot slot = this.parentWidget.getDisplay(this.index);
        if (slot.getConfig() == null) {
            if (mouseOverConfig(mouseX, mouseY)) {
                List<Component> hoverStringList = new ArrayList<>();
                hoverStringList.add(Component.translatable("gtceu.gui.config_slot"));
                if (parentWidget.isAutoPull()) {
                    hoverStringList.add(Component.translatable("gtceu.gui.config_slot.auto_pull_managed"));
                } else {
                    if (!parentWidget.isStocking()) {
                        hoverStringList.add(Component.translatable("gtceu.gui.config_slot.set"));
                        hoverStringList.add(Component.translatable("gtceu.gui.config_slot.scroll"));
                    } else {
                        hoverStringList.add(Component.translatable("gtceu.gui.config_slot.set_only"));
                    }
                    hoverStringList.add(Component.translatable("gtceu.gui.config_slot.remove"));
                }
                graphics.renderTooltip(Minecraft.getInstance().font, hoverStringList, Optional.empty(), mouseX, mouseY);
            }
        } else {
            GenericStack item = null;
            if (mouseOverConfig(mouseX, mouseY)) {
                item = slot.getConfig();
            } else if (mouseOverStock(mouseX, mouseY)) {
                item = slot.getStock();
            }
            if (item != null) {
                graphics.renderTooltip(Minecraft.getInstance().font, GenericStack.wrapInItemStack(item), mouseX, mouseY);
            }
        }
    }

    boolean mouseOverConfig(double mouseX, double mouseY) {
        Position position = getPosition();
        return isMouseOver(position.x, position.y, 18, 18, mouseX, mouseY);
    }

    boolean mouseOverStock(double mouseX, double mouseY) {
        Position position = getPosition();
        return isMouseOver(position.x, position.y + 18, 18, 18, mouseX, mouseY);
    }

    @OnlyIn(Dist.CLIENT)
    static void drawSelectionOverlay(GuiGraphics graphics, int x, int y, int width, int height) {
        RenderSystem.disableDepthTest();
        RenderSystem.colorMask(true, true, true, false);
        drawGradientRect(graphics, x, y, width, height, -2130706433, -2130706433);
        RenderSystem.colorMask(true, true, true, true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
    }

    boolean isStackValidForSlot(GenericStack stack) {
        if (stack == null || stack.amount() < 0) return true;
        if (!parentWidget.isStocking()) return true;
        return !parentWidget.hasStackInConfig(stack);
    }

    public void setSelect(final boolean select) {
        this.select = select;
    }
}
