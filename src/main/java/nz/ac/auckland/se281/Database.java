package nz.ac.auckland.se281;

import java.util.ArrayList;
import nz.ac.auckland.se281.Main.PolicyType;

public class Database {

  private ArrayList<Profile> listProfiles = new ArrayList<Profile>();
  private Profile loadedProfile = null;

  // Checks if the inputted age meets the age requirement
  private boolean checkAge(String age, String userName) {

    // Checks if integer value inputs meet the age requirement
    try {
      if (Integer.parseInt(age) < 0) {
        MessageCli.INVALID_AGE.printMessage(age, userName);
        return false;
      } else {
        return true;
      }

      // Does not accept non-integer input values for the age
    } catch (NumberFormatException exception) {
      MessageCli.INVALID_AGE.printMessage(age, userName);
      return false;
    }
  }

  // Checks if the inputted name meets naming requirements
  private boolean checkName(String userName) {
    if (userName.length() < 3) {
      MessageCli.INVALID_USERNAME_TOO_SHORT.printMessage(userNameCase(userName));
      return false;
    } else {
      return true;
    }
  }

  // Converts the username to the correct formatting of upper and lower cases
  private String userNameCase(String userName) {

    // Lowercase the string after the first letter to lowercase
    String lowerUserName = userName.substring(1).toLowerCase();

    // Uppercase the first letter of the string
    String upperUserName = userName.substring(0, 1).toUpperCase();

    // Combine both of the new strings to form the new correctly formatted username
    String updatedUserName = upperUserName + lowerUserName;
    return updatedUserName;
  }

  // Checks if the inputted username is unique and not already in the database
  private boolean uniqueUserName(String userName) {
    for (Profile currentProfile : listProfiles) {
      if (userNameCase(currentProfile.getUserName()).equals(userNameCase(userName))) {
        MessageCli.INVALID_USERNAME_NOT_UNIQUE.printMessage(userName);
        return false;
      }
    }
    return true;
  }

  // Creates the new Profile and adds it to the listProfiles array if all conditions are met
  public void createProfile(String userName, String age) {
    userName = userNameCase(userName);
    // if a profile is loaded in the listProfiles array then print message
    // CANNOT_CREATE_WHILE_LOADED
    for (Profile currentProfile : listProfiles) {
      if (currentProfile.isLoaded() == true) {
        MessageCli.CANNOT_CREATE_WHILE_LOADED.printMessage(currentProfile.getUserName());
        return;
      }
    }
    if ((checkAge(age, userName) == true)
        && (checkName(userName) == true)
        && (uniqueUserName(userName) == true)) {
      Profile currentProfile = new Profile(userName, age);
      listProfiles.add(currentProfile);
      MessageCli.PROFILE_CREATED.printMessage(userName, age);
    }
  }

