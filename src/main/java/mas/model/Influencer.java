package mas.model;

import mas.model.data.ObjectExtent;


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
        if(contractor == null){
            throw new IllegalArgumentException("Contractor cannot be null");
        }


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

        if(socialMediaHandle == null || socialMediaHandle.isEmpty()){
            throw new IllegalArgumentException("Social Media Handle cannot be null or empty");
        }


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
