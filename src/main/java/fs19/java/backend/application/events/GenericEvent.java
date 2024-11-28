package fs19.java.backend.application.events;


import fs19.java.backend.domain.entity.enums.EntityType;
import org.springframework.context.ApplicationEvent;

public class GenericEvent<T> extends ApplicationEvent {

    private final T entity;
    private final EntityType entityType;
    private final String actionType;

    public GenericEvent(Object source, T entity, EntityType entityType, String actionType) {
        super(source);
        this.entity = entity;
        this.entityType = entityType;
        this.actionType = actionType;
    }

    public T getEntity() {
        return entity;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public String getActionType() {
        return actionType;
    }
}

