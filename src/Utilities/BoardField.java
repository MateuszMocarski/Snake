package Utilities;

public class BoardField {

    private int BoardFieldX;
    private int BoardFieldY;

    public BoardField(int BoardFieldX, int BoardFieldY) {
        this.BoardFieldX = BoardFieldX;
        this.BoardFieldY = BoardFieldY;
    }

    public int getBoardFieldX() {
        return BoardFieldX;
    }

    public void setBoardFieldX(int BoardFieldX) {
        this.BoardFieldX = BoardFieldX;
    }

    public int getBoardFieldY() {
        return BoardFieldY;
    }

    public void setBoardFieldY(int BoardFieldY) {
        this.BoardFieldY = BoardFieldY;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BoardField other = (BoardField) obj;
        if (this.BoardFieldX != other.BoardFieldX) {
            return false;
        }
        return this.BoardFieldY == other.BoardFieldY;
    }

}
