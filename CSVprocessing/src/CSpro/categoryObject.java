package CSpro;

public class categoryObject {
    String uniqueCat;
    String modifiedKey;
    String assocRating;

    public categoryObject(String uniqueCat, String modifiedKey, String assocRating){
        this.uniqueCat = uniqueCat;
        this.modifiedKey = modifiedKey;
        this.assocRating = assocRating;
    }

    public String getUniqueCat() {
        return uniqueCat;
    }
    public String getModifiedKey() {
        return modifiedKey;
    }
    public String getAssocRating() {
        return assocRating;
    }



}


