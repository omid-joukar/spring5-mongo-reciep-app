package omid.springframework.services;

import omid.springframework.commands.UnitOfMeasureCommand;
import reactor.core.publisher.Flux;


/**
 * Created by jt on 6/28/17.
 */
public interface UnitOfMeasureService {

    Flux<UnitOfMeasureCommand> listAllUoms();
}
