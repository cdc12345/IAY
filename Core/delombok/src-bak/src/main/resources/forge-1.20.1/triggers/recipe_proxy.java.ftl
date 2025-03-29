<#include "procedures.java.ftl">
import org.jetbrains.annotations.NotNull;
@Mod.EventBusSubscriber public class ${name}Procedure {
	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		var recipeManager = event.getEntity().level().getRecipeManager();
		var recipes = new ArrayList<Recipe<?>>();
		recipeManager.getRecipes().forEach(a->recipes.add(new ProxyRecipe<>(a,event)));
		recipeManager.replaceRecipes(recipes);
	}

	private static class ProxyRecipe<E extends net.minecraft.world.Container> implements Recipe<E>{
		private final Recipe<E> origin;
		private PlayerEvent.PlayerLoggedInEvent event;

		private ProxyRecipe(Recipe<E> origin,PlayerEvent.PlayerLoggedInEvent event){
			this.origin = origin;
			this.event = event;
		}

		@Override
		public boolean matches(@NotNull E pContainer, @NotNull Level pLevel) {
			return origin.matches(pContainer, pLevel);
		}

		@Override
		public @NotNull ItemStack assemble(@NotNull E pContainer, @NotNull RegistryAccess pRegistryAccess) {
			return origin.assemble(pContainer, pRegistryAccess);
		}

		@Override
		public boolean canCraftInDimensions(int pWidth, int pHeight) {
			return false;
		}

		@Override
		public boolean isSpecial() {
			return origin.isSpecial();
		}

		@Override
		public boolean showNotification() {
			return origin.showNotification();
		}

		@Override
		public @NotNull String getGroup() {
			return origin.getGroup();
		}

		@Override
		public @NotNull ItemStack getToastSymbol() {
			return origin.getToastSymbol();
		}

		@Override
		public @NotNull ResourceLocation getId() {
			return origin.getId();
		}

		@Override
		public @NotNull RecipeSerializer<?> getSerializer() {
			return origin.getSerializer();
		}

		@Override
		public @NotNull RecipeType<?> getType() {
			return origin.getType();
		}

		@Override
		public boolean isIncomplete() {
			return origin.isIncomplete();
		}

		@Override
		public @NotNull ItemStack getResultItem(@NotNull RegistryAccess pRegistryAccess) {
			return origin.getResultItem(pRegistryAccess);
		}

		@Override
		public @NotNull NonNullList<ItemStack> getRemainingItems(@NotNull E pContainer) {
			return origin.getRemainingItems(pContainer);
		}

		@Override
		public @NotNull NonNullList<Ingredient> getIngredients() {
			return origin.getIngredients();
		}
	}