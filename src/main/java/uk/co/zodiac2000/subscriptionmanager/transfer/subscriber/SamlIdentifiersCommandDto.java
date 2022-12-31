package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import java.util.List;
import javax.validation.Valid;

/**
 * Command DTO that holds a reference to a list of SamlIdentifierCommandDto objects. The main purpose of this
 * class is to allow the members of the {@code samlIdentifiers} collection to be validated.
 */
public class SamlIdentifiersCommandDto {

    @Valid
    private List<SamlIdentifierCommandDto> samlIdentifiers;

    /**
     * Zero-arg constructor.
     */
    public SamlIdentifiersCommandDto() { }

    /**
     * @param samlIdentifierCommandDtos a list of SamlIdentifierCommandDto objects
     */
    public SamlIdentifiersCommandDto(final List<SamlIdentifierCommandDto> samlIdentifierCommandDtos) {
        this.samlIdentifiers = samlIdentifierCommandDtos;
    }

    /**
     * @return a list of SamlIdentifierCommandDto objects
     */
    public List<SamlIdentifierCommandDto> getSamlIdentifiers() {
        return this.samlIdentifiers;
    }
}
