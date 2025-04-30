package mas.domain.model.contract;


import mas.domain.model.fighter.Fighter;
import mas.util.Util;
import mas.infrastructure.repository.ObjectExtent;

import java.util.List;
import java.util.Objects;

public class Contract extends ObjectExtent {
    private SigningDate signedAt;
    private ExpirationDate expiresAt;
    private Fighter fighter; //kompozycja (całość)


    private Contract(Fighter fighter, SigningDate signedAt, ExpirationDate expiresAt) {
        try {
            setFighter(fighter);
            setSignedAt(signedAt);
            setExpiresAt(expiresAt);
        } catch (Exception e) {
            removeFromExtent();
        }
    }

    public static Contract createContact(Fighter fighter, SigningDate signedAt, ExpirationDate expiresAt) {
        return new Contract(fighter, signedAt, expiresAt);
    }

    public SigningDate getSignedAt() {
        return signedAt;
    }

    public void setSignedAt(SigningDate signedAt) {
        Util.require(signedAt != null, "Signed date cannot be null");
        this.signedAt = signedAt;
    }

    public ExpirationDate getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(ExpirationDate expiresAt) {
        Util.require(expiresAt != null, "Expiration date cannot be null");
        this.expiresAt = expiresAt;
    }

    public Fighter getFighter() {
        return fighter;
    }

    private void setFighter(Fighter fighter) {
        Util.require(fighter != null, "Fighter cannot be null");

        List<Contract> contracts = fighter.getContracts();

        if (contracts.contains(this)) {
            return;
        }

        fighter.addContract(this);
        this.fighter = fighter;
    }


    @Override
    public void removeFromExtent() {
        removeFromFighter();
        super.removeFromExtent();
    }

    private void removeFromFighter() {
        if (fighter != null) {
            fighter.removeContract(this);
        }
        fighter = null;
    }

    @Override
    public String toString() {
        return "Contract{" +
                "signedAt=" + signedAt +
                ", expiresAt=" + expiresAt +
                ", fighter=" + (fighter != null ? fighter.getName() + " " + fighter.getSurname() : "null") +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contract contract)) return false;
        return Objects.equals(signedAt, contract.signedAt) &&
                Objects.equals(expiresAt, contract.expiresAt) &&
                Objects.equals(fighter, contract.fighter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signedAt, expiresAt, fighter);
    }
}
