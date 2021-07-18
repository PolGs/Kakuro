package Presentacio.KakuroViews.Visualize;

import Domini.Casella;
import Domini.CasellaBlanca;



import javax.swing.*;
import java.awt.*;


public class KakuroCellWhiteVisualizer extends JPanel {
    public int size = 100;


    public KakuroCellWhiteVisualizer() {

            //set preferred size : 100x100
            setPreferredSize(new Dimension(size, size));
            setOpaque(true);

                setBackground(Color.WHITE);
                Label valorLabel = new Label("");
                add(valorLabel);
                setFont(new Font("Arial", Font.PLAIN, 18));
                //fons del button blanc no border layout per que ompli tot el panel
                setBorder(BorderFactory.createEmptyBorder());
                //setLayout(new BorderLayout());
            //Key listen studff
            //Focusable(listen to keys)
        }
    }


