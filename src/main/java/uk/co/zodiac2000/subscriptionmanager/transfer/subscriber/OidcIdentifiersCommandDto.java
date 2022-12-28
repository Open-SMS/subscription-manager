package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import javax.validation.Valid;

/**
 * Command DTO that has a reference to a set of OidcIdentifierCommandDto objects. The main purpose of this
 * class is to allow the members of the oidcIdentifierCommandDtos collection to be validated.
 */
public class OidcIdentifiersCommandDto {

    @Valid
    private List<OidcIdentifierCommandDto> oidcIdentifiers;

    public OidcIdentifiersCommandDto() { }

    /**
     *
     * @param oidcIdentifiers
     */
    public OidcIdentifiersCommandDto(List<OidcIdentifierCommandDto> oidcIdentifiers) {
        this.oidcIdentifiers = oidcIdentifiers;
    }

    /**
     * @return the oidcIdentifiers
     */
    public List<OidcIdentifierCommandDto> getOidcIdentifiers() {
        return oidcIdentifiers;
    }
}
