package uk.co.zodiac2000.subscriptionmanager.domain.oidcclaims;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotEmpty;

/**
 * This class represents an OIDC Claim Name that is used by the system.
 */
@Entity
public class ClaimName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "claim_name_id_gen", sequenceName = "claim_name_id_seq", allocationSize = 1)
    @GeneratedValue(generator = "claim_name_id_gen")
    private Long id;

    @NotEmpty
    private String claimName;

    /**
     * Zero-args constructor for JPA.
     */
    public ClaimName() { }

    /**
     * Constructs a new ClaimName using the supplied arguments.
     * @param claimName the claim name
     */
    public ClaimName(final String claimName) {
        this.claimName = claimName;
    }

    /**
     * @return the claim name identifier
     */
    public Long getId() {
        return this.id;
    }

    /**
     * @return the claim name
     */
    public String getClaimName() {
        return this.claimName;
    }
}
