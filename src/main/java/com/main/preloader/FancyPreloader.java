/*
 *  Copyright © 2016 Litvinau Aleksei (ByAxe).
 *  Licensed under the Apache License, Version 2.0
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.main.preloader;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javafx.application.Preloader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple Preloader Using the ProgressBar Control
 *
 * @author msi
 */
public class FancyPreloader extends Preloader {

    Stage stage;
    GUI gui;

    private Scene createPreloaderScene() {
        gui = new GUI();
        try {
            gui.buildComponents();
        } catch (IOException ex) {
            Logger.getLogger(FancyPreloader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return gui.getScene();
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;

        stage.setScene(createPreloaderScene());
        stage.show();
        gui.animate();
        gui.explodeAnimate();
    }


    @Override
    public void handleStateChangeNotification(StateChangeNotification scn) {
        if (scn.getType() == StateChangeNotification.Type.BEFORE_START) {
            stage.hide();
        }


    }

    @Override
    public void handleProgressNotification(ProgressNotification pn) {
        gui.update(pn.getProgress());

    }
}
