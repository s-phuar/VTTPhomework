package CSpro;

public class cleanCat{
    String category;
    String appName;
    double rating;

public cleanCat(String category, String appName, double rating){
    this.category = category;
    this.appName = appName;
    this.rating = rating;
}

public String getCategory() {
    return category;
}

public void setCategory(String category) {
    this.category = category;
}

public String getAppName() {
    return appName;
}

public void setAppName(String appName) {
    this.appName = appName;
}

public double getRating() {
    return rating;
}

public void setRating(double rating) {
    this.rating = rating;
}
}
