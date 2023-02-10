package uk.co.zodiac2000.subscriptionmanager.api;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.ModelAndView;
import uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription;
import uk.co.zodiac2000.subscriptionmanager.service.SubscriptionService;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.NewSubscriptionCommandDto;
import uk.co.zodiac2000.subscriptionmanager.transfer.subscription.SubscriptionContentIdCommandDto;
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
     * Handles any {@code IllegalStateException} thrown by methods in
     * {@link uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription Subscription}. If the exception
     * is thrown by any other class then it is re-thrown, otherwise the request exception attribute is set with the
     * exception, a Bad Request (400) is set in the response, and an empty {@code ModelAndView} returned.
     * This handler is for situations where a method called on the domain model would leave the model in an invalid
     * state.
     * @param e the exception
     * @param request the HTTP request
     * @param response the HTTP response
     * @return an empty ModelAndView
     * @throws java.io.IOException if there is a problem sending the error in the response
     */
    @ExceptionHandler({IllegalStateException.class})
    public ModelAndView handleIllegalState(final IllegalStateException e,
            final HttpServletRequest request, final HttpServletResponse response) throws IOException {
        if (Subscription.class.getCanonicalName().equals(e.getStackTrace()[0].getClassName())) {
            request.setAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE, e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return new ModelAndView();
        } else {
            throw e;
        }
    }

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
            @RequestBody @Valid final SubscriptionContentIdCommandDto commandDto) {
        return ResponseEntity.of(this.subscriptionService.updateSubscriptionContentIdentifier(id, commandDto));
    }

    /**
     * Suspends the subscription identified by {@code id}. Returns the modified subscription
     * or Not Found if the subscription doesn't exist. See
     * {@link uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription#suspend Subscription#suspend}
     * for details.
     * @param id the subscription identifier
     * @return the modified subscription
     */
    @PutMapping("/{id}/suspend")
    public ResponseEntity<SubscriptionResponseDto> suspendSubscription(
            @PathVariable("id") final long id) {
        return ResponseEntity.of(this.subscriptionService.suspendSubscription(id));
    }

    /**
     * Terminates the subscription identified by {@code id}. Returns the modified subscription
     * or Not Found if the subscription doesn't exist. See
     * {@link uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription#suspend Subscription#terminate}
     * for details.
     * @param id the subscription identifier
     * @return the modified subscription
     */
    @PutMapping("/{id}/terminate")
    public ResponseEntity<SubscriptionResponseDto> terminateSubscription(
            @PathVariable("id") final long id) {
        return ResponseEntity.of(this.subscriptionService.terminateSubscription(id));
    }

    /**
     * Unsuspends the subscription identified by {@code id}. Returns the modified subscription
     * or Not Found if the subscription doesn't exist. See
     * {@link uk.co.zodiac2000.subscriptionmanager.domain.subscription.Subscription#suspend Subscription#unsuspend}
     * for details.
     * @param id the subscription identifier
     * @return the modified subscription
     */
    @PutMapping("/{id}/unsuspend")
    public ResponseEntity<SubscriptionResponseDto> unsuspendSubscription(
            @PathVariable("id") final long id) {
        return ResponseEntity.of(this.subscriptionService.unsuspendSubscription(id));
    }
}
