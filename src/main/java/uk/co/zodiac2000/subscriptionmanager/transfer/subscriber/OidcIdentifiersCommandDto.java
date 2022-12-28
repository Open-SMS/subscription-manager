package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import javax.validation.Valid;

/**
 * Command DTO that holds a reference to a list of {@code OidcIdentifierCommandDto} objects. The main purpose of this
 * class is to allow the members of the {@code oidcIdentifiers} collection to be validated.
 */
public class OidcIdentifiersCommandDto {

    @Valid
    private List<OidcIdentifierCommandDto> oidcIdentifiers;

    /**
     * Zero-arg constructor.
     */
    public OidcIdentifiersCommandDto() { }

    /**
     * @param oidcIdentifiers a list of OidcIdentifierCommandDto objects
     */
    public OidcIdentifiersCommandDto(List<OidcIdentifierCommandDto> oidcIdentifiers) {
        this.oidcIdentifiers = oidcIdentifiers;
    }

    /**
     * @return a list of OidcIdentifierCommandDto objects
     */
    public List<OidcIdentifierCommandDto> getOidcIdentifiers() {
        return oidcIdentifiers;
    }
}
