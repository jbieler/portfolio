package de.desjardins.ol3.demo.client.example;

import com.google.gwt.user.client.Window;

import ol.Coordinate;
import ol.Map;
import ol.MapOptions;
import ol.OLFactory;
import ol.OLUtil;
import ol.View;
import ol.control.Attribution;
import ol.control.Rotate;
import ol.control.ScaleLine;

import de.desjardins.ol3.demo.client.utils.DemoUtils;
import ol.event.EventListener;
import ol.interaction.DragAndDrop;
import ol.interaction.DragAndDropEvent;
import ol.interaction.KeyboardPan;
import ol.interaction.KeyboardZoom;
import ol.layer.Tile;
import ol.source.MapQuest;
import ol.source.MapQuestOptions;
import ol.source.Stamen;
import ol.source.StamenOptions;
import ol.layer.LayerOptions;

/**
 * Example with Tile-layers.
 *
 * @author Tino Desjardins
 *
 */
public class TileExample implements Example {

    /* (non-Javadoc)
     * @see de.desjardins.ol3.demo.client.example.Example#show()
     */
    @Override
    public void show() {

        // create a MapQuest-layer
        LayerOptions mapQuestLayerOptions = OLFactory.createOptions();

        MapQuestOptions mapQuestOptions = OLFactory.createOptions();
        mapQuestOptions.setLayer("hyb");

        MapQuest mapQuestSource = new MapQuest(mapQuestOptions);
        mapQuestLayerOptions.setSource(mapQuestSource);
        Tile mapQuestLayer = new Tile(mapQuestLayerOptions);

        LayerOptions stamenLayerOptions = OLFactory.createOptions();

        // create a Stamen-layer
        StamenOptions stamenOptions = OLFactory.createOptions();
        stamenOptions.setLayer("watercolor");

        Stamen stamenSource = new Stamen(stamenOptions);
        stamenLayerOptions.setSource(stamenSource);
        Tile stamenLayer = new Tile(stamenLayerOptions);

        // create a view
        View view = new View();

        Coordinate centerCoordinate = OLFactory.createCoordinate(1490463, 6894388);

        view.setCenter(centerCoordinate);
        view.setZoom(10);

        // create the map
        MapOptions mapOptions = OLFactory.createOptions();
        mapOptions.setTarget("map");
        mapOptions.setView(view);

        Map map = new Map(mapOptions);

        stamenLayer.setOpacity(0.5f);
        map.addLayer(mapQuestLayer);

        // add some controls
        map.addControl(new ScaleLine());
        DemoUtils.addDefaultControls(map.getControls());

        Attribution attribution = new Attribution();
        attribution.setCollapsed(true);

        map.addControl(attribution);

        // add some interactions
        map.addInteraction(new KeyboardPan());
        map.addInteraction(new KeyboardZoom());

        DragAndDrop dragAndDrop = new DragAndDrop();
        map.addInteraction(dragAndDrop);

        EventListener<DragAndDropEvent> eventListener = new EventListener<DragAndDropEvent>() {

            @Override
            public void onEvent(DragAndDropEvent event) {
                Window.alert(String.valueOf(event.getFeatures().length));
                Window.alert(event.getProjection().getUnits());
                Window.alert(String.valueOf(event.getProjection().getMetersPerUnit()));

            }
        };

        OLUtil.observe(dragAndDrop, "addfeatures", eventListener);

        map.addControl(new Rotate());

        map.getLayers().push(stamenLayer);

    }

}
