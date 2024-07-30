package nz.ac.auckland.se281;

import nz.ac.auckland.se281.Main.PolicyType;

public class InsuranceSystem {

  private Database database = new Database();

  public InsuranceSystem() {
    // Only this constructor can be used (if you need to initialise fields).
  }

  public void printDatabase() {
    database.printDatabase();
  }

  public void createNewProfile(String userName, String age) {
    database.createProfile(userName, age);
  }

  public void loadProfile(String userName) {
    database.loadProfiles(userName);
  }

  public void unloadProfile() {
    database.unloadProfiles();
  }

  public void deleteProfile(String userName) {
    database.deleteProfiles(userName);
  }

  public void createPolicy(PolicyType type, String[] options) {
    database.createPolicy(type, options);
  }
}
