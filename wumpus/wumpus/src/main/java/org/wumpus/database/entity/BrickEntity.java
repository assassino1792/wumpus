package org.wumpus.database.entity;



import org.wumpus.model.BrickType;
import jakarta.persistence.*;

@Entity
public class BrickEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long brickEntityId;

    @Column(nullable = false)
    private int horizontal;
    @Column(nullable = false)
    private int vertical;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private BrickType brickType;

    public Long getBrickEntityId() {
        return brickEntityId;
    }

    public void setBrickEntityId(Long brickEntityId) {
        this.brickEntityId = brickEntityId;
    }

    public int getHorizontal() {
        return horizontal;
    }

    public void setHorizontal(int horizontal) {
        this.horizontal = horizontal;
    }

    public int getVertical() {
        return vertical;
    }

    public void setVertical(int vertical) {
        this.vertical = vertical;
    }

    public BrickType getBrickType() {
        return brickType;
    }

    public void setBrickType(BrickType brickType) {
        this.brickType = brickType;
    }
}

