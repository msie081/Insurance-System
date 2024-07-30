package nz.ac.auckland.se281;

public class Car extends Policy {
  private String makeModel;
  private String licensePlate;
  private boolean breakdownCover;

  public Car(
      int sumInsured, String makeModel, String licensePlate, boolean breakdownCover, int age) {
    super(sumInsured);
    this.makeModel = makeModel;
    this.licensePlate = licensePlate;
    this.breakdownCover = breakdownCover;

    // Calculate base premium
    if (age < 25) {
      this.basePremium = 15 * sumInsured / 100;
    } else {
      this.basePremium = 10 * sumInsured / 100;
    }

    if (breakdownCover) {
      this.basePremium += 80;
    }
  }

  // Getter for makeModel
  public String getMakeModel() {
    return makeModel;
  }
}
