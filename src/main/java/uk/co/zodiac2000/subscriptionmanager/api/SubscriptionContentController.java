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
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionContentService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.NewSubscriptionContentCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.SubscriptionContentResponseDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscriptioncontent.UpdateSubscriptionContentCommandDto;

/**
 * REST API for interacting with the SubscriptionContent aggregate.
 */
@RestController
@RequestMapping(path = "/subscription-content", produces = "application/json")
public class SubscriptionContentController {

    @Autowired
    private SubscriptionContentService subscriptionContentService;

    @Autowired
    private Validator validator;

    /**
     * Returns the SubscriptionContent identified by id, or a 404 response if not found.
     * @param id the subscription content id
     * @return the subscription content
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<SubscriptionContentResponseDto> getSubscriptionContent(@PathVariable("id") final long id) {
        return ResponseEntity.of(this.subscriptionContentService.getSubscriptionContent(id));
    }

    /**
     * Creates a new SubscriptionContent aggregate root and persists it. Returns a response document representing the
     * new subscription content.
     * @param commandDto command DTO representing the new subscription content
     * @return a SubscriptionContentResponseDto
     */
    @PostMapping
    public ResponseEntity<SubscriptionContentResponseDto> createSubscriptionContent(
            @RequestBody @Valid final NewSubscriptionContentCommandDto commandDto) {
        return new ResponseEntity<>(this.subscriptionContentService.createSubscriptionContent(commandDto).get(),
                HttpStatus.CREATED);
    }

    /**
     * Updates the subscription content identified by {@code id}. Returns a response document containing the updated
     * subscription content.
     * @param id the subscription content identifier
     * @param commandDto a UpdateSubscriptionContentCommandDto representing the new state of the subscription content
     * @param errors data binding and validation errors
     * @return the updated subscription content
     * @throws org.springframework.validation.BindException if a validation failure occurs
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubscriptionContentResponseDto> updateSubscriptionContent(
            @PathVariable("id") final long id,
            @RequestBody final UpdateSubscriptionContentCommandDto commandDto,
            final BindingResult errors) throws BindException {
        commandDto.setId(id);
        this.validator.validate(commandDto, errors);
        if (errors.hasErrors()) {
            throw new BindException(errors);
        }

        return ResponseEntity.of(this.subscriptionContentService.updateSubscriptionContent(commandDto));
    }
}
