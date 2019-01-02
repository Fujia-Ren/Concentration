import java.awt.*;
//abstract window toolkit. Contains a lot of tools to make GUIs
import javax.swing.*;
/* swing is made on top of AWT, and is a lot more convenient, with many
similarities to Swift (JLabel, Box, etc...)
Differences (from several online sources):
	- AWT is heavyweight, as it directly invokes native methods
	- AWT is platform dependent (Swing is MVC-based, like Swift). Swing
	  will look the same everywhere
	- AWT needs more memory space
	- Swing extends AWT, is more convenient, and gets rid of many 
	  inconveniences of AWT. */
import java.awt.event.*;
import javax.swing.event.*;
import java.util.Random;

public class cardApp extends JFrame {
	//Labels in the frame
	JLabel title = new JLabel(" Concentration");
	JLabel score = new JLabel(" 52");
	Box help;
	Cards[][] cardholder;
	private final String[] buttons = {"New Game", "Quit", "How To Play"};
	
	private Box mainBox = new Box(BoxLayout.X_AXIS);
	private Box cardBox = new Box(BoxLayout.Y_AXIS);
	private Box helpBox = new Box(BoxLayout.Y_AXIS);
	
	private MouseEvents mouse = new MouseEvents();
	
	private int cardNumber = 52;
	private int scoreNumber = 52;
	private int clickCount = 0;
	
	private Cards lastCard;
	
	private String lastID = "";
	
	private boolean match = false;
	private boolean firstFlip = false;
	
	//main method to show the GUI
	public static void main(String[] pars) {
		cardApp gui = new cardApp();

	}
	
	public cardApp() {
		super("cardApp");
		setFont(new Font("Dialog", Font.ITALIC, 50));
		title.setFont(new Font("Dialog", Font.ITALIC, 50));
		score.setFont(new Font("Dialog", Font.ITALIC, 30));
		
		addComp(random());;
        getContentPane().add(mainBox, BorderLayout.CENTER);
        setLocation(150,50);
        setResizable(true);
        pack();
        setVisible(true);

	}
	
	private void addComp(String[] o) {
		int idCounter = 0;
		Cards[][] cardHolder = new Cards[4][13];
		for (int i = 0; i<4; i++) {
			Box row = new Box(BoxLayout.X_AXIS);
			for (int j = 0; j<13; j++) {
				cardHolder[i][j] = new Cards(i, j, idCounter, o);
				idCounter++;
				row.add(cardHolder[i][j]);
				cardHolder[i][j].addMouseListener(mouse);
			}
			cardBox.add(row);
		}
		mainBox.add(cardBox);
		
	}
	
	
	private String[] random() {
		String[] posCards = new String[52];
		String[] suits = {"C", "D", "H", "S"};
		int counter = 0;
		for (int i = 1; i<= 13; i++) {
			for (int j= 0; j<suits.length; j++) {
				posCards[counter] = "" + i + suits[j];
				counter++;
			}
		}
		fisherYates(posCards);
		return posCards;
		/*Fisher-Yates Shuffle Algorithm, or Knuth Shuffle Algorithm
		 * This particular implementation is an upwards shuffle.
		 * 1) make a for-loop for the indice i from 0 to length-2
		 * 2) Use a random number function to get a number from i to
		 * length, non inclusive.
		 * 3) swap the contents of the indices.  
		 * 
		 * Note: When I was implementing this, I noticed a very intuitive
		 * solution. The idea is in step 2, which I mentioned.
		 * Instead of making j from i to the end, we would make it from 0
		 * to the end. But this is actually wrong, and one of the
		 * very problems Fisher-Yates was implemented to solve.
		 * 
		 * This lies in the fundamental definition of random. 
		 * Because of the possibility of reshuffling, if all the end
		 * results are listed out, some results are simply more likely than others.
		 * 
		 * By implementing step two, which seems unintuitive, we actually ensure all 
		 * outcomes are equally likely. 
		 */
	}
	private void fisherYates(String[] arr) {
		Random rand = new Random();
		for (int i = 0; i<arr.length-2; i++) {
			int j = rand.nextInt(arr.length-i) + i;
			String temp = arr[i];
			arr[i] = arr[j];
			arr[j] = temp;
				
		}
	}
	private void firstClick(Cards c) {
		//enableBoard(true);
		lastID = c.order[c.cardID];
		lastCard = c;
		c.turned = true;
		c.flip();
		c.turnOne = true;
		c.revalidate();
		clickCount = 1;
	}
	
	private void secondClick(Cards d) {
		lastCard.turned = true;
		lastCard.flip();
		d.turned = true;
		d.flip();
		if (d.checkNumber(d.order[d.cardID], lastID)) {
			lastCard.grayFlip();
			d.grayFlip();
			d.setEnabled(false);
			lastCard.setEnabled(false);
		}
		else {
			lastCard.turned = false;
			d.turned = false;
			d.flip();
			lastCard.flip();
		}
		clickCount = 0;
	}
	
	/*private void enableBoard(boolean e) {
		for (int i = 0; i<4; i++) {
			for (int j = 0; j<13; j++) {
				cardholder[i][j].setEnabled(e);
			}
		}
	}*/
	
	class MouseEvents extends MouseInputAdapter {
		
		public void mouseClicked(MouseEvent e) {
			addMouseListener(this);
			Object obj = e.getSource();
			if (obj instanceof Cards && clickCount == 0) {
				firstClick((Cards)obj);
				return;
			}
			if (obj instanceof Cards && clickCount == 1) {
				secondClick((Cards)obj);
				return;
			}
		}
	}
	}

