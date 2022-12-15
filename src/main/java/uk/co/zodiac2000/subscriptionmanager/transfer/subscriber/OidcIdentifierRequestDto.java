package uk.co.zodiac2000.subscriptionmanager.transfer.subscriber;

/**
 * Request DTO representing OIDC claims.
 */
public class OidcIdentifierRequestDto {

    private final String issuer;

    private final String subject;

    /**
     * @param issuer the iss claim
     * @param subject the sub claim
     */
    public OidcIdentifierRequestDto(String issuer, String subject) {
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
