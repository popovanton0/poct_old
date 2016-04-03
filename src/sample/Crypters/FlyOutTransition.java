package sample.Crypters;

import javafx.animation.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.CacheHint;
import javafx.scene.Node;
import javafx.util.Duration;

/**
 * Animate a shake effect on the given node
 *
 * Based on CachedTimelineTransition, a Transition that uses a Timeline internally
 * and turns SPEED caching on for the animated node during the animation.
 *
 * https://github.com/fxexperience/code/blob/master/FXExperienceControls/src/com/fxexperience/javafx/animation/CachedTimelineTransition.java
 *
 * and ShakeTransition
 *
 * https://github.com/fxexperience/code/blob/master/FXExperienceControls/src/com/fxexperience/javafx/animation/ShakeTransition.java
 *
 * @author Jasper Potts
 */
class FlyOutTransition extends Transition {

    private final Interpolator WEB_EASE = Interpolator.SPLINE(0.25, 0.1, 0.25, 1);
    private final Timeline timeline;
    private final Node node;
    private boolean oldCache = false;
    private CacheHint oldCacheHint = CacheHint.DEFAULT;
    private final boolean useCache=true;
    private final double xIni;
    private final double yIni;

    private final DoubleProperty x = new SimpleDoubleProperty();
    private final DoubleProperty y = new SimpleDoubleProperty();

    /**
     * Create new ShakeTransition
     *
     * @param node The node to affect
     */
    public FlyOutTransition(final Node node, EventHandler<ActionEvent> event) {
        this.node=node;
        statusProperty().addListener((ov, t, newStatus) -> {
            switch(newStatus) {
                case RUNNING:
                    starting();
                    break;
                default:
                    stopping();
                    break;
            }
        });

        this.timeline= new Timeline(
                new KeyFrame(Duration.millis(0), new KeyValue(y, 0, WEB_EASE)),
                new KeyFrame(Duration.millis(700), new KeyValue(y, -600, WEB_EASE))
        );
        xIni=node.getScene().getWindow().getX();
        yIni=node.getScene().getWindow().getY();
        x.addListener((ob,n,n1)->(node.getScene().getWindow()).setX(xIni+n1.doubleValue()));
        y.addListener((ob,n,n1)->(node.getScene().getWindow()).setY(yIni+n1.doubleValue()));

        setCycleDuration(Duration.seconds(1));
        setDelay(Duration.seconds(0.2));
        setOnFinished(event);
    }

    /**
     * Called when the animation is starting
     */
    protected final void starting() {
        if (useCache) {
            oldCache = node.isCache();
            oldCacheHint = node.getCacheHint();
            node.setCache(true);
            node.setCacheHint(CacheHint.SPEED);
        }
    }

    /**
     * Called when the animation is stopping
     */
    protected final void stopping() {
        if (useCache) {
            node.setCache(oldCache);
            node.setCacheHint(oldCacheHint);
        }
    }

    @Override
    protected void interpolate(double d) {
        timeline.playFrom(Duration.seconds(d));
        timeline.stop();
    }
}