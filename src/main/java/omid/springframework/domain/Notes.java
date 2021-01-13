package omid.springframework.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Created by jt on 6/13/17.
 */
@Getter
@Setter
public class Notes {
    @Id
    private String id;
    private String recipeNotes;

}
