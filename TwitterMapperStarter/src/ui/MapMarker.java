package ui;

import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.Layer;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;
import twitter4j.Status;
import util.SphericalGeometry;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Observable;

public class MapMarker extends MapMarkerSimple {

    private Status status;
    private boolean viewInfo;
    private Image image;
    public MapMarker(Color color, Status status, Layer layer, Coordinate coord) throws IOException {
        super(layer, coord);
        this.setBackColor(color);
        this.status = status;
        image = ImageIO.read(new URL(status.getUser().getBiggerProfileImageURL()));
    }

    public void showInfo(ICoordinate coord, JMapViewer map){
        double distance = SphericalGeometry.distanceBetween(coord, this.getCoordinate());
        if(distance * 0.00005 < this.getRadius()){
            if(!viewInfo) this.notifyObservers();
            viewInfo = true;
            map.setToolTipText(status.getText());
        }
        else{
            if(viewInfo) notifyObservers();
            viewInfo = false;
        }
    }
    @Override
    public void paint(Graphics g, Point position, int radio) {
        if(viewInfo) {
            int width = (this.image.getWidth(null));
            int height = (this.image.getHeight(null));
            int w2 = width / 2;
            int h2 = height / 2;
            g.drawImage(this.image, position.x - w2, position.y - h2, width, height, null);
            this.paintText(g, position);
        }
        else{
            super.paint(g, position, radio);
        }

    }

}
