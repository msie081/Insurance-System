package nz.ac.auckland.se281;

import java.util.ArrayList;

public class Profile {
  private String userName;
  private String age;
  private boolean loaded;
  private boolean lifePolicy;

  private ArrayList<Policy> listPolicy = new ArrayList<Policy>();

  // Getter for list of policies
  public ArrayList<Policy> getListPolicy() {
    return listPolicy;
  }

  // Adds a home policy to the list of policies taking in the sum insured, address, rental status)
  public void addHomePolicy(int sumInsured, String address, boolean rental) {
    listPolicy.add(new Home(sumInsured, address, rental));
  }

  // Adds a car policy to the list of policies taking in the sum insured, make and model, license
  // plate, breakdown cover)
  public void addCarPolicy(
      int sumInsured, String makeModel, String licensePlate, boolean breakdownCover) {
    listPolicy.add(
        new Car(sumInsured, makeModel, licensePlate, breakdownCover, Integer.parseInt(age)));
  }

  // Adds a life policy to the list of policies taking in the sum insured, age)
  public void addLifePolicy(int sumInsured) {
    listPolicy.add(new Life(sumInsured, Integer.parseInt(age)));
  }

  public Profile(String userName, String age) {
    this.userName = userName;
    this.age = age;
  }

  // Getter for username
  public String getUserName() {
    return userName;
  }

  // Getter for age
  public String getAge() {
    return age;
  }

  // Setter for loaded
  public void setLoaded(boolean loaded) {
    this.loaded = loaded;
  }

  // Getter for loaded
  public boolean isLoaded() {
    return loaded;
  }

  // Setter for life policy
  public void setLifePolicy(boolean lifePolicy) {
    this.lifePolicy = lifePolicy;
  }

  // Getter for life policy
  public boolean getLifePolicy() {
    return lifePolicy;
  }
}
