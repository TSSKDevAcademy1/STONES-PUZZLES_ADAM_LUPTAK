package swingUI;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import core.GameState;
import core.NumberTilesField;
import game.InterFace;
import game.Puzzle;

public class SwingGui extends javax.swing.JFrame implements InterFace, MouseListener {
	private NumberTilesField playingField;
	private int delay = 100;
	private Timer timer1;
	public final static int ONE_SECOND = 1000;
	private BestTimesDialog BestTimes;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingGui frame = new SwingGui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SwingGui() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		initComponents();

	}

	private void initComponents() {

		setTitle("15 PUZZLE GAME");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 299, 294);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				mntmNewMenuItem.setText("YOUR TIME: " + Puzzle.getInstance().getPlayingSeconds() + " s");
			}
		};
		timer1 = new Timer(100, taskPerformer);
		panel = new JPanel();
		panel.setBounds(0, 27, 290, 235);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));

		menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 290, 28);
		contentPane.add(menuBar);

		mnNewMenu = new JMenu("Options");
		menuBar.add(mnNewMenu);

		mntmNewGame = new JMenuItem("New Game");
		mntmNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playingField.shuffleArray();
				newGameStarted(playingField);
				timer1.stop();
			}
		});
		mnNewMenu.add(mntmNewGame);

		mntmSaveGame = new JMenuItem("Save game");
		mntmSaveGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playingField.saveGame();
			}
		});
		mnNewMenu.add(mntmSaveGame);

		mntmLoadGame = new JMenuItem("Load game");
		mntmLoadGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				newGameStarted(playingField.load());
			}
		});
		mnNewMenu.add(mntmLoadGame);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		mntmBesttimes = new JMenuItem("BestTimes");
		mntmBesttimes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BestTimes.setVisible(true);
				setVisible(false);
				dispose();

			}
		});
		mnNewMenu.add(mntmBesttimes);
		mnNewMenu.add(mntmExit);

		mnHelp = new JMenu("help");
		menuBar.add(mnHelp);

		mntmAboutGamePuzzle = new JMenuItem("About Game Puzzle");
		mntmAboutGamePuzzle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(null, "This game is made by ©Adam Luptak");
			}
		});
		mnHelp.add(mntmAboutGamePuzzle);

		mntmNewMenuItem = new JMenuItem("New menu item");
		mntmNewMenuItem.setText("");
		menuBar.add(mntmNewMenuItem);

	}

	public void mousePressed(MouseEvent e) {
		TileComponent button = (TileComponent) e.getSource();
		if (SwingUtilities.isLeftMouseButton(e)) {
			int row = button.getRow();
			int column = button.getColumn();
			int[] position = playingField.huntForPosition(0);
			int counter = 0;
			for (int r = position[0] - 1; r <= position[0] + 1; r++) {
				for (int c = position[1] - 1; c <= position[1] + 1; c++) {

					if ((r >= 0 && c >= 0 && c < playingField.getColumnCount() && r < playingField.getRowCount())) {
						if (button.getCislo() == playingField.getNumberTiles(r, c).getValue()) {

							if (position[0] - 1 == r && position[1] == c) {
								playingField.moveDown();
							} else if (position[0] == r && position[1] + 1 == c) {
								playingField.moveToLeft();
							} else if (position[0] == r && position[1] - 1 == c) {
								playingField.moveToRight();
							} else if (position[0] + 1 == r && position[1] == c) {
								playingField.moveUp();
							}
							update();
						}
					}
				}
			}

			System.out.println("stalceil si " + button.getCislo());

		} else if (SwingUtilities.isRightMouseButton(e)) {

		}

	}

	@Override
	public void update() {
		if (!timer1.isRunning()) {
			timer1.start();
			System.out.println("starujem timer");
		}

		List<TileComponent> tileSaver = new ArrayList<TileComponent>();
		for (int index = 0; index < panel.getComponentCount(); index++) {
			if (panel.getComponent(index) instanceof TileComponent) {
				tileSaver.add((TileComponent) panel.getComponent(index));
			}
		}
		int counter = 0;
		for (int row = 0; row < playingField.getRowCount(); row++) {
			for (int column = 0; column < playingField.getColumnCount(); column++) {
				int toPlayingTile = playingField.getNumberTiles(row, column).getValue();
				tileSaver.get(counter).setCislo(toPlayingTile);
				tileSaver.get(counter).setImage();
				counter++;

			}
		}

		playingField.isSolved();
		if (playingField.getGamteState() == GameState.SOLVED) {
			timer1.stop();
			Puzzle.getInstance();
			long cas = Puzzle.getPlayingSeconds();
			JOptionPane.showMessageDialog(null, "You win your time is: " + cas);
			System.out.println("Aktualny cas hraca: " + cas + "s");
			System.out.println("SI VYHRALLLLLLL");// best time

		}
	}

	@Override
	public void newGameStarted(NumberTilesField playingField) {
		JFrame fr = new JFrame();

		BestTimes = new BestTimesDialog(SwingGui.this,fr , Puzzle.getInstance());
		Puzzle.getInstance().setStartMillis(System.currentTimeMillis());
		this.playingField = playingField;
		test();
		this.setVisible(true);
	}

	private void test() {
		panel.removeAll();
		panel.setLayout(new GridLayout(4, 4));
		int counter = 1;
		System.out.println(playingField.toString());

		for (int row = 0; row < playingField.getRowCount(); row++) {
			for (int column = 0; column < playingField.getColumnCount(); column++) {
				TileComponent t = new TileComponent(playingField.getNumberTiles(row, column));
				t.addMouseListener(this);
				panel.add(t);
			}
		}

		pack();
		this.setSize(295, 290);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
	}

	// Variables for swing gui
	private javax.swing.JPanel contentPane;
	private javax.swing.JPanel panel;
	private javax.swing.JMenuBar menuBar;
	private javax.swing.JMenu mnNewMenu;
	private javax.swing.JMenuItem mntmNewGame;
	private javax.swing.JMenuItem mntmSaveGame;
	private javax.swing.JMenuItem mntmLoadGame;
	private javax.swing.JMenuItem mntmExit;
	private javax.swing.JMenu mnHelp;
	private javax.swing.JMenuItem mntmAboutGamePuzzle;
	private JMenuItem mntmNewMenuItem;
	private JMenuItem mntmBesttimes;

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
