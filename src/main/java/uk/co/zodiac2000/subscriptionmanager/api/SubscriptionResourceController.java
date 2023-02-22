package uk.co.zodiac2000.subscriptionmanager.api;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionResourceService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.NewSubscriptionResourceCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptionresource.SubscriptionResourceResponseDto;

/**
 * REST API for interacting with the subscription resource aggregate.
 */
@RestController
@RequestMapping(path = "/subscription-resource", produces = "application/json")
public class SubscriptionResourceController {

    @Autowired
    private SubscriptionResourceService subscriptionResourceService;

    @Autowired
    private Validator validator;

    /**
     * Returns the SubscriptionResource identified by id, or a 404 response if not found.
     * @param id the subscription resource id
     * @return the subscription resource
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<SubscriptionResourceResponseDto> getSubscriptionResource(@PathVariable("id") final long id) {
        return ResponseEntity.of(this.subscriptionResourceService.getSubscriptionResource(id));
    }

    /**
     * Creates a new SubscriptionResouce aggregate root and persists it. Returns a response document representing the
     * new subscription resource.
     * @param commandDto command DTO representing the new subscription resource
     * @return a SubscriptionResourceResponseDto
     */
    @PostMapping
    public ResponseEntity<SubscriptionResourceResponseDto> createSubscriptionResource(
            @RequestBody @Valid final NewSubscriptionResourceCommandDto commandDto) {
        return new ResponseEntity<>(this.subscriptionResourceService.createSubscriptionResource(commandDto).get(),
                HttpStatus.CREATED);
    }

    /**
     * Updates the subscription resource identified by {@code id}.Returns a response document containing the updated
     * subscription resource.
     * @param id the subscription resource identifier
     * @param commandDto a SubscriptionResourceCommandDto representing the new state of the subscription resource
     * @param errors data binding and validation errors
     * @return the updated subscription resource
     * @throws org.springframework.validation.BindException if a validation failure occurs
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionResourceResponseDto> updateSubscriptionResource(
            @PathVariable("id") final long id,
            @RequestBody final SubscriptionResourceCommandDto commandDto,
            final BindingResult errors) throws BindException {
        commandDto.setId(id);
        this.validator.validate(commandDto, errors);
        if (errors.hasErrors()) {
            throw new BindException(errors);
        }

        return ResponseEntity.of(this.subscriptionResourceService.updateSubscriptionResource(commandDto));
    }
}
