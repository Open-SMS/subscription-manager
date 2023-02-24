package uk.co.zodiac2000.subscriptionmanager.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionContentService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;

/**
 * REST API for interacting with the SubscriptionContent aggregate.
 */
@RestController
@RequestMapping(path = "/subscription-content", produces = "application/json")
public class SubscriptionContentController {

    @Autowired
    private SubscriptionContentService subscriptionContentService;

    /**
     * Returns the SubscriptionContent identified by id, or a 404 response if not found.
     * @param id the subscription content id
     * @return the subscription content
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<SubscriptionContentResponseDto> getSubscriptionContent(@PathVariable("id") final long id) {
        return ResponseEntity.of(this.subscriptionContentService.getSubscriptionContent(id));
    }
}
