package by.epam.learn.mudrahelau.model;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 11.12.2019
 */
public class ContainerLoader {

    private Ship ship;
    private Lock lock = new ReentrantLock(true);
    Warehouse warehouse;

    public static final ContainerLoader CONTAINER_LOADER_INSTANCE = new ContainerLoader();

    public void unloadContainersFromShip(Ship ship) {
        try {
            lock.lock();
            System.out.println("Started unload containers for ship # " + ship.getId());

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            warehouse = Warehouse.getInstance();
            //копируем контейнеры с корабля, затем закидываем их в склад, а в кораблях просто удаляем их
            List<Container> containerList = new ArrayList<>(ship.getContainersWarehouse());

            for (Container container : containerList) {
                if (warehouse.hasFreeSpace()) {
                    warehouse.loadContainer(container);
                    ship.getContainersWarehouse().remove(container);
                } else {
                    System.out.println("WAREHOUSE IS FULL");
                }
            }
            System.out.println("Ship " + ship.getId() + " finished unloading.");
        } finally {
            lock.unlock();
        }
    }

    public void loadContainersToShip(Ship ship) {
        try {
            lock.lock();

            System.out.println("Started loading containers for ship # " + ship.getId());
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            int numberOfContainersToLoadOnShip = ship.getCapacity();
            List<Container> containersToLoadOnShip = new ArrayList<>();

            for (int i = 0; i < numberOfContainersToLoadOnShip; i++) {
                containersToLoadOnShip.add(warehouse.getContainersWarehouse().get(i));
                System.out.println("container removed from warehouse");
            }


            for (Container container : containersToLoadOnShip) {
                warehouse.unloadContainer(container);
                ship.loadContainer(container);
            }

            System.out.println("Ship " + ship.getId() + " finished loading.");
        } finally {
            lock.unlock();
        }

    }

    public static ContainerLoader getInstance() {
        return CONTAINER_LOADER_INSTANCE;
    }

    public Lock getLock() {
        return lock;
    }

}
