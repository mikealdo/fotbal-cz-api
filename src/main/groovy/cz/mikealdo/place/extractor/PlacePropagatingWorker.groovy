package cz.mikealdo.place.extractor
import cz.mikealdo.fotbalcz.domain.FotbalCzLeague
import groovy.transform.TypeChecked
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired

@TypeChecked
@Slf4j
class PlacePropagatingWorker implements PropagationWorker {
    
    private final PlacesJsonBuilder placesJsonBuilder

    @Autowired
    PlacePropagatingWorker(PlacesJsonBuilder placesJsonBuilder) {
        this.placesJsonBuilder = placesJsonBuilder
    }

    @Override
    void collectAndPropagate(long pairId, List<FotbalCzLeague> tweets) {
        Map<String, Optional<FotbalCzLeague>> extractedPlaces = new IdentityHashMap<>();
        String jsonToPropagate = placesJsonBuilder.buildPlacesJson(pairId, extractedPlaces)
        log.debug("Sent json [$jsonToPropagate] to collerator")
    }
}
