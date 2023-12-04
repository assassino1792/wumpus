package org.wumpus.model;



public class Brick {

    private BrickId brickId;

    private BrickType brickType;

    public BrickId getBrickId() {
        return brickId;
    }

    public void setBrickId(BrickId brickId) {
        this.brickId = brickId;
    }

    public BrickType getBrickType() {
        return brickType;
    }

    public void setBrickType(BrickType brickType) {
        this.brickType = brickType;
    }
}
