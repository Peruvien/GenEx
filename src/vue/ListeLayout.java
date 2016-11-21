/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vue;

import java.awt.*;

/**
 *
 * @author Robin
 */
public class ListeLayout implements LayoutManager {
    
    //ATTRIBUTS
    private int vgap;
    private int minWidth, minHeight;
    private int preferredWidth, preferredHeight;
    private boolean sizeUnknown = true;
    
    //CONSTRUCTEURS
    public ListeLayout() {
        vgap = 5;
    }
    public ListeLayout(Dimension size) {
        vgap = 5;
        sizeUnknown = false;
        preferredWidth = size.width;
        preferredHeight = size.height;
    }
    
    
    //MUTATEURS
    private void setSizes(Container parent) {
        int nComps = parent.getComponentCount();
        Dimension d = null;
        
        //Reset preferred/minimum width and height.
        preferredWidth = 0;
        preferredHeight = 0;
        minWidth = 0;
        minHeight = 0;
        
        for (int i = 0; i < nComps; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                d = c.getPreferredSize();
                
                if (i > 0) {
                    //preferredWidth += d.width/2;
                    preferredHeight += vgap;
                } else {
                    preferredWidth = d.width;
                }
                preferredHeight += d.height;
                
                minWidth = Math.max(c.getMinimumSize().width,
                                    minWidth);
                minHeight = preferredHeight;
            }
        }
    }

    
    @Override
    public void addLayoutComponent(String name, Component comp) {
    }

    @Override
    public void removeLayoutComponent(Component comp) {
    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        int nComps = parent.getComponentCount();
        
        setSizes(parent);
        
        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = preferredWidth
                    + insets.left + insets.right;
        dim.height = preferredHeight
                     + insets.top + insets.bottom;
        
        sizeUnknown = false;
        
        return dim;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        Dimension dim = new Dimension(0, 0);
        int nComps = parent.getComponentCount();
        
        //Always add the container's insets!
        Insets insets = parent.getInsets();
        dim.width = minWidth
                    + insets.left + insets.right;
        dim.height = minHeight
                     + insets.top + insets.bottom;
        
        sizeUnknown = false;
        
        return dim;
    }
    
    @Override
    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();
        int maxWidth = parent.getWidth()
                       - (insets.left + insets.right);
        int maxHeight = parent.getHeight()
                        - (insets.top + insets.bottom);
        int nComps = parent.getComponentCount();
        int previousWidth = 0, previousHeight = 0;
        int x = 0, y = insets.top;
        int rowh = 0, start = 0;
        int xFudge = 0, yFudge = 0;
        boolean oneColumn = true;
        
        // Go through the components' sizes, if neither
        // preferredLayoutSize nor minimumLayoutSize has
        // been called.
        if (sizeUnknown) {
            setSizes(parent);
        }
        
        if (maxWidth != preferredWidth && nComps > 1) {
            xFudge = (maxWidth - preferredWidth)/(nComps - 1);
        }
        
        if (maxHeight > preferredHeight && nComps > 1) {
            yFudge = 0;//(maxHeight - preferredHeight)/(nComps - 1);
        }
        
        for (int i = 0 ; i < nComps ; i++) {
            Component c = parent.getComponent(i);
            if (c.isVisible()) {
                Dimension d = c.getPreferredSize();
                
                 // increase x and y, if appropriate
                if (i > 0) {
                    if (!oneColumn) {
                        x += previousWidth/2 + xFudge;
                    }
                    y += previousHeight + vgap + yFudge;
                }
                
                // If x is too large,
                if ((!oneColumn) &&
                    (x + d.width) >
                    (parent.getWidth() - insets.right)) {
                    // reduce x to a reasonable number.
                    x = parent.getWidth()
                        - insets.bottom - d.width;
                }
                
                // If y is too large,
                if ((y + d.height)
                    > (parent.getHeight() - insets.bottom)) {
                    // do nothing.
                    // Another choice would be to do what we do to x.
                }
                
                // Set the component's size and position.
                c.setBounds(x, y, maxWidth, d.height);
                
                previousWidth = d.width;
                previousHeight = d.height;
            }
        }
    }
    
}
