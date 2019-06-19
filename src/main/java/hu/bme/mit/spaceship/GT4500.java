package hu.bme.mit.spaceship;

/**
* A simple spaceship with two proton torpedo stores and four lasers
*/
public class GT4500 implements SpaceShip {

  private TorpedoStore primaryTorpedoStore;
  private TorpedoStore secondaryTorpedoStore;

  private boolean wasPrimaryFiredLast = false;

  public GT4500() {
    this.primaryTorpedoStore = new TorpedoStore(10);
    this.secondaryTorpedoStore = new TorpedoStore(10);
  }


  public GT4500(TorpedoStore primaryTorpedoStore, TorpedoStore secondaryTorpedoStore) {
    this.primaryTorpedoStore = primaryTorpedoStore;
    this.secondaryTorpedoStore = secondaryTorpedoStore;
  }

  public boolean fireLaser(FiringMode firingMode) {
    return false;
  }

  /**
  * Tries to fire the torpedo stores of the ship.
  *
  * @param firingMode how many torpedo bays to fire
  * 	SINGLE: fires only one of the bays.
  * 			- For the first time the primary store is fired.
  * 			- To give some cooling time to the torpedo stores, torpedo stores are fired alternating.
  * 			- But if the store next in line is empty, the ship tries to fire the other store.
  * 			- If the fired store reports a failure, the ship does not try to fire the other one.
  * 	ALL:	tries to fire both of the torpedo stores.
  *
  * @return whether at least one torpedo was fired successfully
  */
  @Override
  public boolean fireTorpedo(FiringMode firingMode) {

    boolean firingSuccess = false;

    switch (firingMode) {
      case SINGLE:
        if (wasPrimaryFiredLast) {
          if (!secondaryTorpedoStore.isEmpty()) {
            firingSuccess = secondaryTorpedoStore.fire(1);
            wasPrimaryFiredLast = false;
          } else if (!primaryTorpedoStore.isEmpty()) {
            firingSuccess = primaryTorpedoStore.fire(1);
            wasPrimaryFiredLast = true;
          }
        } else if (!primaryTorpedoStore.isEmpty()) {
          firingSuccess = primaryTorpedoStore.fire(1);
          wasPrimaryFiredLast = true;
        } else if (!secondaryTorpedoStore.isEmpty()) {
            firingSuccess = secondaryTorpedoStore.fire(1);
            wasPrimaryFiredLast = false;
        }
        break;
      case ALL:
        firingSuccess = primaryTorpedoStore.fire(1) || secondaryTorpedoStore.fire(1);
        break;
    }

    return firingSuccess;
  }

}
