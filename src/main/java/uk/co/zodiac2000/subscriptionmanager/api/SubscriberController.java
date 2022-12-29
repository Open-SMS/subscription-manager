package uk.co.zodiac2000.subscriptionmanager.api;

import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriberService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.NewSubscriberCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.OidcIdentifiersCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SamlIdentifiersCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberNameCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriber.SubscriberResponseDto;

/**
 * REST API for accessing the subscriber aggregate.
 */
@RestController
@RequestMapping(path = "/subscriber", produces = "application/json")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @Autowired
    private Validator validator;

    /**
     * Returns the Subscriber identified by id.
     * @param id the subscriber id
     * @return the subscriber
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<SubscriberResponseDto> getSubscriber(@PathVariable("id") long id) {
        return ResponseEntity.of(this.subscriberService.getSubscriberById(id));
    }
    
    /**
     * Creates a new Subscriber aggregate root and persists it. Returns a response document representing the new subscriber.
     * @param commandDto command DTO representing the new subscriber
     * @return a SubscriberResponseDto
     */
    @PostMapping
    public ResponseEntity<SubscriberResponseDto> createSubscriber(@RequestBody @Valid NewSubscriberCommandDto commandDto) {
        return new ResponseEntity(this.subscriberService.createSubscriber(commandDto).get(), HttpStatus.CREATED);
    }

    /**
     * Deletes the subscriber identified by id.
     * @param id the subscriber identifier
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code=HttpStatus.NO_CONTENT)
    public void deleteSubscriber(@PathVariable("id") long id) {
        this.subscriberService.deleteSubscriber(id);
    }

    /**
     * Updates the subscriber name of the subscriber identified by id. If the subscriber doesn't exist then Not Found is returned
     * and no change is made to the state of the system.
     * @param id the subscriber id
     * @param commandDto the new subscriber name
     * @param errors data binding and validation errors
     * @return the modified subscriber
     * @throws org.springframework.validation.BindException if a validation failure occurs
     */
    @PutMapping("/{id}/subscriber-name")
    public ResponseEntity<SubscriberResponseDto> updateSubscriberName(@PathVariable("id") long id,
            @RequestBody SubscriberNameCommandDto commandDto, BindingResult errors) throws BindException {

        commandDto.setId(id);
        this.validator.validate(commandDto, errors);
        if (errors.hasErrors()) {
            throw new BindException(errors);
        }

        return ResponseEntity.of(this.subscriberService.updateSubscriberName(commandDto));
    }

    /**
     * Replaces the set of SAML identifiers associated with the subscriber identified by {@code id} with the SAML identifiers
     * specified by {@code commandDto}.
     * @param id the subscriber identifier
     * @param commandDto an SamlIdentifiersCommandDto containing a list of SamlIdentifierCommandDto objects
     * @return the modified subscriber
     */
    @PutMapping("/{id}/saml-identifiers")
    public ResponseEntity<SubscriberResponseDto> setSamlIdentifiers(@PathVariable("id") long id,
            @RequestBody @Valid SamlIdentifiersCommandDto commandDto) {
        return ResponseEntity.of(this.subscriberService.setSamlIdentifiers(id, Set.copyOf(commandDto.getSamlIdentifiers())));
    }

    /**
     * Replaces the set of OIDC identifiers associated with the subscriber identified by {@code id} with the OIDC identifiers
     * specified by {@code commandDto}.
     * @param id the subscriber identifier
     * @param commandDto an OidcIdentifiersCommandDto containing a list of OidcIdentifierCommandDto objects
     * @return the modified subscriber
     */
    @PutMapping("/{id}/oidc-identifiers")
    public ResponseEntity<SubscriberResponseDto> setOidcIdentifiers(@PathVariable("id") long id,
            @RequestBody @Valid OidcIdentifiersCommandDto commandDto) {
        return ResponseEntity.of(this.subscriberService.setOidcIdentifiers(id, Set.copyOf(commandDto.getOidcIdentifiers())));
    }
}
