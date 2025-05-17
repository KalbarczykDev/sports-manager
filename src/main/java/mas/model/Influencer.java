package mas.model;

import mas.model.data.ObjectExtent;
import mas.util.Util;

public class Influencer extends ObjectExtent {

    private Contractor contractor;

    private String socialMediaHandle;

    private Influencer(Contractor contractor, String socialMediaHandle) {
        try {
            setContractor(contractor);
            setSocialMediaHandle(socialMediaHandle);
        } catch (Exception e) {
            System.out.println("Influencer not created: " + e.getMessage());
            removeFromExtent();
        }
    }

    public static Influencer of(Contractor contractor, String socialMediaHandle) {
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

    public String getSocialMediaHandle() {
        return socialMediaHandle;
    }

    public void setSocialMediaHandle(String socialMediaHandle) {
        Util.require(socialMediaHandle != null, "SocialMediaHandle cannot be null");
        Util.require(!socialMediaHandle.isEmpty(), "SocialMediaHandle cannot be empty");
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
