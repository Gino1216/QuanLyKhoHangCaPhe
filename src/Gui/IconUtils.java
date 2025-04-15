/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gui;

/**
 *
 * @author hjepr
 */
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class IconUtils {

    // Method to load SVG from file path and return as an ImageIcon
    public static ImageIcon loadSVGIcon(String filePath, int width, int height) {
            // Create a FlatSVGIcon with the file path
            FlatSVGIcon svgIcon = new FlatSVGIcon(filePath);

            // Resize the icon if needed
            Image image = svgIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);

            // Return the ImageIcon
            return new ImageIcon(image);
        }
    }
