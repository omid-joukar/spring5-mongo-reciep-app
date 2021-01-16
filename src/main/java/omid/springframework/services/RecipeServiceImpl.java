package omid.springframework.services;

import omid.springframework.commands.RecipeCommand;
import omid.springframework.converters.RecipeCommandToRecipe;
import omid.springframework.converters.RecipeToRecipeCommand;
import omid.springframework.domain.Recipe;
import omid.springframework.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import omid.springframework.repositories.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by jt on 6/13/17.
 */
@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeReactiveRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeReactiveRepository recipeReactiveRepository, RecipeCommandToRecipe recipeCommandToRecipe, RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public Flux<Recipe> getRecipes() {
        log.debug("I'm in the service");

        Flux<Recipe> recipes = recipeReactiveRepository.findAll();
        return recipes;
    }

    @Override
    public Mono<Recipe> findById(String id) {

        Mono<Recipe>  recipeMono = recipeReactiveRepository.findById(id);

        if (recipeMono.block().getId() == null) {
            throw new NotFoundException("Recipe Not Found. For ID value: " + id );
        }

        return recipeMono;
    }

    @Override
    public Mono<RecipeCommand> findCommandById(String id) {
        return Mono.just(recipeToRecipeCommand.convert(findById(id).block()));
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand command) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(command);

        Recipe savedRecipe = recipeReactiveRepository.save(detachedRecipe).block();
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return Mono.just(recipeToRecipeCommand.convert(savedRecipe));
    }

    @Override
    public void deleteById(String idToDelete) {
        recipeReactiveRepository.deleteById(idToDelete);
    }
}
