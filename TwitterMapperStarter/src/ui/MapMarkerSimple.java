package ui;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.MapMarkerCircle;

import java.awt.*;
import observer.Observable;
import observer.Observer;

public class MapMarkerSimple extends MapMarkerCircle {
    public static final double defaultMarkerSize = 5.0;
    public static final Color defaultColor = Color.red;

    private Observable observable;
    public MapMarkerSimple(Layer layer, Coordinate coord) {
        super(layer, null, coord, defaultMarkerSize, STYLE.FIXED, getDefaultStyle());
        setColor(Color.WHITE);
        setBackColor(defaultColor);
        observable = new Observable();
    }

    public void addObserver(Observer o){
        observable.subscribe(o);
    }
    protected void notifyObservers(){
        observable.notifyObservers(null);
    }
}
