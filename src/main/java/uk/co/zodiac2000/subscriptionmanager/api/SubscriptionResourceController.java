package uk.co.zodiac2000.subscriptionmanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionResourceService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * REST API for interacting with the subscription resource aggregate.
 */
@RestController
@RequestMapping(path = "/subscription-resource", produces = "application/json")
public class SubscriptionResourceController {

    @Autowired
    private SubscriptionResourceService subscriptionResourceService;

    /**
     * Returns the SubscriptionResource identified by id, or a 404 response if not found.
     * @param id the subscription resource id
     * @return the subscription resource
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<SubscriptionResourceResponseDto> getSubscriptionResource(@PathVariable("id") final long id) {
        return ResponseEntity.of(this.subscriptionResourceService.getSubscriptionResource(id));
    }

}
