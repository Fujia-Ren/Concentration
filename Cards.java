import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.*;
//images for cards from http://acbl.mybigcommerce.com/52-playing-cards/
public class Cards extends JPanel {
	int row;
	int col;
	int cardID;
	String[] order;
	boolean turned = false;
	boolean turnOne = false;
	
	BufferedImage bPicture; //= ImageIO.read(new File("/images/blue_back.png"));
	
	public Cards(int r, int c, int ID, String[] o) {
		super();
		//TODO Finish up why it needs a try catch
        try {
            bPicture = ImageIO.read(new File(getClass().getResource("/images/blue_back.png").toURI()));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
		row = r;
		col = c;
		cardID = ID;
		order = o;
				
		Dimension d = new Dimension(225, 600);
		setPreferredSize(d);
      
		
	}
    public void paintComponent(Graphics g) {
		Image scaled = bPicture.getScaledInstance(this.getWidth(), this.getHeight(),
		        Image.SCALE_SMOOTH);
        g.drawImage(scaled, 0, 0, this);
    }
    public void flip() {
    	if (turned) {
    		turnOne = true;
    		try {
                bPicture = ImageIO.read(new File(getClass().getResource("/images/" + "" + order[cardID]+ ".png").toURI()));
                revalidate();
                repaint();
    		} catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
    	}
    	else {
            try {
                bPicture = ImageIO.read(new File(getClass().getResource("/images/blue_back.png").toURI()));
                revalidate();
                repaint();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
    		return;
    	}
    }
    public boolean checkNumber(String a, String b) {
    	String num1;
    	String num2;
    	int index1=0;
    	int index2=0;
    	for (int i = 0; i<a.length(); i++) {
    		if (Character.isLetter(a.charAt(i))){
    			index1 = i;
    			break;
    		}
    	}
    	for (int j = 0; j<b.length(); j++) {
    		if (Character.isLetter(b.charAt(j))) {
    			index2 = j;
    			break;
    		}
    	}
    	num1 = a.substring(0, index1);
    	num2 = b.substring(0, index2);
    	
    	return num1.equals(num2);
    }
    public void grayFlip () {
		try {
            bPicture = ImageIO.read(new File(getClass().getResource("/images/gray_back.png").toURI()));
            revalidate();
            repaint();
		} catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }
    public void endTurnFlip(String a, String b) {
    	if (checkNumber(a, b)) {
            try {
                bPicture = ImageIO.read(new File(getClass().getResource("/images/gray_back.png").toURI()));
                revalidate();
                repaint();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
    	}
    	else {
            try {
                bPicture = ImageIO.read(new File(getClass().getResource("/images/blue_back.png").toURI()));
                revalidate();
                repaint();
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
    	}
    }
}
