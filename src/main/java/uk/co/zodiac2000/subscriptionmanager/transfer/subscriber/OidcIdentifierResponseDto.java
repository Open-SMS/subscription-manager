package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

/**
 * Response DTO representing an OIDC identifier.
 */
public class OidcIdentifierResponseDto {

    private final String issuer;

    private final String subject;

    /**
     * @param issuer the iss claim
     * @param subject the sub claim
     */
    public OidcIdentifierResponseDto(final String issuer, final String subject) {
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
