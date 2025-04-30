package mas.domain.model.Influencer;

import mas.domain.model.contractor.Contractor;
import mas.infrastructure.repository.ObjectExtent;
import mas.util.Util;

public class Influencer extends ObjectExtent {

    private Contractor contractor;

    private SocialMediaHandle socialMediaHandle;

    private Influencer(Contractor contractor, SocialMediaHandle socialMediaHandle) {
        try {
            setContractor(contractor);
            setSocialMediaHandle(socialMediaHandle);
        } catch (Exception e) {
            System.out.println("Influencer not created: " + e.getMessage());
            removeFromExtent();
        }
    }

    public static Influencer of(Contractor contractor, SocialMediaHandle socialMediaHandle) {
        return new Influencer(contractor, socialMediaHandle);
    }

    public Contractor getContractor() {
        return contractor;
    }

    public void setContractor(Contractor contractor) {
        Util.require(contractor != null, "Contractor cannot be null");

        if (contractor.getInfluencerRole().isPresent()) {
            contractor.getInfluencerRole().get().removeFromExtent();
        }

        this.contractor = contractor;
    }

    public void removeContractor() {
        if (contractor != null) {
            contractor = null;
        }
    }

    public SocialMediaHandle getSocialMediaHandle() {
        return socialMediaHandle;
    }

    public void setSocialMediaHandle(SocialMediaHandle socialMediaHandle) {
        Util.require(socialMediaHandle != null, "SocialMediaHandle cannot be null");
        this.socialMediaHandle = socialMediaHandle;
    }

    @Override
    public void removeFromExtent() {
        removeFromContractor();
        super.removeFromExtent();

    }

    private void removeFromContractor() {
        if (contractor != null) {
            contractor.removeInfluencerRole();
        }
    }
}
