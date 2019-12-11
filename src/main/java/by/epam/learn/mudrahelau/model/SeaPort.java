package by.epam.learn.mudrahelau.model;

import by.epam.learn.mudrahelau.states.PierState;
import by.epam.learn.mudrahelau.states.ShipState;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 09.12.2019
 */


public class SeaPort {
    private static SeaPort seaPort;
    private Lock lock = new ReentrantLock(true);
    private int counter = 0;
    private List<Pier> piers = new ArrayList<>();
    private Warehouse warehouse = Warehouse.getInstance();
    private ContainerLoader containerLoader;


    public static SeaPort getInstance() {
        if (seaPort == null) {
            seaPort = new SeaPort();
        }
        return seaPort;
    }


    public ShipReport shipService(Ship ship) throws InterruptedException {
//        lock.lock();

//        ship.setShipState(ShipState.ON_UNLOAD);

        // три потока(корабли) одновременно заходят в порт


        lock.lock();
        findFreePier(ship);
        lock.unlock();

        return new ShipReport(ship.getId(), counter);
    }


    public LoaderReport startLoaderWork(Ship ship, ContainerLoader containerLoader, Pier pier) {

        pier.getLock().lock();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        containerLoader.getLock().lock();
        //  если у корабля статус "На разгрузку", то перекидываем контейнеры с корабля на склад.
        if (ship.getShipState() == ShipState.ON_UNLOAD) {
            System.out.println("Started unload containers for ship # " + ship.getId());

            //копируем контейнеры с корабля, затем закидываем их в склад, а в кораблях просто удаляем их
            List<Container> containerList = new ArrayList<>(ship.getContainersWarehouse());
            for (Container container : containerList) {
                if(warehouse.checkIsWarehouseHasFreeSpace()){
                warehouse.loadContainer(container);
                }
                else {
                    System.out.println("WAREHOUSE IS FULL");
                }
                ship.getContainersWarehouse().removeAll(containerList);
            }
            System.out.println("Ship " + ship.getId() + " finished unloading.");
        }else if(ship.getShipState() == ShipState.ON_LOAD){
            //здесь будет погрузка на корабль
            System.out.println(warehouse.getContainersWarehouse().size());
            warehouse.getContainersWarehouse().remove(0);
            warehouse.getContainersWarehouse().remove(1);
            warehouse.getContainersWarehouse().remove(2);
            warehouse.getContainersWarehouse().remove(3);
            warehouse.getContainersWarehouse().remove(4);
            System.out.println("Ship " + ship.getId() + " finished loading.");
        }


        containerLoader.getLock().unlock();
        pier.getLock().unlock();
        pier.setPierState(PierState.FREE);
        return new LoaderReport(ship.getId());
    }

    public void findFreePier(Ship ship){
        Pier occupiedPier = null;
        boolean flag = true;
        System.out.println("> Ship #" + ship.getId() + " is looking for free pier...");
        do{
            for (Pier pier : piers) {
                if(pier.getPierState() == PierState.FREE){
                    pier.setPierState(PierState.OCCUPIED);
                    occupiedPier = pier;
                    flag = false;
                }
            }
        }while (flag);

        startLoaderWork(ship, ContainerLoader.instance, occupiedPier);
    }

    public void addPier(Pier pier) {
        piers.add(pier);
    }

}
