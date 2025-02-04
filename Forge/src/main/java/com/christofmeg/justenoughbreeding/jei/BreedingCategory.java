package com.christofmeg.justenoughbreeding.jei;

import com.christofmeg.justenoughbreeding.CommonConstants;
import com.christofmeg.justenoughbreeding.recipe.BreedingRecipe;
import com.christofmeg.justenoughbreeding.utils.Utils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BreedingCategory implements IRecipeCategory<BreedingRecipe> {

    public static final RecipeType<BreedingRecipe> TYPE = new RecipeType<>(
            new ResourceLocation(CommonConstants.MOD_ID, "breeding"), BreedingRecipe.class);

    final ResourceLocation slotVanilla = new ResourceLocation("jei",
            "textures/jei/atlas/gui/slot.png");

    final ResourceLocation guiVanilla = new ResourceLocation("jei",
            "textures/jei/gui/gui_vanilla.png");

    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable slot;
    private final IDrawable outputSlot;
    private final IDrawable mobRenderSlotTop;
    private final IDrawable mobRenderSlotBottom;
    private final IDrawable mobRenderSlotLeft;
    private final IDrawable mobRenderSlotRight;
    private final IDrawable mobRenderSlotTopCorner;
    private final IDrawable mobRenderSlotTopCenter;

    final int inputSlotItemX = 69;
    final int inputSlotFrameX = 68;
    final int inputSlot1ItemY = 52;
    final int inputSlot1FrameY = 51;
    final int inputSlot2FrameY = 32;

    final int outputSlotFrameX = 94;
    final int outputSlotFrameY = 38;


    public BreedingCategory(IGuiHelper helper, ItemLike itemStack) {
        background = helper.createBlankDrawable(151, 91);
        icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(itemStack));
        slot = helper.drawableBuilder(slotVanilla, 0, 0, 18, 18).setTextureSize(18, 18).build();
        outputSlot = helper.drawableBuilder(guiVanilla, 25, 224, 57, 26).setTextureSize(256,256).build();
        mobRenderSlotTop = helper.drawableBuilder(guiVanilla, 56, 128, 25, 1).setTextureSize(256, 256).build();
        mobRenderSlotBottom = helper.drawableBuilder(guiVanilla, 57, 153, 25, 1).setTextureSize(256, 256).build();
        mobRenderSlotLeft = helper.drawableBuilder(guiVanilla, 56, 129, 1, 24).setTextureSize(256, 256).build();
        mobRenderSlotRight = helper.drawableBuilder(guiVanilla, 81, 129, 1, 24).setTextureSize(256, 256).build();
        mobRenderSlotTopCorner = helper.drawableBuilder(guiVanilla, 81, 128, 1, 1).setTextureSize(256, 256).build();
        mobRenderSlotTopCenter = helper.drawableBuilder(guiVanilla, 57, 129, 24, 24).setTextureSize(256, 256).build();
    }

    @Override
    public @NotNull RecipeType<BreedingRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("translation.justenoughbreeding.breeding");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return background;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BreedingRecipe recipe, @NotNull IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 134, 1).addItemStack((recipe.spawnEgg));
        builder.addInvisibleIngredients(RecipeIngredientRole.OUTPUT).addItemStack(recipe.spawnEgg);
        builder.addSlot(RecipeIngredientRole.INPUT, inputSlotItemX, inputSlot1ItemY).addIngredients((recipe.breedingCatalyst));

        final int outputSlotItemX = 130;
        final int outputSlotItemY = 43;
        final int inputSlot2ItemY = 33;

        if (recipe.resultItemStack != null && !recipe.resultItemStack.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, outputSlotItemX, outputSlotItemY).addIngredients(recipe.resultItemStack);
        }
        if (recipe.extraInputStack != null  && !recipe.extraInputStack.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 69, inputSlot2ItemY).addIngredients(recipe.extraInputStack);
        }
    }

    @Override
    public void draw(@NotNull BreedingRecipe recipe, @NotNull IRecipeSlotsView recipeSlotsView, @NotNull GuiGraphics stack, double mouseX, double mouseY) {

        // Spawn Egg Slot
        slot.draw(stack, 133, 0);

        // Input Slot
        slot.draw(stack, inputSlotFrameX, inputSlot1FrameY);

        // Extra Input Slot
        slot.draw(stack, inputSlotFrameX, inputSlot2FrameY);

        // Output Slot
        outputSlot.draw(stack, outputSlotFrameX, outputSlotFrameY);

        mobRenderSlotTop.draw(stack, 0, 10);
        mobRenderSlotTop.draw(stack, 25, 10);
        mobRenderSlotTop.draw(stack, 35, 10);

        mobRenderSlotTopCorner.draw(stack, 60, 10);
        mobRenderSlotTopCorner.draw(stack, 0, 90);

        mobRenderSlotBottom.draw(stack, 1, 90);
        mobRenderSlotBottom.draw(stack, 26, 90);
        mobRenderSlotBottom.draw(stack, 36, 90);

        mobRenderSlotLeft.draw(stack, 0, 11);
        mobRenderSlotLeft.draw(stack, 0, 35);
        mobRenderSlotLeft.draw(stack, 0, 59);
        mobRenderSlotLeft.draw(stack, 0, 66);

        mobRenderSlotRight.draw(stack, 60, 11);
        mobRenderSlotRight.draw(stack, 60, 35);
        mobRenderSlotRight.draw(stack, 60, 59);
        mobRenderSlotRight.draw(stack, 60, 66);

        mobRenderSlotTopCenter.draw(stack, 1, 11);
        mobRenderSlotTopCenter.draw(stack, 25, 11);
        mobRenderSlotTopCenter.draw(stack, 36, 11);
        mobRenderSlotTopCenter.draw(stack, 1, 35);
        mobRenderSlotTopCenter.draw(stack, 25, 35);
        mobRenderSlotTopCenter.draw(stack, 36, 35);
        mobRenderSlotTopCenter.draw(stack, 1, 59);
        mobRenderSlotTopCenter.draw(stack, 25, 59);
        mobRenderSlotTopCenter.draw(stack, 36, 59);
        mobRenderSlotTopCenter.draw(stack, 1, 66);
        mobRenderSlotTopCenter.draw(stack, 25, 66);
        mobRenderSlotTopCenter.draw(stack, 36, 66);

        EntityType<?> entityType = recipe.entityType;
        if (entityType != null) {
            Minecraft instance = Minecraft.getInstance();
            Font font = instance.font;
            Component entityName = Component.translatable(entityType.getDescriptionId());

            String entityNameString = entityName.getString(); // Convert Component to String
            if (recipe.needsToBeTamed != null) {
                Component tamed = Component.translatable("translation.justenoughbreeding.tamed");
                entityNameString += " (" + tamed.getString() + ")";
            } else if (recipe.animalTrusting != null) {
                Component trusting = Component.translatable("translation.justenoughbreeding.trusting");
                entityNameString += " (" + trusting.getString() + ")";
            } else if (recipe.spawnEgg.getDescriptionId().startsWith("item.tfc")) {
                Component familiarity = Component.translatable("tfc.jade.familiarity");
                String tfc = familiarity.getString().replaceAll(":[^:]*$", "");
                entityNameString += " (" + tfc + " > 30" + ")";
            }

            int stringWidth = font.width(entityNameString); // Measure the width of the string in pixels

            int availableWidth = 154; // Initial available width in pixels
            if (stringWidth > availableWidth) {
                float pixelWidthPerCharacter = (float) stringWidth / entityNameString.length();
                int maxCharacters = (int) (availableWidth / pixelWidthPerCharacter);
                entityNameString = entityNameString.substring(0, maxCharacters);
            }

            if (!entityNameString.isEmpty()) {
                Component abbreviatedEntityName = Component.nullToEmpty(entityNameString);
                stack.drawString(font, abbreviatedEntityName, 0, 0, DyeColor.BLACK.getTextColor(), false);
            }

            LivingEntity currentLivingEntity = recipe.doRendering();
            if (currentLivingEntity != null) {
                Utils.renderEntity(stack.pose(), mouseX, currentLivingEntity);
            }

        }
    }

}