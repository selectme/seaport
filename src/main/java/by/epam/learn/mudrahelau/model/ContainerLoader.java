package by.epam.learn.mudrahelau.model;


import by.epam.learn.mudrahelau.service.ContainerLoaderService;

/**
 * Model object thar represents a container loader.
 *
 * @author Viktar on 11.12.2019
 */
public class ContainerLoader {
    /**
     * {@link ContainerLoaderService}
     */
    private ContainerLoaderService containerLoaderService = new ContainerLoaderService();

    /**
     * Static instance.
     */
    private static final ContainerLoader CONTAINER_LOADER_INSTANCE = new ContainerLoader();

    public static ContainerLoader getInstance() {
        return CONTAINER_LOADER_INSTANCE;
    }

    public ContainerLoaderService getContainerLoaderService() {
        return containerLoaderService;
    }

}
