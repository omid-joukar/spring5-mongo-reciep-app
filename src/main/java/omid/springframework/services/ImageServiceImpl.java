package omid.springframework.services;
import lombok.extern.slf4j.Slf4j;
import omid.springframework.domain.Recipe;
import omid.springframework.repositories.reactive.RecipeReactiveRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * Created by jt on 7/3/17.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecipeReactiveRepository recipeReactiveRepository;

    public ImageServiceImpl(RecipeReactiveRepository recipeReactiveRepository) {
        this.recipeReactiveRepository = recipeReactiveRepository;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

       Mono<Recipe> recipeMono =  recipeReactiveRepository.findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObjects = new Byte[0];
                    try {
                        byteObjects = new Byte[file.getBytes().length];
                        int i = 0;
                        for (byte b : file.getBytes()){
                            byteObjects[i++] = b;
                        }
                        recipe.setImage(byteObjects);
                    } catch (IOException e) {
                        //todo handle better
                        log.error("Error occurred", e);
                        e.printStackTrace();
                    }
                    return recipe;
                });
        recipeReactiveRepository.save(recipeMono.block()).block();
        return Mono.empty();
    }
}
