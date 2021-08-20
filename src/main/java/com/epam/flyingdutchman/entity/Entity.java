package com.epam.flyingdutchman.entity;

/**
 * The abstract class represents basic entity.
 *
 * @author Aliaksei Halkin
 * @version 1.0
 */
public abstract class Entity {
    private int id;

    /**
     * Instantiates a new Entity.
     */
    public Entity() {
    }

    /**
     * Instantiates a new Entity.
     *
     * @param id the id
     */
    public Entity(int id) {
        this.id = id;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entity{");
        sb.append("id=").append(id);
        sb.append('}');
        return sb.toString();
    }
}
