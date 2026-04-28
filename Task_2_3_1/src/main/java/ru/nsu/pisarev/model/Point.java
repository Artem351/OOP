package ru.nsu.pisarev.model;

public record Point(int x, int y) {

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point p)) {
            return false;
        }
        return x == p.x && y == p.y;
    }

}
