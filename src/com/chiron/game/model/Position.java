package com.chiron.game.model;

public final class Position {

	private int x;
	
	private int y;
	
	private int plane;

	public Position(int x, int y) {
		this(x, y, 0);
	}
	
	public Position(int x, int y, int plane) {
		this.x = x;
		this.y = y;
		this.plane = plane;
	}
	
	public boolean isViewable(Position position) {
		if (plane != position.getPlane()) {
			return false;
		}
        int deltaX = position.getX() - x;
        int deltaY = position.getY() - y;
        return Math.abs(deltaX) <= 15 && Math.abs(deltaY) <= 15;
	}
	
    public Position move(int amountX, int amountY) {
        return move(amountX, amountY, plane);
    }
    
    public Position move(int amountX, int amountY, int amountPlane) {
        return new Position(x + amountX, y + amountY, plane + amountPlane);
    }
	
	public void set(int x, int y) {
		set(x, y, plane);
	}
	
	public void set(int x, int y, int plane) {
		this.x = x;
		this.y = y;
		this.plane = plane;
	}
	
    public int getRegionX() {
        return (x >> 3) - 6;
    }

    public int getRegionY() {
        return (y >> 3) - 6;
    }

    public int getLocalX(Position base) {
        return x - (base.getRegionX() << 3);
    }

    public int getLocalY(Position base) {
        return y - (base.getRegionY() << 3);
    }

    public int getLocalX() {
        return getLocalX(this);
    }

    public int getLocalY() {
        return getLocalY(this);
    }

    public int getChunkX() {
        return (x >> 6);
    }

    public int getChunkY() {
        return (y >> 6);
    }

    public int getRegionId() {
        return ((getChunkX() << 8) + getChunkY());
    }
    
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getPlane() {
		return plane;
	}

	public void setPlane(int plane) {
		this.plane = plane;
	}
	
}