  // Prints database information, display dependent on the number of inputs
  public void printDatabase() {

    // If the listProfiles array is has 0 profiles, display the message
    if (listProfiles.isEmpty()) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("0", "s", ".");

      // Else if the listProfiles array has 1 profile, display message and profile
    } else if (listProfiles.size() == 1) {
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage("1", "", ":");
      databaseProfiles();
      // Else if the listProfiles array 1+ profiles, display message and profile
    } else if (listProfiles.size() > 1) {
      int size = listProfiles.size();
      MessageCli.PRINT_DB_POLICY_COUNT.printMessage(Integer.toString(size), "s", ":");
      databaseProfiles();
    }
  }

  // Create display printDatabase if there is 1 or more profiles in the listProfiles array
  private void databaseProfiles() {
    // Set the first profile as rank 1
    int rankProfile = 1;

    // Creates message for (rank(increases through listProfiles), username, age)
    for (Profile currentProfile : listProfiles) {
      String rankOfProfile = Integer.toString(rankProfile);

      // If the profile is loaded, add stars to the message
      String stars = "";
      if (currentProfile.isLoaded() == true) {
        stars = "*** ";
      }
      // If the profile has 0 policies, display message
      if (currentProfile.getListPolicy().size() == 0) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            stars,
            rankOfProfile,
            currentProfile.getUserName(),
            currentProfile.getAge(),
            Integer.toString(currentProfile.getListPolicy().size()),
            "ies",
            "0");
        // If the profile has 1 policy, display message and policy
      } else if (currentProfile.getListPolicy().size() == 1) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            stars,
            rankOfProfile,
            currentProfile.getUserName(),
            currentProfile.getAge(),
            Integer.toString(currentProfile.getListPolicy().size()),
            "y",
            Integer.toString(totalCostOfPolicies(currentProfile)));
        printPolicy(currentProfile);

        // If the profile has 1+ policies, display message and policies
      } else if (currentProfile.getListPolicy().size() > 1) {
        MessageCli.PRINT_DB_PROFILE_HEADER_LONG.printMessage(
            stars,
            rankOfProfile,
            currentProfile.getUserName(),
            currentProfile.getAge(),
            Integer.toString(currentProfile.getListPolicy().size()),
            "ies",
            Integer.toString(totalCostOfPolicies(currentProfile)));
        printPolicy(currentProfile);
      }
      rankProfile++;
    }
  }

  // Prints the policies of the profile and the premium and discount rates
  private void printPolicy(Profile currentProfile) {
    for (Policy currentPolicy : currentProfile.getListPolicy()) {

      // If the policy is a Car, display message and policy
      if (currentPolicy instanceof Car) {
        Car car = (Car) currentPolicy;
        MessageCli.PRINT_DB_CAR_POLICY.printMessage(
            car.getMakeModel(),
            Integer.toString(car.getSumInsured()),
            Integer.toString(car.getBasePremium()),
            Integer.toString(
                discountPolicy(currentProfile.getListPolicy().size(), car.getBasePremium())));

        // If the policy is a Home, display message and policy
      } else if (currentPolicy instanceof Home) {
        Home home = (Home) currentPolicy;
        MessageCli.PRINT_DB_HOME_POLICY.printMessage(
            home.getAddress(),
            Integer.toString(home.getSumInsured()),
            Integer.toString(home.getBasePremium()),
            Integer.toString(
                discountPolicy(currentProfile.getListPolicy().size(), home.getBasePremium())));

        // If the policy is a Life, display message and policy
      } else if (currentPolicy instanceof Life) {
        Life life = (Life) currentPolicy;
        MessageCli.PRINT_DB_LIFE_POLICY.printMessage(
            Integer.toString(life.getSumInsured()),
            Integer.toString(life.getBasePremium()),
            Integer.toString(
                discountPolicy(currentProfile.getListPolicy().size(), life.getBasePremium())));
      }
    }
  }

  // Checks if the inputted username is unique and not already in the database (used for
  // loadProfiles with no error message)
  private boolean uniqueUserNames(String userName) {
    for (Profile currentProfile : listProfiles) {
      if (userNameCase(currentProfile.getUserName()).equals(userNameCase(userName))) {
        return false;
      }
    }
    return true;
  }

  // Loads the profile if the inputted username is already in the database
  public void loadProfiles(String userName) {
    // Apply userNameCase to the inputted username
    userName = userNameCase(userName);
    // Check if the inputted username is unique, if it is then print the no_profile_found_to_load
    // message.
    // If it is not unique, then set all profiles in the array list to not be loaded and then set
    // the profile that matches the inputted username to be loaded
    if (uniqueUserNames(userName) == true) {
      MessageCli.NO_PROFILE_FOUND_TO_LOAD.printMessage(userName);

    } else {
      MessageCli.PROFILE_LOADED.printMessage(userName);
      // Set all profiles in listProfiles array to not be loaded
      for (Profile currentProfile : listProfiles) {
        currentProfile.setLoaded(false);
      }

      // Set the profile that matches the inputted username to be loaded
      for (Profile currentProfile : listProfiles) {
        if (currentProfile.getUserName().equals(userName)) {
          currentProfile.setLoaded(true);
          loadedProfile = currentProfile;
        }
      }
    }
  }

  // Unloads the profile if there is a profile loaded
  public void unloadProfiles() {
    for (Profile currentProfile : listProfiles) {
      // If the profile is loaded, set it to not be loaded and print the profile_unloaded message
      if (currentProfile.isLoaded() == true) {
        currentProfile.setLoaded(false);
        MessageCli.PROFILE_UNLOADED.printMessage(currentProfile.getUserName());
        loadedProfile = null;
        return;
      }
    }
    MessageCli.NO_PROFILE_LOADED.printMessage();
  }

  // Deletes the profile if the inputted username is already in the database and there are no loaded
  // profiles
  public void deleteProfiles(String userName) {
    userName = userNameCase(userName);
    // Check that the profile is loaded and if it is, cannot delete the profile with loaded profile
    // Return message
    for (Profile currentProfile : listProfiles) {
      if (currentProfile.getUserName().equals(userName)) {
        if (currentProfile.isLoaded() == true) {
          MessageCli.CANNOT_DELETE_PROFILE_WHILE_LOADED.printMessage(userName);
          return;
          // If no profile is loaded, remove the profile from the array list and print the message
          // deleted
        } else {
          listProfiles.remove(currentProfile);
          MessageCli.PROFILE_DELETED.printMessage(userName);
          return;
        }
      }
    }
    MessageCli.NO_PROFILE_FOUND_TO_DELETE.printMessage(userName);
  }

  // LIFE POLICY REQUIREMENTS
  // Requirement for making life policy - age must not be over 100
  public boolean lifePolicyAge(String userName, int age) {
    if (age > 100) {
      MessageCli.OVER_AGE_LIMIT_LIFE_POLICY.printMessage(userNameCase(userName));
      return false;
    }
    return true;
  }

  // Requirement for making life policy - must not already have a life policy
  public boolean checkIfOneLifePolicy(String userName) {
    for (Profile currentProfile : listProfiles) {
      if (currentProfile.getUserName().equals(userNameCase(userName))) {
        if (currentProfile.getLifePolicy() == true) {
          MessageCli.ALREADY_HAS_LIFE_POLICY.printMessage(userNameCase(userName));
          return false;
        }
      }
    }
    return true;
  }

  // Creates a new policy based on the type of policy and saves it to the profile
  public void createPolicy(PolicyType type, String[] options) {
    if (loadedProfile == null) {
      MessageCli.NO_PROFILE_FOUND_TO_CREATE_POLICY.printMessage();
      return;

      // If the policy is a life, create a new life policy and save it to the profile
    } else if (type == PolicyType.LIFE) {
      if (lifePolicyAge(loadedProfile.getUserName(), Integer.parseInt(loadedProfile.getAge()))
              == true
          && checkIfOneLifePolicy(loadedProfile.getUserName()) == true) {
        loadedProfile.setLifePolicy(true);
        loadedProfile.addLifePolicy(Integer.parseInt(options[0]));
        MessageCli.NEW_POLICY_CREATED.printMessage("life", loadedProfile.getUserName());
      }

      // If the policy is a home, create a new home policy and save it to the profile
    } else if (type == PolicyType.HOME) {
      boolean rental = options[2].toUpperCase().startsWith("Y", 0);
      loadedProfile.addHomePolicy(Integer.parseInt(options[0]), options[1], rental);
      MessageCli.NEW_POLICY_CREATED.printMessage("home", loadedProfile.getUserName());

      // If the policy is a car, create a new car policy and save it to the profile
    } else if (type == PolicyType.CAR) {
      boolean breakdownCover = options[3].toUpperCase().startsWith("Y", 0);
      loadedProfile.addCarPolicy(
          Integer.parseInt(options[0]), options[1], options[2], breakdownCover);
      MessageCli.NEW_POLICY_CREATED.printMessage("car", loadedProfile.getUserName());
    }
  }

  // Calculates the discount from the premium
  private int discountPolicy(int numPolicies, int basePremium) {
    int discount = basePremium;
    if (numPolicies == 2) {
      discount *= 0.9;
    } else if (numPolicies >= 3) {
      discount *= 0.8;
    }
    return discount;
  }

  // Calculates the total cost of all the policies of a profile in the listProfile array after
  // discount has been applied to each policy per profile
  private int totalCostOfPolicies(Profile currentProfile) {
    int totalCost = 0;
    for (Policy currentPolicy : currentProfile.getListPolicy()) {
      totalCost +=
          discountPolicy(currentProfile.getListPolicy().size(), currentPolicy.getBasePremium());
    }
    return totalCost;
  }
}
