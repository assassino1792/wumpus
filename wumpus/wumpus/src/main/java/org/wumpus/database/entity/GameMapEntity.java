package org.wumpus.database.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class GameMapEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long gameMapId;

    @Column(nullable = false)
    private int size;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<BrickEntity> bricks;

    public Long getGameMapId() {
        return gameMapId;
    }

    public void setGameMapId(Long gameMapId) {
        this.gameMapId = gameMapId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<BrickEntity> getBricks() {
        return bricks;
    }

    public void setBricks(List<BrickEntity> bricks) {
        this.bricks = bricks;
    }
}
