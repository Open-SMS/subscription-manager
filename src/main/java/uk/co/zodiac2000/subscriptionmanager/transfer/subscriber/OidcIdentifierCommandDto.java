package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

import javax.validation.constraints.NotEmpty;

/**
 * Command DTO representing an OIDC identifier.
 */
public class OidcIdentifierCommandDto {

    @NotEmpty
    private final String issuer;

    @NotEmpty
    private final String subject;

    /**
     * @param issuer the iss claim
     * @param subject the sub claim
     */
    public OidcIdentifierCommandDto(String issuer, String subject) {
        this.issuer = issuer;
        this.subject = subject;
    }

    /**
     * @return the iss claim
     */
    public String getIssuer() {
        return this.issuer;
    }

    /**
     * @return the sub claim
     */
    public String getSubject() {
        return this.subject;
    }
}
