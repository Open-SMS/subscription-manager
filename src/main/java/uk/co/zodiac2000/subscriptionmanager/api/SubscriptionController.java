package uk.co.zodiac2000.subscriptionmanager.api;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.NewSubscriptionCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdentifierCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionDatesCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionResponseDto;

/**
 * REST API for accessing the subscription aggregate.
 */
@RestController
@RequestMapping(path = "/subscription", produces = "application/json")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    /**
     * Returns the Subscription identified by id.
     * @param id the subscription id
     * @return the subscription
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<SubscriptionResponseDto> getSubscription(@PathVariable("id") final long id) {
        return ResponseEntity.of(this.subscriptionService.getSubscription(id));
    }

    /**
     * Creates a new Subscription aggregate root and persists it. Returns a response document representing the new
     * subscription.
     * @param commandDto command DTO representing the new subscription
     * @return a SubscriptionResponseDto
     */
    @PostMapping
    public ResponseEntity<SubscriptionResponseDto> createSubscription(
            @RequestBody @Valid final NewSubscriptionCommandDto commandDto) {
        return new ResponseEntity<>(this.subscriptionService.createSubscription(commandDto).get(), HttpStatus.CREATED);
    }

    /**
     * Deletes the subscription identified by id.
     * @param id the subscription identifier
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteSubscription(@PathVariable("id") final long id) {
        this.subscriptionService.deleteSubscription(id);
    }

    /**
     * Updates the start and end dates associated with the subscription identified by id. Returns the modified
     * subscription or Not Found if the subscription didn't exist.
     * @param id the subscription identifier
     * @param commandDto command DTO representing the new subscription dates
     * @return the modified subscription
     */
    @PutMapping("/{id}/dates")
    public ResponseEntity<SubscriptionResponseDto> updateSubscriptionDates(@PathVariable("id") final long id,
            @RequestBody @Valid final SubscriptionDatesCommandDto commandDto) {
        return ResponseEntity.of(this.subscriptionService.updateSubscriptionDates(id, commandDto));
    }

    /**
     * Updates the subscription content identifier. Returns the modified subscription
     * or Not Found if the subscription didn't exist.
     * @param id the subscription identifier
     * @param commandDto command DTO representing the new content identifier
     * @return the modified subscription
     */
    @PutMapping("/{id}/content-identifier")
    public ResponseEntity<SubscriptionResponseDto> updateSubscriptionContentIdentifier(
            @PathVariable("id") final long id,
            @RequestBody @Valid final SubscriptionContentIdentifierCommandDto commandDto) {
        return ResponseEntity.of(this.subscriptionService.updateSubscriptionContentIdentifier(id, commandDto));
    }
}
