package by.epam.learn.mudrahelau.model;


import by.epam.learn.mudrahelau.service.ContainerLoaderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Viktar on 11.12.2019
 */
public class ContainerLoader {

    private ContainerLoaderService containerLoaderService = new ContainerLoaderService();

    private static final ContainerLoader CONTAINER_LOADER_INSTANCE = new ContainerLoader();

   public static ContainerLoader getInstance() {
        return CONTAINER_LOADER_INSTANCE;
    }

    public ContainerLoaderService getContainerLoaderService() {
        return containerLoaderService;
    }

}
