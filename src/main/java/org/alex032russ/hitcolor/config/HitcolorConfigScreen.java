package org.alex032russ.hitcolor.config;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

import java.awt.Color;

public class HitcolorConfigScreen extends Screen {
    private TextFieldWidget colorField;
    private CustomSlider alphaSlider;
    private Button saveButton;
    private Button cancelButton;

    public HitcolorConfigScreen() {
        super(new StringTextComponent("Hitcolor Configuration"));
    }

    @Override
    protected void init() {
        super.init();

        this.minecraft = Minecraft.getInstance();

        // Текстовое поле для ввода цвета в формате #RRGGBB
        this.colorField = new TextFieldWidget(
                this.font,
                this.width / 2 - 100, 50,
                200, 20,
                new StringTextComponent("Color")
        );
        this.colorField.setMaxLength(7); // #RRGGBB
        this.colorField.setValue(HitcolorConfig.CONFIG.colorHex.get());
        this.children.add(this.colorField);

        // Слайдер для настройки прозрачности
        this.alphaSlider = new CustomSlider(
                this.width / 2 - 100, 90,
                200, 20,
                "Transparency: ",
                "%",
                0, 100,
                HitcolorConfig.CONFIG.alpha.get()
        );
        this.addButton(this.alphaSlider);

        // Кнопки сохранения и отмены
        this.saveButton = this.addButton(new Button(
                this.width / 2 - 102, this.height - 40,
                100, 20,
                new StringTextComponent("Save"),
                button -> save()
        ));

        this.cancelButton = this.addButton(new Button(
                this.width / 2 + 2, this.height - 40,
                100, 20,
                new StringTextComponent("Cancel"),
                button -> cancel()
        ));
    }

    private void cancel() {
        this.minecraft.setScreen(null); // исправлено
    }

    private void save() {
        if (validateInputs()) {
            saveSettings();
            this.minecraft.setScreen(null); // Правильное имя в 1.16.5
        }
    }

    private boolean validateInputs() {
        // Проверка цвета
        String color = this.colorField.getValue();
        if (!color.startsWith("#") || color.length() != 7) {
            // Неверный формат цвета
            this.colorField.setValue(color);
            return false;
        }

        try {
            // Пытаемся распарсить цвет
            Color.decode(color);
        } catch (NumberFormatException e) {
            // Неверный формат цвета
            return false;
        }

        // Если все в порядке, возвращаем true
        return true;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        // Рендерим метки
        drawCenteredString(matrixStack, this.font, "Hitcolor Configuration", this.width / 2, 15, 0xFFFFFF);
        drawString(matrixStack, this.font, "Color (hex format, e.g. #ff0000):", this.width / 2 - 100, 40, 0xFFFFFF);

        // Рендерим образец цвета
        try {
            Color color = Color.decode(this.colorField.getValue());
            int alpha = (int) (255 * (1 - this.alphaSlider.getValue() / 100));
            fill(matrixStack, this.width / 2 + 120, 50, this.width / 2 + 150, 70, alpha << 24 | color.getRGB() & 0xFFFFFF);
        } catch (NumberFormatException e) {
            // Ничего не делаем, просто не рендерим образец
        }

        // Рендерим текстовое поле (оно не рендерится автоматически)
        this.colorField.render(matrixStack, mouseX, mouseY, partialTicks);
    }

    @Override
    public void tick() {
        super.tick();
        this.colorField.tick();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.colorField.isFocused()) {
            return this.colorField.keyPressed(keyCode, scanCode, modifiers);
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(char c, int keyCode) {
        if (this.colorField.isFocused()) {
            return this.colorField.charTyped(c, keyCode);
        }
        return super.charTyped(c, keyCode);
    }

    private void saveSettings() {
        org.alex032russ.hitcolor.config.HitcolorConfig.CONFIG.colorHex.set(this.colorField.getValue());
        org.alex032russ.hitcolor.config.HitcolorConfig.CONFIG.alpha.set(this.alphaSlider.getValue());

        // Обновляем цвет и текстуру
        org.alex032russ.hitcolor.config.HitcolorConfig.updateColor();
        org.alex032russ.hitcolor.config.HitcolorConfig.updateHitcolor();
    }
}