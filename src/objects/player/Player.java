package objects.player;

import objects.Color;

public interface Player {

    public void setName(String name) throws PlayerException;
    public Color getColor();
    public String getName();
    public void setColor(char c) throws PlayerException;
}
