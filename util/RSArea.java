package scripts.miscrangeguild.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Rectangle2D;

import org.tribot.api.General;
import org.tribot.api2007.Projection;
import org.tribot.api2007.types.RSTile;

public class RSArea 
{
    Polygon p; //Polygon for the area
    //Creates a polygon from an array of tiles
    public RSArea(RSTile[] tiles)
    {
        int[] xTiles = new int[tiles.length];
        int[] yTiles = new int[tiles.length];
        for(int i = 0; i < tiles.length; i++)
        {
            xTiles[i] = tiles[i].getX();
            yTiles[i] = tiles[i].getY();
        }
        p = new Polygon(xTiles, yTiles, xTiles.length);
    }
    //Creates a rectangular area from 2 tiles
    public RSArea(RSTile min, RSTile max)
    {
        p = new Polygon(new int[]{min.getX(), min.getX(), max.getX(), max.getX()}, new int[]{min.getY(), max.getY(), max.getY(), min.getY()}, 4);
    }
    //Creates an area with a center point and radius
    public RSArea(RSTile center, int rad)
    {
        p = new Polygon(new int[]{center.getX() - rad, center.getX() - rad, center.getX() + rad, center.getX() + rad}, new int[]{center.getY() - rad, center.getY() + rad, center.getY() + rad, center.getY() - rad}, 4);
    }
    //Checkes if the polygon contains the tile
    public boolean contains(RSTile tile)
    {
        return p.contains(tile.getX(), tile.getY());
    }
    //Gets the tile at the coords
    public RSTile getTile(int x, int y)
    {
        return p.contains(x, y) ? new RSTile(x, y) : null;
    }
    //Draws the area
    public void drawArea(Graphics g)
    {
        Rectangle2D b = p.getBounds2D();
        for(int x = (int)b.getMinX(); x <= (int)b.getMaxX(); x++)
        {
            for (int y = (int)b.getMinX(); y <= (int)b.getMaxY(); y++)
            {
                if (p.contains(x, y))
                    drawTile(new RSTile(x, y), (Graphics2D)g, false);
            }
        }
    }
    //Draws the tile
    private void drawTile(RSTile tile, Graphics2D g, boolean fill)
    {
        if (tile.isOnScreen())
        {
            if (fill)
                g.fillPolygon(Projection.getTileBoundsPoly(tile, 0));
            else
                g.drawPolygon(Projection.getTileBoundsPoly(tile, 0));
        }
    }
    //Gets a random tile from the area
    public RSTile getRandomTile()
    {
        Rectangle2D bounds = p.getBounds2D();
        RSTile tile = new RSTile(General.random((int)bounds.getMinX(), (int)bounds.getMaxX()), General.random((int)bounds.getMinY(), (int)bounds.getMaxY()));
        return p.contains(tile.getX(), tile.getY()) ? tile : getRandomTile();
    }
}